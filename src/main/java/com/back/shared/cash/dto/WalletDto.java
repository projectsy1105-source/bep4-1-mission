package com.back.shared.cash.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class WalletDto {

    private final int id;
    private final LocalDateTime creationDate;
    private final LocalDateTime modifyDate;
    private final int holderId;
    private final String holderName;
    private final BigDecimal balance;

}
