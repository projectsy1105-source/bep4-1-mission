package com.example.plantmarket.order.internal;

import com.example.plantmarket.order.PurchaseConfirmedEvent;
import com.example.plantmarket.product.InventoryUseCase;
import com.example.plantmarket.product.ReservationResult;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
class CheckoutService {
    private final InventoryUseCase inventoryUseCase;
    private final ApplicationEventPublisher events;

    CheckoutService(InventoryUseCase inventoryUseCase, ApplicationEventPublisher events) {
        this.inventoryUseCase = inventoryUseCase;
        this.events = events;
    }

    /** 즉시 성공/실패가 필요하므로 Product 공개 API를 동기 호출한다. */
    ReservationResult checkout(Long orderId, Long plantItemId) {
        return inventoryUseCase.reserve(orderId, plantItemId);
    }

    /** 구매확정은 이미 일어난 사실이므로 Settlement를 직접 호출하지 않고 이벤트만 발행한다. */
    void confirmPurchase(Long orderId, Long sellerId, long amount) {
        events.publishEvent(new PurchaseConfirmedEvent(orderId, sellerId, amount));
    }
}
