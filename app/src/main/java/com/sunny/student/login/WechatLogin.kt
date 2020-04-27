package com.sunny.student.login

import android.content.Context
import android.text.TextUtils
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import java.lang.IllegalArgumentException

/**
 * created by sunshuo
 * on 2020-02-28
 */
class WechatLogin private constructor(){

    private lateinit var context : Context
    constructor(context: Context) : this() {
        this.context = context
    }
    private var mAppID = ""
    private var mAppSecret = ""
    private var scope = "snsapi_userinfo" //应用授权作用域，如获取用户个人信息则填写 snsapi_userinfo
    private var state = WeChatConstant.loginState // 第三方程序发送时用来标识其请求的唯一性的标志，由第三方程序调用 sendReq 时传入，由微信终端回传，state 字符串长度不能超过 1K
    private var mILoginListener : ILoginListener? = null
    private val mIWXapi : IWXAPI by lazy {
        WXAPIFactory.createWXAPI(context, mAppID, false)
    }

    fun login(){
        if (TextUtils.isEmpty(mAppID) || TextUtils.isEmpty(mAppSecret)) {
            mILoginListener?.onError("AppID and AppSecret must not be null")
        }
        mIWXapi.registerApp(mAppID)
        WeChatConstant.iwxapi = mIWXapi
        val req = SendAuth.Req()
        req.scope = scope
        req.state = state
        mIWXapi.sendReq(req)
        WeChatConstant.iLoginListener = object : ILoginListener{
            override fun onSuccess(code: String) {
                val map = HashMap<String, String>()
                map["code"] = code
                map["scope"] = scope
                map[state] = state
                mILoginListener?.onSuccess(map)
            }
            override fun onSuccess(map: HashMap<String, String>) {
            }

            override fun onError(message: String) {
                mILoginListener?.onError(message)
            }

            override fun onCancel(message: String) {
                mILoginListener?.onCancel(message)
            }

        }

    }


    class Builder {
        private var mAppID = ""
        private var mAppSecret = ""
        private var mILoginListener: ILoginListener? = null
        private var mContext: Context? = null
        fun appID(appID: String): Builder {
            mAppID = appID
            return this
        }
        fun appSecret(appSecret : String) : Builder{
            mAppSecret = appSecret
            return this
        }
        fun loginListener(loginListener: ILoginListener) : Builder{
            mILoginListener = loginListener
            return this
        }

        fun context(context: Context) : Builder{
            mContext = context
            return this
        }

        fun build(): WechatLogin {
            if (mContext == null) {
                throw IllegalArgumentException("Context must not be null")
            }
            val wechatLogin = WechatLogin(mContext!!)
            wechatLogin.mAppID = mAppID
            wechatLogin.mAppSecret = mAppSecret
            wechatLogin.mILoginListener = mILoginListener
            return wechatLogin
        }

    }



}