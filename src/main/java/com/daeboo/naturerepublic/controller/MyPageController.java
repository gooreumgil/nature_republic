package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.*;
import com.daeboo.naturerepublic.dto.*;
import com.daeboo.naturerepublic.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
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
    public String index0(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC, sort = "orderDateTime") Pageable pageable, Principal principal, Model model) {

        Member member = memberService.findByName(principal.getName());
        MemberDto.MyPageIndex myPageIndex = new MemberDto.MyPageIndex(member);

        int itemCount = 0;

        Page<Orders> orders = orderService.findByMemberId(member.getId(), pageable);
        Page<OrderDto.Preview> orderPages = orders.map(OrderDto.Preview::new);

        Long count = orderService.onGoingCount(member.getId());

        model.addAttribute("memberDto", myPageIndex);
        model.addAttribute("orderPages", orderPages);
        model.addAttribute("onGoing", count);
        model.addAttribute("itemCount", itemCount);
        model.addAttribute("nav", "index");

        return "myPage/index";
    }

    @GetMapping("/detail")
    public String orderDetail(@RequestParam("id") Long id, Principal principal, Model model) {

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
    public String itemLikes(@PageableDefault(page = 0, size = 12, direction = Sort.Direction.DESC, sort = "likedAt") Pageable pageable,
                            @ModelAttribute("likeDelete") LikesDto.Delete deleteDto, Principal principal, Model model) {

        String name = principal.getName();
        Member member = memberService.findByName(name);

        Page<Likes> likesList = likesService.findAllByMemberId(member.getId(), pageable);
        Page<LikesDto.LikePage> likeDtoPages = likesList.map(LikesDto.LikePage::new);

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
    public String qna(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC, sort = "wroteAt") Pageable pageable, Principal principal, Model model) {

        Member member = memberService.findByName(principal.getName());

        Page<Qna> qnaList = qnaService.findAllByMemberId(member.getId(), pageable);
        Page<QnaDto.MyPage> myPages = qnaList.map(QnaDto.MyPage::new);

        model.addAttribute("qnaDtos", myPages);
        model.addAttribute("newLineChar", "\n");

        return "myPage/qna";
    }

    @GetMapping("/reviews")
    public String reviews(@PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC, sort = "wroteAt") Pageable pageable, Principal principal, Model model, HttpSession httpSession) {

        Member member = memberService.findByName(principal.getName());

        Page<Review> reviews = reviewService.findAllByMeberId(member.getId(), pageable);
        Page<ReviewResponseDto> reviewResponseDtoPage = reviews.map(ReviewResponseDto::new);

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
