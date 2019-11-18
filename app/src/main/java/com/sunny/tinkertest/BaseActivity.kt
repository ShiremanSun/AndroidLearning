package com.sunny.tinkertest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import com.sunny.onkeyskin.SkinLayoutFactory

/**
 * Created by SunShuo.
 * Date: 2019-10-21
 * Time: 09:10
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //因为只能设置一次，setContent在后就不需要
        val mFactorySet = layoutInflater::class.java.getDeclaredField("mFactorySet")
        mFactorySet.isAccessible = true
        mFactorySet.set(layoutInflater, false)

        LayoutInflaterCompat.setFactory2(layoutInflater, SkinLayoutFactory())
        setContentView(getLayoutID())
    }

    abstract fun getLayoutID() : Int
}