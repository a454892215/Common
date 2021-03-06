package com.test.java.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class FileUtil {

    static List<String> getAllChildFileAbsolutePathList(String directionPath) {
        File file = new File(directionPath);
        List<String> list = new ArrayList<>();
        File[] files = file.listFiles();
        if (files == null) {
            LogUtil.e("空目录");
            return list;
        }
        for (File value : files) {
            list.add(value.getAbsolutePath());
        }
        return list;
    }

    public static void deleteDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            LogUtil.d("删除文件失败:" + dirPath + "不存在！");
            return;
        }
        int count = 0;
        if (file.isDirectory()) {
            List<String> allChildFileAbsolutePathList = getAllChildFileAbsolutePathList(dirPath);
            for (int i = 0; i < allChildFileAbsolutePathList.size(); i++) {
                String filePath = allChildFileAbsolutePathList.get(i);
                File childFile = new File(filePath);
                if (childFile.isFile()) {
                    boolean delete = childFile.delete();
                    LogUtil.d("===删除文件：" + filePath + "是否成功：" + delete);
                    if (delete) {
                        count++;
                    }
                }
            }
            LogUtil.d("删除目录完毕，共删除文件:" + count + "个");
        } else {
            LogUtil.e("不是目录");
        }
    }


    public static void createDir(File dir) {
        if (!dir.exists()) {
            LogUtil.d("======createDir:" + dir.mkdirs());
        }
    }

    public static void readPerLine(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                LogUtil.d("=============:" + line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
