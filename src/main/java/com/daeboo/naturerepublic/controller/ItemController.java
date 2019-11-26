package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.Category;
import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.dto.CategoryDto;
import com.daeboo.naturerepublic.dto.CategoryItemDto;
import com.daeboo.naturerepublic.dto.ItemDto;
import com.daeboo.naturerepublic.service.CategoryItemService;
import com.daeboo.naturerepublic.service.CategoryService;
import com.daeboo.naturerepublic.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    private final CategoryItemService categoryItemService;
    private final CategoryService categoryService;
    private final LinkedHashMap<String, String> sortList;
//    private final List<CategoryDto.NewLine> categoryList;

    @GetMapping
    public String itemIndex(Model model, @PageableDefault(size = 12, page = 0, direction = Sort.Direction.DESC, sort = "item.likes") Pageable pageable,
                            String currentCategory, Integer offset) {

        model.addAttribute("offset", offset);

        if (currentCategory == null) {
            currentCategory = "ALL";
        }

        List<Category> all = categoryService.findAll();
        List<CategoryDto.NewLine> categoryList = all.stream().map(category -> {
            return new CategoryDto.NewLine(category);
        }).collect(Collectors.toList());

        model.addAttribute("currentCategory", currentCategory);
        model.addAttribute("categories", categoryList);

        // sort List
        model.addAttribute("sorts", sortList);

        //  Best Items
        Page<CategoryItemDto.ListView> topBest = categoryItemService
                .findALLByCategoryName("ALL", PageRequest.of(0, 4, Sort.Direction.DESC, "item.likes"));
        model.addAttribute("populars", topBest);

        // Item Count
        int itemCount = categoryItemService.countAllByCategoryName(currentCategory);
        model.addAttribute("itemCount", itemCount);

        // Item List
        Page<CategoryItemDto.ListView> itemList = categoryItemService.findALLByCategoryName(currentCategory, pageable);
        model.addAttribute("categoryItems", itemList);

        return "item/index";

    }

    @GetMapping("/detail")
    public String itemDetail(Long id, String currentCategory, Model model) {

        if (currentCategory == null) {
            currentCategory = "ALL";
        }

        Item findItem = itemService.findById(id);
        ItemDto.Detail detail = new ItemDto.Detail(findItem);
        model.addAttribute("item", detail);
        model.addAttribute("currentCategory", currentCategory);

        String mainCategory = detail.getMainCategory();
        Page<CategoryItemDto.ListView> allByCategoryName = categoryItemService.findALLByCategoryName(mainCategory, PageRequest.of(0, 4, Sort.Direction.DESC, "item.likes"));
        model.addAttribute("categoryBests", allByCategoryName);

        return "item/detail";

    }







}
