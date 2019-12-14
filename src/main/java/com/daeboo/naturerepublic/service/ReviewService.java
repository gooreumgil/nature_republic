package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.Review;
import com.daeboo.naturerepublic.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<Review> findAllByMeberId(Long memberId) {
        return reviewRepository.findAllByMemberId(memberId);
    }

}
