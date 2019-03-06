package android.support.v7.app;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.common.AppApplication;


/**
 * author: ${VenRen}
 * created on: 2019/2/22 13:03
 * description: 自定义 TextView 使用指定字体 防止用户修改系统字体之后导致界面错乱
 */
public class FontText extends AppCompatTextView {

    public FontText(Context context) {
        super(context);
    }

    public FontText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        replaceCustomFount();
    }

    private void replaceCustomFount() {
        try {
            /*
             * 必须事先在assets底下创建一fonts文件夹，并放入要使用的字体文件(.ttf/.otf)
             * 并提供相对路径给creatFromAsset()来创建Typeface对象
             */
            Typeface typeface = getTypeface();
            int style = Typeface.NORMAL;
            if (typeface != null) {
                style = typeface.getStyle();
            }

            setTypeface(AppApplication.sTypeface, style);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
