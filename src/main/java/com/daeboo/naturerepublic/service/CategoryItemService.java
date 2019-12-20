package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.CategoryItem;
import com.daeboo.naturerepublic.dto.CategoryItemDto;
import com.daeboo.naturerepublic.repository.CategoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryItemService {

    private final CategoryItemRepository categoryItemRepository;

    @PersistenceContext
    private EntityManager em;

    public Page<CategoryItem> findALLByCategoryName(String currentCategory, Pageable pageable) {

        Page<CategoryItem> allByCategoryName = categoryItemRepository.findALLByCategoryName(currentCategory, pageable);

//        List<CategoryItemDto.ListView> popularPreviews = getListViews2(allByCategoryName);
//        return new PageImpl<>(popularPreviews, pageable, popularPreviews.size());

        return allByCategoryName;

    }

//    public List<CategoryItemDto.ListView> findALLByCategoryName(String currentCategory, int limit, String sortBy, String direction) {
//
//        List<CategoryItem> category = em.createQuery("select ci from CategoryItem ci join fetch ci.item i where ci.categoryName = :category order by " + sortBy + " " + direction, CategoryItem.class)
//                .setParameter("category", currentCategory)
//                .setFirstResult(0)
//                .setMaxResults(limit)
//                .getResultList()
//                .stream().collect(Collectors.toList());
//
//        List<CategoryItemDto.ListView> listViews2 = getListViews2(category);
//        return listViews2;
//
//    }

    private List<CategoryItemDto.ListView> getListViews2(List<CategoryItem> allByCategoryName) {
        return allByCategoryName.stream().map(categoryItem -> {
            return new CategoryItemDto.ListView(categoryItem);
        }).collect(Collectors.toList());
    }

    public int countAllByCategoryName(String currentCategory) {
        return categoryItemRepository.countAllByCategoryName(currentCategory);
    }
}
