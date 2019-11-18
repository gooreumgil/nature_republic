package com.daeboo.naturerepublic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<CategoryItem> categoryItems;

    public static Category createCategory(String name) {
        Category category = new Category();
        category.name = name;

        return category;
    }

    public static Category NewLine(Category category) {
        Category newCategory = new Category();
        String categoryName = category.getName();

        if (categoryName.contains(" ")) {
//            StringBuilder stringBuilder = new StringBuilder();
//
//            String[] split = categoryName.split("\\s");
//            stringBuilder.append(split[0]);
//            stringBuilder.append('\n');
//            stringBuilder.append(split[1]);
//
//            String s = stringBuilder.toString();
//            String replace = StringUtils.replace(s, "\n", "<br/>");
            String replace = StringUtils.replace(categoryName, " ", "<br/>");

            newCategory.name = replace;

        }
        else {
            newCategory.name = categoryName;
        }
        return newCategory;
    }


    public void addCategoryItem(CategoryItem categoryItem) {
        this.categoryItems.add(categoryItem);
    }

    public void updateCategory(Category categoryOne) {
        this.name = categoryOne.getName();
    }
}
