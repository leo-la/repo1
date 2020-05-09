package com.leo.manager.service;

import com.leo.pojo.Member;
import com.leo.pojo.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    boolean findUserByAuth(UserInfo userInfo);

    boolean findUserByAcct(UserInfo userInfo);

    void addUser(UserInfo userInfo);

    boolean addMember(Member member);

    Integer findUserIdByName(String name);

    Integer updateMemberByMid(Member member);

    UserInfo findUserByName(String name);

    Member findMemberById(Integer id);

    Member findMemberByName(String name);

    boolean updateUserAvatar(String avatarpath,Integer id,String usertype);

    void updateStatus(String authstatus);

    void updateStatusByid(String authstatus,Integer id);

    void updateUserInfo();
}
