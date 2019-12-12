package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.domain.Order;
import com.daeboo.naturerepublic.domain.OrderItem;
import com.daeboo.naturerepublic.dto.*;
import com.daeboo.naturerepublic.service.ItemService;
import com.daeboo.naturerepublic.service.MemberService;
import com.daeboo.naturerepublic.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping
    public String orderForm(@RequestParam("itemId") Long itemId, int count, @ModelAttribute("orderItemDto") OrderItemDto.Create orderItemDto, Principal principal, Model model) {

        String name = principal.getName();
        Member member = memberService.findByName(name);
        MemberDto.OrderPage memberOrderPage = new MemberDto.OrderPage(member);

        Item item = itemService.findById(itemId);
        ItemDto.Order itemOrder = new ItemDto.Order(item);

        model.addAttribute("orderItemDto", orderItemDto);
        model.addAttribute("memberDto", memberOrderPage);
        model.addAttribute("itemDto", itemOrder);
        model.addAttribute("count", count);

        return "order/index";
    }

    @PostMapping
    public String orderCreate(@ModelAttribute OrderItemDto.Create orderItemDto, Model model) {

        Order order = orderService.order(orderItemDto);

        OrderDto.OrderComplete orderDtoComplete = new OrderDto.OrderComplete(order);

        model.addAttribute("orderDto", orderDtoComplete);

        return "order/complete";

    }

    @PatchMapping("/complete")
    public String orderComplete(@RequestParam Long orderId, Long memberId, boolean isReview) {

        orderService.orderComplete(orderId, memberId, isReview);
        return "myPage/index";
    }

    @GetMapping("/review")
    public String orderReviewForm(Long orderId, Long memberId, Model model) {

        Order order = orderService.findById(orderId);
        List<OrderItem> orderItems = order.getOrderItems();

        List<ItemDto.ReviewForm> itemList = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            ItemDto.ReviewForm itemDto = new ItemDto.ReviewForm(orderItem.getItem());
            itemList.add(itemDto);
        }

        List<ReviewDto> reviewDtos = new ArrayList<>();

        for (ItemDto.ReviewForm reviewForm : itemList) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setItemDto(reviewForm);
            reviewDtos.add(reviewDto);
        }

//        for (int i = 0; i < itemList.size(); i++) {
//            CommentDto.OrderReview orderReview = new CommentDto.OrderReview();
//            reviewRequestDto.getOrderReviews().add(orderReview);
//        }

        model.addAttribute("reviewDtos", reviewDtos);
//        model.addAttribute("itemDtos", itemList);
        model.addAttribute("orderId", orderId);
        model.addAttribute("memberId", memberId);

        return "order/review";
    }

    @PostMapping("/review")
    public String orderReview(@ModelAttribute("reviewRequest") ReviewDto reviewDto) {
        List<MultipartFile> srcs = reviewDto.getSrcs();
        List<String> remove = reviewDto.getRemove();
        Long orderId = reviewDto.getOrderId();
        Long memberId = reviewDto.getMemberId();

        return "redirect:/myPage";
    }


}
