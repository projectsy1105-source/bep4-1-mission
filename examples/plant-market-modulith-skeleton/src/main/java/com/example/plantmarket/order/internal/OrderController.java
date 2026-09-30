package com.example.plantmarket.order.internal;

import com.example.plantmarket.product.ReservationResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
class OrderController {
    private final CheckoutService checkoutService;

    OrderController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/{orderId}/checkout")
    ReservationResult checkout(@PathVariable Long orderId, @RequestParam Long plantItemId) {
        return checkoutService.checkout(orderId, plantItemId);
    }

    @PostMapping("/{orderId}/confirm")
    void confirm(@PathVariable Long orderId, @RequestParam Long sellerId, @RequestParam long amount) {
        checkoutService.confirmPurchase(orderId, sellerId, amount);
    }
}
