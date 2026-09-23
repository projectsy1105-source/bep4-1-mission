package com.back.shared.payout.dto;

import com.back.standard.modelType.HasModelTypeCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class PayoutDto implements HasModelTypeCode {

    private final int id;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifyDate;
    private final int payeeId;
    private final String payeeName;
    private final LocalDateTime payoutDate;
    private final BigDecimal amount;
    private final boolean isPayeeSystem;

    @Override
    public String getModelTypeCode() {
        return "Payout";
    }

}
