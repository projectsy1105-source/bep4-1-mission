package com.back.boundedContext.market.domain;

import com.back.global.entity.BaseManualIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MARKET_CART")
@NoArgsConstructor
@Getter
public class Cart extends BaseManualIdAndTime {

    @ManyToOne(fetch = FetchType.LAZY)
    private MarketMember buyer;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<CartItem> items =  new ArrayList<>();

    private int itemCount;

    public Cart(MarketMember buyer) {
        super(buyer.getId());
        this.buyer = buyer;
    }

    public void addItem(Product product){
        CartItem item = new CartItem(this, product);
        this.items.add(item);
        this.itemCount++;
    }

    public boolean hasItems() {
        return itemCount > 0;
    }

    public void clearItems() {
        this.items.clear();
        this.itemCount = 0;
    }
}
