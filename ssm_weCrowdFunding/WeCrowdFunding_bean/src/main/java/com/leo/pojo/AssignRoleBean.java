package com.leo.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AssignRoleBean implements Serializable {
    private Integer id;
    private Integer userid;
    private Integer roleid;
    private List<Role> allRoles;
    private List<Role> unAssignRoles;
    private List<Role> assignedRoles;
    private List<Integer> updateRoles;
}
