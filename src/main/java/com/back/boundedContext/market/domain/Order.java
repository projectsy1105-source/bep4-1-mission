package com.back.boundedContext.market.domain;

import com.back.global.entity.BaseIdAndTime;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.market.event.MarketOrderPaymentCompletedEvent;
import com.back.shared.market.event.MarketOrderPaymentRequestedEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    private LocalDateTime requestPaymentDate;

    private LocalDateTime paymentDate;

    private LocalDateTime cancelDate;

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
        salePrice = salePrice.add(product.getSalePrice());
    }

    public void completePayment() {
        paymentDate = LocalDateTime.now();
        publishEvent(new MarketOrderPaymentCompletedEvent(toDto()));
    }

    public boolean isPaid() {
        return paymentDate != null;
    }

    public void requestPayment(BigDecimal pgPaymentAmount) {
        requestPaymentDate = LocalDateTime.now();

        publishEvent(new MarketOrderPaymentRequestedEvent(toDto(), pgPaymentAmount));
    }

    public void cancelPayment() {
        requestPaymentDate = null;
    }

    public boolean isCancelled() {
        return cancelDate != null;
    }

    public  boolean isPaymentInProgress() {
        return requestPaymentDate != null && paymentDate == null && cancelDate == null;
    }

    public OrderDto toDto() {
        return new OrderDto(getId(), getCreateDate(), getModifyDate(),getBuyer().getId(), getBuyer().getNickname(), getPrice(), getSalePrice(), getRequestPaymentDate(), getPaymentDate());
    }

}
