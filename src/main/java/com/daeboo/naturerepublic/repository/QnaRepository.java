package com.daeboo.naturerepublic.repository;

import com.daeboo.naturerepublic.domain.Qna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QnaRepository extends JpaRepository<Qna, Long> {

    Page<Qna> findAllByMemberId(Long memberId, Pageable pageable);
    Page<Qna> findAllByItemId(Long itemId, Pageable pageable);

}
