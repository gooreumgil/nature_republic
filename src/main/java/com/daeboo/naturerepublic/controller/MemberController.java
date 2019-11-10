package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.dto.MemberDto;
import com.daeboo.naturerepublic.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/new")
    public String memberCreateForm(@ModelAttribute("memberDto") MemberDto.SignUp memberDto, Model model) {

        model.addAttribute("memberDto", memberDto);
        return "signUp";

    }

    @PostMapping("/members/new")
    public String memberCreate(@ModelAttribute("memberDto") @Valid MemberDto.SignUp memberDto, BindingResult result) {

        Member member = memberDto.toMember();
        memberRepository.save(member);

        return "redirect:/";

    }




















}
