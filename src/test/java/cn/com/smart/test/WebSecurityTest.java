package cn.com.smart.test;

import org.junit.Test;

import cn.com.smart.web.helper.WebSecurityHelper;

public class WebSecurityTest {

    @Test
    public void test() {
        String value = "doXUxX1bQxaiB3_ZDCya_ZFaKRRjU0MW0PDIKrM0-588XzBZV3WJpSI3kXkBogOI2EyyqwLXyo6_LYGbiL_U6Q";
        String value2 = WebSecurityHelper.decrypt(value);
        System.out.println(value2);
    }
    
}
