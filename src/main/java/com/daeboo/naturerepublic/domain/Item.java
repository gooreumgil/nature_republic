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

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<CategoryItem> categoryItems = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
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

//        mainImgPaths.stream().forEach(mainPath -> {
//            ItemSrc itemSrc = ItemSrc.createItemSrcMain(mainPath, item);
//            item.itemSrcs.add(itemSrc);
//        });
//
//        detailImgPaths.stream().forEach(detailPath -> {
//            ItemSrc itemSrc = ItemSrc.createItemSrcDetail(detailPath, item);
//            item.itemSrcs.add(itemSrc);
//        });
//
//        for (Category category : categories) {
//            CategoryItem categoryItem = CategoryItem.createCategoryItem(category, item);
//            item.categoryItems.add(categoryItem);
//        }

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
    public Item updateItem(ItemDto.UpdateForm itemDto, List<Category> categories) {
        this.nameKor = itemDto.getNameKor();
        this.nameEng = itemDto.getNameEng();
        this.price = itemDto.getPrice();
        this.stockQuantity = itemDto.getStockQuantity();
        this.description = itemDto.getDescription();
        this.capacity = itemDto.getCapacity();

        for (int i = 0; i < this.categoryItems.size(); i++) {

            for (int j = 0; j < categories.size(); j++) {

                CategoryItem categoryItem = categoryItems.get(i);
                Category category = categories.get(j);

                if (j > i) {

                    CategoryItem newCategoryItem = CategoryItem.createCategoryItem(categories.get(j), this);
                    this.categoryItems.add(newCategoryItem);

                }

                if (!categoryItems.get(i).getCategoryName().equals(categories.get(j).getName())) {
                    Category category1 = categoryItems.get(i).getCategory();
                    category1.updateCategory(category);
                }
            }

        }

        List<ItemSrc> itemSrcs = null;
        itemSrcs.addAll(itemDto.getMainImg());
        itemSrcs.addAll(itemDto.getDetailImg());

        for (int i = 0; i < this.itemSrcs.size(); i++) {

            for (int j = 0; j < itemSrcs.size(); j++) {

                if (j > i) {
                    if (itemSrcs.get(j).getImgType().equals(ImgType.MAIN)) {
                        ItemSrc.createItemSrcMain(itemSrcs.get(j).getS3Key(), this);
                    } else {
                        ItemSrc.createItemSrcDetail(itemSrcs.get(j).getS3Key(), this);
                    }
                }

                if (!this.itemSrcs.get(i).getS3Key().equals(itemSrcs.get(j).getS3Key())) {
                    ItemSrc itemSrc = this.itemSrcs.get(i);
                    itemSrc.updateItemSrc(itemSrcs.get(j));
                }

            }

        }

        return this;

//        for (int i = 0; i < this.categoryItems.size(); i++) {
//
//            Category category = categoryItems.get(i).getCategory();
//            for (int j = 0; j < categories.size(); j++) {
//                if (j > i) {
//                    CategoryItem.createCategoryItem(categories.get(i), this);
//                } else {
//                    category.updateCategory(categories.get(i));
//                }
//            }
//        }
    }













}
