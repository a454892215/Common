package com.common.helper;

import androidx.recyclerview.widget.RecyclerView;

import com.common.base.BaseAppRVAdapter;
import com.common.base.BaseRVAdapter;
import com.common.utils.LogUtil;

import java.util.List;

public class AdapterHelper {

    @SuppressWarnings("unchecked")
    public static void notifyAdapterRefresh(List list, RecyclerView rv) {
        BaseRVAdapter adapter = (BaseRVAdapter) rv.getAdapter();
        if (adapter != null) {
            adapter.getList().clear();
            if (list != null) adapter.getList().addAll(list);
            adapter.notifyDataSetChanged();
        } else {
            LogUtil.e("发生异常：RecyclerView还没有设置Adapter");
        }
    }

    @SuppressWarnings("unchecked")
    public static void notifyAdapterLoadMore(List list, RecyclerView rv) {
        BaseRVAdapter adapter = (BaseRVAdapter) rv.getAdapter();
        if (adapter != null && list != null) {
            adapter.getList().addAll(list);
            adapter.notifyDataSetChanged();
        } else {
            LogUtil.e("发生异常：RecyclerView还没有设置Adapter");
        }
    }
}
