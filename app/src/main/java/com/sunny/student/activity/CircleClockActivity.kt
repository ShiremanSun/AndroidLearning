package com.sunny.student.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sunny.student.R
import kotlinx.android.synthetic.main.activity_circle_clock.*

class CircleClockActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_clock)

        circleClock.doInvalidate()
    }
}
