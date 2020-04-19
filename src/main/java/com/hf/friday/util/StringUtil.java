package com.hf.friday.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    /**
     * 生成规定格式的id
     * @param prefix
     * @param index
     * @return
     */
    public static String genNO(String prefix,int index)
    {
        //同一四位格式
        DecimalFormat format = new DecimalFormat("0000");
        String suffix = format.format(index);
        return prefix + suffix;
    }

    public static String genNO(int index)
    {
        //同一四位格式
        DecimalFormat format = new DecimalFormat("000000");
        String s = format.format(index);
        return s;
    }
}
