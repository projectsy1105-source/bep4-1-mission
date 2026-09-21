package com.back.shared.cash.event;

import com.back.shared.market.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class CashOrderPaymentFailedEvent {
    private final String resultCode;
    private final String msg;
    private final OrderDto order;
    private final BigDecimal pgPaymentAmount;
    private final BigDecimal shortfallAmount;
}
