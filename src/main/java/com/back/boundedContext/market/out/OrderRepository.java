package com.back.boundedContext.market.out;

import com.back.boundedContext.market.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("""
            select o
            from Order o
            join fetch o.buyer
            where o.requestPaymentDate is null
              and o.paymentDate is null
              and o.cancelDate is null
            order by o.id
            """)
    List<Order> findAllReadyForPayment();
}
