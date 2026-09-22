package com.back.boundedContext.payout.app;

import com.back.boundedContext.payout.domain.PayoutMember;
import com.back.boundedContext.payout.out.PayoutMemberRepository;
import com.back.global.eventPublisher.EventPublisher;
import com.back.shared.member.dto.MemberDto;
import com.back.shared.payout.event.PayoutMemberCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayoutSyncMemberUseCase {

    private final PayoutMemberRepository payoutMemberRepository;
    private final EventPublisher eventPublisher;

    public PayoutMember syncMember(MemberDto memberDto) {
        boolean isNew = !payoutMemberRepository.existsById(memberDto.getId());
        PayoutMember m = payoutMemberRepository.save(new PayoutMember(
                memberDto.getId(), memberDto.getCreateDate(), memberDto.getModifyDate(), memberDto.getUsername(), memberDto.getNickname(), memberDto.getActivityScore()
        ));
        if (isNew) {
            //퍼블리셔
            eventPublisher.publisher(new PayoutMemberCreatedEvent(m.toDto()));
        }
        return m;
    }

}
