package com.back.boundedContext.payout.domain;

import com.back.global.entity.BaseIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PAYOUT_PAYOUT")
@NoArgsConstructor
@Getter
public class Payout extends BaseIdAndTime {

    @ManyToOne(fetch = FetchType.LAZY)
    private PayoutMember payee;

    private LocalDateTime payoutDate;

    private BigDecimal amount;

    @OneToMany(mappedBy = "payout", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<PayoutItem> items =  new ArrayList<>();

    public Payout(PayoutMember payee) {
        this.payee = payee;
    }

    public PayoutItem addItem(PayoutEventType eventType, String relTypeCode, int relId, LocalDateTime payDate, PayoutMember payer, PayoutMember payee, BigDecimal amount) {
        PayoutItem payoutItem = new PayoutItem(this, eventType, relTypeCode, relId, payDate, payer, payee, amount);
        items.add(payoutItem);
        this.amount = this.amount.add(amount);
        return payoutItem;
    }
}
