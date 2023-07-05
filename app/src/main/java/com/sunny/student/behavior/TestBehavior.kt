package com.sunny.student.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout

/**
 * Created by sunshuo.man on 2022/9/21
 * @author sunshuo.man@bytedance.com
 */
class TestBehavior: AppBarLayout.ScrollingViewBehavior {

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {

        // Offset the child, pinning it to the bottom the header-dependency, maintaining
        // any vertical gap and overlap
       /* val behavior = (dependency.layoutParams as CoordinatorLayout.LayoutParams).behavior
        val ablBehavior = behavior as AppBarLayout.BaseBehavior<*>*/
        android.util.Log.e("onDependentViewChanged","top = ${child.top} + ${child.bottom} + ${dependency.bottom - child.top}")
        ViewCompat.offsetTopAndBottom(
            child, (dependency.bottom - child.top))
        return false
    }
}