package com.example.plantmarket.product;

public record ReservationResult(boolean success, Long plantItemId, String reason) {
    public static ReservationResult success(Long plantItemId) {
        return new ReservationResult(true, plantItemId, null);
    }

    public static ReservationResult fail(Long plantItemId, String reason) {
        return new ReservationResult(false, plantItemId, reason);
    }
}
