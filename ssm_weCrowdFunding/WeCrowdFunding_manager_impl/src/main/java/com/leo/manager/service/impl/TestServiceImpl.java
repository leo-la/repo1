package com.leo.manager.service.impl;

import com.leo.manager.dao.ITestDao;
import com.leo.manager.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TestServiceImpl implements ITestService {
    @Autowired
    ITestDao testDao;

    @Override
    public void inser(String name) {
        System.out.println("service111");
        Map map = new HashMap();
        map.put("name","leo");
        testDao.insert(map);
        System.out.println("service222");
    }
}
