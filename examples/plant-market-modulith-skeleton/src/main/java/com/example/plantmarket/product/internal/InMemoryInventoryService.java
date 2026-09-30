package com.example.plantmarket.product.internal;

import com.example.plantmarket.product.InventoryUseCase;
import com.example.plantmarket.product.ReservationResult;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
class InMemoryInventoryService implements InventoryUseCase {
    private final Set<Long> reservedItems = ConcurrentHashMap.newKeySet();

    @Override
    public ReservationResult reserve(Long orderId, Long plantItemId) {
        boolean reserved = reservedItems.add(plantItemId);
        return reserved
                ? ReservationResult.success(plantItemId)
                : ReservationResult.fail(plantItemId, "ALREADY_RESERVED");
    }
}
