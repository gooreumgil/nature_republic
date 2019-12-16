package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.LikeReview;
import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.domain.Review;
import com.daeboo.naturerepublic.repository.LikeReviewRepository;
import com.daeboo.naturerepublic.repository.MemberRepository;
import com.daeboo.naturerepublic.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeReviewService {

    private final LikeReviewRepository likeReviewRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    public List<LikeReview> findAllByReviewId(Long reviewId) {
        return likeReviewRepository.findAllByReviewId(reviewId);
    }

    @Transactional
    public void save(Long reviewId, Long memberId) {

        Review review = reviewRepository.findById(reviewId).get();
        Member member = memberRepository.findById(memberId).get();

        LikeReview likeReview = LikeReview.createLikeReview(review, member);

        likeReviewRepository.save(likeReview);
    }

    @Transactional
    public void deleteByReviewIdAndMemberId(Long reviewId, Long memberId) {
        likeReviewRepository.deleteByReviewIdAndMemberId(reviewId, memberId);
    }
}
