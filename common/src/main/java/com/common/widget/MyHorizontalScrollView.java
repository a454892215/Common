package com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

public class MyHorizontalScrollView extends HorizontalScrollView {

    private OnScrollViewListener scrollViewListener = null;

    public MyHorizontalScrollView(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(OnScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
/*
    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        if (scrollEnable) {
            return super.onInterceptHoverEvent(event);
        } else {
            return false;
        }

    }*/

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (scrollEnable) {
            return super.dispatchTouchEvent(ev);
        } else {
            View view = getChildAt(0);
            if (view != null) {
                view.dispatchTouchEvent(ev);
            }
        }
        return true;
    }

    private boolean scrollEnable = true;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (scrollEnable) {
            return super.onTouchEvent(ev);
        }
        return true;
    }


    public void setScrollEnable(boolean scrollEnable) {
        this.scrollEnable = scrollEnable;
    }

    public boolean isScrollEnable() {
        return scrollEnable;
    }


    public interface OnScrollViewListener {
        void onScrollChanged(MyHorizontalScrollView scrollView, int x, int y, int oldx, int oldy);
    }

    @Override
    public void fling(int velocity) {
        super.fling(velocity / damping);
    }

    int damping = 1;

    public void stopFling() {
        damping = 1000000;
    }

    public void restartFling() {
        damping = 1;
    }
}
