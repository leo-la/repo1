package com.leo.test;

import com.leo.dao.IAccountDao;
import com.leo.domain.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 *测试-注解配置
 */
public class Test2 {
    private InputStream is;
    private SqlSessionFactory factory;
    private SqlSession session;
    private IAccountDao dao;

    @Before
    public void init() throws IOException {
        //1.读取配置文件
        is = Resources.getResourceAsStream("SqlMapConfig.xml");

        //2.创建SqlSessionFactory工厂
        factory =  new SqlSessionFactoryBuilder().build(is);

        //3.使用工厂生产SqlSession对象
        session = factory.openSession();

        //4.使用SqlSession创建Dao接口的代理对象
        dao = session.getMapper(IAccountDao.class);
    }
    @After
    public void destroy() throws IOException {
        session.commit();
        //6.释放资源
        session.close();
        is.close();
    }

    @Test
    public void testAnno1(){
        List<Account> all = dao.findAll();
        for (Account account : all) {
            System.out.println(account);
        }
    }

    @Test
    public void testAnno2(){
        Account account = new Account();
        account.setMoney(1000);
        dao.saveAccount(account);
    }

}
