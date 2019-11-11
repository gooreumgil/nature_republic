package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.Category;
import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.domain.ItemSrc;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public class ItemDto {

    @Getter @Setter
    public static class Create {

        private String nameKor;
        private String nameEng;
        private Integer price;
        private Integer stockQuantity;
        private String description;
        private Integer capacity;
        private String[] multiCategoryValues;
        private MultipartFile mainImg;
        private MultipartFile detailImg;

        public Item toItem(String nameKor, String nameEng, int price, int stockQuantity, String description, int capacity, List<Category> categories) {
            return Item.createItem(nameKor, nameEng, price, stockQuantity, description, capacity, categories);
        }

        public Item toItemWithImg(
                String nameKor, String nameEng, Integer price, Integer stockQuantity, String description,
                Integer capacity, List<Category> categories, String mainImgPath, String detailImgPath) {

            return Item.createItemWithImg(nameKor, nameEng, price, stockQuantity, description, capacity, categories, mainImgPath, detailImgPath);
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
        private List<ItemSrc> itemSrcs;

        public ListView(Item item) {
            this.id = item.getId();
            this.nameKor = item.getNameKor();
            this.nameEng = item.getNameEng();
            this.price = item.getPrice();
            this.stockQuantity = item.getStockQuantity();
            this.likes = item.getLikes();
            this.description = item.getDescription();
            this.capacity = item.getCapacity();
            this.itemSrcs = item.getItemSrcs();
        }
    }

}
