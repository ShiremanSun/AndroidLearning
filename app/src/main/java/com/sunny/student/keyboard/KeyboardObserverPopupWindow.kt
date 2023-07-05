package com.sunny.student.keyboard

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
import android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
import android.view.WindowManager.LayoutParams.MATCH_PARENT
import android.widget.EditText
import com.sunny.student.R
import com.sunny.student.util.KeyBoardUtil

/**
 * Created by sunshuo.man on 2023/7/4
 */
class KeyboardObserverPopupWindow constructor(context: Context, editText: EditText, val keyboardObserver: KeyBoardObserver) : Dialog(context, R.style.ttlive_emoji_input_dialog), KeyBoardObserver{

    init {
        val contentView = EditText(context)
        setContentView(contentView)
        window?.let {
            it.setLayout(MATCH_PARENT, MATCH_PARENT)
            it.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            contentView.setOnClickListener {
                KeyBoardUtil.showSoftKeyBoard(getContext(), editText)
            }
            it.addFlags(FLAG_NOT_FOCUSABLE)
           // it.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            it.addFlags(FLAG_NOT_TOUCH_MODAL)
            KeyboardObservable.Builder().keyboardObserver(this).window(it).rootView(contentView).build()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun updateSoftKeyboardState(keyBoardVisible: Boolean, keyBoardHeight: Int) {
        keyboardObserver.updateSoftKeyboardState(keyBoardVisible, keyBoardHeight)
    }
}