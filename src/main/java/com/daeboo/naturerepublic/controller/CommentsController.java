package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.domain.Qna;
import com.daeboo.naturerepublic.dto.CommentDto;
import com.daeboo.naturerepublic.dto.QnaDto;
import com.daeboo.naturerepublic.service.CommentService;
import com.daeboo.naturerepublic.service.ItemService;
import com.daeboo.naturerepublic.service.MemberService;
import com.daeboo.naturerepublic.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentsController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final ItemService itemService;
    private final QnaService qnaService;

    @PostMapping("/qna")
    public String addQnaComment(@ModelAttribute("qnaCommentDto") @Valid QnaDto.RequestComment qnaCommentDto, Model model, HttpServletRequest request) {

        Member member = memberService.findbyId(qnaCommentDto.getMemberId());
        Item item = itemService.findById(qnaCommentDto.getItemId());
        Qna qna = qnaService.findById(qnaCommentDto.getQnaId());

        commentService.addQnaComment(qnaCommentDto.getContent(), member, item, qna);

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }

    @PostMapping("/update/qna")
    public String updateQnaComment(@ModelAttribute("commentUpdateDto") CommentDto.RequestCommentUpdate commentUpdateDto, HttpServletRequest request) {

        commentService.updateQnaComment(commentUpdateDto);

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;

    }

    @DeleteMapping("/delete/qna")
    public String deleteQnaComment(@RequestParam("commentId") Long commentId, Model model, HttpServletRequest request) {
        commentService.deleteById(commentId);

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;
    }

}
