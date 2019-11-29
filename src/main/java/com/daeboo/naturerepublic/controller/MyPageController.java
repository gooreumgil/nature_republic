package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.dto.MemberDto;
import com.daeboo.naturerepublic.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/myPage")
public class MyPageController {

    private final MemberService memberService;

    @GetMapping
    public String index(Principal principal, Model model) {
        Member member = memberService.findByName(principal.getName());
        MemberDto.MyPageIndex myPageIndex = new MemberDto.MyPageIndex(member);

        return "myPage/index";
    }

}
