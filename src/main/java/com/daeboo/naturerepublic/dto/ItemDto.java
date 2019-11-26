package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemDto {

//    @Getter @Setter
//    @NoArgsConstructor
//    public static class CreateForm {
//
//        private String nameKor;
//        private String nameEng;
//        private Integer price;
//        private Integer stockQuantity;
//        private String description;
//        private Integer capacity;
//        private String[] multiCategoryValues;
//        private List<MultipartFile> mainImg;
//        private List<MultipartFile> detailImg;
//
//        public Item toItemWithImg(
//                String nameKor, String nameEng, Integer price, Integer stockQuantity, String description,
//                Integer capacity, List<Category> categories, List<String> mainImgPaths, List<String> detailImgPaths) {
//
//            return Item.createItemWithImg(nameKor, nameEng, price, stockQuantity, description, capacity, categories, mainImgPaths, detailImgPaths);
//        }
//
//    }

    @Getter @Setter
    @NoArgsConstructor
    public static class CreateForm {

        private String nameKor;
        private String nameEng;
        private Integer price;
        private Integer stockQuantity;
        private String description;
        private Integer capacity;
        private List<String> multiCategoryValues = new ArrayList<>();
        private List<MultipartFile> mainImg = new ArrayList<>();
        private List<MultipartFile> detailImg = new ArrayList<>();
        private List<String> mainRemove = new ArrayList<>();
        private List<String> detailRemove = new ArrayList<>();

        public Item toItemWithImg(List<Category> categories, List<String> mainImgPath, List<String> detailImgPath) {

            return Item.createItemWithImg(nameKor, nameEng, price, stockQuantity, description, capacity, categories, mainImgPath, detailImgPath);

//            List<String> mainImgPath = new ArrayList<>();
//            List<String> detailImgPath = new ArrayList<>();
//
//            mainImg.forEach(multipartFile -> {
//                StringBuilder stringBuilder = new StringBuilder();
//                stringBuilder.append(multipartFile.getOriginalFilename() + " ");
//                mainImgPath.add(stringBuilder.toString());
//            });
//
//            detailImg.forEach(multipartFile -> {
//                StringBuilder stringBuilder = new StringBuilder();
//                stringBuilder.append(multipartFile.getOriginalFilename() + " ");
//                detailImgPath.add(stringBuilder.toString());
//            });

        }

    }

    @Getter @Setter
    @NoArgsConstructor
    public static class UpdateForm {

        private Long id;
        private String nameKor;
        private String nameEng;
        private Integer price;
        private Integer stockQuantity;
        private String description;
        private Integer capacity;
        private List<String> multiCategoryValues = new ArrayList<>();

        private List<ItemSrc> mainSrcs = new ArrayList<>();
        private List<ItemSrc> detailSrcs = new ArrayList<>();

        private List<MultipartFile> mainImg = new ArrayList<>();
        private List<MultipartFile> detailImg = new ArrayList<>();

        private List<String> mainRemove = new ArrayList<>();
        private List<String> detailRemove = new ArrayList<>();
        private List<Long> originRemove = new ArrayList<>();


        public UpdateForm(Item item) {
            this.id = item.getId();
            this.nameKor = item.getNameKor();
            this.nameEng = item.getNameEng();
            this.price = item.getPrice();
            this.stockQuantity = item.getStockQuantity();
            this.description = item.getDescription();
            this.capacity = item.getCapacity();
            List<CategoryItem> categoryItems = item.getCategoryItems();
            for (int i = 0; i < categoryItems.size(); i++) {
                this.multiCategoryValues.add(categoryItems.get(i).getCategoryName());
            }

            List<ItemSrc> itemSrcs = item.getItemSrcs();
            itemSrcs.forEach(itemSrc -> {
                if (itemSrc.getImgType().equals(ImgType.MAIN)) {
                    mainSrcs.add(itemSrc);
                } else {
                    detailSrcs.add(itemSrc);
                }
            });
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
        private int likes;
        private List<ItemSrc> mainSrcs = new ArrayList<>();

        public CategoryList(Item item) {
            this.id = item.getId();
            this.nameKor = item.getNameKor();
            this.description = item.getDescription();
            this.price = item.getPrice();
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
    public static class Detail {

        private Long id;
        private String nameKor;
        private String nameEng;
        private int price;
        private int likes;
        private String description;
        private int capacity;
        private String mainCategory;
        private List<ItemSrc> mainSrcs = new ArrayList<>();
        private List<ItemSrc> detailSrcs = new ArrayList<>();
        private List<CommentDto.ItemReview> comments = new ArrayList<>();

        public Detail(Item item) {
            this.id = item.getId();
            this.nameKor = item.getNameKor();
            this.nameEng = item.getNameEng();
            this.price = item.getPrice();
            this.likes = item.getLikes();
            this.description = item.getDescription();
            this.capacity = item.getCapacity();
            List<CategoryItem> categoryExcludeAll = item.getCategoryItems().stream().filter(categoryItem -> {
                if (!categoryItem.getCategoryName().equals("ALL")) {
                    return true;
                }
                return false;
            }).collect(Collectors.toList());

            this.mainCategory = categoryExcludeAll.get(0).getCategoryName();

            List<ItemSrc> itemSrcs = item.getItemSrcs();

            itemSrcs.stream().forEach(itemSrc -> {
                if (itemSrc.getImgType().equals(ImgType.MAIN)) {
                    mainSrcs.add(itemSrc);
                } else {
                    detailSrcs.add(itemSrc);
                }
            });

            List<Comment> comments = item.getComments();
            this.comments = comments.stream().map(comment -> {
                return new CommentDto.ItemReview(comment);
            }).collect(Collectors.toList());

        }
    }

    @Getter @Setter
    @NoArgsConstructor
    public static class Order {

        private Long id;
        private String itemName;
        private int price;
        private String s3Key;

        public Order(Item item) {
            this.id = item.getId();
            this.itemName = item.getNameKor();
            this.price = item.getPrice();
            this.s3Key = item.getItemSrcs().get(0).getS3Key();
        }
    }
}
