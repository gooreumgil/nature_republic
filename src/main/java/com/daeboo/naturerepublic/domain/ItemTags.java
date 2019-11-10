package com.daeboo.naturerepublic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ItemTags {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // 연관관계 편의 메소드
    public void setItem(Item item) {
        this.item = item;
        item.addItemTags(this);
    }
}
