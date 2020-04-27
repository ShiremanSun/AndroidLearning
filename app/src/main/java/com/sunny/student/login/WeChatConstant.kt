package com.sunny.student.login

import com.tencent.mm.opensdk.openapi.IWXAPI

/**
 * created by sunshuo
 * on 2020-02-28
 */
object WeChatConstant {
    val loginState = "login_sdk_wechat"
    var iwxapi : IWXAPI? = null
    var iLoginListener : ILoginListener? = null
}