package com.test.util.custom_view2.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.common.comm.L;
import com.test.util.R;

import java.util.ArrayList;
import java.util.Random;


/**
 * Author:  L
 * CreateDate: 2019/1/23 17:05
 * Description: No
 */

public class BombView extends View {
    private Paint paint;
    private Paint textPaint;
    private Matrix matrix;
    private Bitmap bitmap;
    private ArrayList<P> list;
    private static final float diam = 2f; // 粒子直径


    public BombView(Context context) {
        this(context, null);
    }

    public BombView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }


    public BombView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null); // 图层混合模式使用 必须关闭硬件加速
        paint = new Paint();
        textPaint = new TextPaint();
        textPaint.setTextSize(L.dp_1 * 10);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        matrix = new Matrix();
        list = new ArrayList<>();
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);

        for (int x = 0; x < bitmap.getWidth(); x++) {
            for (int y = 0; y < bitmap.getHeight(); y++) {
                P p = new P();
                p.r = diam / 2f;
                p.x = x * diam + diam / 2f;
                p.y = y * diam + diam / 2f;
                p.color = bitmap.getPixel(x, y);

                p.vx = new Random().nextInt(40) - 20; // -20到20随机
                p.vy = new Random().nextInt(40) - 10; // -10到30随机

                p.ax = 1;
                p.ay = 1;
                list.add(p);
            }
        }

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setRepeatCount(-1);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            float animatedValue = (float) animation.getAnimatedValue();
            updateP();
            invalidate();
        });
        setOnClickListener(v -> {
            animator.start();
        });

    }

    private void updateP() {
        for (P p : list) {
            p.x += p.vx;
            p.y += p.vy;

            p.vx += p.ax;
            p.vy += p.ay;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // canvas.drawBitmap(bitmap, matrix, paint);
        canvas.translate(L.dp_1 * 120, L.dp_1 * 40);
        for (P p : list) {
            paint.setColor(p.color);
            canvas.drawCircle(p.x, p.y, p.r, paint);
        }
    }


}
