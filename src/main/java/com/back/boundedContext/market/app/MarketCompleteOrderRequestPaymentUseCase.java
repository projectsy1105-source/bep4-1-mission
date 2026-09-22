package com.back.boundedContext.market.app;

import com.back.boundedContext.market.domain.Order;
import com.back.boundedContext.market.out.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketCompleteOrderRequestPaymentUseCase {
    private final OrderRepository orderRepository;

    public void handle(int id) {
        Order order = orderRepository.findById(id).get();
        order.completePayment();
    }
}
