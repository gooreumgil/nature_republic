package com.daeboo.naturerepublic.repository;

import com.daeboo.naturerepublic.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByMemberId(Long memberId, Pageable pageable);

    Page<Review> findAllByItemId(Long itemId, Pageable pageable);

}
