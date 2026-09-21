package com.back.boundedContext.market.domain;

import com.back.global.entity.BaseIdAndTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "MARET_PRODUCT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Product extends BaseIdAndTime {

    @ManyToOne(fetch = FetchType.LAZY)
    private MarketMember seller;

    private String sourceTypeCode;

    private int sourceId;

    private String name;

    private String description;

    private BigDecimal price;

    private BigDecimal salePrice;

}
