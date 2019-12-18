package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.dto.ShoppingCartWrapper;
import com.daeboo.naturerepublic.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    private final ItemService itemService;

    @GetMapping
    public String cart(@ModelAttribute("shoppingWrapper") ShoppingCartWrapper shoppingWrapper, Principal principal, HttpSession httpSession, Model model) {

        String name = principal.getName();
        List<Item> cartItems = new ArrayList<>();
        List<Item> attributes = (List<Item>) httpSession.getAttribute(name);

        if (attributes != null) {
            cartItems = attributes;
        }

        model.addAttribute("cartItems", cartItems);
        return "shoppingCart/index";

    }

    @PostMapping
    public String addCart(@RequestParam("itemId") Long itemId, Principal principal, HttpSession httpSession, HttpServletRequest request) {

        String name = principal.getName();

        List<Item> cartItems = new ArrayList<>();

        List<Item> attribute = (List<Item>) httpSession.getAttribute(name);

        if (attribute != null) {
            cartItems = attribute;
        }

        Item item = itemService.findById(itemId);
        cartItems.add(item);

        httpSession.setAttribute(name, cartItems);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/delete")
    public String deleteAttribute(@RequestParam("itemId") Long itemId, Principal principal, HttpSession httpSession, HttpServletRequest request) {

        String name = principal.getName();

        List<Item> cartItems = (List<Item>) httpSession.getAttribute(name);
        cartItems.removeIf(item -> item.getId().equals(itemId));

        httpSession.setAttribute(name, cartItems);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;

    }

}
