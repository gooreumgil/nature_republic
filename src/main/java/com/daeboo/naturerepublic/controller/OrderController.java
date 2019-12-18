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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderService orderService;

//    @GetMapping
//    public String orderForm(@RequestParam("itemId") Long itemId, int count, @ModelAttribute("orderItemDto") OrderItemDto.Create orderItemDto, Principal principal, Model model) {
//
//        String name = principal.getName();
//        Member member = memberService.findByName(name);
//        MemberDto.OrderPage memberOrderPage = new MemberDto.OrderPage(member);
//
//        Item item = itemService.findById(itemId);
//        ItemDto.Order itemOrder = new ItemDto.Order(item);
//
//        model.addAttribute("orderItemDto", orderItemDto);
//        model.addAttribute("memberDto", memberOrderPage);
//        model.addAttribute("itemDto", itemOrder);
//        model.addAttribute("count", count);
//
//        return "order/index";
//    }

    @GetMapping
    public String orderForm(@ModelAttribute("shoppingWrapper") ShoppingCartWrapper shoppingWrapper, @ModelAttribute("orderWrapper") OrderItemDtoWrapper orderWrapper, Principal principal, Model model) {

        String name = principal.getName();
        Member member = memberService.findByName(name);
        MemberDto.OrderPage memberOrderPage = new MemberDto.OrderPage(member);

        List<Item> itemList = new ArrayList<>();

        int savePoints = 0;
        int totalPrice = 0;
        int totalDiscount = 0;

        List<ShoppingCartDto> shoppingCartDtos = shoppingWrapper.getShoppingCartDtos();

        for (ShoppingCartDto shoppingCartDto : shoppingCartDtos) {

            Item item = itemService.findById(shoppingCartDto.getItemId());
            itemList.add(item);

            int price = item.getPrice();
            int count = shoppingCartDto.getCount();
            int itemPriceMultiplyQuantity = price * count;

            totalPrice += itemPriceMultiplyQuantity;

            int savePoint = itemPriceMultiplyQuantity * (1 / 100);

            savePoints += savePoint;

            int discountPrice = item.getDiscountPrice();
            totalDiscount += discountPrice * count;

        }

        List<ItemDto.Order> itemOrders = itemList.stream().map(item -> {
            int count = 0;
            for (ShoppingCartDto shoppingCartDto : shoppingCartDtos) {
                count = shoppingCartDto.getCount();
            }
            return new ItemDto.Order(item, count);
        }).collect(Collectors.toList());


//        model.addAttribute("orderItemDto", orderItemDtoWrapper.getOrderItemDtos().get(0));
        model.addAttribute("savePoints", savePoints);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("totalDiscount", totalDiscount);
        model.addAttribute("memberDto", memberOrderPage);
        model.addAttribute("itemOrders", itemOrders);

        return "order/index";
    }


//    @PostMapping
//    public String orderCreate(@ModelAttribute OrderItemDto.Create orderItemDto, Model model) {
//
//        Order order = orderService.order(orderItemDto);
//
//        OrderDto.OrderComplete orderDtoComplete = new OrderDto.OrderComplete(order);
//
//        model.addAttribute("orderDto", orderDtoComplete);
//
//        return "order/complete";
//
//    }

    @PostMapping
    public String orderCreate(@ModelAttribute("orderWrapper") OrderItemDtoWrapper orderWrapper, Model model) {

        List<OrderItemDto.Create> orderItemDtos = orderWrapper.getOrderItemDtos();

        Order order = orderService.order(orderWrapper);

        OrderDto.OrderComplete orderComplete = new OrderDto.OrderComplete(order);

        model.addAttribute("orderDto", orderComplete);

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

        ReviewDtoWrapper reviewDtoWrapper = new ReviewDtoWrapper();

        for (ItemDto.ReviewForm reviewForm : itemList) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setItemDto(reviewForm);
            reviewDtoWrapper.getReviewDtos().add(reviewDto);
        }

        model.addAttribute("wrapper", reviewDtoWrapper);
        model.addAttribute("orderId", orderId);
        model.addAttribute("memberId", memberId);

        return "order/review";
    }

    @PostMapping("/review")
    public String orderReview(@ModelAttribute("reviewRequest") ReviewDtoWrapper reviewDto) {

        orderService.orderCompleteWithReviewAll(reviewDto);

        return "redirect:/myPage";

    }



}
