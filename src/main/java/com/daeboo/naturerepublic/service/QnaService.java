package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.Comment;
import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.domain.Qna;
import com.daeboo.naturerepublic.dto.QnaDto;
import com.daeboo.naturerepublic.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnaService {

    private final QnaRepository qnaRepository;

    public Qna findById(Long id) {
        return qnaRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
    }

    public Page<Qna> findAllByMemberId(Long id, Pageable pageable) {
        return qnaRepository.findAllByMemberId(id, pageable);
    }

    public Page<Qna> findAllByItemId(Long id, Pageable pageable) {
        return qnaRepository.findAllByItemId(id, pageable);

    }

    @Transactional
    public void save(Qna qna) {
        qnaRepository.save(qna);
    }

}
