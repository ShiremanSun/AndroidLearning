package com.sunny.onkeyskin

import android.app.Application
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources

/**
 * Created by SunShuo.
 * Date: 2019-10-20
 * Time: 22:20
 */
object SkinManager {


    private var application : Application? = null
    private var skinResource : Resources? = null
    private var skinPackageName : String? = null

    fun init(application: Application) {
        this.application = application
    }

    /**
     * @param path 要加载的APK路径
     */
    fun loadApk(path : String) {
        try {
            val assetManager = AssetManager::class.java.newInstance()
            val addAssetPath = assetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
            addAssetPath.invoke(assetManager, path)
            val appResource = application?.resources
            //实例化要加载的APK的Resource
            skinResource = Resources(assetManager, appResource?.displayMetrics, appResource?.configuration)
            //拿到要加载的APK包名
            val packageManager = application?.packageManager
            skinPackageName = packageManager?.getPackageArchiveInfo(path,
                    PackageManager.GET_ACTIVITIES)?.packageName
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }





}