package com.common.helper;

import com.common.utils.LogUtil;
import com.google.gson.Gson;


public class GsonHelper {

    public static <T> T getEntity(String text, Class<T> classType){
        T t = null;
        try {
            t = new Gson().fromJson(text, classType);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("封装数据失败");
        }
        return t;
    }
}
