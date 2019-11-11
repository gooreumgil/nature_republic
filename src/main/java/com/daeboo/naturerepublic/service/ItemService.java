package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.Category;
import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.dto.ItemDto;
import com.daeboo.naturerepublic.repository.CategoryRepository;
import com.daeboo.naturerepublic.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public static String uploadDirectory = "C:\\Users\\hunte\\dev\\nature_republic\\src\\main\\resources\\static\\image";


    public void save(ItemDto.Create itemDto) {

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
    }

    public List<ItemDto.ListView> findAll() {

        List<Item> allItems = itemRepository.findAll();

        List<ItemDto.ListView> result = allItems.stream().map(item -> {
            return new ItemDto.ListView(item);
        }).collect(Collectors.toList());

        return result;
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
