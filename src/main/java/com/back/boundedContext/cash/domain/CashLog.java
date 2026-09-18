package com.back.boundedContext.cash.domain;

import com.back.global.entity.BaseIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Table(name = "CASH_CASH_LOG")
@Getter
public class CashLog extends BaseIdAndTime {

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String relTypeCode;

    private int relId;

    @ManyToOne(fetch = LAZY)
    private CashMember holder;

    @ManyToOne(fetch = LAZY)
    private Wallet wallet;

    private BigDecimal amount;

    private BigDecimal balance;

    public CashLog(EventType eventType, String relTypeCode, int relId, CashMember holder, Wallet wallet, BigDecimal amount, BigDecimal balance) {
        this.eventType = eventType;
        this.relTypeCode = relTypeCode;
        this.relId = relId;
        this.holder = holder;
        this.wallet = wallet;
        this.amount = amount;
        this.balance = balance;
    }

    public enum EventType {
        충전__무통장입금,
        충전__PG결제_토스페이먼츠,
        출금__통장입금,
        사용__주문결제,
        임시보관__주문결제,
        정산지급__상품판매_수수료,
        정산수령__상품판매_수수료,
        정산지급__상품판매_대금,
        정산수령__상품판매_대금,
    }
}
