package com.back.boundedContext.payout.domain;

import com.back.global.entity.BaseIdAndTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAYOUT_PAYOUT_ITEM")
@NoArgsConstructor
@Getter
public class PayoutItem extends BaseIdAndTime {

    @ManyToOne(fetch = FetchType.LAZY)
    private Payout payout;

    private PayoutEventType eventType;

    private String relTypeCode;
    private int relId;
    private LocalDateTime paymentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private PayoutMember payer;

    @ManyToOne(fetch = FetchType.LAZY)
    private PayoutMember payee;

    private BigDecimal amount;

    public PayoutItem(Payout payout, PayoutEventType eventType, String relTypeCode, int relId, LocalDateTime paymentDate, PayoutMember payer, PayoutMember payee, BigDecimal amount) {
        this.payout = payout;
        this.eventType = eventType;
        this.relTypeCode = relTypeCode;
        this.relId = relId;
        this.paymentDate = paymentDate;
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
    }
}
