package com.test.util.custom_view.fragment;

import com.common.base.BaseFragment;
import com.common.helper.FragmentHelper;
import com.common.widget.CommonTabLayout;
import com.test.util.R;
import com.test.util.custom_view.rv.RVTest1Fragment;
import com.test.util.custom_view.rv.RVTest2Fragment;

public class RVFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rv;
    }

    @Override
    protected void initView() {
        String[] tabNames = {"粘性头部", "RV动画"};
        Class[] classArr = {RVTest1Fragment.class, RVTest2Fragment.class};


        CommonTabLayout tab_layout_2 = findViewById(R.id.tab_layout_2);
        tab_layout_2.setData(tabNames, R.layout.template_hor_scroll_tab_item_2, R.id.tv);
        tab_layout_2.setOnSelectChangedListener(position -> FragmentHelper.onSwitchFragment(fm, classArr, position, R.id.flt_content, true));
        tab_layout_2.setCurrentPosition(0);

    }
}
