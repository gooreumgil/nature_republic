package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.CategoryItem;
import com.daeboo.naturerepublic.dto.CategoryItemDto;
import com.daeboo.naturerepublic.repository.CategoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryItemService {

    private final CategoryItemRepository categoryItemRepository;


    public Page<CategoryItemDto.ListView> findALLByCategoryName(String currentCategory, Pageable page, String sort) {
        Page<CategoryItem> allByCategoryName = categoryItemRepository.findALLByCategoryName(currentCategory, page, sort);
        List<CategoryItemDto.ListView> popularPreviews = allByCategoryName.stream().map(categoryItem -> {
            return new CategoryItemDto.ListView(categoryItem);
        }).collect(Collectors.toList());

        return new PageImpl<>(popularPreviews, PageRequest.of(0, 4), popularPreviews.size());
    }

    public int countAllByCategoryName(String currentCategory) {
        return categoryItemRepository.countAllByCategoryName(currentCategory);
    }
}
