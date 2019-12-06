package com.daeboo.naturerepublic.service;

import com.daeboo.naturerepublic.domain.Comment;
import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.domain.Qna;
import com.daeboo.naturerepublic.dto.QnaDto;
import com.daeboo.naturerepublic.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public void addQnaComment(String content, Member member, Item item, Qna qna) {

        Comment comment = Comment.createComment(content, item, member, qna);
        commentRepository.save(comment);

        qna.setStatusComp();

    }

}
