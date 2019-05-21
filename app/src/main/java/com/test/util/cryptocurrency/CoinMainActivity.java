package com.test.util.cryptocurrency;

import android.os.Bundle;

import com.common.http.ApiCreator;
import com.common.http.HttpUtil;
import com.common.http.inter.HttpCallback;
import com.common.utils.LogUtil;
import com.test.util.BuildConfig;
import com.test.util.R;
import com.test.util.base.BaseAppActivity;

public class CoinMainActivity extends BaseAppActivity {

    private static GateApi gateApi;
    private HttpUtil httpUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (gateApi == null) {
            ApiCreator apiCreator = new ApiCreator();
            apiCreator.logEnable(BuildConfig.DEBUG);
            gateApi = apiCreator.getApi(GateApi.baseUrl, GateApi.class);
        }

        httpUtil = new HttpUtil(this);
        requestData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coin_enter;
    }


    private void requestData() {
        httpUtil.requestData(gateApi.getCandleStick2(), new HttpCallback() {
            @Override
            public void onSuccess(String text) {
                LogUtil.d("===================text:" + text);
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }
}