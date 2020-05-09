package com.leo.factory;

import com.leo.enums.NodeType;
import com.leo.pojo.PermissionNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点工厂
 */
public class NodeFactory {
    public static PermissionNode generateNode(NodeType type){
        if(type.equals(NodeType.SINGLENODE)){
            return new PermissionNode();
        }else{
            return null;
        }

    }

    public static List<PermissionNode> generateNodeList(NodeType type){
        if(type.equals(NodeType.NODELIST)){
            return new ArrayList<>();
        }else{
            return null;
        }
    }
}


