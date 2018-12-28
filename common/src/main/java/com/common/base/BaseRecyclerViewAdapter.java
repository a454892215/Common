package com.common.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.R;
import com.common.utils.CastUtil;
import com.common.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  Pan
 * CreateDate: 2018/12/18
 * Description: No
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder> {

    private Context context;
    protected List<T> list = new ArrayList<>();
    private int itemLayoutId;

    public BaseRecyclerViewAdapter(Context activity, int itemLayoutId, List<T> list) {
        this.context = activity;
        if (list != null) this.list.addAll(list);
        this.itemLayoutId = itemLayoutId;
    }

    //int i = 0;
    @NonNull
    @Override
    public BaseRecyclerViewAdapter.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(itemLayoutId, parent, false);
     //   LogUtil.d("====onCreateViewHolder=========:" + i++);
        return new BaseRecyclerViewAdapter.BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseRecyclerViewAdapter.BaseViewHolder holder, int position) {
        if (onItemClick != null) {
            OnClick onClick = CastUtil.cast(holder.itemView.getTag(R.id.key_item_click));
            if (onClick == null) {
                onClick = new OnClick();
                holder.itemView.setTag(R.id.key_item_click, onClick);
            }
            onClick.setView(holder.itemView);
            onClick.setPosition(position);
            holder.itemView.setOnClickListener(onClick);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class BaseViewHolder extends RecyclerView.ViewHolder {
        public View itemView;

        BaseViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    public List<T> getList() {
        return list;
    }


    private class OnClick implements View.OnClickListener {
        private View view;
        private int position;

        private void setView(View view) {
            this.view = view;
        }

        private void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            onItemClick.onItemClick(view, position);
        }
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onItemClick(View view, int position);
    }
}
