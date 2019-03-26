package com.test.util.fragment;

import android.support.v4.app.FragmentManager;
import android.view.View;

import com.common.base.BaseFragment;
import com.common.utils.LogUtil;
import com.test.util.R;
import com.test.util.dialog.TestDialogFragment;

public class DiaFragFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dia_fra;
    }

    @Override
    protected void initView(View rootView) {
        TestDialogFragment testDialogFragment = new TestDialogFragment();
        FragmentManager fragmentManager = getFragmentManager();
        rootView.findViewById(R.id.btn).setOnClickListener(v -> {
            String name = testDialogFragment.getClass().getName();
            LogUtil.d("name:"+name);
            testDialogFragment.show(fragmentManager, name);
        });
    }
}