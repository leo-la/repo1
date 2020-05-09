package com.leo.manager.dao;

import com.leo.pojo.*;

import java.util.List;

public interface IPermissionDao extends BaseDao {

    List<Role> findAllRoles();

    List<Role> findUserRoles(Integer userid);

    Integer deleteUserRolesByUserid(Integer userid);

    Integer addUserRolesByUserid(Integer userid,List<Integer> updateRoles);

    List<Permission> findAllPermissions();

    Integer addRole(Role role);

    Integer findRoleCount();

    List<Role> searchRolePage(Integer start,Integer size);

    List<Member> searchMemberPage(Integer start, Integer size);

    Integer findMembersCount();

    Integer deleteRoleById(Integer id);

    List<Role> findRoleByCondition(String condition,Integer start,Integer end);

    List<Member> findMemberByCondition(String condition,Integer start,Integer end);

    Integer findRoleCountByCondition(String condition);

    Integer findMemberCountByCondition(String condition);

    Integer findRoleIdByName(String name);

    List<Integer> findPermissionByRoleid(Integer roleid);

    Integer deletePermissionByRoleid(Integer roleid);

    Integer addPermissionsByRoleid(Integer roleid,List<Integer> permissionid);

    Permission findPermissionById(Integer id);

    Permission findPermissionByName(String  name);

    List<Icon> findAllIcon();

    Integer updatePermission(Permission permission);

    Integer deletePermission(String name);

    Integer addPermission(Permission permission);

    Role findRoleById(Integer id);

    Integer updateRole(Role role);

}
