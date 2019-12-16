package com.daeboo.naturerepublic.repository;

import com.daeboo.naturerepublic.domain.LikeReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeReviewRepository extends JpaRepository<LikeReview, Long> {

    List<LikeReview> findAllByReviewId(Long reviewId);
    void deleteByReviewIdAndMemberId(Long reviewId, Long memberId);

}
