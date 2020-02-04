package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.dto.MemberDto;
import com.daeboo.naturerepublic.repository.MemberRepository;
import com.daeboo.naturerepublic.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/new")
    public String memberCreateForm(@ModelAttribute("memberDto") MemberDto.SignUp memberDto, Model model) {

        model.addAttribute("memberDto", memberDto);
        return "members/signUp";

    }

    @PostMapping("/new")
    public String memberCreate(@ModelAttribute("memberDto") @Valid MemberDto.SignUp memberDto, BindingResult result) {

        Member member = memberDto.toMember();
        memberService.save(member);

        return "redirect:/";

    }


}
