package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.domain.Qna;
import com.daeboo.naturerepublic.dto.QnaDto;
import com.daeboo.naturerepublic.service.ItemService;
import com.daeboo.naturerepublic.service.MemberService;
import com.daeboo.naturerepublic.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {

    private final QnaService qnaService;
    private final MemberService memberService;
    private final ItemService itemService;

    @PostMapping
    public String qnaSave(@ModelAttribute("qnaDto") @Valid QnaDto.RequestForm qnaDto, Errors errors, Principal principal, Model model, HttpServletRequest request) {

        if (errors.hasErrors()) {
            if (errors.getFieldError("content") != null) {
                throw new RuntimeException("값을 넣어라");
            }
        }

        String name = principal.getName();
        Member member = memberService.findByName(name);

        Item item = itemService.findById(qnaDto.getItemId());

        Qna qna = Qna.create(qnaDto, member, item);
        qnaService.save(qna);

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;

    }

}
