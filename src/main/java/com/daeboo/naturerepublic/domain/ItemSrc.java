package com.daeboo.naturerepublic.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "item_src")
@NoArgsConstructor @AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

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
        itemSrc.imgType = ImgType.DETAIL;
        itemSrc.item = item;

        return itemSrc;

    }

    // 연관관계 편의 메소드
    public void setItem(Item item) {
        this.item = item;
        item.addItemSrc(this);
    }

    // 업데이트 메소드
    public void updateItemSrc(ItemSrc itemSrc) {
        this.s3Key = itemSrc.getS3Key();

    }
}
