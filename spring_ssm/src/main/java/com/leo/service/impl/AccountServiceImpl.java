package com.leo.service.impl;

import com.leo.dao.IAccountDao;
import com.leo.domain.Account;
import com.leo.domain.UserCount;
import com.leo.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private IAccountDao dao;

    @Override
    public List<Account> findAll() {
        System.out.println("业务层：查询所有账户信息...");
        return dao.findAll();
    }

    @Override
    public void saveAccount(Account account) {
        System.out.println("业务层：保存账户信息...");
        dao.saveAccount(account);
    }

    public void transfer(String sourceName, String targetName, Integer count) {
        UserCount source = dao.findUserByName(sourceName);
        UserCount target = dao.findUserByName(targetName);

        source.setAccount(source.getAccount()-count);
        target.setAccount(target.getAccount()+count);
        dao.updateCount(source);
        int i=9/0;
        dao.updateCount(target);
    }
}
