package com.leo.test;

import com.leo.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class Test3 {
    @Autowired
    @Qualifier("accountService")
    private IAccountService accountService = null;

    @Test
    public void test1(){
        accountService.transfer("leo","jack",100);

    }
    @Test
    public void test2(){
        System.out.println("hahaha");
    }
}
