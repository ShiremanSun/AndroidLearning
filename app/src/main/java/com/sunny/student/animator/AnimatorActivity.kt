package com.sunny.student.animator

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.animation.TranslateAnimation
import com.sunny.student.R
import kotlinx.android.synthetic.main.activity_animator.*

class AnimatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator)


        val translateAnimation = TranslateAnimation(0F,300F,0F,0F)
        translateAnimation.duration = 1000

        val translationX = ObjectAnimator.ofFloat(button, "translationX", 0F,300f)
        button2.setOnClickListener {
            Log.d("test-start", button.translationX.toString())
            //button.startAnimation(translateAnimation)
            translationX.start()
        }
        val scaleAnimationX = ObjectAnimator.ofFloat(button2,"scaleX",1.0F,0.8F)
        val scaleAnimationY = ObjectAnimator.ofFloat(button2,"scaleY",0.8F)
        val animationSet = with(AnimatorSet()) {
            playTogether(scaleAnimationX,scaleAnimationY)
            setDuration(300)
        }

        val scaleAnimationX1 = ObjectAnimator.ofFloat(button2,"scaleX",0.8F,1F)
        val scaleAnimationY1 = ObjectAnimator.ofFloat(button2,"scaleY",0.8F,1F)
        val animationSet1 = with(AnimatorSet()) {
            playTogether(scaleAnimationX1,scaleAnimationY1)
            setDuration(300)
        }
        animationSet.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                animationSet1.start()
            }

        })

        button2.setOnTouchListener { v, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> if (!animationSet.isRunning && !animationSet1.isRunning) animationSet.start() else return@setOnTouchListener true
                //MotionEvent.ACTION_UP -> animationSet1.start()
            }
            return@setOnTouchListener false
        }


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("test-touch", event?.x.toString())
        return super.onTouchEvent(event)
    }
}
