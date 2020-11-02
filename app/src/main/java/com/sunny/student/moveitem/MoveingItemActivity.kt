package com.sunny.student.moveitem

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sunny.student.R
import kotlinx.android.synthetic.main.activity_moveing_item.*
import kotlinx.android.synthetic.main.item_maodian_recycler.view.*
import java.util.*

class MoveingItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moveing_item)

        val list = mutableListOf<String>()
        list.add("实用英语")
        list.add("考研数学")
        list.add("公考")

        recyclerView.adapter = MyAdapter(list)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
                        0)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                recyclerView.adapter?.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
                Collections.swap(list, viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
            }


        }).run {
            attachToRecyclerView(recyclerView)
        }

    }


    class MyAdapter private constructor(): RecyclerView.Adapter<MyAdapter.ViewHolder>() {
        private lateinit var list : MutableList<String>
        constructor(list: MutableList<String>) :this(){
            this.list = list
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = TextView(parent.context).apply {
                textSize = 16F
                setTextColor(Color.WHITE)
                gravity = Gravity.CENTER
                setPadding(10, 10, 10, 10)

                background = ResourcesCompat.getDrawable(parent.context.resources, R.drawable.file_item_bg, null)
            }
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = list[position]
        }

        override fun getItemCount(): Int {
            return list.size
        }

        class ViewHolder(view:View) : RecyclerView.ViewHolder(view) {
            val textView : TextView = view as TextView
        }
    }
}