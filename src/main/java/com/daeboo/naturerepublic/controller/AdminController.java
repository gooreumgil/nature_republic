package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.Category;
import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.dto.ItemDto;
import com.daeboo.naturerepublic.dto.MemberDto;
import com.daeboo.naturerepublic.repository.CategoryRepository;
import com.daeboo.naturerepublic.repository.ItemRepository;
import com.daeboo.naturerepublic.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

//    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
    public static String uploadDirectory = "C:\\Users\\hunte\\dev\\nature_republic\\src\\main\\resources\\static\\image";

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public String admin() {
        return "admin/index";
    }

    // member
    @GetMapping("/members")
    public String memberList(Model model) {

        List<Member> memberAll = memberRepository.findAll();
        List<MemberDto.ListView> result = memberAll.stream().map(member -> {
            return new MemberDto.ListView(member);
        }).collect(Collectors.toList());

        model.addAttribute("memberDto", result);

        return "admin/member/memberList";

    }

    // item
    @GetMapping("/items/new")
    public String createItemForm(@ModelAttribute("itemDto") ItemDto.Create itemDto, Model model) {

        List<Category> categoryAll = categoryRepository.findAll();

        model.addAttribute("allCategories", categoryAll);
        model.addAttribute("itemDto", itemDto);
        return "admin/item/itemReg";
    }

    @PostMapping("/items/new")
    public String createItem(@ModelAttribute("itemDto") ItemDto.Create itemDto) {

        StringBuilder mainFileName = new StringBuilder();
        MultipartFile mainImg = itemDto.getMainImg();
        createFileMain(mainFileName, mainImg);
        String mainImgPath = mainFileName.toString();

        StringBuilder detailFileName = new StringBuilder();
        MultipartFile detailImg = itemDto.getDetailImg();
        createFile(detailFileName, detailImg);
        String detailImgPath = detailFileName.toString();


        String[] multiCategoryValues = itemDto.getMultiCategoryValues();
        List<Category> categories = new ArrayList<>();

        for (String multiCategoryValue : multiCategoryValues) {
            Category category = categoryRepository.findByName(multiCategoryValue).get();
            categories.add(category);
        }

        Item item = itemDto.toItemWithImg(itemDto.getNameKor(), itemDto.getNameEng(),
                itemDto.getPrice(), itemDto.getStockQuantity(),
                itemDto.getDescription(), itemDto.getCapacity(), categories, mainImgPath, detailImgPath);

        itemRepository.save(item);

        return "redirect:/admin";
    }

    @GetMapping("/items")
    public String itemList(Model model) {

        List<Item> allItems = itemRepository.findAll();
        List<ItemDto.ListView> result = allItems.stream().map(item -> {
            return new ItemDto.ListView(item);
        }).collect(Collectors.toList());

        model.addAttribute("itemDto", result);

        return "admin/item/itemList";

    }

    private void createFileMain(StringBuilder mainFileName, MultipartFile mainImg) {
        String originalFilename = mainImg.getOriginalFilename();
        originalFilename = "main" + originalFilename;

        Path fileNameAndPath = Paths.get(uploadDirectory, originalFilename);
        mainFileName.append("main");
        mainFileName.append(mainImg.getOriginalFilename() + " ");

        try {
            Files.write(fileNameAndPath, mainImg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFile(StringBuilder fileName, MultipartFile img) {
        Path fileNameAndPath = Paths.get(uploadDirectory, img.getOriginalFilename());
        fileName.append(img.getOriginalFilename() + " ");

        try {
            Files.write(fileNameAndPath, img.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }











}
