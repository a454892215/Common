package com.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  Pan
 * CreateDate: 2018/12/20 15:35
 * Description: No
 */

public class TestDataUtil {

    public static List<String> getData(int size) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            arrayList.add("测试数据："+i);
        }
        return arrayList;
    }
}
