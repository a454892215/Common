package com.test.util.custom_view.fragment;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;

import com.common.base.BaseFragment;
import com.common.helper.FragmentHelper;
import com.common.utils.CastUtil;
import com.test.util.R;
import com.common.dialog.BottomDialogFragment;
import com.common.dialog.CenterDialogFragment;

public class DialogTestFragment extends BaseFragment {

    Class[] fragmentArr = {CenterDialogFragment.class, BottomDialogFragment.class};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dia_fra;
    }

    @Override
    protected void initView() {
        LinearLayout llt_root = findViewById(R.id.llt_root);
        int childCount = llt_root.getChildCount();
        FragmentManager fm = getChildFragmentManager();
        for (int i = 0; i < childCount; i++) {
            View view = llt_root.getChildAt(i);
            int finalI = i;
            DialogFragment dialogFragment = (DialogFragment) FragmentHelper.getInstance(fm, CastUtil.cast(fragmentArr[finalI]));//缓存模式 无懒加载
            view.setOnClickListener(v -> dialogFragment.show(fm, fragmentArr[finalI].getName()));
        }
    }
}