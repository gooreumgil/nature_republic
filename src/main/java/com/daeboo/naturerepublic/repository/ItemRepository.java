package com.daeboo.naturerepublic.repository;

import com.daeboo.naturerepublic.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
