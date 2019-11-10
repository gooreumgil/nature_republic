package com.daeboo.naturerepublic.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String nameKor;
    private String nameEng;
    private int price;
    private int stockQuantity;
    private int likes;
    private String description;
    private int capacity;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<CategoryItem> categoryItems;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<ItemSrc> itemSrcs;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<ItemTags> itemTags;

    // 연관관계 편의 메소드
    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void addItemSrc(ItemSrc itemSrc) {
        this.itemSrcs.add(itemSrc);
    }

    public void addCategoryItem(CategoryItem categoryItem) {
        this.categoryItems.add(categoryItem);
    }

    public void addItemTags(ItemTags itemTags) {
        this.itemTags.add(itemTags);
    }
}
