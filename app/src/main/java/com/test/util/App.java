package com.test.util;

import com.common.CommApp;
import com.common.utils.LogUtil;
import com.common.helper.SoundPoolHelper;
import com.common.utils.SystemRingUtil;
import com.example.jpushdemo.JGInit;

/**
 * Author:  L
 * CreateDate: 2018/12/17 16:47
 * Description: No
 */

public class App extends CommApp {

    public SoundPoolHelper soundPoolUtil;
    public static App app;

    @Override
    public void onCreate() {
        isInitX5Web = true;
        app = this;
        super.onCreate();
        LogUtil.logEnable(BuildConfig.DEBUG);
        soundPoolUtil = new SoundPoolHelper(this, R.raw.button_tap);
        JGInit.init(this);
        SystemRingUtil.getInstance().init(this);
        LogUtil.i("===onCreate==App=========:" + BuildConfig.app_info);
    }
}
