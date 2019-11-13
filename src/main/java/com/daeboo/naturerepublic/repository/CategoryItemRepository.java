package com.daeboo.naturerepublic.repository;

import com.daeboo.naturerepublic.domain.Category;
import com.daeboo.naturerepublic.domain.CategoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryItemRepository extends JpaRepository<CategoryItem, Long> {

    @Query(value = "select ci from CategoryItem ci join fetch ci.item i where ci.categoryName = :category order by :sortBy desc",
            countQuery = "select count(ci) from CategoryItem ci")
    Page<CategoryItem> findALLByCategoryName(@Param("category") String category, Pageable pageable, @Param("sortBy") String sortBy);

    int countAllByCategoryName(String currentCategory);
}
