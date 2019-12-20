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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
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
        categoryAll.removeIf(category -> category.getName().equals("ALL"));

        model.addAttribute("allCategories", categoryAll);
        model.addAttribute("itemDto", itemDto);

        return "admin/item/itemReg";
    }

    @PostMapping("/items/new")
    public String createItem(@ModelAttribute("itemDto") ItemDto.CreateForm itemDto) {

        List<MultipartFile> mainImg = itemDto.getMainImg();
        List<MultipartFile> detailImg = itemDto.getDetailImg();

        List<String> mainRemove = itemDto.getMainRemove();
        List<String> detailRemove = itemDto.getDetailRemove();

        mainImg.remove(mainImg.size() - 1);
        detailImg.remove(detailImg.size() - 1);

        for (String s : mainRemove) {
            mainImg.removeIf(x -> x.getOriginalFilename().equals(s));
        }

        for (String s : detailRemove) {
            detailImg.removeIf(x -> x.getOriginalFilename().equals(s));
        }

        List<String> categoryValues = itemDto.getMultiCategoryValues();
        categoryValues.add(0, "ALL");

        itemService.save(itemDto);

        return "redirect:/admin";
    }

    @GetMapping("/items")
    public String itemList(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC, sort = "registerAt") Pageable pageable, Model model) {

//        List<Item> allItems = itemService.findAllWithSrc();
        Page<Item> allItems = itemService.findAllPage(pageable);

        Page<ItemDto.ListView> listViews = allItems.map(ItemDto.ListView::new);

        int size = listViews.getSize();
        long totalElements = listViews.getTotalElements();
        int totalPages = listViews.getTotalPages();
        int number = listViews.getNumber();
        int numberOfElements = listViews.getNumberOfElements();

        model.addAttribute("itemDto", listViews);

        return "admin/item/itemList";

    }

    @GetMapping("/items/update/{id}")
    public String updateItemForm(@ModelAttribute("itemDto") ItemDto.UpdateForm result, @PathVariable Long id, @RequestParam(value = "status", defaultValue = "update") String status, Model model) {

        result = itemService.findByIdForUpdate(id);
        List<Category> allCategories = categoryService.findAll();

        allCategories.removeIf(category -> category.getName().equals("ALL"));

        model.addAttribute("status", status);
        model.addAttribute("updateDto", result);
        model.addAttribute("allCategories", allCategories);

        return "admin/item/itemReg";
    }

    @PostMapping("/items/update")
    public String updateItem(@ModelAttribute("itemDto") ItemDto.UpdateForm itemDto) {

        List<MultipartFile> mainImg = itemDto.getMainImg();
        List<MultipartFile> detailImg = itemDto.getDetailImg();

        mainImg.remove(mainImg.size() - 1);
        detailImg.remove(detailImg.size() - 1);

        List<String> mainRemove = itemDto.getMainRemove();
        List<String> detailRemove = itemDto.getDetailRemove();

        for (String s : mainRemove) {
            mainImg.removeIf(x -> x.getOriginalFilename().equals(s));
        }

        for (String s : detailRemove) {
            detailImg.removeIf(x -> x.getOriginalFilename().equals(s));
        }

        itemService.update(itemDto);
        return "redirect:/admin";
    }

    @DeleteMapping("/items/delete/{id}")
    public String itemDelete(@PathVariable("id") Long id) {
        itemService.deleteById(id);
        return "redirect:/admin/items";
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
