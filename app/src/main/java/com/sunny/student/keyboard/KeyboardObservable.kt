package com.sunny.student.keyboard

import android.graphics.Color
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Created by sunshuo.man on 2022/12/22
 *
 * Observe keyboard
 * the padding in rootView will be ignored
 *
 * if use it in DialogFragment, please call [KeyboardObservable.Builder.build] after [androidx.fragment.app.DialogFragment.onCreateDialog]
 */
class KeyboardObservable private constructor(val keyBoardObserver: KeyBoardObserver?, val keyboardAnimatorObserver: KeyboardAnimatorObserver?, val rootView: View?,
                                             val window: Window?) {

    init {
        rootView?.let {
            it.fitsSystemWindows = true
            val windowsInsetsAnimatorCallBack =
                WindowsInsetsAnimatorCallBack(
                    WindowInsetsAnimationCompat.Callback.DISPATCH_MODE_STOP,
                )
            ViewCompat.setOnApplyWindowInsetsListener(it, windowsInsetsAnimatorCallBack)
            if (keyboardAnimatorObserver != null) {
                ViewCompat.setWindowInsetsAnimationCallback(it, windowsInsetsAnimatorCallBack)
            }

            if (keyBoardObserver != null && keyboardAnimatorObserver != null) {
                throw IllegalStateException("Can not set keyBoardObserver and keyboardAnimatorObserver together")
            }
        }
        window?.let {
            WindowCompat.setDecorFitsSystemWindows(it, false)
            it.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            it.statusBarColor = Color.TRANSPARENT
            it.navigationBarColor = Color.WHITE
        }
    }
    private inner class WindowsInsetsAnimatorCallBack(dispatchMode: Int) : WindowInsetsAnimationCompat.Callback(dispatchMode),
        OnApplyWindowInsetsListener {
        private var imeHeight = 0
        private var deferredInsets = false
        private val persistentInsetTypes = WindowInsetsCompat.Type.navigationBars()
        private val deferredInsetTypes = WindowInsetsCompat.Type.ime()
        private var lastWindowInsets: WindowInsetsCompat? = null
        private var isIMEShowing = false
        private var lastBottom = 0
        private var isClosingIME = false
        private var lastProgressHeight = 0

        private var isFloatingKeyboardPeriod = false



        private fun getIMEHeight(insets: WindowInsetsCompat): Int {
            val navbarHeight = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            //WindowInsetsCompat.Type.ime()).bottom is navigationBar's height + IME's height
            var imeHeight = (insets.getInsets(WindowInsetsCompat.Type.ime()).bottom - navbarHeight).coerceAtLeast(0)
            //there may have accuracy issues
            if (imeHeight <= 1) {
                imeHeight = 0
            }
            return imeHeight
        }
        override fun onApplyWindowInsets(v: View, insets: WindowInsetsCompat): WindowInsetsCompat {
            // avoid duplicate call
            if (lastWindowInsets != null && insets.getInsets(WindowInsetsCompat.Type.ime()).bottom == lastWindowInsets?.getInsets(
                    WindowInsetsCompat.Type.ime(),
                )?.bottom) {
                return insets
            }
            imeHeight = getIMEHeight(insets)
            // just use sysBars to set padding，ignore top
            rootView?.setPadding(
                insets.getInsets(WindowInsetsCompat.Type.systemBars()).left, rootView.paddingTop,
                insets.getInsets(
                    WindowInsetsCompat.Type.systemBars(),
                ).right,
                insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom,
            )

            isIMEShowing = if (imeHeight > 0) {
                true
            } else {
                insets.isVisible(WindowInsetsCompat.Type.ime())
            }
            if (imeHeight > 0) {
                isFloatingKeyboardPeriod = false
            } else if (isIMEShowing && imeHeight == 0) {
                isFloatingKeyboardPeriod = true
            }

            if (keyBoardObserver != null) {
                keyBoardObserver.updateSoftKeyboardState(isIMEShowing, imeHeight)
            } else {

                val types: Int =
                    if (deferredInsets) persistentInsetTypes else persistentInsetTypes or deferredInsetTypes
                val navbarHeight = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
                val targetInsets = insets.getInsets(types)
                var bottom = targetInsets.bottom
                if (navbarHeight > 0) {
                    bottom -= navbarHeight
                }
                bottom = bottom.coerceAtLeast(0)
                if (lastBottom != bottom) {
                    keyboardAnimatorObserver?.updateSoftKeyboardState(
                        isIMEShowing,
                        bottom,
                        bottom,
                    )
                    lastBottom = bottom
                }
            }
            lastWindowInsets = insets
            return insets
        }

        override fun onStart(
            animation: WindowInsetsAnimationCompat,
            bounds: WindowInsetsAnimationCompat.BoundsCompat
        ): WindowInsetsAnimationCompat.BoundsCompat {
            if (animation.typeMask and WindowInsetsCompat.Type.ime() != 0) {
                isClosingIME = false
            }
            return super.onStart(animation, bounds)
        }

        override fun onProgress(
            insets: WindowInsetsCompat,
            runningAnimations: List<WindowInsetsAnimationCompat>
        ): WindowInsetsCompat {
            for (windowInsetsAnimation in runningAnimations) {
                if (windowInsetsAnimation.typeMask and WindowInsetsCompat.Type.ime() != 0) {

                    val progressImeHeight = getIMEHeight(insets)
                    // bug fix
                    // when screen's orientation changes to landscape from portrait, insets.bottom is always 0
                    if (imeHeight == 0 && !isFloatingKeyboardPeriod) {
                        if (lastProgressHeight == 0) {
                            break
                        }
                    }

                    lastProgressHeight = progressImeHeight
                    isClosingIME = !isIMEShowing
                    //will npe if view be set null when progressImeHeight is 0(Dialog dismiss outside) before onEnd
                    if (keyboardAnimatorObserver != null) {
                        if (progressImeHeight > 0) {
                            keyboardAnimatorObserver.updateSoftKeyboardState(
                                isIMEShowing,
                                imeHeight,
                                progressImeHeight,
                            )
                        } else {
                            keyboardAnimatorObserver.updateSoftKeyboardState(
                                isIMEShowing,
                                imeHeight,
                                1
                            )
                        }
                    }
                    break
                }
            }
            return insets
        }

        override fun onPrepare(animation: WindowInsetsAnimationCompat) {
            if (animation.typeMask and deferredInsetTypes != 0) {
                deferredInsets = true
            }
            super.onPrepare(animation)
        }

        override fun onEnd(animation: WindowInsetsAnimationCompat) {
            if (deferredInsets && animation.typeMask and deferredInsetTypes != 0) {
                // If we deferred the IME insets and an IME animation has finished, we need to reset
                // the flag
                deferredInsets = false
                if (isClosingIME) {
                    isFloatingKeyboardPeriod = false
                    keyboardAnimatorObserver?.updateSoftKeyboardState(false, 0, 0)
                }
            }
            super.onEnd(animation)
        }
    }


    class Builder {
        private var keyBoardObserver: KeyBoardObserver? = null
        private var keyBoardAnimatorObserver: KeyboardAnimatorObserver? = null
        private var rootView: View? = null
        private var window: Window? = null

        /**
         * keyboardObserver without animator progress
         */
        fun keyboardObserver(keyboardObserver: KeyBoardObserver): Builder {
            this.keyBoardObserver = keyboardObserver
            return this
        }

        /**
         * keyboardObserver with animator progress
         */
        fun keyboardAnimatorObserver(keyboardAnimatorObserver: KeyboardAnimatorObserver): Builder {
            this.keyBoardAnimatorObserver = keyboardAnimatorObserver
            return this
        }

        /**
         * root view of your dialog or fragment
         */
        fun rootView(view: View): Builder {
            rootView = view
            return this
        }

        /**
         * window of your dialog
         * @param window nullable
         */
        fun window(window: Window?): Builder {
            this.window = window
            return this
        }

        /**
         *  if use it in DialogFragment, please call it after [androidx.fragment.app.DialogFragment.onCreateDialog]
         *  [androidx.fragment.app.DialogFragment.onViewCreated] is a good choice
         */
        fun build(): KeyboardObservable {
            return  KeyboardObservable(keyBoardObserver, keyBoardAnimatorObserver, rootView, window)
        }
    }
}