package com.sunny.student

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.graphics.Bitmap
import android.os.Bundle
import com.facebook.common.internal.Supplier
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.facebook.imagepipeline.core.ImagePipelineConfig

class MyApplication : Application(), Application.ActivityLifecycleCallbacks {



    companion object{
        var application : MyApplication? = null
    }
    private val activities = HashSet<Activity>()
    override fun onCreate() {
        super.onCreate()
        application = this
        registerActivityLifecycleCallbacks(this)
        val config = ImagePipelineConfig.newBuilder(applicationContext)
            .setBitmapMemoryCacheParamsSupplier(object : Supplier<MemoryCacheParams?> {
                private val KB = 1024
                private val MB = 1024 * KB
                override fun get(): MemoryCacheParams? {
                    val maxCacheSize = maxCacheSize
                    return MemoryCacheParams(maxCacheSize, 128, maxCacheSize / 12, Int.MAX_VALUE, Int.MAX_VALUE)
                }

                private val maxCacheSize: Int
                    private get() {
                        val obj = applicationContext.getSystemService(ACTIVITY_SERVICE) as? ActivityManager
                            ?: return 0
                        val maxMemory = Math.min(obj.memoryClass * MB, Int.MAX_VALUE)
                        if (maxMemory < 32 * MB) {
                            return 4 * MB
                        }
                        return if (maxMemory < 64 * MB) {
                            6 * MB
                        } else maxMemory / 4
                    }
            })
            .setDownsampleEnabled(true)
            .setBitmapsConfig(Bitmap.Config.RGB_565)
            .build()
        Fresco.initialize(applicationContext, config)
    }
    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        activities.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activity.let {
            activities.add(it)
        }
    }

    fun removeAllActivities() {
        activities.clear()
    }

}