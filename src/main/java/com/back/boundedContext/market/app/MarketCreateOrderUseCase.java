package com.back.boundedContext.market.app;

import com.back.boundedContext.market.domain.Cart;
import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.domain.Order;
import com.back.boundedContext.market.out.OrderRepository;
import com.back.global.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketCreateOrderUseCase {

    private final OrderRepository orderRepository;

    public RsData<Order> createOrder(Cart cart) {
        Order order = new Order(cart);
        orderRepository.save(order);

        cart.clearItems();

        return new RsData<>("201-1", "%d번 주문이 생성되었습니다.".formatted(order.getId()), order);
    }

}
