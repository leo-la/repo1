package com.leo.utils;

import com.leo.pojo.Member;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * 全局域工具
 */
public class ApplicationUtils{
    public static Member getUserInfo(){
        WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext application = applicationContext.getServletContext();
        Member user = (Member) application.getAttribute("user");
        return user;
    }
}
