package com.leo.manager.service.impl;

import com.leo.manager.dao.IUserDao;
import com.leo.manager.service.IMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainServiceImpl implements IMainService {
    @Autowired
    IUserDao userDao;


}
