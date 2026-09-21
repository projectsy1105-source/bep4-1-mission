package com.back.boundedContext.market.domain;

import com.back.global.entity.BaseIdAndTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "MARKET_ORDER_ITEM")
@NoArgsConstructor
@Getter
public class OrderItem extends BaseIdAndTime {

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private String productName;

    private BigDecimal price;

    private BigDecimal salePrice;

    private double payoutRate = MarketPolicy.PRODUCT_PAYOUT_RATE;

    public OrderItem(Order order, Product product, BigDecimal price, BigDecimal salePrice) {
        this.order = order;
        this.product = product;
        this.productName = product.getName();
        this.price = price;
        this.salePrice = salePrice;
    }

}
