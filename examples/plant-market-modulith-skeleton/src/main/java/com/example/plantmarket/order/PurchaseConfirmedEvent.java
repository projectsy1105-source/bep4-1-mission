package com.example.plantmarket.order;

/** Order 모듈이 외부에 공개하는 '이미 발생한 사실'. */
public record PurchaseConfirmedEvent(Long orderId, Long sellerId, long amount) {
}
