package com.back.boundedContext.market.app;


import com.back.boundedContext.cash.app.CashCreateWalletUseCase;
import com.back.boundedContext.cash.app.CashSupport;
import com.back.boundedContext.cash.app.CashSyncMemberUseCase;
import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.boundedContext.market.domain.MarketMember;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketFacade {

    private final MarketSyncMemberUseCase marketSyncMemberUseCase;

    @Transactional
    public MarketMember syncMember(MemberDto member) {
        return marketSyncMemberUseCase.syncMember(member);
    }

}
