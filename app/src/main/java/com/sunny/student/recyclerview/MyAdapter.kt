package com.sunny.student.recyclerview

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sunny.student.bean.CityBean
import com.sunny.student.R
import java.util.ArrayList

/**
 * Created by Android Studio.
 * Date: 2019/8/15
 * Time: 11:18 AM
 */
class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    var datas = ArrayList<CityBean>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tag.text = datas[position].tag
    }

    class ViewHolder(view : View): androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val tag = view.findViewById<TextView>(R.id.tag)!!

    }


}