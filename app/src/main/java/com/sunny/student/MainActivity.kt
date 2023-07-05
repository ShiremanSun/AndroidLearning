package com.sunny.student

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.Choreographer
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import com.sunny.student.activity.CircleClockActivity
import com.sunny.student.activity.FragmentTestActivity
import com.sunny.student.activity.ThemeActivity
import com.sunny.student.animator.AnimatorActivity
import com.sunny.student.banner.test.BannerActivity
import com.sunny.student.behavior.SuctionBottomBehavior
import com.sunny.student.dialog.TopDialogFragment
import com.sunny.student.douyin.DouYinActivity
import com.sunny.student.fragment.ItemListDialogFragment
import com.sunny.student.fresco.FrescoActivity
import com.sunny.student.keyboard.KeyboardActivity
import com.sunny.student.login.ILoginListener
import com.sunny.student.login.WechatLogin
import com.sunny.student.maodian.MaoDianActivity
import com.sunny.student.moveitem.MoveingItemActivity
import com.sunny.student.navigation.NavigationRootActivity
import com.sunny.student.overlapopt.OverLapingActivity
import com.sunny.student.retrofit.RetrofitTestActivity
import com.sunny.student.test.TestActivity
import com.sunny.student.util.CrashHandler
import com.sunny.student.viewmodel.ViewModelActivity
import com.sunny.student.webview.WebViewFragment
import com.sunshuo.grpc.jaeger.ui.JaegerTestActivity
import kotlinx.android.synthetic.main.activity_main.EditText
import kotlinx.android.synthetic.main.activity_main.animatorActivity
import kotlinx.android.synthetic.main.activity_main.banner
import kotlinx.android.synthetic.main.activity_main.behavior
import kotlinx.android.synthetic.main.activity_main.bottomSheet
import kotlinx.android.synthetic.main.activity_main.button_container
import kotlinx.android.synthetic.main.activity_main.circleClock
import kotlinx.android.synthetic.main.activity_main.douyin
import kotlinx.android.synthetic.main.activity_main.fragmentTest
import kotlinx.android.synthetic.main.activity_main.fresco
import kotlinx.android.synthetic.main.activity_main.goAnimator
import kotlinx.android.synthetic.main.activity_main.jaeger
import kotlinx.android.synthetic.main.activity_main.keyboard
import kotlinx.android.synthetic.main.activity_main.maodian
import kotlinx.android.synthetic.main.activity_main.movingItem
import kotlinx.android.synthetic.main.activity_main.navigation
import kotlinx.android.synthetic.main.activity_main.overlap
import kotlinx.android.synthetic.main.activity_main.retrofit
import kotlinx.android.synthetic.main.activity_main.switchTheme
import kotlinx.android.synthetic.main.activity_main.test
import kotlinx.android.synthetic.main.activity_main.viewModel
import kotlinx.android.synthetic.main.activity_main.webview
import kotlinx.android.synthetic.main.activity_main.wechatLogin
import kotlinx.android.synthetic.main.activity_main.zButton


class MainActivity : FragmentActivity(), MainActivityContact.View, ItemListDialogFragment.Listener{
    override fun onItemClicked(position: Int) {
    }

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CrashHandler.instance.init(this)
        goAnimator.setOnClickListener {
            Log.d("openNo", isOpen2(this).toString())
            openN(this)
        }

        val webView = WebView(this)
        webView.webViewClient = object : WebViewClient(){

        }



        val choreographer = Choreographer.getInstance()

        val method = Choreographer::class.java.getDeclaredMethod("postCallback",Int::class.java,Runnable::class.java,Object::class.java)
        method.isAccessible = true
        val runnable = Runnable {
            Log.d("syncScreen", "屏幕刷新了")
            //method.invoke(choreographer, 2,this,null)
        }
        method.invoke(choreographer, 2,runnable,null)

        zButton.setOnClickListener {
            Toast.makeText(this, "hhh", Toast.LENGTH_SHORT).show()
        }

        button_container.isClickable = true


        fragmentTest.setOnClickListener {
            val intent = Intent(this, FragmentTestActivity::class.java)
            startActivity(intent)

        }


        circleClock.setOnClickListener {
            val intent = Intent(this, CircleClockActivity::class.java)
            startActivity(intent)
        }


        bottomSheet.setOnClickListener {
            val itemListDialogFragment = ItemListDialogFragment.newInstance(5)
            itemListDialogFragment.show(supportFragmentManager, "item")
        }

        navigation.setOnClickListener {
            val intent = Intent(this, NavigationRootActivity::class.java)
            startActivity(intent)
        }

        viewModel.setOnClickListener {
            val intent = Intent(this, ViewModelActivity::class.java)
            startActivity(intent)
        }

        test.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }

        banner.setOnClickListener {
            startActivity(Intent(this, BannerActivity::class.java))
        }

        wechatLogin.setOnClickListener {
            WechatLogin.Builder()
                    .context(this)
                    .appID("wxe30be860e55617af")
                    .appSecret("1d6f15a128152d6fa98355ced088b3ae")
                    .loginListener(object : ILoginListener{
                        override fun onSuccess(code: String) {
                        }

                        override fun onSuccess(map: HashMap<String, String>) {
                        }

                        override fun onError(message: String) {
                        }

                        override fun onCancel(message: String) {
                        }

                    })
                    .build().login()
        }
        EditText.setOnClickListener {
            startActivity(Intent(this, EditTextTest::class.java))
        }
        webview.setOnClickListener {
            startActivity(Intent(this, WebViewFragment::class.java))
        }

        switchTheme.setOnClickListener {
            startActivity(Intent(this, ThemeActivity::class.java))
        }

        maodian.setOnClickListener {
            startActivity(Intent(this, MaoDianActivity::class.java))
        }

        jaeger.setOnClickListener {
            startActivity(Intent(this, JaegerTestActivity::class.java))
        }

        douyin.setOnClickListener {
            TopDialogFragment().show(supportFragmentManager, "")
            startActivity(Intent(this, DouYinActivity::class.java))
            Handler().postDelayed({

            }, 1000)
        }

        movingItem.setOnClickListener {
            startActivity(Intent(this, MoveingItemActivity::class.java))
        }

        behavior.setOnClickListener {
            startActivity(Intent(this, SuctionBottomBehavior::class.java))

        }

        retrofit.setOnClickListener {
            startActivity(Intent(this, RetrofitTestActivity::class.java))
        }
        overlap.setOnClickListener {
            startActivity(Intent(this, OverLapingActivity::class.java))
        }

        fresco.setOnClickListener {
            startActivity(Intent(this, FrescoActivity::class.java))
        }

        keyboard.setOnClickListener {
            startActivity(Intent(this, KeyboardActivity::class.java))

        }

        animatorActivity.setOnClickListener {
            val intent = Intent(this, AnimatorActivity::class.java)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, animatorActivity, "button")
            startActivity(intent, activityOptions.toBundle())
        }

    }

    /**
     * 获取设备信息（目前支持几大主流的全面屏手机，亲测华为、小米、oppo、魅族、vivo、三星都可以）
     *
     * @return
     */
    private fun getDeviceInfo(): String {
        val brand = Build.BRAND
        if (TextUtils.isEmpty(brand)) return "navigationbar_is_min"

        return if (brand.equals("HUAWEI", ignoreCase = true) || "HONOR" == brand) {
            "navigationbar_is_min"
        } else if (brand.equals("XIAOMI", ignoreCase = true)) {
            "force_fsg_nav_bar"
        } else if (brand.equals("VIVO", ignoreCase = true)) {
            "navigation_gesture_on"
        } else if (brand.equals("OPPO", ignoreCase = true)) {
            "navigation_gesture_on"
        } else if (brand.equals("samsung", ignoreCase = true)) {
            "navigationbar_hide_bar_enabled"
        } else {
            "navigationbar_is_min"
        }
    }

    /**
     * 判断设备是否存在NavigationBar
     *
     * @return true 存在, false 不存在
     */
    fun deviceHasNavigationBar(): Boolean {
        var haveNav = false
        try {
            //1.通过WindowManagerGlobal获取windowManagerService
            // 反射方法：IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
            val windowManagerGlobalClass = Class.forName("android.view.WindowManagerGlobal")
            val getWmServiceMethod = windowManagerGlobalClass.getDeclaredMethod("getWindowManagerService")
            getWmServiceMethod.isAccessible = true
            //getWindowManagerService是静态方法，所以invoke null
            val iWindowManager = getWmServiceMethod.invoke(null)

            //2.获取windowMangerService的hasNavigationBar方法返回值
            // 反射方法：haveNav = windowManagerService.hasNavigationBar();
            val iWindowManagerClass = iWindowManager.javaClass
            val hasNavBarMethod = iWindowManagerClass.getDeclaredMethod("hasNavigationBar")
            hasNavBarMethod.isAccessible = true
            haveNav = hasNavBarMethod.invoke(iWindowManager) as Boolean
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return haveNav
    }

    private fun navigationGestureEnabled(context: Context): Boolean {
        val `val` = Settings.Global.getInt(context.getContentResolver(), getDeviceInfo(), 0)
        return `val` != 0
    }

    fun hasNavigationBar(context: Context): Boolean {
        //navigationGestureEnabled()从设置中取不到值的话，返回false，因此也不会影响在其他手机上的判断
        return deviceHasNavigationBar() && !navigationGestureEnabled(context)
    }

    fun getNavigationBarHeight(context: Context): Int {
        var result = 0
        if (hasNavigationBar(context)) {
            val res = context.resources
            val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId)
            }
        }
        return result
    }

    fun getForceNavigationBarHeight(context: Context):Int {
        val res = context.resources
        var result = 0
        val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun getStatusBarHeight(context: Context?): Int {
        var statusBarHeight = 0
        if (context != null) {
            try {
                val c = Class.forName("com.android.internal.R\$dimen")
                val o = c.newInstance()
                val field = c.getField("status_bar_height")
                val x = field.get(o) as Int
                statusBarHeight = context.resources.getDimensionPixelSize(x)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return statusBarHeight
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            Log.d("open", "打开了")
        } else {
            Log.d("open", "没打卡")
        }
    }
}
