package com.sunny.tinkertest

import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import java.lang.reflect.InvocationTargetException
import kotlin.math.acos


/**
 * Created by Android Studio.
 * Date: 2019/8/9
 * Time: 8:53 AM
 */

/**
 * 获取文字贴近x轴的上边缘的y坐标
 */


public fun Paint.getBottomY() : Float {
    //获取基线和文字底边缘的距离
    return - this.fontMetrics.bottom
}

public fun Paint.getCenterY() : Float {

    //两行文字的中心
    return this.fontSpacing / 2 - this.fontMetrics.bottom
}
//
fun Paint.getTopY() : Float {
    //基线到文字上边的距离
    return - this.fontMetrics.ascent
}

//将数字转换为中文,两位数之内
fun Int.toText() : String{
    //首先转为Int数组
    var result = ""
    val iArr = "$this".toCharArray().map { it.toString().toInt()}
    if (iArr.size > 1) {
        //在"十"之前将改数字加上
        if (iArr[0] != 1) {
            result += Text.NUMBER_TEXT_LIST[iArr[0]]
        }
        result += "十"
        //如果第二个数=0可直接跳过
        if (iArr[1] > 0) {
            result += Text.NUMBER_TEXT_LIST[iArr[1]]
        }
    } else {
        result = Text.NUMBER_TEXT_LIST[iArr[0]]
    }

    return result
}

object Text{
    val NUMBER_TEXT_LIST = listOf(
            "日",
            "一",
            "二",
            "三",
            "四",
            "五",
            "六",
            "七",
            "八",
            "九",
            "十"
    )
}

private val CHECK_OP_NO_THROW = "checkOpNoThrow"
private val OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION"
fun isOpen(context : Context) : Boolean {
    val mAppOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager

    val appInfo = context.applicationInfo

    val pkg = context.applicationContext.packageName

    val uid = appInfo.uid

    var appOpsClass: Class<*>? = null /* Context.APP_OPS_MANAGER */

    try {

        appOpsClass = Class.forName(AppOpsManager::class.java.name)

        val checkOpNoThrowMethod = appOpsClass!!.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String::class.java)

        val opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION)
        val value = opPostNotificationValue.get(Int::class.java) as Int

        return checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) as Int == AppOpsManager.MODE_ALLOWED

    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
    } catch (e: NoSuchMethodException) {
        e.printStackTrace()
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: InvocationTargetException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }

    return false
}

fun isOpen2(context: Context) : Boolean {
    return NotificationManagerCompat.from(context).areNotificationsEnabled();
}

fun openN(context: Context) {
    val intent = Intent()

    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, context.applicationInfo.uid)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra("app_package", context.packageName)
            intent.putExtra("app_uid", context.applicationInfo.uid)

        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.data = Uri.fromParts("package", context.packageName, null);
        }
        context.startActivity(intent)

    } catch (e : Exception) {

    }



}