package com.sunny.student.lambda

import org.junit.Assert.*
import org.junit.Test
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * created by sunshuo
 * on 2020-03-03
 */
class LambdaTestTest{
    @Test
    fun lambdaTest() {
        val lambdaTest = LambdaTest()
        val nums = listOf<Int>(5,6,7,8,1,3,5,10)
        /*nums.forEach {
            canshu(it) { x -> if (x % 2 == 0) print(x)
        }}*/

        nums.let{

        }

        nums.apply {

        }
        nums?.run {

        }



        val test = with(nums) {
            1000
            print(this)

        }

        func1(nums) {
            this

        }

        func2(nums) {
            this
        }


    }

    public inline fun <T, R> T.lettest(block: (T) -> R) {
        block(this)
    }

    public inline fun <T, R> withTest(receiver : T, block : T.() -> R) : R {
        return receiver.block()
    }

    val sum = {x : Int -> {y : Int -> x + y}}


    val function : Function<Void>? = null
    //匿名函数作为参数
    fun canshu(x : Int, canshu: (x : Int) -> Unit){
        canshu(x + 5)
    }

    /**
     * 第二个参数不一样
     */

    public inline fun <T, R> func1(t : T,block : (T) -> R){
       block(t)
    }

    public inline fun <T, R> func2(t : T, block : T.() -> R) {
        t.block()
    }

}