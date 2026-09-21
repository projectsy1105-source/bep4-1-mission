package com.back.boundedContext.market.domain;

import com.back.global.entity.BaseIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MARKET_ORDER")
@NoArgsConstructor
@Getter
public class Order extends BaseIdAndTime {

    @ManyToOne(fetch = FetchType.LAZY)
    private MarketMember buyer;

    private BigDecimal price = BigDecimal.ZERO;

    private BigDecimal salePrice = BigDecimal.ZERO;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(Cart cart) {
        this.buyer = cart.getBuyer();

        cart.getItems().forEach(item -> {
            addItem(item.getProduct());
        });
    }

    public void addItem(Product product) {
        OrderItem i =  new OrderItem(this, product, product.getPrice(), product.getSalePrice());
        orderItems.add(i);

        price = price.add(product.getPrice());
        salePrice = price.add(product.getSalePrice());
    }

}
