package com.nice.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sun.security.provider.MD5;

import java.util.HashMap;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        Integer i = 10;
        Integer j = 10;
        System.out.println(i == j);

        Integer a = 128;
        Integer b = 128;
        System.out.println(a == b);

        int k = 10;
        System.out.println(k == i);
        int kk = 128;
        System.out.println(kk == a);

        Integer m = new Integer(10);
        Integer n = new Integer(10);
        System.out.println(m == n);
    }

}
