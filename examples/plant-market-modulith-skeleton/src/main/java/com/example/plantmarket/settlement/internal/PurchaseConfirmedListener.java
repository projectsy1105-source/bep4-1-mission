package com.example.plantmarket.settlement.internal;

import com.example.plantmarket.order.PurchaseConfirmedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class PurchaseConfirmedListener {
    private static final Logger log = LoggerFactory.getLogger(PurchaseConfirmedListener.class);

    @EventListener
    void on(PurchaseConfirmedEvent event) {
        log.info("create settlement target. orderId={}, sellerId={}, amount={}",
                event.orderId(), event.sellerId(), event.amount());
    }
}
