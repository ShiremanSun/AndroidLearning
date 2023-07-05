package com.sunny.student.overlapopt

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sunny.student.R
import kotlinx.android.synthetic.main.activity_over_laping.changeAlpha
import kotlinx.android.synthetic.main.activity_over_laping.content

class OverLapingActivity : AppCompatActivity() {

    var alphaed = false

    companion object {
        val renderNode = View::class.java.getDeclaredField("mRenderNode").apply {
            isAccessible = true

        }
        var setOverlapMethod = renderNode.type.getDeclaredMethod("setHasOverlappingRendering", Boolean::class.java).apply {
            isAccessible = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_over_laping)
        changeAlpha.setOnClickListener {
            changeAlpha()
        }

    }

    fun View.setHeight(height: Int) {
         val layoutParams = getLayoutParams()
        if (layoutParams.height != height) {
            layoutParams.height = height
        }
        setLayoutParams(layoutParams)
    }

    fun changeAlpha() {
        ValueAnimator().apply {
            setFloatValues(0F, 1F)
            duration = 500
            addUpdateListener {
                val count = content.childCount
                for (i in 0 until count) {
                    val alpha = if (!alphaed) {
                        (1 - (it.animatedValue as Float))
                    } else {
                        (it.animatedValue as Float)
                    }
                    val child = content.getChildAt(i)
                    if (child.id != R.id.changeAlpha && child.id != R.id.text2) {
                        child.alpha = alpha
                    }
                }
            }
            addListener(object :AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    val count = content.childCount
                    for (i in 0 until count) {
                        val child = content.getChildAt(i)
                        val time = System.currentTimeMillis()
                        //setOverlapMethod?.invoke(renderNode.get(child), false)
                        Log.e("reflectDuration", (System.currentTimeMillis() - time).toString())
                    }
                }
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    alphaed = !alphaed
                    val count = content.childCount
                    for (i in 0 until count) {
                        val child = content.getChildAt(i)
                        //setOverlapMethod?.invoke(renderNode.get(child), true)
                    }
                }
            })
            start()
        }
    }
}