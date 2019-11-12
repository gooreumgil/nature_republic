package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.Category;
import com.daeboo.naturerepublic.domain.CategoryItem;
import com.daeboo.naturerepublic.domain.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CategoryItemDto {

    @Getter @Setter
    @NoArgsConstructor
    public static class BestViews {
        private Long id;
        private String categoryName;
        private ItemDto.CategoryList itemDto;

        public BestViews(CategoryItem categoryItem) {
            this.id = categoryItem.getId();
            this.categoryName = categoryItem.getCategoryName();
            itemDto = new ItemDto.CategoryList(categoryItem.getItem());
        }
    }

}
