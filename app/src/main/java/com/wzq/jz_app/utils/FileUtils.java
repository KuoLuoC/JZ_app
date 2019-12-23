package com.wzq.jz_app.utils;


import java.io.File;
import java.io.IOException;

/**
 * 项目名称:  家庭记账
 */
public class FileUtils {


    private static boolean createOrExistsDir(File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判断文件是否存在，存在则在创建之前删除
     */
    public static boolean createFileByDeleteOldFile(File file) {
        if (file == null) return false;
        if (file.exists() && file.isFile() && !file.delete()) return false;
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}