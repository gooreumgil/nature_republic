package com.daeboo.naturerepublic.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CategoryItem {

    @Id
    @GeneratedValue
    @Column(name = "category_item_id")
    private Long id;

    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public static CategoryItem createCategoryItem(Category category, Item item) {
        CategoryItem categoryItem = new CategoryItem();
        categoryItem.category = category;
        categoryItem.categoryName = category.getName();
        categoryItem.item = item;
//        categoryItem.category = category;

        return categoryItem;
    }

    // 연관관계 편의 메소드
    public void setItem(Item item) {
        this.item = item;
        item.addCategoryItem(this);
    }

    public void setCategory(Category category) {
        this.category = category;
        this.categoryName = category.getName();
        category.addCategoryItem(this);
    }
}
