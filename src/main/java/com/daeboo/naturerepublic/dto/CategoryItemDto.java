package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.Category;
import com.daeboo.naturerepublic.domain.CategoryItem;
import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.domain.ItemSrc;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

public class CategoryItemDto {

    @Getter @Setter
    @NoArgsConstructor
    public static class ListView {
        private Long id;
        private String categoryName;
        private ItemDto.CategoryList itemDto;

        public ListView(CategoryItem categoryItem) {
            this.id = categoryItem.getId();
            this.categoryName = categoryItem.getCategoryName();
            itemDto = new ItemDto.CategoryList(categoryItem.getItem());
        }
    }

}
