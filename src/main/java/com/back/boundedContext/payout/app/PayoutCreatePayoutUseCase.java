package com.back.boundedContext.payout.app;

import com.back.boundedContext.payout.domain.Payout;
import com.back.boundedContext.payout.domain.PayoutMember;
import com.back.boundedContext.payout.out.PayoutMemberRepository;
import com.back.boundedContext.payout.out.PayoutRepository;
import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayoutCreatePayoutUseCase {

    private final PayoutRepository payoutRepository;
    private final PayoutMemberRepository payoutMemberRepository;

    public Payout createPayout(int payeeId) {
        PayoutMember m = payoutMemberRepository.getReferenceById(payeeId);
        return payoutRepository.save(new Payout(m));
    }
}
