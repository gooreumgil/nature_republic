package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.Review;
import com.daeboo.naturerepublic.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Page<Review> findAllByMeberId(Long memberId, Pageable pageable) {
        return reviewRepository.findAllByMemberId(memberId, pageable);
    }

    public Page<Review> findAllByItemId(Long itemId, Pageable pageable) {
        return reviewRepository.findAllByItemId(itemId, pageable);
    }
}
