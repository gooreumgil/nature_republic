package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.Category;
import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.dto.ItemDto;
import com.daeboo.naturerepublic.dto.MemberDto;
import com.daeboo.naturerepublic.dto.NewsDto;
import com.daeboo.naturerepublic.repository.ItemRepository;
import com.daeboo.naturerepublic.service.CategoryService;
import com.daeboo.naturerepublic.service.ItemService;
import com.daeboo.naturerepublic.service.MemberService;
import com.daeboo.naturerepublic.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

//    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

    private final MemberService memberService;
    private final ItemService itemService;
    private final CategoryService categoryService;
    private final NewsService newsService;

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
    public String createItemForm(@ModelAttribute("itemDto") ItemDto.CreateForm itemDto, Model model) {

        List<Category> categoryAll = categoryService.findAll();

        model.addAttribute("allCategories", categoryAll);
        model.addAttribute("itemDto", itemDto);

        return "admin/item/itemReg";
    }

    @PostMapping("/items/new")
    public String createItem(@ModelAttribute("itemDto") ItemDto.CreateForm itemDto) {

        itemService.save(itemDto);

        return "redirect:/admin";
    }

    @GetMapping("/items")
    public String itemList(Model model) {

        List<ItemDto.ListView> result = itemService.findAllWithSrc();

        model.addAttribute("itemDto", result);

        return "admin/item/itemList";

    }

    @GetMapping("/update/{id}")
    public String updateItemForm(@ModelAttribute("itemDto") ItemDto.UpdateForm result, @PathVariable Long id, @RequestParam(value = "status", defaultValue = "update") String status, Model model) {
        result = itemService.findByIdForUpdate(id);
        model.addAttribute("status", status);
        model.addAttribute("updateDto", result);

        return "admin/item/itemReg";
    }

    @PostMapping("/update")
    public String updateItem(@ModelAttribute("itemDto") ItemDto.UpdateForm itemDto) {

        itemService.update(itemDto);
        return "redirect:/admin";
    }

    @GetMapping("/news/new")
    public String createNewsForm(@ModelAttribute("newsDto") NewsDto.CreateForm newsDto , Model model) {

        model.addAttribute("newsDto", newsDto);
        return "admin/news/newsReg";

    }

    @PostMapping("/news/new")
    public String createNews(@ModelAttribute("newsDto") NewsDto.CreateForm newsDto) {

        newsService.save(newsDto);
        return "redirect:/admin";

    }






}
