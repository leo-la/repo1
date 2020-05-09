package com.leo.utils;

import com.leo.pojo.Member;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 全局域工具
 */
public class StorageUtils{
    public static ServletContext getApplication(){
        WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext application = applicationContext.getServletContext();
        return application;
    }

    public static HttpServletRequest getRequest(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(requestAttributes,true);
        HttpServletRequest request = (requestAttributes).getRequest();
        return request;
    }

    public static Member getMember(HttpServletRequest request){
        Member user = (Member) request.getSession().getAttribute("user");
        return user;
    }
}
