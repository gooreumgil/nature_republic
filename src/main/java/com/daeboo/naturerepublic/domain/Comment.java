package com.daeboo.naturerepublic.domain;

import com.daeboo.naturerepublic.dto.QnaDto;
import com.daeboo.naturerepublic.dto.ReviewDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    private String content;
    private Integer rating;
    private LocalDateTime wroteAt;
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    private CommentType commentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private Qna qna;

    // 생성 메소드
    public static Comment createComment(String content, Item item, Member member, Qna qna) {
        Comment comment = new Comment();
        comment.content = content;
        comment.wroteAt = LocalDateTime.now();
        comment.commentType = CommentType.QNA;
        comment.item = item;
        comment.member = member;
        comment.qna = qna;

        return comment;
    }

    // 생성 메소드 type 구매후기
    public static Comment createCommentTypeReview(ReviewDto reviewDto, Item item, Member member) {
        Comment comment = new Comment();
        comment.content = reviewDto.getContent();
        comment.rating = reviewDto.getRating();
        comment.wroteAt = LocalDateTime.now();
        comment.commentType = CommentType.ITEM;
        comment.item = item;
        comment.member = member;

        return comment;
    }

    // 연관관계 편의 메소드
    public void setItem(Item item) {
        this.item = item;
        item.addComment(this);
    }

    // update 메소드
    public void update(String content) {
        this.content = content;
    }

    // 양방향
}
