package com.back.shared.payout.event;

import com.back.shared.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PayoutMemberCreatedEvent {
    private final MemberDto member;
}
