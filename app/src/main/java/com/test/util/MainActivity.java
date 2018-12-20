package com.test.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.common.adapter.TextViewAdapter;
import com.common.adapter.common.RecyclerViewUtil;
import com.common.base.BaseActivity;
import com.common.base.BaseRecyclerViewAdapter;

import java.util.Arrays;

public class MainActivity extends BaseActivity {

    private String[] names = {"RecyclerView 测试"};
    private Class[] classArr = {RVTestActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recycler_view = findViewById(R.id.recycler_view);
        BaseRecyclerViewAdapter adapter = new TextViewAdapter(activity, R.layout.view_btn_1, Arrays.asList(names));
        RecyclerViewUtil.setRecyclerView(recycler_view, adapter);
        adapter.setOnItemClick((itemView, position) -> startActivity(new Intent(activity, classArr[position])));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
