package com.common.utils;

import android.text.TextUtils;
import android.util.Log;

import com.common.BuildConfig;
import com.orhanobut.logger.Logger;

/**
 * Description: 日志工具类
 */

public class LogUtil {

    public LogUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static boolean isDebug = BuildConfig.IS_DEBUG;

    private static final String TAG = "LLpp,LOGGER: ";

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);

    }

    public static void debug(String msg) {
        if (isDebug) {
            if (!TextUtils.isEmpty(msg)) {
                int length = msg.length();
                int count = 1024 * 3;
                int times = length / count + 1;
                for (int i = 0; i < times; i++) {
                    int end = (i + 1) * count > msg.length() ? msg.length() : (i + 1) * count;
                    Logger.d(TAG + i + "  text:  " + unicodeToUTF_8(msg.substring(i * count, end)));
                }
            }
        }
    }

    public static void json(String msg) {
        if (isDebug) {
            Logger.json(unicodeToUTF_8(msg));
        }
    }

    public static void e(String msg) {
        Logger.e("LLpp====" + unicodeToUTF_8(msg));
    }

    private static String unicodeToUTF_8(String src) {
        if (TextUtils.isEmpty(src)) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < src.length(); ) {
            char c = src.charAt(i);
            if (i + 6 < src.length() && c == '\\' && src.charAt(i + 1) == 'u') {
                String hex = src.substring(i + 2, i + 6);
                try {
                    out.append((char) Integer.parseInt(hex, 16));
                } catch (NumberFormatException nfe) {
                    nfe.fillInStackTrace();
                }
                i = i + 6;
            } else {
                out.append(src.charAt(i));
                ++i;
            }
        }
        return out.toString();
    }
}
