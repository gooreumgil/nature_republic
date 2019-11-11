package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.Category;
import com.daeboo.naturerepublic.domain.ImgType;
import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.domain.ItemSrc;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class ItemDto {

    @Getter @Setter
    @NoArgsConstructor
    public static class Create {

        private String nameKor;
        private String nameEng;
        private Integer price;
        private Integer stockQuantity;
        private String description;
        private Integer capacity;
        private String[] multiCategoryValues;
        private List<MultipartFile> mainImg;
        private List<MultipartFile> detailImg;

        public Item toItemWithImg(
                String nameKor, String nameEng, Integer price, Integer stockQuantity, String description,
                Integer capacity, List<Category> categories, List<String> mainImgPaths, List<String> detailImgPaths) {

            return Item.createItemWithImg(nameKor, nameEng, price, stockQuantity, description, capacity, categories, mainImgPaths, detailImgPaths);
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class ListView {

        private Long id;
        private String nameKor;
        private String nameEng;
        private int price;
        private int stockQuantity;
        private int likes;
        private String description;
        private int capacity;
        private List<ItemSrc> mainSrcs = new ArrayList<>();
        private List<ItemSrc> detailSrcs = new ArrayList<>();

        public ListView(Item item) {
            this.id = item.getId();
            this.nameKor = item.getNameKor();
            this.nameEng = item.getNameEng();
            this.price = item.getPrice();
            this.stockQuantity = item.getStockQuantity();
            this.likes = item.getLikes();
            this.description = item.getDescription();
            this.capacity = item.getCapacity();
            item.getItemSrcs().stream().forEach(itemSrc -> {

                if (itemSrc.getImgType().equals(ImgType.MAIN)) {
                    this.mainSrcs.add(itemSrc);
                } else {
                    this.detailSrcs.add(itemSrc);
                }
            });
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class PopularPreview {

        private Long id;
        private String nameKor;
        private int price;
        private int discountPrice;
        private int likes;
        private List<ItemSrc> mainSrcs = new ArrayList<>();

        public PopularPreview(Item item) {
            this.id = item.getId();
            this.nameKor = item.getNameKor();
            this.price = item.getPrice();
            this.discountPrice = (int) (item.getPrice() * 0.7);
            this.likes = item.getLikes();
            item.getItemSrcs().forEach(itemSrc -> {
                if (itemSrc.getImgType().equals(ImgType.MAIN)) {
                    mainSrcs.add(itemSrc);
                }
            });
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class LatestPreview {

        private Long id;
        private String nameKor;
        private int price;
        private List<ItemSrc> mainSrcs = new ArrayList<>();

        public LatestPreview(Item item) {
            this.id = item.getId();
            this.nameKor = item.getNameKor();
            this.price = item.getPrice();
            item.getItemSrcs().forEach(itemSrc -> {
                if(itemSrc.getImgType().equals(ImgType.MAIN)) {
                    mainSrcs.add(itemSrc);
                }
            });
        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class CategoryList {

        private Long id;
        private String nameKor;
        private String description;
        private int price;
        private List<ItemSrc> mainSrcs = new ArrayList<>();

        public CategoryList(Item item) {
            this.id = item.getId();
            this.nameKor = item.getNameKor();
            this.description = item.getDescription();
            this.price = item.getPrice();
            item.getItemSrcs().forEach(itemSrc -> {
                if (itemSrc.getImgType().equals(ImgType.MAIN)) {
                    mainSrcs.add(itemSrc);
                }
            });
        }
    }









}
