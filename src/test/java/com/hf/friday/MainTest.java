package com.hf.friday;

import com.hf.friday.util.TokenUtil;
import org.junit.Test;

/**
 * @Author CoolWind
 * @Date 2020/4/27 19:37
 */

public class MainTest {

    @Test
    public void test1()
    {
        String str = "/comic/00111110004/残酷/0000046532517.jpg";
        String[] split = str.split("/");
        System.out.println();
    }

    @Test
    public void test2() throws InterruptedException {
        String s = TokenUtil.buildJWT(10);
        System.out.println(s);
        Thread.sleep(2000);
        System.out.println(TokenUtil.verifyToken(s));
        Thread.sleep(3000);
        System.out.println(TokenUtil.verifyToken(s));
    }

    @Test
    public void test3() throws InterruptedException {
        String s = "Screenshot_2020-05-05-10-19-58-654_com.tencent.mm.jpg";
        System.out.println(s.substring(s.lastIndexOf('.')));
    }
}
