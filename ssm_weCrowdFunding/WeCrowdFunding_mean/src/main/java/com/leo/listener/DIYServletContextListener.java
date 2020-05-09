package com.leo.listener;

import com.leo.manager.service.IUserService;
import com.leo.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Collection;

public class DIYServletContextListener implements ServletContextListener {
    @Value("${ava}")
    private String staticAccessPath;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("listener........");
        System.out.println("staticAccessPath........"+staticAccessPath);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
