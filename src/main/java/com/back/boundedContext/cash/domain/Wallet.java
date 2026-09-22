package com.back.boundedContext.cash.domain;

import com.back.global.entity.BaseEntity;
import com.back.global.entity.BaseManualIdAndTime;
import com.back.shared.cash.dto.WalletDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "CASH_WALLET")
@Getter
public class Wallet extends BaseManualIdAndTime {

    @ManyToOne(fetch = FetchType.LAZY)
    private CashMember holder;

    private BigDecimal balance;

    @OneToMany(mappedBy = "wallet", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<CashLog> logs = new ArrayList<>();

    public Wallet(CashMember holder) {
        super(holder.getId());
        this.holder = holder;
        this.balance = BigDecimal.ZERO;
    }

    public boolean hasBalance() {
        return balance.compareTo(BigDecimal.ZERO) > 0;
    }

    public void credit(BigDecimal amount, CashLog.EventType eventType, String relTypeCode, int relId) {
        this.balance = this.balance.add(amount);
        addCashLog(amount, eventType, relTypeCode, relId);
    }

    public void credit(BigDecimal amount, CashLog.EventType eventType, BaseEntity rel) {
        credit(amount, eventType, rel.getModelTypeCode(), rel.getId());
    }

    public void credit(BigDecimal amount, CashLog.EventType eventType) {
        credit(amount, eventType, holder);
    }

    public void debit(BigDecimal amount, CashLog.EventType eventType, String relTypeCode, int relId) {
        this.balance = this.balance.subtract(amount);
        addCashLog(amount.negate(), eventType, relTypeCode, relId);
    }

    public void debit(BigDecimal amount, CashLog.EventType eventType, BaseEntity rel) {
        debit(amount, eventType, rel.getModelTypeCode(), rel.getId());
    }

    public void debit(BigDecimal amount, CashLog.EventType eventType) {
        debit(amount, eventType, holder);
    }

    private CashLog addCashLog(BigDecimal amount, CashLog.EventType eventType, String relTypeCode, int relId) {
        CashLog cashLog = new CashLog(eventType, relTypeCode, relId, holder, this, amount, balance);
        logs.add(cashLog);
        return cashLog;
    }

    public WalletDto toDto() {
        return new WalletDto(getId(), getCreateDate(), getModifyDate(), getHolder().getId(), getHolder().getNickname(), getBalance());
    }

}
