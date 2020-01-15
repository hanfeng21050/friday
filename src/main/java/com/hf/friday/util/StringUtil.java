package com.hf.friday.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    /**
     * 将字符串转化为int集合
     * 1,2,3, --> [1,2,3]
     * @param str
     * @return
     */
    public static List<Integer> String2Int(String str)
    {
        List<Integer> list = new ArrayList<>();
        String subString = str.substring(0,str.length()-1);
        String[] split = subString.split(",");
        for (String s : split) {
            list.add(Integer.valueOf(s));
        }
        return list;
    }
}
