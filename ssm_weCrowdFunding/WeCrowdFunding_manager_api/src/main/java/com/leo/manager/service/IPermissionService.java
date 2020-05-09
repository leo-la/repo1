package com.leo.manager.service;

import com.leo.pojo.*;

import java.util.List;

public interface IPermissionService{

    PageBean searchUserPage(PageBean pageBean);

    PageBean searchMemberPage(PageBean pageBean);

    PageBean searchRolePage(PageBean pageBean);

    Integer deleteUserById(Integer id);

    Integer deleteRoleById(Integer id);

    boolean addRolePermissions(Integer roleid,List<Integer> permisssions);

    PageBean searchUserPageCondition(PageBean pageBean);

    PageBean searchRolePageCondition(PageBean pageBean);

    PageBean searchMemberpageByCondition(PageBean pageBean);

    AssignRoleBean searchAssignRoleInformation(AssignRoleBean assignRoleBean);

    boolean updateRoles(AssignRoleBean assignRoleBean);

    boolean updatePermission(Permission permission);

    List<PermissionNode> searchPermissionTree();

    List<PermissionNode> searchRolePermissionTree(Integer roleid,List<PermissionNode> nodes);

    Permission findPermissionById(Integer permissionid);

    Permission findPermissionByName(String  name);

    List<Icon> findAllIcon();

    List<Permission> findAllPermissions();

    boolean deletePermission(String name);

    boolean addPermission(Permission permission);

    boolean addRole(Role role);

    Role findRoleById(Integer id);

    boolean updateRole(Role role);



}
