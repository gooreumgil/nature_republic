package com.daeboo.naturerepublic.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Likes {

    @Id @GeneratedValue
    @Column(name = "likes_id")
    private Long id;

    private LocalDateTime likedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public static Likes CreateLikes(Item item, Member member) {
        Likes likes = new Likes();
        likes.setMember(member);
        likes.setItem(item);
        likes.likedAt = LocalDateTime.now();

        return likes;
    }

    // 연관관계 편의 메소드
    public void setMember(Member member) {
        member.getLikes().add(this);
    }

    public void setItem(Item item) {
        item.getLikesList().add(this);
        item.plusLikes();
    }

}
