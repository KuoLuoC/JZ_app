package com.wzq.jz_app.utils;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * 作者：王志强 on 2019/6/17.
 * 邮箱：wang_love152@163.com
 *  将数据存储到文件/文件中读取字符串
 */

public class FileUtil {

    //读取文件中的字符串
    public static String readFile(String filePath) {
        File file = new File(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        char [] buf = new char[64];
        int count=0;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fileInputStream, "UTF-8");
            while ((count = reader.read(buf)) != -1) {
                stringBuilder.append(buf,0,count);
            }
        } catch (Exception e) {
            Log.e("读取文件出错",e.getMessage());
        }
        return stringBuilder.toString();
    }

    //将内容写入文件
    public static void writeToFile(String filePath, String content){
        File file = getFile(filePath);
        try {
            FileWriter fw = new FileWriter(file,false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            fw.close();
        } catch (Exception e) {
            Log.e("写文件出错",e.getMessage());
        }
    }

    //根据路径获取文件
    public static File getFile(String filePath) {
        File dir = new File(filePath);
        if (!dir.getParentFile().exists()) {
            dir.getParentFile().mkdirs();
        }
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                boolean flag = file.createNewFile();
                if (!flag) {
                    Log.e("创建文件失败", "createNewFile 失败");
                }
            } catch (Exception e) {
                Log.e("创建文件失败", e.getMessage());
            }
        }
        return file;
    }
}
