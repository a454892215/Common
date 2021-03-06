package com.common.x5_web.dialog;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.WindowManager;

import com.common.R;
import com.common.base.BaseAppRVAdapter;
import com.common.base.BaseDialogFragment;
import com.common.helper.RVHelper;
import com.common.listener.OnClickListener;
import com.common.utils.CastUtil;
import com.common.x5_web.adapter.BookmarkAdapter;
import com.common.x5_web.entity.BookmarkEntity;

import org.litepal.LitePal;
import org.litepal.crud.async.FindMultiExecutor;

import java.util.Collections;

public class BookmarkDialogFragment extends BaseDialogFragment {

    private RecyclerView rv;

    private OnClickListener onClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimStyle(R.style.dialog_anim_right);
        setDimeAmount(0f);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fra_web_bookmark;
    }

    @Override
    protected void initView() {
        rv = findViewById(R.id.rv);
        BaseAppRVAdapter adapter = RVHelper.initVerticalRV(activity, null, rv, BookmarkAdapter.class);
        adapter.setOnItemClick((view, position) -> {
            BookmarkEntity entity = CastUtil.cast(adapter.getList().get(position));
            String url = entity.getUrl();
            if (onClickListener != null) {
                dismiss();
                onClickListener.onClick(url);
            }
        });
        updateUI();
    }

    public void updateUI() {
        FindMultiExecutor<BookmarkEntity> allAsync = LitePal.findAllAsync(BookmarkEntity.class);
        allAsync.listen(entityList -> {
            Collections.reverse(entityList);
            RVHelper.notifyAdapterRefresh(entityList, rv);
        });
    }


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


}
