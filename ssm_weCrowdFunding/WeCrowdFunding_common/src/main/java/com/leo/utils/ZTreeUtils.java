package com.leo.utils;

import com.leo.factory.NodeFactory;
import com.leo.enums.NodeType;
import com.leo.pojo.Permission;
import com.leo.pojo.PermissionNode;

import java.util.List;

/**
 * ZTree工具
 */
public class ZTreeUtils {
    /**
     * 生成树
     * @param id
     * @param list
     * @return
     */
    public static List<PermissionNode> generateTree(Integer id, List<Permission> list){
        List<PermissionNode> nodes = NodeFactory.generateNodeList(NodeType.NODELIST);
        for (Permission permission : list) {
            if(permission.getPid()==id){
                PermissionNode node = NodeFactory.generateNode(NodeType.SINGLENODE);
                node.setId(permission.getId());
                node.setPid(permission.getPid());
                node.setName(permission.getName());
//                node.setSeqno("1");
                node.setIcon(permission.getIcon());
                node.setUrl(permission.getUrl());
                node.setOpen(true);
                List<PermissionNode> children = generateTree(permission.getId(), list);
                if(children.size()>0){
                    node.setChildren(children);
                }
                nodes.add(node);
            }
        }
        return nodes;
    }

    /**
     * 添加许可节点
     * @param node
     * @param permissions
     * @return
     */
    public static PermissionNode setRolePermission(PermissionNode node,List<Integer> permissions){
        node.setOpen(true);
        for (Integer permission : permissions) {
            if(node.getId()==permission){
                node.setChecked(true);
                break;
            }
        }
        if(node.getChildren()!=null){
            for (PermissionNode permissionNode : node.getChildren()) {
                setRolePermission(permissionNode,permissions);
            }

        }
        return node;
    }
}
