package com.back.boundedContext.payout.app;

import com.back.shared.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayoutCreatePayoutUseCase {

    public void createPayout(MemberDto payee) {
        log.debug("createPayout.payee: {}", payee.getId());
    }
}
