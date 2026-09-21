package com.back.shared.market.dto;

import com.back.boundedContext.market.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;

@AllArgsConstructor
@Getter
public class OrderDto {

    private final int id;
    private final LocalDateTime creationDate;
    private final LocalDateTime modifyDate;
    private final int customerId;
    private final String customerName;
    private final BigDecimal price;
    private final BigDecimal salePrice;
    private final LocalDateTime requestPaymentDate;
    private final LocalDateTime paymentDate;

    public OrderDto(Order order) {
        this(
                order.getId(),
                order.getCreateDate(),
                order.getModifyDate(),
                order.getBuyer().getId(),
                order.getBuyer().getNickname(),
                order.getPrice(),
                order.getSalePrice(),
                order.getRequestPaymentDate(),
                order.getPaymentDate()
        );
    }

}
