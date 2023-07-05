package com.sunny.student.keyboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.sunny.student.R
import com.sunny.student.databinding.ActivityKeyboardBinding

class KeyboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKeyboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKeyboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.comment.setOnClickListener {
            statusBarHeight?.let {
                Log.e("statusBarHeight", it.toString())
            }
            supportFragmentManager.beginTransaction().replace(R.id.input_container, ConversationFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }


        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        binding.switchFullScreen.setOnClickListener {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        //沉浸式状态栏
        //ImmersionBar.with(this).transparentStatusBar().init()



    }
}

val Context.statusBarHeight: Int
    get() {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
