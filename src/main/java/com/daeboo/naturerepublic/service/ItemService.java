package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.*;
import com.daeboo.naturerepublic.dto.ItemDto;
import com.daeboo.naturerepublic.dto.ItemSearchDto;
import com.daeboo.naturerepublic.repository.CategoryItemRepository;
import com.daeboo.naturerepublic.repository.CategoryRepository;
import com.daeboo.naturerepublic.repository.ItemRepository;
import com.daeboo.naturerepublic.repository.ItemSrcRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import static com.daeboo.naturerepublic.domain.QItem.item;

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

    // 검색
    public Page<ItemDto.Search> itemSearch(Pageable pageable, ItemSearchDto itemSearchDto) {

        BooleanBuilder builder = new BooleanBuilder();
        if (Strings.isNotEmpty(itemSearchDto.getName())) builder.and(item.nameKor.like("%" + itemSearchDto.getName() + "%"));

        Page<Item> itemPage = itemRepository.findItem(itemSearchDto, pageable);
        return itemPage.map(ItemDto.Search::new);

    }

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

//    public List<Item> findAllWithSrc() {
//        return itemRepository.findAllWithSrc();
//
//    }

    public Page<Item> findAllPage(Pageable pageable) {
        return itemRepository.findAllPage(pageable);

    }

    public List<ItemDto.ListView> findAll() {

        List<Item> allItems = itemRepository.findAll();

        List<ItemDto.ListView> result = allItems.stream().map(item -> {
            return new ItemDto.ListView(item);
        }).collect(Collectors.toList());

        return result;
    }

    public Item findById(Long id) {

        Optional<Item> optionalItem = itemRepository.findByIdWithImg(id);
        if (optionalItem.isPresent()) {
            return optionalItem.get();
        } else {
            throw new RuntimeException("존재하지 않는 상품입니다.");
        }

    }

    public Item findByIdForUpdate(Long id) {

        return itemRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다"));


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