package com.common.comm.timer;

import com.common.utils.LogUtil;

public class TimerTest {

    /**
     * @param time 秒
     */
    public static void testFPS(int time) {
        MyTimer myTimer = new MyTimer(1000 * time, 10);
        myTimer.setOnTickListener((millisUntilFinished, count) -> {
            long hasExecuteTime = myTimer.getHasExecuteTime();
            //计算每秒调用的次数
            if (count % 100 == 0) {
                LogUtil.d("=====:" + count + "  hasExecuteTime:" + hasExecuteTime + "  :" + count / (hasExecuteTime / 1000f));
            }
        });
        myTimer.start();
    }
}
