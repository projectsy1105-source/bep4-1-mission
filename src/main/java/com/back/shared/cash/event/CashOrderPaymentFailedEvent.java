package com.back.shared.cash.event;

import com.back.shared.market.dto.OrderDto;
import com.back.standard.resultType.ResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class CashOrderPaymentFailedEvent implements ResultType {
    private final String resultCode;
    private final String msg;
    private final OrderDto order;
    private final BigDecimal pgPaymentAmount;
    private final BigDecimal shortfallAmount;
}
