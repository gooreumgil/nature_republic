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
import javax.servlet.http.HttpSession;
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

        int itemCount = 0;
//
        Page<Orders> orders = orderService.findByMemberId(member.getId(), PageRequest.of(0, 10, Sort.Direction.DESC, "orderDateTime"));

        List<OrderDto.Preview> collect = orders.stream().map(OrderDto.Preview::new).collect(Collectors.toList());
        Page<OrderDto.Preview> orderPages = new PageImpl<>(collect, orders.getPageable(), collect.size());
//
        Long count = orderService.onGoingCount(member.getId());

        model.addAttribute("memberDto", myPageIndex);
        model.addAttribute("orderPages", orderPages);
        model.addAttribute("onGoing", count);
        model.addAttribute("itemCount", itemCount);
        model.addAttribute("nav", "index");

        return "myPage/index";
    }

    @GetMapping("/detail")
    public String orderDetail(@RequestParam("id") Long id, Principal principal,  Model model) {

//        Order order = orderService.findById(id);
        Orders orders = orderService.findByIdQuery(id);
        OrderDto.DetailPage orderDto = new OrderDto.DetailPage(orders);

        String name = principal.getName();
//        Member member = memberService.findByName(name);

        model.addAttribute("orderDtos", orderDto);
        model.addAttribute("memberId", orderDto.getMemberId());

        return "myPage/orderDetail";

    }

    @GetMapping("/likes")
    public String itemLikes(@ModelAttribute("likeDelete") LikesDto.Delete deleteDto, Principal principal, Model model) {

        String name = principal.getName();
        Member member = memberService.findByName(name);

        Page<Likes> likesList = likesService.findAllByMemberId(member.getId(), PageRequest.of(0, 12, Sort.Direction.DESC, "likedAt"));
        List<LikesDto.LikePage> likesDtoList = likesList.stream().map(LikesDto.LikePage::new).collect(Collectors.toList());

        PageImpl<LikesDto.LikePage> likeDtoPages = new PageImpl<>(likesDtoList, likesList.getPageable(), likesDtoList.size());

        model.addAttribute("memberId", member.getId());
        model.addAttribute("likeDtos", likeDtoPages);
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
    public String reviews(Principal principal, Model model, HttpSession httpSession) {

        Member member = memberService.findByName(principal.getName());

        Page<Review> reviews = reviewService.findAllByMeberId(member.getId(), PageRequest.of(0, 12, Sort.Direction.ASC, "wroteAt"));

        List<ReviewResponseDto> reviewResponseDtos = reviews.stream().map(review -> {
            return new ReviewResponseDto(review);
        }).collect(Collectors.toList());

        Page<ReviewResponseDto> reviewResponseDtoPage = new PageImpl<>(reviewResponseDtos, reviews.getPageable(), reviewResponseDtos.size());

        model.addAttribute("newLineChar", "\n");
        model.addAttribute("reviewDtos", reviewResponseDtoPage);

        return "myPage/reviews";

    }

    @GetMapping("/update")
    public String memberUpdateForm(Principal principal, Model model) {

        String name = principal.getName();

        Member member = memberService.findByName(name);
        MemberDto.Update memberDto = new MemberDto.Update(member);

        model.addAttribute("memberDto", memberDto);

        return "myPage/update";

    }

    @PostMapping("/update")
    public String memberUpdate(@ModelAttribute("memberDto") MemberDto.Update memberDto) {

        memberService.update(memberDto);

        return "redirect:/myPage";

    }

















}
