package com.sunny.tinkertest.util

import android.content.Context
import android.os.Process
import com.sunny.tinkertest.MyApplication

class CrashHandler : Thread.UncaughtExceptionHandler{
    private lateinit var mContext : Context
    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CrashHandler()
        }
    }
    fun init (context: Context) {
        Thread.setDefaultUncaughtExceptionHandler(this)
        mContext = context
    }
    override fun uncaughtException(t: Thread?, e: Throwable?) {

        (mContext.applicationContext as MyApplication).removeAllActivities()
        Process.killProcess(Process.myPid())
    }
}