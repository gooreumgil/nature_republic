package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.Category;
import com.daeboo.naturerepublic.dto.ItemDto;
import com.daeboo.naturerepublic.dto.MemberDto;
import com.daeboo.naturerepublic.service.CategoryService;
import com.daeboo.naturerepublic.service.ItemService;
import com.daeboo.naturerepublic.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

//    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

    private final MemberService memberService;
    private final ItemService itemService;
    private final CategoryService categoryService;

    @GetMapping
    public String admin() {
        return "admin/index";
    }

    // member
    @GetMapping("/members")
    public String memberList(Model model) {

        List<MemberDto.ListView> result = memberService.findAll();

        model.addAttribute("memberDto", result);

        return "admin/member/memberList";

    }

    // item
    @GetMapping("/items/new")
    public String createItemForm(@ModelAttribute("itemDto") ItemDto.Create itemDto, Model model) {

        List<Category> categoryAll = categoryService.findAll();

        model.addAttribute("allCategories", categoryAll);
        model.addAttribute("itemDto", itemDto);

        return "admin/item/itemReg";
    }

    @PostMapping("/items/new")
    public String createItem(@ModelAttribute("itemDto") ItemDto.Create itemDto) {

        itemService.save(itemDto);

        return "redirect:/admin";
    }

    @GetMapping("/items")
    public String itemList(Model model) {

        List<ItemDto.ListView> result = itemService.findAllWithSrc();

        model.addAttribute("itemDto", result);

        return "admin/item/itemList";

    }












}
