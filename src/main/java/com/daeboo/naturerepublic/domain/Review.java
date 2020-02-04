package com.daeboo.naturerepublic.domain;

import com.daeboo.naturerepublic.dto.ReviewDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Literal;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;
    private int likes;
    private int views;
    private int rating;
    private LocalDateTime wroteAt;
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemSrc> itemSrcs = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeReview> likeReviews = new ArrayList<>();

    public static Review createReview(ReviewDto reviewDto, Item item, Member member) {
        Review review = new Review();
        review.content = reviewDto.getContent();
        review.item = item;
        review.member = member;
        review.likes = 0;
        review.rating = reviewDto.getRating();
        review.wroteAt = LocalDateTime.now();

        return review;
    }

    public void addItemSrc(ItemSrc itemSrc) {
        this.itemSrcs.add(itemSrc);
        itemSrc.setReview(this);
    }
}
