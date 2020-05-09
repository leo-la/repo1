package com.leo.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数转换工具
 */
public class ParamConverterUtils {
    /**
     * restFul字符转换
     * @param str
     * @return
     */
    public static Integer restFulConverter(String str){
        String s1 = str.substring(1, str.length()-1);
        Integer i = Integer.parseInt(s1);
        return i;
    }

    /**
     * json列表数据转换
     * @param json
     * @return
     */
    public static List<Integer> listJsonConverter(String json){
        String s1 = json.split(":")[1];
        String[] strings = s1.replace("[", "").replace("]", "")
                .replace("}", "").split(",");
        List<Integer> list = new ArrayList<>();
        for (String string : strings) {
            list.add(Integer.parseInt(string));
        }
        return list;
    }
}
