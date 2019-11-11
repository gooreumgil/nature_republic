package com.daeboo.naturerepublic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ItemSrc {

    @Id @GeneratedValue
    @Column(name = "item_src_id")
    private Long id;

    private String key;

    @Enumerated(EnumType.STRING)
    private ImgType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public static ItemSrc createItemSrcMain(String mainPath, Item item) {
        ItemSrc itemSrc = new ItemSrc();
        itemSrc.key = mainPath;
        itemSrc.type = ImgType.MAIN;
        itemSrc.item = item;

        return itemSrc;
    }

    public static ItemSrc createItemSrcDetail(String imgPath, Item item) {
        ItemSrc itemSrc = new ItemSrc();
        itemSrc.key = imgPath;
        if (imgPath.contains("main")) {
            itemSrc.type = ImgType.MAIN;
        } else {
            itemSrc.type = ImgType.DETAIL;
        }

        itemSrc.item = item;

        return itemSrc;

    }

    // 연관관계 편의 메소드
    public void setItem(Item item) {
        this.item = item;
        item.addItemSrc(this);
    }
}
