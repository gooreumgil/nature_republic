package com.daeboo.naturerepublic.repository;

import com.daeboo.naturerepublic.domain.ItemSrc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemSrcRepository extends JpaRepository<ItemSrc, Long> {

    List<ItemSrc> findByItemId(Long id);

    void deleteByItemIdAndS3Key(Long id, String s3Key);

}
