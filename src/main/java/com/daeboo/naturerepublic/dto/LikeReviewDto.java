package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.LikeReview;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class LikeReviewDto {

    @Getter @Setter
    @NoArgsConstructor
    public static class ItemDetail {

        private Long id;
        private Long reviewId;
        private Long memberId;

        public ItemDetail(LikeReview likeReview) {
            this.id = likeReview.getId();
            this.reviewId = likeReview.getReview().getId();
            this.memberId = likeReview.getMember().getId();
        }
    }

}
