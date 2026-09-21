package com.back.shared.market.event;

import com.back.shared.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MarketMemberCreatedEvent {
    private final MemberDto memberDto;
}
