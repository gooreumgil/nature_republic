package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.Qna;
import com.daeboo.naturerepublic.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnaService {

    private final QnaRepository qnaRepository;

    public List<Qna> findAllByMemberId(Long id) {
        return qnaRepository.findAllByMemberId(id);
    }

}
