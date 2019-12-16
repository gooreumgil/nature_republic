package com.daeboo.naturerepublic.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class LikeReview {

    @Id
    @GeneratedValue
    @Column(name = "like_review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;


    public static LikeReview createLikeReview(Review review, Member member) {
        LikeReview likeReview = new LikeReview();
        likeReview.member = member;
        likeReview.review = review;

        return likeReview;
    }
}
