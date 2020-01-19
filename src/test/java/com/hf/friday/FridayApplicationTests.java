package com.hf.friday;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class FridayApplicationTests {

    @Test
    static void contextLoads() {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }

    public static void main(String[] args) {
        contextLoads();
    }
}
