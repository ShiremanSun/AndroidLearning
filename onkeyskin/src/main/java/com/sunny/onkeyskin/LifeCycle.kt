package com.sunny.onkeyskin

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Created by SunShuo.
 * Date: 2019-10-20
 * Time: 23:18
 */

class LifeCycle : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityStopped(activity: Activity) {
    }

    //在setContentView之前调用的，可以用
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityResumed(activity: Activity) {
    }

}