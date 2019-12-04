package com.daeboo.naturerepublic.repository;

import com.daeboo.naturerepublic.domain.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {

    List<Qna> findAllByMemberId(Long memberId);

}