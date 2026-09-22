package com.back.boundedContext.payout.app;

import com.back.boundedContext.payout.domain.PayoutMember;
import com.back.boundedContext.payout.out.PayoutMemberRepository;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PayoutFacade {

    private final PayoutSyncMemberUseCase payoutSyncMemberUseCase;
    private final PayoutAndPayoutCandidateItemsUseCase payoutAndPayoutCandidateItemsUseCase;
    private final PayoutCreatePayoutUseCase payoutCreatePayoutUseCase;

    @Transactional
    public PayoutMember syncMember(MemberDto memberDto) {
        return payoutSyncMemberUseCase.syncMember(memberDto);
    }

    @Transactional
    public void createPayout(MemberDto member) {
        payoutCreatePayoutUseCase.createPayout(member);
    }

    @Transactional
    public void addPayoutCandidateItems(OrderDto orderDto) {
        payoutAndPayoutCandidateItemsUseCase.addPayoutCandidateItems(orderDto);
    }

}
