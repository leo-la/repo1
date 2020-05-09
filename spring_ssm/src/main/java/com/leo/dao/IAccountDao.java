package com.leo.dao;

import com.leo.domain.Account;
import com.leo.domain.UserCount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IAccountDao  {

    @Select("select * from account")
    public List<Account> findAll();

    @Insert("insert into account(money) values(#{money})")
    public void saveAccount(Account account);

    @Update("update count set account=#{account} where name=#{name}")
    void updateCount(UserCount uc);

    @Select("select * from count where name=#{name}")
    UserCount findUserByName(String name);
}

