package com.sunny.student;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

    Test1 a = new Test1();
    @Test
    public void test1() {
        String st = "绑定微信可得10学分";
        String regex = "[^0-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(st);
        //只得到数字
        String number = m.replaceAll("").trim();

        System.out.println(st.replaceAll("\\d","<font color=\"#FF6C00\">" + number + "</font>"));

        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

        if (a.instance != null) {
            a.instance.print();
        }

    }


    class Test1{
        public Test1 instance = new Test1();

        public void print() {

        }
    }
}
