package com.leo;

import com.leo.manager.dao.IUserDao;
import com.leo.pojo.UserInfo;
import org.activiti.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:spring/spring*.xml");
        ProcessEngine processEngine = (ProcessEngine) ioc.getBean("processEngine");
        System.out.println(processEngine);
    }
}
