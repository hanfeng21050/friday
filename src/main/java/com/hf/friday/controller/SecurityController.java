package com.hf.friday.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/login.html")
    public String login()
    {
        return "login";
    }
}
