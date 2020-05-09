package com.leo.manager.dao;

import com.leo.pojo.Member;
import com.leo.pojo.Role;
import com.leo.pojo.UserInfo;

import java.util.List;

public interface IUserDao extends BaseDao {
    UserInfo findUserByName(String username);

    Member findMemberByName(String username);

    Member findMembeById(Integer id);

    void updatePasswordByName(UserInfo userInfo);

    UserInfo findUserByAuth(UserInfo userInfo);

    UserInfo findUserByAcct(UserInfo userInfo);

    void addUser(UserInfo userInfo);

    Integer addMember(Member member);

    Integer findUserCount();

    List<UserInfo> searchUserPage(Integer start,Integer size);

    Integer deleteUserById(Integer id);

    List<UserInfo> findUserByCondition(String condition,Integer start,Integer end);

    Integer findUserCountByCondition(String condition);

    Integer findUserIdByName(String name);

    List<Role> findUserRoles(Integer userid);

    Integer updateUserAvatar(String avatar,Integer id);

    Integer updateMemberAvatar(String avatar,Integer id);

    Integer updateMemberByMid(Member member);

    Integer updateAccttype(String accttype,Integer id);

    Integer updateAuthstatus(String status,Integer id);

}
