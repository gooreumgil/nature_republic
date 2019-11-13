package com.daeboo.naturerepublic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    private String content;
    private LocalDateTime wroteAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 연관관계 편의 메소드
    public void setItem(Item item) {
        this.item = item;
        item.addComment(this);
    }
}
