package com.sunny.student.activity

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.sunny.student.R
import kotlinx.android.synthetic.main.activity_theme.*

class ThemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
        setContentView(R.layout.activity_theme)

        switchTheme.setOnClickListener {
            switchTheme(true)
        }
    }

    private fun switchTheme(on:Boolean){

        val config = resources.configuration
        config.uiMode = config.uiMode.and(Configuration.UI_MODE_NIGHT_MASK.inv())

        config.uiMode = config.uiMode.or(if (on) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO)
        delegate.localNightMode = config.uiMode
    }
}