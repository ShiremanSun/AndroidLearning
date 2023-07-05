package com.sunny.student.behavior

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.student.R
import com.sunny.student.bean.CityBean
import com.sunny.student.recyclerview.MyAdapter
import kotlinx.android.synthetic.main.activity_suction_bottom_behavior.*
import java.util.ArrayList

class SuctionBottomBehavior : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suction_bottom_behavior)

        val cityBeanList = ArrayList<CityBean>().apply {
            add(CityBean("B", "北京"))
            add(CityBean("B", "北京"))
            add(CityBean("B", "北京"))
            add(CityBean("B", "北京"))
            add(CityBean("B", "北京"))
        }

        recycleView.layoutManager = LinearLayoutManager(this)

        recycleView.adapter = MyAdapter().apply {
            datas = cityBeanList
        }


    }
}