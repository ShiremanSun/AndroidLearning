package com.sunny.student

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * Created by SunShuo.
 * Date: 2019-10-24
 * Time: 12:14
 */
object MyThreadPool {
    val threadPoolExecutor = Executors.newCachedThreadPool()

    fun submit() : Future<Any>{
        return threadPoolExecutor.submit(object : Callable<Any> {
            override fun call(): Any {
                //do something then return result
                return 1
            }

        })
    }

    fun execute() {
        threadPoolExecutor.execute(object : Runnable{
            override fun run() {
                //do something
            }

        })
    }
}