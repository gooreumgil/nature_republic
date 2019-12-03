package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.Likes;
import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.domain.Order;
import com.daeboo.naturerepublic.dto.LikesDto;
import com.daeboo.naturerepublic.dto.MemberDto;
import com.daeboo.naturerepublic.dto.OrderDto;
import com.daeboo.naturerepublic.dto.OrderItemDto;
import com.daeboo.naturerepublic.service.LikesService;
import com.daeboo.naturerepublic.service.MemberService;
import com.daeboo.naturerepublic.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/myPage")
public class MyPageController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final LikesService likesService;

    @GetMapping
    public String index(Principal principal,  Model model) {
        Member member = memberService.findByName(principal.getName());
        MemberDto.MyPageIndex myPageIndex = new MemberDto.MyPageIndex(member);

        Long count = orderService.onGoingCount(member.getId());

        model.addAttribute("memberDto", myPageIndex);
        model.addAttribute("onGoing", count);
        model.addAttribute("nav", "index");

        return "myPage/index";
    }

    @GetMapping("/detail")
    public String orderDetail(@RequestParam("id") Long id,  Model model) {

        Order order = orderService.findById(id);
        OrderDto.DetailPage orderDto = new OrderDto.DetailPage(order);

        model.addAttribute("orderDtos", orderDto);
        model.addAttribute("nav", "index");

        return "myPage/orderDetail";

    }

    @GetMapping("/likes")
    public String itemLikes(@ModelAttribute("likeDelete") LikesDto.Delete deleteDto, Principal principal, Model model) {

        String name = principal.getName();
        Member member = memberService.findByName(name);

        List<Likes> likesList = likesService.findAllByMemberId(member.getId());
        List<LikesDto.LikePage> likesDtoList = likesList.stream().map(LikesDto.LikePage::new).collect(Collectors.toList());

        model.addAttribute("memberId", member.getId());
        model.addAttribute("likeDtos", likesDtoList);
        model.addAttribute("deleteDto", deleteDto);
        model.addAttribute("nav", "likes");

        return "myPage/likes";

    }

    @DeleteMapping("/removeLikes")
    public String removeLikes(@ModelAttribute("likeDelete") LikesDto.Delete deleteDto, Model model, HttpServletRequest request) {

        List<Long> likeIds = deleteDto.getLikeIds();
        likesService.removeByLikeId(likeIds);

        String referer = request.getHeader("Referer");

        return "redirect:" + referer;

    }


}
