package com.daeboo.naturerepublic.domain;

import com.daeboo.naturerepublic.dto.ItemDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private LocalDateTime registerAt;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryItem> categoryItems = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemSrc> itemSrcs = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<ItemTags> itemTags;

    // 생성 메소드
    public static Item createItemWithImg(
            String nameKor, String nameEng, Integer price, Integer stockQuantity, String description,
            Integer capacity, List<Category> categories, List<String> mainImgPaths, List<String> detailImgPaths) {

        Item item = new Item();
        item.nameKor = nameKor;
        item.nameEng = nameEng;
        item.price = price;
        item.stockQuantity = stockQuantity;
        item.likes = 0;
        item.description = description;
        item.capacity = capacity;
        item.registerAt = LocalDateTime.now();

        mainImgPaths.forEach(s -> {
            ItemSrc itemSrcMain = ItemSrc.createItemSrcMain(s, item);
            item.itemSrcs.add(itemSrcMain);
        });

        detailImgPaths.forEach(s -> {
            ItemSrc itemSrcDetail = ItemSrc.createItemSrcDetail(s, item);
            item.itemSrcs.add(itemSrcDetail);
        });

        for (Category category : categories) {
            CategoryItem categoryItem = CategoryItem.createCategoryItem(category, item);
            item.categoryItems.add(categoryItem);
        }


        return item;

    }

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

    // 업데이트 메소드
    public Item updateItem(ItemDto.UpdateForm itemDto, List<Category> categories, List<String> originRemove, List<String> mainImgPath, List<String> detailImgPath) {
        this.nameKor = itemDto.getNameKor();
        this.nameEng = itemDto.getNameEng();
        this.price = itemDto.getPrice();
        this.stockQuantity = itemDto.getStockQuantity();
        this.description = itemDto.getDescription();
        this.capacity = itemDto.getCapacity();

        List<CategoryItem> categoryItems = this.categoryItems;
        categoryItems.clear();

        List<ItemSrc> itemSrcs = this.getItemSrcs();
        for (String s : originRemove) {
            itemSrcs.removeIf(itemSrc -> itemSrc.getS3Key().equals(s));
        }

        mainImgPath.forEach(s -> {
            ItemSrc itemSrcMain = ItemSrc.createItemSrcMain(s, this);
            this.itemSrcs.add(itemSrcMain);
        });

        detailImgPath.forEach(s -> {
            ItemSrc itemSrcDetail = ItemSrc.createItemSrcDetail(s, this);
            this.itemSrcs.add(itemSrcDetail);
        });

        for (Category category : categories) {
            CategoryItem categoryItem = CategoryItem.createCategoryItem(category, this);
            categoryItems.add(categoryItem);
        }

        return this;

    }












}
