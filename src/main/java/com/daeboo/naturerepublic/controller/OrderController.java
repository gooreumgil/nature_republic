package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.domain.Member;
import com.daeboo.naturerepublic.domain.Order;
import com.daeboo.naturerepublic.dto.ItemDto;
import com.daeboo.naturerepublic.dto.MemberDto;
import com.daeboo.naturerepublic.dto.OrderDto;
import com.daeboo.naturerepublic.dto.OrderItemDto;
import com.daeboo.naturerepublic.service.ItemService;
import com.daeboo.naturerepublic.service.MemberService;
import com.daeboo.naturerepublic.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

}
