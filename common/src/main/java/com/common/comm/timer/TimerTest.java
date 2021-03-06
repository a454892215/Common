package com.common.comm.timer;

import com.common.base.BaseActivity;
import com.common.utils.LogUtil;

public class TimerTest {

    /**
     * @param time 秒
     */
    public static void testFPS(BaseActivity activity, int time) {
        MyTimer myTimer = new MyTimer(1000 * time, 10);
        myTimer.setOnTickListener((millisUntilFinished, count) -> {
            //计算每秒调用的次数
     /*       if (count % 100 == 0) {
                LogUtil.d("==1===count:" + count + "  FPS:" + count / (myTimer.getHasExecuteTime() / 1000f) + " aty:" + activity.getClass().getSimpleName());
            }*/
        });
        myTimer.start();
        activity.addOnPauseListener(myTimer::cancel);
    }
}
