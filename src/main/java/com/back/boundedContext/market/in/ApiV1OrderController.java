package com.back.boundedContext.market.in;

import com.back.boundedContext.market.app.MarketFacade;
import com.back.boundedContext.market.domain.Order;
import com.back.global.exception.DomainException;
import com.back.global.global.RsData.RsData;
import com.back.shared.cash.out.CashApiClient;
import com.back.shared.market.out.TossPaymentsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/market/orders")
@RequiredArgsConstructor
public class ApiV1OrderController {

    private final CashApiClient cashApiClient;
    private final MarketFacade marketFacade;
    private final TossPaymentsService tossPaymentsService;

    public record ConfirmPaymentByTossPaymentsRequestBody (@NotBlank String paymentKey, @NotBlank String orderId, @NotNull BigDecimal amount) {}
    public record PayableOrderResponse(int id, String buyerName, BigDecimal salePrice) {}

    @GetMapping("/payable")
    public RsData<List<PayableOrderResponse>> getPayableOrders() {
        List<PayableOrderResponse> orders = marketFacade.findAllReadyForPayment().stream()
                .map(order -> new PayableOrderResponse(
                        order.getId(),
                        order.getBuyer().getNickname(),
                        order.getSalePrice()
                ))
                .toList();

        return new RsData<>("200-1", "결제 가능한 주문 목록 조회 성공", orders);
    }

    @Transactional
    @CrossOrigin(
            origins = {
                    "https://cdpn.io",
                    "https://codepen.io"
            },
            allowedHeaders = "*",
            methods = {RequestMethod.POST}
    )
    @PostMapping("/{id}/payment/confirm/by/tossPayments")
    public RsData<Void> confirmPaymentByTossPayment(@PathVariable int id,
                                                     @Valid @RequestBody ConfirmPaymentByTossPaymentsRequestBody reqBody) {
        Order order = marketFacade.findOrderById(id).get();

        if (order.isCancelled()) {
            throw new DomainException("400-1", "이미 취소된 주문입니다.");
        }

        if (order.isPaymentInProgress()) {
            throw new DomainException("400-2", "이미 결제 진행중인 주문입니다.");
        }

        if (order.isPaid()) {
            throw new DomainException("400-3", "이미 결제된 주문입니다.");
        }

        BigDecimal walletBalance = cashApiClient.getBalanceByHolderId(order.getBuyer().getId());

        if (order.getSalePrice().compareTo(walletBalance.add(reqBody.amount)) > 0) {
            throw new DomainException("400-4", "결제를 완료하기에 결제 금액이 부족합니다.");
        }

        if (order.getId() != Integer.parseInt(reqBody.orderId.split("-", 3)[1])) {
            throw new DomainException("400-5", "주문번호가 일치하지 않습니다");
        }
        //결제 컨펌모듈이라 결제 완료인데 왜 결제 프로세스가 시작?
        //결제 완료 바디 값은 언제처리?
        tossPaymentsService.confirmCardPayment(reqBody.paymentKey, reqBody.orderId, reqBody.amount);
        marketFacade.requestPayment(order, reqBody.amount);

        return new RsData<>("202-1", "결제 프로세스가 시작되었습니다.");
    }

}
