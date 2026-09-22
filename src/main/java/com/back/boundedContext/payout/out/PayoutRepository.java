package com.back.boundedContext.payout.out;

import com.back.boundedContext.payout.domain.Payout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayoutRepository extends JpaRepository<Payout, Integer> {

    Optional<Payout> findByPayeeId(int customerId);
}
