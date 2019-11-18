package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.Category;
import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.dto.ItemDto;
import com.daeboo.naturerepublic.repository.CategoryRepository;
import com.daeboo.naturerepublic.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public static String uploadDirectory = "C:\\Users\\hunte\\dev\\nature_republic\\src\\main\\resources\\static\\upload";

    @Transactional
    public void save(ItemDto.CreateForm itemDto) {

        List<MultipartFile> mainImg = itemDto.getMainImg();
        List<MultipartFile> detailImg = itemDto.getDetailImg();

//        List<String> mainImgPaths = new ArrayList<>();
//        List<String> detailImgPaths = new ArrayList<>();

        mainImg.forEach(img -> {
            StringBuilder fileName = new StringBuilder();
            createFile(fileName, img);
//            String mainImgPath = fileName.toString();
//            mainImgPaths.add(mainImgPath);
        });

        detailImg.forEach(img -> {
            StringBuilder fileName = new StringBuilder();
            createFile(fileName, img);
//            String detailImgPath = fileName.toString();
//            detailImgPaths.add(detailImgPath);
        });


        String[] multiCategoryValues = itemDto.getMultiCategoryValues();
        List<Category> categories = new ArrayList<>();

        for (String multiCategoryValue : multiCategoryValues) {
            Category category = categoryRepository.findByName(multiCategoryValue).get();
            categories.add(category);
        }

        Item item = itemDto.toItemWithImg(categories);

        itemRepository.save(item);

    }

    public List<ItemDto.ListView> findAllWithSrc() {
        List<Item> allItems = itemRepository.findAllWithSrc();

        List<ItemDto.ListView> result = allItems.stream().map(item -> {
            return new ItemDto.ListView(item);
        }).collect(Collectors.toList());

        return result;
    }

    public List<ItemDto.ListView> findAll() {

        List<Item> allItems = itemRepository.findAll();

        List<ItemDto.ListView> result = allItems.stream().map(item -> {
            return new ItemDto.ListView(item);
        }).collect(Collectors.toList());

        return result;
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

    public ItemDto.Detail findById(Long id) {

        Optional<Item> optionalItem = itemRepository.findByIdWithImg(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            return new ItemDto.Detail(item);
        } else {
            throw new RuntimeException("존재하지 않는 상품입니다.");
        }

    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    public ItemDto.UpdateForm findByIdForUpdate(Long id) {

        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            return new ItemDto.UpdateForm(item);
        } else {
            throw new RuntimeException("존재하지 않는 상품입니다.");
        }

    }












}