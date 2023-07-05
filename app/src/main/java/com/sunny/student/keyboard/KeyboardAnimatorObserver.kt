package com.sunny.student.keyboard

interface KeyboardAnimatorObserver {
    /**
     * for new keyboard animator
     * @param keyBoardVisible true keyboard is visible
     * @param targetKeyboardHeight 0 if keyboard will close, x (not 0) keyboard will show
     * @param progressHeight progressHeight of Keyboard when animation is running
     */

    fun updateSoftKeyboardState(
        keyBoardVisible: Boolean,
        targetKeyboardHeight: Int,
        progressHeight: Int
    )

}