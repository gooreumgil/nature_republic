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
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;
    private final CategoryItemService categoryItemService;

    @GetMapping
    public String itemIndex(Model model, @PageableDefault(size = 12, page = 0, direction = Sort.Direction.DESC, sort = "item.likes") Pageable pageable,
                            String currentCategory, Integer offset) {

        model.addAttribute("offset", offset);

        // Category List
        List<Category> categories = categoryService.findAll();
        List<CategoryDto.NewLine> result = categories.stream().map(category -> {
            return new CategoryDto.NewLine(category);
        }).collect(Collectors.toList());

        if (currentCategory == null) {
            currentCategory = "ALL";
        }

        model.addAttribute("currentCategory", currentCategory);
        model.addAttribute("categories", result);

        // sort List
        LinkedHashMap<String, String> sortList = new LinkedHashMap<>();
        sortList.put("item.likes", "인기상품순");
        sortList.put("item.registerAt", "등록일순");
        sortList.put("item.price,ASC", "낮은가격순");
        sortList.put("item.price,DESC", "높은가격순");

        model.addAttribute("sorts", sortList);

        // Category Best
//        Page<CategoryItemDto.ListView> listViewPages = categoryItemService.
//                findALLByCategoryName(currentCategory, PageRequest.of(0, 4), "price", direction);

        Page<CategoryItemDto.ListView> listViewPages = categoryItemService.
                findALLByCategoryName(currentCategory, PageRequest.of(0, 4, Sort.Direction.DESC, "item.likes"));


        model.addAttribute("populars", listViewPages);

//         Item List
        int itemCount = categoryItemService.countAllByCategoryName(currentCategory);
        model.addAttribute("itemCount", itemCount);

        Page<CategoryItemDto.ListView> itemList = categoryItemService.findALLByCategoryName(currentCategory, pageable);

        model.addAttribute("categoryItems", itemList);

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
