package com.back.shared.cash.dto;

import com.back.boundedContext.cash.domain.Wallet;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor(
        onConstructor_ = @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
)
@Getter
public class WalletDto {

    private final int id;
    private final LocalDateTime creationDate;
    private final LocalDateTime modifyDate;
    private final int holderId;
    private final String holderName;
    private final BigDecimal balance;

    public WalletDto(Wallet wallet) {
        this(
                wallet.getId(),
                wallet.getCreateDate(),
                wallet.getModifyDate(),
                wallet.getHolder().getId(),
                wallet.getHolder().getNickname(),
                wallet.getBalance()
        );
    }
}
