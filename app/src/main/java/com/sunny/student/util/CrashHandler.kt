package com.sunny.student.util

import android.content.Context
import android.os.Process
import com.sunny.student.MyApplication

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

        e?.printStackTrace()
        (mContext.applicationContext as MyApplication).removeAllActivities()
        Process.killProcess(Process.myPid())
    }
}