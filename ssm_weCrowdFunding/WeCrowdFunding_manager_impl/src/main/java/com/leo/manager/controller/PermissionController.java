package com.leo.manager.controller;

import com.leo.manager.service.IPermissionService;
import com.leo.manager.service.IUserService;
import com.leo.pojo.*;
import com.leo.utils.ParamConverterUtils;
import com.leo.utils.UserInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 权限控制器
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {
    /**
     * 权限服务
     */
    @Autowired
    IPermissionService permissionService;
    /**
     * 用户服务
     */
    @Autowired
    IUserService userService;

    /**
     * 跳转用户维护
     * @param model
     * @return
     */
    @RequestMapping("/toUser")
    public String toUser(Model model){
        UserInfoUtils.addUserInfo(model);
        return "user";
    }

    /**
     * 跳转用户分配角色
     * @param model
     * @return
     */
    @RequestMapping("/toAssignRole")
    public String toAssignRole(Model model){
        UserInfoUtils.addUserInfo(model);
        return "assignRole";
    }

    /**
     *用户维护-查询用户页面信息
     * @param pageBean
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchUserPage")
    public PageBean searchUserPage(@RequestBody PageBean pageBean){
        pageBean = permissionService.searchUserPage(pageBean);
        return pageBean;
    }

    /**
     * 用户维护-查询客户页面数据
     * @param pageBean
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchMemberPage")
    public PageBean searchMemberPage(@RequestBody PageBean pageBean){
        pageBean = permissionService.searchMemberPage(pageBean);
        return pageBean;
    }

    /**
     * 用户维护-删除用户
     * @param userInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteUser")
    public boolean deleteOne(@RequestBody UserInfo userInfo){
        Integer integer = permissionService.deleteUserById(userInfo.getId());
        if(integer==1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 用户维护-删除多个用户
     * @param deletes
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteUsers")
    public Integer deleteSelect(@RequestBody String deletes){
        String s1 = deletes.split(":")[1];
        String[] strings = s1.replace("[", "").replace("]", "")
                .replace("}", "").split(",");
        int deleteCount = 0;
        for (String string : strings) {
            int id = Integer.parseInt(string.substring(1, string.length() - 1));
            Integer integer = permissionService.deleteUserById(id);
            if(integer==1){
                deleteCount ++;
            }
        }
        System.out.println("删除选中："+deletes);
        return deleteCount;
    }

    /**
     * 用户维护-更新用户分配角色
     * @param assignRoleBean
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateUserRole")
    public boolean updateUserRole(@RequestBody AssignRoleBean assignRoleBean){
        System.out.println("分配角色控制层:"+assignRoleBean);
        return permissionService.updateRoles(assignRoleBean);
    }

    /**
     * 用户维护-条件查询用户
     * @param pageBean
     * @return
     */
    @ResponseBody
    @RequestMapping("/conditionSearchUsers")
    public PageBean conditionSearch(@RequestBody PageBean pageBean){
        pageBean = permissionService.searchUserPageCondition(pageBean);
        return pageBean;
    }

    /**
     * 用户维护-条件查询客户信息
     * @param pageBean
     * @return
     */
    @ResponseBody
    @RequestMapping("/conditionSearchMembers")
    public PageBean conditionSearchMembers(@RequestBody PageBean pageBean){
        pageBean = permissionService.searchMemberpageByCondition(pageBean);
        return pageBean;
    }

    /**
     * 用户维护-查询用户分配角色页面信息
     * @param assignRoleBean
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchAssignRoleInformation")
    public AssignRoleBean searchAssignRoleInformation(@RequestBody AssignRoleBean assignRoleBean){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Integer id = userService.findUserIdByName(username);
        assignRoleBean.setUserid(id);
        permissionService.searchAssignRoleInformation(assignRoleBean);
        return assignRoleBean;
    }

    /**
     * 跳转角色维护
     * @param model
     * @return
     */
    @RequestMapping("/toRole")
    public String toRole(Model model){
        UserInfoUtils.addUserInfo(model);
        return "role";
    }

    /**
     * 跳转新增角色
     * @param model
     * @return
     */
    @RequestMapping("/toAddRole")
    public String toAddRole(Model model){
        UserInfoUtils.addUserInfo(model);
        return "addRole";
    }

    /**
     *跳转角色分配权限
     * @param s
     * @param model
     * @return
     */
    @RequestMapping("/toAssignPermissions/{roleid}")
    public String toAssignPermissions(@PathVariable("roleid") String s, Model model){
        System.out.println("toAssignPermissions:"+s);
        Integer roleid = ParamConverterUtils.restFulConverter(s);
        UserInfoUtils.addUserInfo(model);
        model.addAttribute("roleid",roleid);
        return "assignPermission";
    }

    /**
     * 跳转更新角色
     * @param s
     * @param model
     * @return
     */
    @RequestMapping("/toUpdateRole/{roleid}")
    public String toUpdateRole(@PathVariable("roleid") String s, Model model){
        System.out.println("toUpdateRole:"+s);
        Integer roleid = ParamConverterUtils.restFulConverter(s);
        Role role = permissionService.findRoleById(roleid);
        UserInfoUtils.addUserInfo(model);
        model.addAttribute("role",role);
        return "updateRole";
    }

    /**
     * 角色维护-查询角色页面信息
     * @param pageBean
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchRolePage")
    public PageBean searchRolePage(@RequestBody PageBean pageBean){
        pageBean = permissionService.searchRolePage(pageBean);
        return pageBean;
    }

    /**
     * 角色维护-删除角色
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteRole")
    public boolean deleteRole(@RequestBody Role role){
        Integer integer = permissionService.deleteRoleById(role.getId());
        System.out.println("删除与否："+integer);
        if(integer==1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 角色维护-删除多个角色
     * @param deletes
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteRoles")
    public Integer deleteRoles(@RequestBody String deletes){
        List<Integer> integers = ParamConverterUtils.listJsonConverter(deletes);
        int deleteCount = 0;
        for (Integer id : integers) {
            Integer integer = permissionService.deleteRoleById(id);
            if(integer==1){
                deleteCount ++;
            }
        }
        System.out.println("删除选中："+deletes);
        return deleteCount;
    }

    /**
     *角色维护-分配许可
     * @param permissionid
     * @param s
     * @return
     */
    @ResponseBody
    @RequestMapping("/assignPermission/{roleid}")
    public boolean assignPermission(@RequestBody String permissionid,@PathVariable("roleid") String s){
        Integer roleid = ParamConverterUtils.restFulConverter(s);
        List<Integer> integers = ParamConverterUtils.listJsonConverter(permissionid);
        boolean isSuccess = permissionService.addRolePermissions(roleid, integers);
        System.out.println("添加许可："+permissionid);
        return isSuccess;
    }

    /**
     *角色维护-更新角色
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateRole")
    public boolean updateRole(@RequestBody Role role){
        System.out.println("修改角色控制层:"+role);
        return permissionService.updateRole(role);
    }

    /**
     * 角色维护-新增角色
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping("/addRole")
    public boolean addRole(@RequestBody Role role){
        System.out.println("添加角色控制层:"+role);
        return permissionService.addRole(role);
    }

    /**
     * 角色维护-条件查询角色
     * @param pageBean
     * @return
     */
    @ResponseBody
    @RequestMapping("/conditionSearchRoles")
    public PageBean conditionSearchRoles(@RequestBody PageBean pageBean){
        pageBean = permissionService.searchRolePageCondition(pageBean);
        return pageBean;
    }

    /**
     * 角色维护-查询角色分配许可树
     * @param s
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchRolePermissionTree/{roleid}")
    public List<PermissionNode> searchRolePermissionTree(@PathVariable("roleid") String s){
        Integer roleid = ParamConverterUtils.restFulConverter(s);
        System.out.println("roleid:"+roleid);
        List<PermissionNode> tree = permissionService.searchPermissionTree();
        List<PermissionNode> roleTree = permissionService.searchRolePermissionTree(roleid,tree);
        return roleTree;
    }

    /**
     * 跳转许可维护
     * @param model
     * @return
     */
    @RequestMapping("/toPermission")
    public String toPermission(Model model){
        UserInfoUtils.addUserInfo(model);
        return "permission";
    }

    /**
     * 跳转新增许可
     * @param model
     * @return
     */
    @RequestMapping("/toAddPermission")
    public String toAddPermission(Model model){
        List<Icon> icons = permissionService.findAllIcon();
        model.addAttribute("icons",icons);
        List<Permission> permissions = permissionService.findAllPermissions();
        model.addAttribute("allPermissions",permissions);
        UserInfoUtils.addUserInfo(model);
        return "addPermission";
    }

    /**
     * 跳转更新许可
     * @param s
     * @param model
     * @return
     */
    @RequestMapping("/toUpdatePermission/{name}")
    public String toUpdatePermission(@PathVariable("name") String s, Model model){
        System.out.println("toUpdatePermission:"+s);
        UserInfoUtils.addUserInfo(model);
        String name = s.substring(1,s.length()-1);
        Permission permission = permissionService.findPermissionByName(name);
        model.addAttribute("permission",permission);
        List<Icon> icons = permissionService.findAllIcon();
        model.addAttribute("icons",icons);
        List<Permission> permissions = permissionService.findAllPermissions();
        model.addAttribute("allPermissions",permissions);
        return "updatePermission";
    }

    /**
     * 许可维护-删除许可
     * @param s
     * @return
     */
    @ResponseBody
    @RequestMapping("/deletePermission/{name}")
    public boolean deletePermission(@PathVariable("name") String s){
        System.out.println("deletePermission:"+s);
        String name = s.substring(1,s.length()-1);
        return permissionService.deletePermission(name);
    }

    /**
     * 许可维护-更新许可
     * @param permission
     * @return
     */
    @ResponseBody
    @RequestMapping("/updatePermission")
    public boolean updatePermission(@RequestBody Permission permission){
        System.out.println("修改许可控制层:"+permission);
        return permissionService.updatePermission(permission);
    }

    /**
     * 许可维护-添加许可
     * @param permission
     * @return
     */
    @ResponseBody
    @RequestMapping("/addPermission")
    public boolean addPermission(@RequestBody Permission permission){
        System.out.println("添加许可控制层:"+permission);
        return permissionService.addPermission(permission);
    }

    /**
     * 许可维护-查询许可树
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchPermissionTree")
    public List<PermissionNode> searchPermissionTree(){
        List<PermissionNode> tree = permissionService.searchPermissionTree();
        return tree;
    }
}
