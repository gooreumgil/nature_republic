package com.daeboo.naturerepublic.repository;

import com.daeboo.naturerepublic.domain.CategoryItem;
import com.daeboo.naturerepublic.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemDslRepository {

    Page<Item> findAll(Pageable pageable);

    @Query("select distinct i from Item i join fetch i.itemSrcs src")
    List<Item> findAllWithSrc();

    @Query("select i from Item i join fetch i.itemSrcs src where i.id = :id")
    Optional<Item> findByIdWithImg(@Param("id") Long id);

    @Query(value = "select distinct i from  Item i join fetch i.itemSrcs", countQuery = "select count(i) from Item i")
    Page<Item> findAllPage(Pageable pageable);
}
