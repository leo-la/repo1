package com.leo.service;

import com.leo.domain.Account;

import java.util.List;

public interface IAccountService {
    public List<Account> findAll();

    public void saveAccount(Account account);

    void transfer(String sourceName,String targetName,Integer count);
}
