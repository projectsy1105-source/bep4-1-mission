package com.back.shared.cash.event;

import com.back.shared.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CashMemberCreatedEvent {
    private final MemberDto memberDto;
}
