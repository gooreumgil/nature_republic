package com.daeboo.naturerepublic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String memberLogin() {
        return "members/login";
    }

    @GetMapping("/logout")
    public String memberLogout() {
        return "members/logout";
    }

}
