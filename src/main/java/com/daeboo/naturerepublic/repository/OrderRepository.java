package com.daeboo.naturerepublic.repository;

import com.daeboo.naturerepublic.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select count(o) from Order o where o.delivery.deliveryStatus = 'ONGOING'")
    Long countDeliveryOngoing(Long memberId);
}
