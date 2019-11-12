package com.daeboo.naturerepublic.repository;

import com.daeboo.naturerepublic.domain.CategoryItem;
import com.daeboo.naturerepublic.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findAll(Pageable pageable);

    @Query("select distinct i from Item i join fetch i.itemSrcs")
    List<Item> findAllWithImg(Pageable pageable);

}
