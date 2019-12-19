package com.daeboo.naturerepublic.controller;

import com.daeboo.naturerepublic.domain.Item;
import com.daeboo.naturerepublic.dto.ShoppingCartWrapper;
import com.daeboo.naturerepublic.serializable.ItemSerializable;
import com.daeboo.naturerepublic.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.SessionCookieConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
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
        List<ItemSerializable> cartItems = new ArrayList<>();
        Object attributes = httpSession.getAttribute(name);

        if (attributes != null) {
            cartItems = (List<ItemSerializable>) attributes;

        }
//
        model.addAttribute("cartItems", cartItems);
        return "shoppingCart/index";

    }

    @PostMapping
    public String addCart(@RequestParam("itemId") Long itemId, Principal principal, HttpSession httpSession, HttpServletRequest request) {

        String name = principal.getName();

        List<ItemSerializable> cartItems = new ArrayList<>();

        Object attribute = httpSession.getAttribute(name);

        if (attribute != null) {
            cartItems = (List<ItemSerializable>) attribute;
        }

        Item item = itemService.findById(itemId);
        ItemSerializable itemSerializable = new ItemSerializable(item);
        cartItems.add(itemSerializable);

        httpSession.setAttribute(name, cartItems);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/delete")
    public String deleteAttribute(@RequestParam("itemId") Long itemId, Principal principal, HttpSession httpSession, HttpServletRequest request) {

        String name = principal.getName();

        List<ItemSerializable> cartItems = new ArrayList<>();

        Object attribute = httpSession.getAttribute(name);

        if (attribute != null) {
            cartItems = (List<ItemSerializable>) attribute;
        }

        cartItems.removeIf(item -> item.getId().equals(itemId));

        httpSession.setAttribute(name, cartItems);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;

    }

}
