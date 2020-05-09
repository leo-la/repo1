package com.leo.manager.controller;

import com.leo.manager.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/guagua")
public class TestController {
    @Autowired
    ITestService testService;

    @RequestMapping("/test")
    public String insert(){
        System.out.println("123");
        testService.inser("leo");
        String s = UUID.randomUUID().toString();
        System.out.println(s);
        System.out.println("1233242");
        return "success";
    }
}
