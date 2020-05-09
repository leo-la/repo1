package com.leo.manager.service.impl;

import com.leo.enums.PageTemplateType;
import com.leo.factory.NodeFactory;
import com.leo.enums.NodeType;
import com.leo.factory.PageTemplateFactory;
import com.leo.manager.dao.IPermissionDao;
import com.leo.manager.dao.IUserDao;
import com.leo.manager.service.IPermissionService;
import com.leo.pojo.*;
import com.leo.template.PageSearchTemplate;
import com.leo.utils.ZTreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    IUserDao userDao;
    @Autowired
    IPermissionDao permissionDao;

    /**
     * 用户维护-查询管理员页面数据
     * @param pageBean
     * @return
     */
    @Override
    public PageBean searchUserPage(PageBean pageBean) {
        System.out.println("查询用户数据->传入数据："+pageBean);
        PageSearchTemplate userTemplate = PageTemplateFactory.createTemplate(PageTemplateType.USER_PAGE);
        //count
        userTemplate.getTotalCount(pageBean,userDao);
        //currentPage
        userTemplate.getCurrentPage(pageBean);
        //totalPage
        userTemplate.getTotalPage(pageBean);
        //data
        userTemplate.getData(pageBean,userDao);
        System.out.println("查询用户数据->封装数据："+pageBean);
        return pageBean;
    }

    /**
     * 用户维护-查询会员页面数据
     * @param pageBean
     * @return
     */
    @Override
    public PageBean searchMemberPage(PageBean pageBean) {
        System.out.println("查询会员数据->传入数据："+pageBean);
        PageSearchTemplate memberTemplate = PageTemplateFactory.createTemplate(PageTemplateType.MEMBER_PAGE);
        //count
        memberTemplate.getTotalCount(pageBean,permissionDao);
        //currentPage
        memberTemplate.getCurrentPage(pageBean);
        //totalPage
        memberTemplate.getTotalPage(pageBean);
        //data
        memberTemplate.getData(pageBean,permissionDao);
        System.out.println("查询会员数据->封装数据："+pageBean);
        return pageBean;
    }

    /**
     * 角色维护-查询角色页面数据
     * @param pageBean
     * @return
     */
    @Override
    public PageBean searchRolePage(PageBean pageBean) {
        System.out.println("查询角色数据->传入数据："+pageBean);
        PageSearchTemplate roleTemplate = PageTemplateFactory.createTemplate(PageTemplateType.ROLE_PAGE);
        //count
        roleTemplate.getTotalCount(pageBean,permissionDao);
        //currentPage
        roleTemplate.getCurrentPage(pageBean);
        //totalPage
        roleTemplate.getTotalPage(pageBean);
        //data
        roleTemplate.getData(pageBean,permissionDao);
        System.out.println("查询角色数据->封装数据："+pageBean);
        return pageBean;
    }

    @Override
    public Integer deleteUserById(Integer id) {
        Integer integer = userDao.deleteUserById(id);
        return integer;
    }

    @Override
    public Integer deleteRoleById(Integer id) {
        Integer integer = permissionDao.deleteRoleById(id);
        return integer;
    }

    @Override
    public boolean addRolePermissions(Integer roleid, List<Integer> permisssions) {
        Integer integer = permissionDao.deletePermissionByRoleid(roleid);
        Integer adds = permissionDao.addPermissionsByRoleid(roleid, permisssions);
        if(adds==0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 用户维护-查询管理员条件查询页面数据
     * @param pageBean
     * @return
     */
    @Override
    public PageBean searchUserPageCondition(PageBean pageBean) {
        System.out.println("条件查询用户数据->传入数据："+pageBean);
        PageSearchTemplate userConditionTemplate = PageTemplateFactory.createTemplate(PageTemplateType.USER_CONDITION_PAGE);
        //count
        userConditionTemplate.getTotalCount(pageBean,userDao);

        //当前页
        userConditionTemplate.getCurrentPage(pageBean);

        //总页数
        userConditionTemplate.getTotalPage(pageBean);
        //数据
        userConditionTemplate.getData(pageBean,userDao);
        System.out.println("条件查询用户数据->封装数据："+pageBean);
        return pageBean;
    }

    /**
     * 角色维护-查询角色条件查询页面数据
     * @param pageBean
     * @return
     */
    @Override
    public PageBean searchRolePageCondition(PageBean pageBean) {
        System.out.println("条件查询角色数据->传入数据："+pageBean);
        PageSearchTemplate roleConditionTemplate = PageTemplateFactory.createTemplate(PageTemplateType.ROLE_CONDITION_PAGE);
        //count
        roleConditionTemplate.getTotalCount(pageBean,permissionDao);
        //currentPage
        roleConditionTemplate.getCurrentPage(pageBean);
        //totalPage
        roleConditionTemplate.getTotalPage(pageBean);
        //data
        roleConditionTemplate.getData(pageBean,permissionDao);
        System.out.println("条件查询角色数据->封装数据："+pageBean);
        return pageBean;
    }

    /**
     * 用户维护-查询会员条件查询页面数据
     * @param pageBean
     * @return
     */
    @Override
    public PageBean searchMemberpageByCondition(PageBean pageBean) {
        System.out.println("条件查询会员数据->传入数据："+pageBean);
        PageSearchTemplate memberConditionTemplate = PageTemplateFactory.createTemplate(PageTemplateType.MEMBER_CONDITION_PAGE);
        //count
        memberConditionTemplate.getTotalCount(pageBean,permissionDao);
        //currentPage
        memberConditionTemplate.getCurrentPage(pageBean);
        //totalPage
        memberConditionTemplate.getTotalPage(pageBean);
        //data
        memberConditionTemplate.getData(pageBean,permissionDao);
        System.out.println("条件查询会员数据->封装数据："+pageBean);
        return pageBean;
    }

    @Override
    public AssignRoleBean searchAssignRoleInformation(AssignRoleBean assignRoleBean) {
        List<Role> roles = permissionDao.findAllRoles();
        assignRoleBean.setAllRoles(roles);

        List<Role> userRoles = permissionDao.findUserRoles(assignRoleBean.getUserid());
        assignRoleBean.setAssignedRoles(userRoles);

        roles.removeAll(userRoles);
        assignRoleBean.setUnAssignRoles(roles);
        System.out.println("角色分配查询数据："+assignRoleBean);
        return assignRoleBean;
    }

    @Override
    public boolean updateRoles(AssignRoleBean assignRoleBean) {
        permissionDao.deleteUserRolesByUserid(assignRoleBean.getUserid());

        Integer integer = permissionDao.addUserRolesByUserid(assignRoleBean.getUserid(), assignRoleBean.getUpdateRoles());
        if(integer>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updatePermission(Permission permission) {
        Integer integer = permissionDao.updatePermission(permission);
        if(integer==1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 许可维护-查询许可树
     * @return
     */
    @Override
    public List<PermissionNode> searchPermissionTree() {
        List<Permission> permissions = permissionDao.findAllPermissions();
        PermissionNode root = null;
        for (Permission permission : permissions) {
            if(permission.getPid()==null){
                root = NodeFactory.generateNode(NodeType.SINGLENODE);
                root.setId(permission.getId());
                root.setName(permission.getName());
//                root.setSeqno("0");
                root.setIcon(permission.getIcon());
                root.setUrl(permission.getUrl());
                root.setOpen(true);
                List<PermissionNode> nodes = ZTreeUtils.generateTree(permission.getId(), permissions);
                root.setChildren(nodes);
                break;
            }
        }

        List<PermissionNode> tree = NodeFactory.generateNodeList(NodeType.NODELIST);
        tree.add(root);
        System.out.println("权限树："+root);
        return tree;
    }

    /**
     * 角色维护-查询角色许可树
     * @param roleid
     * @param nodes
     * @return
     */
    @Override
    public List<PermissionNode> searchRolePermissionTree(Integer roleid, List<PermissionNode> nodes) {
        PermissionNode root = nodes.get(0);
        List<Integer> permissions = permissionDao.findPermissionByRoleid(roleid);

        PermissionNode permissionNode = ZTreeUtils.setRolePermission(root, permissions);
        List<PermissionNode> node = NodeFactory.generateNodeList(NodeType.NODELIST);
        node.add(permissionNode);
        System.out.println("checked:"+permissionNode);
        return node;
    }

    @Override
    public Permission findPermissionById(Integer id) {
        Permission permisison = permissionDao.findPermissionById(id);
        return permisison;
    }

    @Override
    public Permission findPermissionByName(String name) {
        Permission permission = permissionDao.findPermissionByName(name);
        return permission;
    }

    @Override
    public List<Icon> findAllIcon() {
        List<Icon> icons = permissionDao.findAllIcon();
        return icons;
    }

    @Override
    public List<Permission> findAllPermissions() {
        List<Permission> permissions = permissionDao.findAllPermissions();
        return permissions;
    }

    @Override
    public boolean deletePermission(String name) {
        Integer integer = permissionDao.deletePermission(name);
        if(integer==1){
            return true;
        }else if(integer==0){
            return false;
        }else{
            throw new RuntimeException("发现重复名称");
        }
    }

    @Override
    public boolean addPermission(Permission permission) {
        Integer integer = permissionDao.addPermission(permission);
        if(integer==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean addRole(Role role) {
        Integer integer = permissionDao.addRole(role);
        if(integer==1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Role findRoleById(Integer id) {
        Role role = permissionDao.findRoleById(id);
        return role;
    }

    @Override
    public boolean updateRole(Role role) {
        Integer integer = permissionDao.updateRole(role);
        if(integer==1){
            return true;
        }else if(integer==0){
            return false;
        }else{
            throw new RuntimeException("存在多条更新数据");
        }
    }

}

