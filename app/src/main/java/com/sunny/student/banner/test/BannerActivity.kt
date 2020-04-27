package com.sunny.student.banner.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sunny.student.R
import com.sunny.student.banner.Banner

class BannerActivity : AppCompatActivity() {

    private val banner : Banner by lazy {
        findViewById<Banner>(R.id.banner)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)

       val bannerAdapter = BannerAdapter().apply {
           setData(listOf("1", "2", "3", "4"))
       }
        banner.run {
            setAdapter(bannerAdapter)
            setAutoPlay(true)
            setDelayTime(5000)
            setIndicatorAdapter(MyBannerIndicatorAdapter())
            setMarginBottom(50)
            setIndicatorVisible(true)
            build()
        }

        lifecycle.addObserver(banner)

    }



}
