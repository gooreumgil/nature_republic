package com.daeboo.naturerepublic.repository;

import com.daeboo.naturerepublic.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select count(o) from Order o where o.delivery.deliveryStatus = 'ONGOING' and o.member.id = :memberId")
    Long countDeliveryOngoing(@Param("memberId") Long memberId);

    @Query("select o from Order o join fetch o.member m join fetch o.delivery join fetch o.orderItems oi join fetch oi.item where o.id = :id")
    Optional<Order> findByIdQuery(@Param("id") Long id);

}
