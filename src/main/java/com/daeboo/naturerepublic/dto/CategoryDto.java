package com.daeboo.naturerepublic.dto;

import com.daeboo.naturerepublic.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

public class CategoryDto {

    @Getter @Setter
    @NoArgsConstructor
    public static class NewLine {
        String categoryName;

        public NewLine(Category category) {
            String name = category.getName();
            if (name.contains(" ")) {
                String replace = StringUtils.replace(name, " ", "<br/>");
                this.categoryName = replace;
            } else {
                categoryName = name;
            }
        }
    }

}
