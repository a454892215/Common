package com.common.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;


public abstract class BasePop {

    private View rootView;

    public BasePop(BaseActivity activity) {
        this.activity = activity;
        activity.setOnBackPressedListener(() -> {
            if (rootView != null && rootView.getParent() != null) {
                dismiss();
                return true;
            }
            return false;
        });
    }

    protected BaseActivity activity;

    @SuppressWarnings("unused")
    public void show() {
        ViewGroup contentView = activity.findViewById(android.R.id.content);
        if (rootView == null) {
            rootView = LayoutInflater.from(activity).inflate(getLayoutId(), contentView, false);
        }
        if (rootView.getParent() == null) {
            contentView.addView(rootView);
            initView();
        }
    }

    public void showAsDropDown(View anchorView) {
        int[] location_anchor = new int[2];
        int[] location_content = new int[2];
        anchorView.getLocationOnScreen(location_anchor);
        ViewGroup contentView = activity.findViewById(android.R.id.content);
        contentView.getLocationOnScreen(location_content);
        if (rootView == null) {
            rootView = LayoutInflater.from(activity).inflate(getLayoutId(), contentView, false);
        }
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) rootView.getLayoutParams();
        lp.topMargin = location_anchor[1] + anchorView.getHeight() - location_content[1];
        if (rootView.getParent() == null) {
            contentView.addView(rootView, lp);
            initView();
        }
    }

    public void dismiss() {
        ViewParent parent = rootView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(rootView);
        }
    }

    public boolean isShowing() {
        return rootView != null && rootView.getParent() != null;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    public <T extends View> T findViewById(int id) {
        return rootView.findViewById(id);
    }

}