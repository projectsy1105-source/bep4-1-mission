package com.back.boundedContext.market.app;

import com.back.boundedContext.market.domain.Cart;
import com.back.boundedContext.market.domain.MarketMember;
import com.back.boundedContext.market.out.CartRepository;
import com.back.boundedContext.market.out.MarketMemberRepository;
import com.back.global.global.RsData.RsData;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketCreateCartUseCase {

    private final CartRepository cartRepository;
    private final MarketMemberRepository marketMemberRepository;

    public RsData<Cart> createCart(MemberDto buyer) {
        MarketMember m = marketMemberRepository.getReferenceById(buyer.getId());
        Cart cart = new Cart(m);
        cartRepository.save(cart);
        return new RsData<>("201-1", "장바구니가 생성되었습니다.", cart);

    }

}
