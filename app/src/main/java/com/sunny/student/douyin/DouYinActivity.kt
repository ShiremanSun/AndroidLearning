package com.sunny.student.douyin

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.sunny.student.R
import kotlinx.android.synthetic.main.activity_dou_yin.*

class DouYinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dou_yin)
        // 最外面的左右滑动adapter
        viewPager.adapter = MyPageAdapter()
    }

    class MyPageAdapter : PagerAdapter() {
        private var list  = mutableListOf<String>()
        init {
            for (i in 0..10) {
                list.add(i, i.toString())
            }
        }
        override fun getCount(): Int {
            return 2
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            return if (position == 0) {
                val view = LayoutInflater.from(container.context).inflate(R.layout.item_douyin_list, container, false)
                val viewPager2 = view.findViewById<ViewPager2>(R.id.twoPages)
                viewPager2.adapter = MyAdapter(list)
                container.addView(view)
                view
            } else {
                val image = ImageView(container.context)
                image.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                image.setImageResource(R.drawable.test)
                container.addView(image)
                image
            }
        }

    }
    //非静态内部类
    class MyAdapter private constructor() : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
        private lateinit var list : List<String>
        constructor(list: List<String>) : this() {
            this.list = list
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = TextView(parent.context)
            view.textSize = 20F
            view.gravity = Gravity.CENTER
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            view.layoutParams = layoutParams
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = list[position]
        }

        override fun getItemCount(): Int {
           return list.size
        }

        class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
            var textView : TextView = itemView as TextView
        }

    }
}