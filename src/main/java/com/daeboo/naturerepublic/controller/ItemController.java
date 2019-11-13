package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.Category;
import com.daeboo.naturerepublic.domain.CategoryItem;
import com.daeboo.naturerepublic.dto.CategoryDto;
import com.daeboo.naturerepublic.dto.CategoryItemDto;
import com.daeboo.naturerepublic.dto.ItemDto;
import com.daeboo.naturerepublic.repository.CategoryItemRepository;
import com.daeboo.naturerepublic.service.CategoryItemService;
import com.daeboo.naturerepublic.service.CategoryService;
import com.daeboo.naturerepublic.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final CategoryItemService categoryItemService;
    private final CategoryItemRepository categoryItemRepository;

    @GetMapping
    public String itemIndex(Model model, @PageableDefault(size = 12, page = 0, direction = Sort.Direction.DESC) Pageable pageable,
                            String currentCategory, String soryBy) {

        // Category List
        List<Category> categories = categoryService.findAll();
        List<CategoryDto.NewLine> result = categories.stream().map(category -> {
            return new CategoryDto.NewLine(category);
        }).collect(Collectors.toList());

        if (currentCategory == null) {
            currentCategory = "ALL";
        }

//        if (currentCategory.contains("<br/>")) {
//            String replace = StringUtils.replace(currentCategory, "<br/>", " ");
//            currentCategory = replace;
//        }

        model.addAttribute("currentCategory", currentCategory);
        model.addAttribute("categories", result);

        // Category Best
        Page<CategoryItemDto.ListView> listViewPages = categoryItemService.findALLByCategoryName(currentCategory, PageRequest.of(0, 4), "likes");
        model.addAttribute("populars", listViewPages);

//        Page<Item> populars = itemService.findAll(PageRequest.of(0, 4, Sort.Direction.DESC, "likes"));
//        List<ItemDto.PopularPreview> popularPreviews = populars.stream().map(item -> {
//            return new ItemDto.PopularPreview(item);
//        }).collect(Collectors.toList());
//
        // Item List
        if (soryBy == null) {
            soryBy = "likes";
        }

        int itemCount = categoryItemService.countAllByCategoryName(currentCategory);
        model.addAttribute("itemCount", itemCount);

        Page<CategoryItemDto.ListView> itemList = categoryItemService.findALLByCategoryName(currentCategory, pageable, soryBy);

//        List<Item> categoryItems = itemService.findAllWithImg(pageable);
//        List<ItemDto.CategoryList> items = categoryItems.stream().map(item -> {
//            return new ItemDto.CategoryList(item);
//        }).collect(Collectors.toList());

//        int moreView = pageable.getPageSize() + 12;

        model.addAttribute("categoryItems", itemList);
//        model.addAttribute("moreView", moreView);

        return "item/index";

    }

    @GetMapping("/detail")
    public String itemDetail(Long id, String currentCategory, Model model) {

        ItemDto.Detail findItem = itemService.findById(id);
        model.addAttribute("item", findItem);
        model.addAttribute("currentCategory", currentCategory);

        return "item/detail";

    }









}
