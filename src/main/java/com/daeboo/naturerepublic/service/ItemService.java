package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.*;
import com.daeboo.naturerepublic.dto.ItemDto;
import com.daeboo.naturerepublic.repository.CategoryItemRepository;
import com.daeboo.naturerepublic.repository.CategoryRepository;
import com.daeboo.naturerepublic.repository.ItemRepository;
import com.daeboo.naturerepublic.repository.ItemSrcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
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
    private final ItemSrcRepository itemSrcRepository;
    private final CategoryItemRepository categoryItemRepository;
    private final EntityManager em;

    public static String uploadDirectory = "C:\\Users\\hunte\\dev\\nature_republic\\src\\main\\resources\\static\\upload";

    @Transactional
    public void save(ItemDto.CreateForm itemDto) {

        List<MultipartFile> mainImg = itemDto.getMainImg();
        List<MultipartFile> detailImg = itemDto.getDetailImg();

        List<String> mainImgPath = new ArrayList<>();
        List<String> detailImgPath = new ArrayList<>();

        mainImg.forEach(img -> {

            StringBuilder fileName = new StringBuilder();
            fileName.append(img.getOriginalFilename() + " ");

            createFile(img);

            mainImgPath.add(fileName.toString());

        });

        detailImg.forEach(img -> {

            StringBuilder fileName = new StringBuilder();

            fileName.append(img.getOriginalFilename() + " ");
            createFile(img);

            detailImgPath.add(fileName.toString());

        });


        List<String> multiCategoryValues = itemDto.getMultiCategoryValues();
        List<Category> categories = new ArrayList<>();

        for (String multiCategoryValue : multiCategoryValues) {
            Category category = categoryRepository.findByName(multiCategoryValue).get();
            categories.add(category);
        }

        Item item = itemDto.toItemWithImg(categories, mainImgPath, detailImgPath);

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

    public ItemDto.Detail findById(Long id) {

        Optional<Item> optionalItem = itemRepository.findByIdWithImg(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            return new ItemDto.Detail(item);
        } else {
            throw new RuntimeException("존재하지 않는 상품입니다.");
        }

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

    @Transactional
    public void update(ItemDto.UpdateForm itemDto) {

        // 파일 쓰기 //
        List<MultipartFile> mainImg = itemDto.getMainImg();
        List<MultipartFile> detailImg = itemDto.getDetailImg();

        List<String> mainImgPath = new ArrayList<>();
        List<String> detailImgPath = new ArrayList<>();

        mainImg.forEach(img -> {

            StringBuilder fileName = new StringBuilder();
            fileName.append(img.getOriginalFilename() + " ");

            createFile(img);

            mainImgPath.add(fileName.toString());
        });

        detailImg.forEach(img -> {

            StringBuilder fileName = new StringBuilder();
            fileName.append(img.getOriginalFilename() + " ");

            createFile(img);

            detailImgPath.add(fileName.toString());

        });

        // 업데이트 //
        List<String> categoryValues = itemDto.getMultiCategoryValues();
        categoryValues.add(0, "ALL");

        List<Category> categories = new ArrayList<>();

        for (String categoryValue : categoryValues) {
            Category category = categoryRepository.findByName(categoryValue).get();
            categories.add(category);
        }

        Item item = itemRepository.findById(itemDto.getId()).get();

        List<Long> originRemove = itemDto.getOriginRemove();

        Item updateItem = item.updateItem(itemDto, categories, originRemove, mainImgPath, detailImgPath);
        itemRepository.save(updateItem);

    }

    @Transactional
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }


    // 일반 메소드
    private void createFile(MultipartFile img) {
        Path fileNameAndPath = Paths.get(uploadDirectory, img.getOriginalFilename());

        try {
            Files.write(fileNameAndPath, img.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}