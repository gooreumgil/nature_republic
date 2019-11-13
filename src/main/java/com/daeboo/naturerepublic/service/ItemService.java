package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.Category;
import com.daeboo.naturerepublic.domain.CategoryItem;
import com.daeboo.naturerepublic.domain.ImgType;
import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.dto.ItemDto;
import com.daeboo.naturerepublic.repository.CategoryRepository;
import com.daeboo.naturerepublic.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public static String uploadDirectory = "C:\\Users\\hunte\\dev\\nature_republic\\src\\main\\resources\\static\\upload";

    public void save(ItemDto.Create itemDto) {

//        Map<String, List<MultipartFile>> stringListMap = new HashMap<>();

        List<MultipartFile> mainImg = itemDto.getMainImg();
        List<MultipartFile> detailImg = itemDto.getDetailImg();

//        stringListMap.put(ImgType.MAIN.name(), mainImg);
//        stringListMap.put(ImgType.DETAIL.name(), detailImg);

        List<String> mainImgPaths = new ArrayList<>();
        List<String> detailImgPaths = new ArrayList<>();

        mainImg.forEach(img -> {
            StringBuilder fileName = new StringBuilder();
            createFile(fileName, img);
            String mainImgPath = fileName.toString();
            mainImgPaths.add(mainImgPath);
        });

        detailImg.forEach(img -> {
            StringBuilder fileName = new StringBuilder();
            createFile(fileName, img);
            String detailImgPath = fileName.toString();
            detailImgPaths.add(detailImgPath);
        });

//        for (Map.Entry<String, List<MultipartFile>> entry : stringListMap.entrySet()) {
//
//            String s = entry.getKey();
//            List<MultipartFile> multipartFiles = entry.getValue();
//
//            if (s.contains(ImgType.MAIN.name())) {
//                multipartFiles.forEach(multipartFile -> {
//                    StringBuilder fileName = new StringBuilder();
//                    createFile(fileName, multipartFile);
//                    String mainImgPath = fileName.toString();
//                    mainImgPaths.add(mainImgPath);
//                });
//            }
//
//            if (s.contains(ImgType.DETAIL.name())) {
//                multipartFiles.forEach(multipartFile -> {
//                    StringBuilder fileName = new StringBuilder();
//                    createFile(fileName, multipartFile);
//                    String detailImgPath = fileName.toString();
//                    detailImgPaths.add(detailImgPath);
//                });
//            }
//        }

        String[] multiCategoryValues = itemDto.getMultiCategoryValues();
        List<Category> categories = new ArrayList<>();

        for (String multiCategoryValue : multiCategoryValues) {
            Category category = categoryRepository.findByName(multiCategoryValue).get();
            categories.add(category);
        }

        Item item = itemDto.toItemWithImg(itemDto.getNameKor(), itemDto.getNameEng(),
                itemDto.getPrice(), itemDto.getStockQuantity(),
                itemDto.getDescription(), itemDto.getCapacity(), categories, mainImgPaths, detailImgPaths);

        itemRepository.save(item);

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

        return itemRepository.findById(id).map(item -> {
            return new ItemDto.Detail(item);
        }).orElseThrow(()-> new RuntimeException("존재하지 않는 아이디입니다."));

    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }













}