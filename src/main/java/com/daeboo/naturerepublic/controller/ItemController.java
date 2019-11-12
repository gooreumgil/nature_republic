package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.Category;
import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.dto.ItemDto;
import com.daeboo.naturerepublic.repository.ItemRepository;
import com.daeboo.naturerepublic.service.CategoryService;
import com.daeboo.naturerepublic.service.ItemService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    @GetMapping
    public String itemIndex(Model model, @PageableDefault(size = 12, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String currentCategory) {

        // Category Best
        Page<Item> populars = itemService.findAll(PageRequest.of(0, 4, Sort.Direction.DESC, "likes"));
        List<ItemDto.PopularPreview> popularPreviews = populars.stream().map(item -> {
            return new ItemDto.PopularPreview(item);
        }).collect(Collectors.toList());

        model.addAttribute("populars", popularPreviews);

        // Category List
        List<Category> categories = categoryService.findAll();
        List<Category> result = categories.stream().map(category -> {
            return Category.NewLine(category);
        }).collect(Collectors.toList());

        if (currentCategory == null) {
            currentCategory = "ALL";
        }

        model.addAttribute("currentCategory", currentCategory);
        model.addAttribute("newLineChar", '\n');
        model.addAttribute("categories", result);

        // Item List
        List<Item> categoryItems = itemService.findAllWithImg(pageable);
        List<ItemDto.CategoryList> items = categoryItems.stream().map(item -> {
            return new ItemDto.CategoryList(item);
        }).collect(Collectors.toList());

        int moreView = pageable.getPageSize() + 12;

        model.addAttribute("items", items);
        model.addAttribute("moreView", moreView);

        return "item/index";



    }


}
