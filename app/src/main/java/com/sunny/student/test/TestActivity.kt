package com.sunny.student.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.sunny.student.R
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val view = LayoutInflater.from(this).inflate(R.layout.test_add_view_layout, null)
        test_container.addView(view)
//        view.layoutParams = view.layoutParams.apply {
//           width = ViewGroup.LayoutParams.MATCH_PARENT
//           height = ViewGroup.LayoutParams.MATCH_PARENT
//       }
    }
}
