package com.sunny.student.banner.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.student.R
import com.sunny.student.banner.BannerIndicatorAdapter
import com.sunny.student.banner.BannerIndicatorViewHolder

/**
 * created by sunshuo
 * on 2020-02-26
 */
class MyBannerIndicatorAdapter : BannerIndicatorAdapter<MyBannerIndicatorAdapter.ViewHolder>{

    inner class ViewHolder(itemView : View) : BannerIndicatorViewHolder(itemView) {
        private val indicatorView = itemView.findViewById<View>(R.id.indicator)
        override fun select() {
            indicatorView.background = view.context.resources.getDrawable(R.drawable.banner_indicator_selected)
        }
        override fun unSelect() {
            indicatorView.background = view.context.resources.getDrawable(R.drawable.banner_indicator_unselected)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_banner_indicator, parent, false)
        return ViewHolder(view)
    }
}