package com.example.plantmarket.product;

/** Product 모듈이 외부에 공개하는 동기 API. */
public interface InventoryUseCase {
    ReservationResult reserve(Long orderId, Long plantItemId);
}
