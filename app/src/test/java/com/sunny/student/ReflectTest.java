package com.sunny.student;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * created by sunshuo
 * on 2020-04-27
 * //测试缓存setter方法是否有效
 */
public class ReflectTest {

    @Test
    public void test() throws NoSuchMethodException, InstantiationException, IllegalAccessException {
        //方法是类所有的，所以某个对象来调没有问题
        Method method = TestValue.class.getDeclaredMethod("setValue", int.class);

        TestValue value = TestValue.class.newInstance();

    }



    class TestValue{
        private int value;

        public void setValue(int value) {
            this.value = value;
        }
    }

}
