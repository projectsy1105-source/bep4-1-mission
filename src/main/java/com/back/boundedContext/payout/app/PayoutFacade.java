package com.back.boundedContext.payout.app;

import com.back.boundedContext.payout.domain.Payout;
import com.back.boundedContext.payout.domain.PayoutCandidateItem;
import com.back.boundedContext.payout.domain.PayoutMember;
import com.back.boundedContext.payout.out.PayoutMemberRepository;
import com.back.global.global.RsData.RsData;
import com.back.shared.market.dto.OrderDto;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PayoutFacade {

    private final PayoutSyncMemberUseCase payoutSyncMemberUseCase;
    private final PayoutAndPayoutCandidateItemsUseCase payoutAndPayoutCandidateItemsUseCase;
    private final PayoutCreatePayoutUseCase payoutCreatePayoutUseCase;
    private final PayoutCollectPayoutItemsMoreUseCase payoutCollectPayoutItemsMoreUseCase;
    private final PayoutCompletePayoutsMoreUseCase payoutCompletePayoutsMoreUseCase;
    private final PayoutSupport payoutSupport;

    @Transactional
    public PayoutMember syncMember(MemberDto memberDto) {
        return payoutSyncMemberUseCase.syncMember(memberDto);
    }

    @Transactional
    public Payout createPayout(int payeeId) {
        return payoutCreatePayoutUseCase.createPayout(payeeId);
    }

    @Transactional
    public void addPayoutCandidateItems(OrderDto orderDto) {
        payoutAndPayoutCandidateItemsUseCase.addPayoutCandidateItems(orderDto);
    }

    @Transactional
    public RsData<Integer> collectPayoutItemsMore(int limit) {
        return payoutCollectPayoutItemsMoreUseCase.collectPayoutItemsMore(limit);
    }

    @Transactional(readOnly = true)
    public List<PayoutCandidateItem> findPayoutCandidateItems() {
        return payoutSupport.findPayoutCandidateItems();
    }

    @Transactional
    public RsData<Integer> completePayoutsMore(int limit) {
        return payoutCompletePayoutsMoreUseCase.completePayoutsMore(limit);
    }
}
