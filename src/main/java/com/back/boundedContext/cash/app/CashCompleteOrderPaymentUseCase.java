package com.back.boundedContext.cash.app;

import com.back.boundedContext.cash.domain.CashLog;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.cash.event.CashOrderPaymentFailedEvent;
import com.back.shared.cash.event.CashOrderPaymentSucceededEvent;
import com.back.shared.market.event.MarketOrderPaymentRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CashCompleteOrderPaymentUseCase {

    private final CashSupport cashSupport;
    private final EventPublisher eventPublisher;

    public void handle(MarketOrderPaymentRequestedEvent event) {

        Wallet customerWallet = cashSupport.findWalletByHolderId(event.getOrderDto().getCustomerId()).get();
        Wallet holdingWallet = cashSupport.findHoldingWallet().get();

        if (event.getPgPaymentAmount().compareTo(BigDecimal.ZERO) > 0) {
            customerWallet.credit(
                    event.getPgPaymentAmount(),
                    CashLog.EventType.충전__PG결제_토스페이먼츠,
                    "Order",
                    event.getOrderDto().getId());
        }

        boolean canPay = customerWallet.getBalance().compareTo(event.getOrderDto().getSalePrice()) >= 0;

        if (canPay) {
            customerWallet.debit(
                    event.getOrderDto().getSalePrice(),
                    CashLog.EventType.사용__주문결제,
                    "Order",
                    event.getOrderDto().getId()
            );

            holdingWallet.credit(
                    event.getOrderDto().getSalePrice(),
                    CashLog.EventType.임시보관__주문결제,
                    "Order",
                    event.getOrderDto().getId()
            );

            eventPublisher.publisher(
                    new CashOrderPaymentSucceededEvent(
                            event.getOrderDto(),
                            event.getPgPaymentAmount()
                    )
            );
        } else {
            eventPublisher.publisher(
                    new CashOrderPaymentFailedEvent(
                            "400-1",
                            "충전은 완료했지만 %d번 주문을 결제완료처리를 하기에는 예치금이 부족합니다.".formatted(event.getOrderDto().getId()),
                            event.getOrderDto(),
                            event.getPgPaymentAmount(),
                            event.getOrderDto().getSalePrice().subtract(customerWallet.getBalance())
                    )
            );
        }
    }

}
