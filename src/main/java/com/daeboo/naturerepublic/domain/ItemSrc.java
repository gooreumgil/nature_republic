package com.daeboo.naturerepublic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "item_src")
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ItemSrc {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_src_id")
    private Long id;

    private String s3Key;

    @Enumerated(EnumType.STRING)
    private ImgType imgType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public static ItemSrc createItemSrcMain(String mainPath, Item item) {
        ItemSrc itemSrc = new ItemSrc();
        itemSrc.s3Key = mainPath;
        itemSrc.imgType = ImgType.MAIN;
        itemSrc.item = item;

        return itemSrc;
    }

    public static ItemSrc createItemSrcDetail(String imgPath, Item item) {
        ItemSrc itemSrc = new ItemSrc();
        itemSrc.s3Key = imgPath;
        if (imgPath.contains("main")) {
            itemSrc.imgType = ImgType.MAIN;
        } else {
            itemSrc.imgType = ImgType.DETAIL;
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
