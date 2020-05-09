package com.leo.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController {
    @RequestMapping("/toLogin")
    public String toLogin(){
        System.out.println("2324123");
        return "login";
    }

    @RequestMapping("/login")
    public String main(){
        System.out.println("loginjjjj");
        return "main";
    }
}
