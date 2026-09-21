package com.back.boundedContext.market.app;


import com.back.boundedContext.cash.app.CashCreateWalletUseCase;
import com.back.boundedContext.cash.app.CashSupport;
import com.back.boundedContext.cash.app.CashSyncMemberUseCase;
import com.back.boundedContext.cash.domain.CashMember;
import com.back.boundedContext.cash.domain.Wallet;
import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.domain.Product;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarketFacade {

    private final MarketSupport marketSupport;
    private final MarketSyncMemberUseCase marketSyncMemberUseCase;
    private final MarketCreateProductUseCase marketCreateProductUseCase;

    @Transactional(readOnly = true)
    public long productsCount() {
        return marketSupport.productsCount();
    }

    @Transactional(readOnly = true)
    public Optional<MarketMember> findMemberByUsername(String username) {
        return marketSupport.findMemberByUsername(username);
    }

    @Transactional
    public MarketMember syncMember(MemberDto member) {
        return marketSyncMemberUseCase.syncMember(member);
    }

    @Transactional
    public Product createProduct(MarketMember seller, String sourceTypeCode, int sourceId, String name, String description, BigDecimal price, BigDecimal salePrice) {
        return marketCreateProductUseCase.createProduct(seller, sourceTypeCode, sourceId, name, description, price, salePrice);
    }

}
