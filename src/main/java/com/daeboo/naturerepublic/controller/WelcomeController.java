package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.dto.ItemDto;
import com.daeboo.naturerepublic.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class WelcomeController {

    private final ItemRepository itemRepository;

    @GetMapping("/")
    public String welcome(Model model) {

        // Best Items
        Page<Item> populars = itemRepository.findAll(PageRequest.of(0, 4, Sort.Direction.DESC, "likes"));

        List<ItemDto.PopularPreview> popularResult = populars.stream().map(item -> {
            return new ItemDto.PopularPreview(item);
        }).collect(Collectors.toList());

        // Latest Items
        Page<Item> latestItems = itemRepository.findAll(PageRequest.of(0, 4, Sort.Direction.DESC, "registerAt"));

        List<ItemDto.LatestPreview> latestResult = latestItems.stream().map(item -> {
            return new ItemDto.LatestPreview(item);
        }).collect(Collectors.toList());

        model.addAttribute("populars", popularResult);
        model.addAttribute("latestItems", latestResult);

        return "welcome/index";
    }

    @GetMapping("/brand")
    public String brand() {
        return "brand/index";
    }

}
