package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.dto.ItemDto;
import com.daeboo.naturerepublic.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String itemIndex(Model model, @PageableDefault(size = 12, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Item> populars = itemRepository.findAll(PageRequest.of(0, 4, Sort.Direction.DESC, "likes"));
        List<ItemDto.PopularPreview> popularPreviews = populars.stream().map(item -> {
            return new ItemDto.PopularPreview(item);
        }).collect(Collectors.toList());

        model.addAttribute("populars", popularPreviews);

        Page<Item> categoryItems = itemRepository.findAll(pageable);
        List<ItemDto.CategoryList> items = categoryItems.stream().map(item -> {
            return new ItemDto.CategoryList(item);
        }).collect(Collectors.toList());

        model.addAttribute("items", items);

        return "item/index";



    }


}
