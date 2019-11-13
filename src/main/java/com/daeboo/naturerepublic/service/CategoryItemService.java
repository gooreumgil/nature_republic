package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.CategoryItem;
import com.daeboo.naturerepublic.repository.CategoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryItemService {

    private final CategoryItemRepository categoryItemRepository;


    public Page<CategoryItem> findALLByCategoryName(String currentCategory, Pageable page, String sort) {
        return categoryItemRepository.findALLByCategoryName(currentCategory, page, sort);
    }

    public int countAllByCategoryName(String currentCategory) {
        return categoryItemRepository.countAllByCategoryName(currentCategory);
    }
}
