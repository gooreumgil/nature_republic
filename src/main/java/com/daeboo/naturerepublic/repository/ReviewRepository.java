package com.daeboo.naturerepublic.repository;

import com.daeboo.naturerepublic.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByMemberId(Long memberId);
    List<Review> findAllByItemId(Long itemId);

}
