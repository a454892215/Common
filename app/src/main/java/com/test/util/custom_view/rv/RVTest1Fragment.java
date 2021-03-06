package com.test.util.custom_view.rv;

import androidx.recyclerview.widget.RecyclerView;

import com.common.base.BaseFragment;
import com.common.comm.L;
import com.common.helper.RVHelper;
import com.common.test.TestEntity;
import com.common.widget.rv.StickyHeaderDecoration;
import com.test.util.R;
import com.test.util.custom_view.adapter.RVTestAdapter;

import java.util.Arrays;

public class RVTest1Fragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rv_test1;
    }

    @Override
    protected void initView() {
        RecyclerView rv = findViewById(R.id.rv);
        Integer[] posArr = {3, 5, 9, 30, 34, 55, 78}; //有序数组
        String[] decorNameArr = {"3-title", "5-title", "9-title", "30-title", "34-title", "55-title", "78-title"};
        StickyHeaderDecoration decoration = new StickyHeaderDecoration(activity)
                .setDecorPositionList(Arrays.asList(posArr))
                .setDecorNameList(Arrays.asList(decorNameArr))
                .setHeaderHeight(Math.round(L.dp_1 * 18))
                .setHeaderBgColor(activity.getResources().getColor(R.color.light_purple));
        rv.addItemDecoration(decoration);
        RVHelper.initVerticalRV(activity, TestEntity.getList(), rv, RVTestAdapter.class);
    }
}
