package com.sunny.student.lambda;

/**
 * created by sunshuo
 * on 2020-03-12
 */
public class JavaLammbda {
    public void let(Let<JavaLammbda> let) {
        let.let(this);
    }

    public void test() {
        let(new Let<JavaLammbda>() {
            @Override
            public void let(JavaLammbda javaLammbda) {

            }
        });
    }
    public interface Let<T>{
        void let(T t);
    }
}
