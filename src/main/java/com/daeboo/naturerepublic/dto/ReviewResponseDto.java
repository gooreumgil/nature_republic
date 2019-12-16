package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.ItemSrc;
import com.daeboo.naturerepublic.domain.LikeReview;
import com.daeboo.naturerepublic.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ReviewResponseDto {

    private Long reviewId;
    private Long writerId;
    private String writer;
    private String content;
    private Long itemId;
    private String itemName;
    private int rating;
    private int views;
    private int likes;
    private LocalDateTime wroteAt;
    private ItemSrc itemSrc;
    private List<ItemSrc> reviewSrcs = new ArrayList<>();
    private List<LikeReviewDto.ItemDetail> likeReviewDtos = new ArrayList<>();
    private List<Long> likeMemberIds = new ArrayList<>();

    public ReviewResponseDto(Review review) {
        this.reviewId = review.getId();
        this.writerId = review.getMember().getId();
        this.writer = review.getMember().getName();
        this.content = review.getContent();
        this.itemId = review.getItem().getId();
        this.itemName = review.getItem().getNameKor();
        this.rating = review.getRating();
        this.views = review.getViews();
        this.likes = review.getLikes();
        this.wroteAt = review.getWroteAt();
        this.itemSrc = review.getItem().getItemSrcs().get(0);
        this.reviewSrcs = review.getItemSrcs();

        List<LikeReview> likeReviews = review.getLikeReviews();
        List<LikeReviewDto.ItemDetail> dtos = likeReviews.stream().map(LikeReviewDto.ItemDetail::new).collect(Collectors.toList());

        likeReviews.forEach(likeReview -> {
            likeMemberIds.add(likeReview.getMember().getId());
        });

        this.likeReviewDtos = dtos;

    }
}
