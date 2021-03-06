package com.test.util.custom_view.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.common.base.BaseFragment;
import com.common.comm.timer.MyTimer;
import com.common.dialog.BottomDialogFragment;
import com.common.dialog.CenterDialogFragment;
import com.common.helper.FragmentHelper;
import com.common.utils.CastUtil;
import com.common.utils.FastClickUtil;
import com.common.utils.LogUtil;
import com.test.util.App;
import com.test.util.R;
import com.common.base.BaseDropDialogFragment;

public class DialogTestFragment_03 extends BaseFragment {

    private Class[] fragmentArr = {CenterDialogFragment.class, BottomDialogFragment.class};
    private MyTimer timer;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dia_fra;
    }

    @Override
    protected void initView() {
        BaseDropDialogFragment drop_2 = new BaseDropDialogFragment(activity);
        findViewById(R.id.tv_drop_2).setOnClickListener(anchorView -> {
            App app = (App) activity.getApplication();
            app.soundPoolUtil.play(0);
            drop_2.showAsDropDown(anchorView, 0, 0);
        });

        LinearLayout llt_root = findViewById(R.id.llt_content);
        int childCount = llt_root.getChildCount();
        FragmentManager fm = getChildFragmentManager();
        for (int i = 0; i < childCount; i++) {
            View view = llt_root.getChildAt(i);
            int finalI = i;
            DialogFragment dialogFragment = (DialogFragment) FragmentHelper.getInstance(fm, CastUtil.cast(fragmentArr[finalI]));//缓存模式 无懒加载
            view.setOnClickListener(v -> dialogFragment.show(fm, fragmentArr[finalI].getName()));
        }

        ProgressBar progress_bar = findViewById(R.id.progress_bar);
        progress_bar.setProgress(100);
        progress_bar.setOnClickListener(v -> {
            FastClickUtil.isFastClick(3000);
            if (timer != null) timer.cancel();
            timer = new MyTimer(3000, 30);
            timer.setOnTickListener((time, count) -> {
                LogUtil.d("=======count：" + count + "  time:" + (time / 1000f));
                progress_bar.setProgress(count);
            });
            timer.start();
        });

        initSeekBar(findViewById(R.id.seek_bar));
    }

    private void initSeekBar(SeekBar seek_bar_bg_music) {
        seek_bar_bg_music.setMax(50);
        seek_bar_bg_music.setProgress(100);
        seek_bar_bg_music.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                this.progress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

}
