package com.sunny.student.wxapi;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sunny.student.login.WeChatConstant;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * created by sunshuo
 * on 2020-02-28
 */
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    /**
     * 微信发送的请求将回调到 onReq 方法
     */
    @Override
    public void onReq(BaseReq baseReq) {

    }

    /**
     *发送到微信请求的响应结果将回调到 onResp 方法
     */
    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp != null) {
            switch (baseResp.errCode) {
                case -4:
                    Toast.makeText(this, "用户拒绝授权", Toast.LENGTH_SHORT).show();
                    break;
                case -2:
                    Toast.makeText(this, "用户取消授权", Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    SendAuth.Resp resp = (SendAuth.Resp) baseResp;
                    //如果是登录
                    if (WeChatConstant.INSTANCE.getLoginState().equals(resp.state)) {
                        if (WeChatConstant.INSTANCE.getILoginListener() != null) {
                            WeChatConstant.INSTANCE.getILoginListener().onSuccess(resp.code);
                        }
                    }

            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (WeChatConstant.INSTANCE.getIwxapi() != null) {
            WeChatConstant.INSTANCE.getIwxapi().handleIntent(getIntent(), this);
        }
    }
}
