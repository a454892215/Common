package com.example.jpushdemo;

import android.text.TextUtils;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Description: 日志工具类
 */

public class LogUtil {

    private static final String TAG = "LLpp: ";
    private static boolean enable = true;

    public static void logEnable(boolean enable) {
        LogUtil.enable = enable;
    }

    public static void d(String msg) {
        if (enable) {
            if (!TextUtils.isEmpty(msg)) {
                int length = msg.length();
                int count = 1024 * 3;
                int times = length / count + 1;
                for (int i = 0; i < times; i++) {
                    int end = (i + 1) * count > msg.length() ? msg.length() : (i + 1) * count;
                    Log.d(TAG + getLineNum(), "  text:  " + unicodeToUTF_8(msg.substring(i * count, end)));
                }
            }
        }
    }
    public static void i(String msg) {
        Log.i(TAG + getLineNum(), unicodeToUTF_8(msg));
    }

    public static void v(String msg) {
        Log.v(TAG + getLineNum(), unicodeToUTF_8(msg));
    }

    public static void w(String msg) {
        Log.w(TAG + getLineNum(), unicodeToUTF_8(msg));
    }

    public static void e(String msg) {
        Log.e(TAG + getLineNum(), unicodeToUTF_8(msg));
    }

    public static void e(Throwable e) {
        Log.e(TAG + getLineNum(), getThrowableInfo(e));
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

    private static String getLineNum() {
        StackTraceElement ste = new Throwable().getStackTrace()[2];
        return "(" + ste.getFileName() + ":" + ste.getLineNumber() + ") ";
    }

    public static String getThrowableInfo(Throwable e) {
        String text = ":null:";
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            text = sw.toString();
            sw.close();
        } catch (Exception e1) {
            LogUtil.e("===============e:" + e);
            e1.printStackTrace();
        }

        return text;
    }
}
