package com.back.shared.market.dto;

import com.back.global.entity.BaseIdAndTime;
import com.back.standard.modelType.HasModelTypeCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class OrderItemDto implements HasModelTypeCode {

    private final int id;
    private final LocalDateTime creationDate;
    private final LocalDateTime modifyDate;
    private final int orderId;
    private final int buyerId;
    private final String buyerName;
    private final int sellerId;
    private final String sellerName;
    private final int productId;
    private final String productName;
    private final BigDecimal price;
    private final BigDecimal salePrice;
    private final double payoutRate;
    private final BigDecimal payoutFee;
    private final BigDecimal salePriceWithoutFee;

    @Override
    public String getModelTypeCode() {
        return "OrderItem";
    }

}
