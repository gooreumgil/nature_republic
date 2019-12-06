package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.dto.QnaDto;
import com.daeboo.naturerepublic.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentsController {

    private final CommentService commentService;

    @PostMapping("/qna")
    public String addQnaComment(@ModelAttribute("qnaCommentDto") QnaDto.RequestComment qnaCommentDto, Model model) {
        // TODO 여기서부터 하면 된다.
        return null;
    }

}
