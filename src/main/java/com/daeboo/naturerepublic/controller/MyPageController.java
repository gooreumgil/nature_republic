package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.*;
import com.daeboo.naturerepublic.dto.*;
import com.daeboo.naturerepublic.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private final QnaService qnaService;
    private final ReviewService reviewService;

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
    public String orderDetail(@RequestParam("id") Long id, Principal principal,  Model model) {

        Order order = orderService.findById(id);
        OrderDto.DetailPage orderDto = new OrderDto.DetailPage(order);

        String name = principal.getName();
        Member member = memberService.findByName(name);

        model.addAttribute("orderDtos", orderDto);
        model.addAttribute("memberId", member.getId());

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

    @GetMapping("/qna")
    public String qna(Principal principal, Model model) {

        Member member = memberService.findByName(principal.getName());

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "wroteAt");

        Page<Qna> qnaList = qnaService.findAllByMemberId(member.getId(), pageRequest);
        List<QnaDto.MyPage> qnaDtos = qnaList.stream().map(QnaDto.MyPage::new).collect(Collectors.toList());
        Page<QnaDto.MyPage> myPages = new PageImpl<>(qnaDtos, pageRequest, qnaDtos.size());

        model.addAttribute("qnaDtos", myPages);
        model.addAttribute("newLineChar", "\n");

        return "myPage/qna";
    }

    @GetMapping("/reviews")
    public String reviews(Principal principal, Model model) {

        Member member = memberService.findByName(principal.getName());

        List<Review> reviews = reviewService.findAllByMeberId(member.getId());

        List<ReviewResponseDto> reviewResponseDtos = reviews.stream().map(review -> {
            return new ReviewResponseDto(review);
        }).collect(Collectors.toList());

        model.addAttribute("newLineChar", "\n");
        model.addAttribute("reviewDtos", reviewResponseDtos);

        return "myPage/reviews";

    }

















}
