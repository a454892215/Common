package com.common.utils;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * Author:  L
 * Description: No
 */
@SuppressWarnings("unused")
public class ViewAnimUtil {

    public static void startBgAlphaAnim(View view, int startAlpha, int endAlpha,int during) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startAlpha, endAlpha);
        valueAnimator.setDuration(during);
        valueAnimator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            String hex = Integer.toHexString(value);
            if (hex.length() == 1) hex = "0" + hex;
            String color = "#" + hex + "000000";
            view.setBackgroundColor(Color.parseColor(color));
        });
        valueAnimator.start();
    }

    public static void startAlphaAnim(View view, float startAlpha, float endAlpha, int during) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(startAlpha, endAlpha);
        valueAnimator.setDuration(during);
        valueAnimator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            view.setAlpha(value);
        });
        valueAnimator.start();
    }

    public static Animation getDownOpenAnim(int during){
        ScaleAnimation animation = new ScaleAnimation(1, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(during);
        return animation;
    }

    public static Animation getDownCloseAnim(int during){
        ScaleAnimation animation = new ScaleAnimation(1, 1, 1, 0,
                Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(during);
        animation.setFillAfter(true);
        return animation;
    }
}