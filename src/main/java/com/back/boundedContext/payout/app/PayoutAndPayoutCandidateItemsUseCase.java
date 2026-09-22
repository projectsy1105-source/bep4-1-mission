package com.back.boundedContext.payout.app;

import com.back.shared.market.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayoutAndPayoutCandidateItemsUseCase {

    public void addPayoutCandidateItems(OrderDto order) {
        log.info("Adding payout candidate items for order {}", order.getId());
    }

}
