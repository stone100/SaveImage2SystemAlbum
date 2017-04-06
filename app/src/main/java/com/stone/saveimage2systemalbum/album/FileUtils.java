package com.stone.saveimage2systemalbum.album;

import android.os.Environment;

import com.stone.saveimage2systemalbum.ApplicationProvider;

import java.io.File;
import java.io.IOException;

/**
 * @author stone
 * @date 17/3/29
 */

public class FileUtils {

    private FileUtils() {
        //no instance
    }

    public static File createFileFrom(String url) {
        String fileName = MD5Utils.getMD5StringFrom(url) + getFileSuffixFrom(url);
        File file = new File(getAppAlbumDir(), fileName);

        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static String getFileSuffixFrom(String url) {
        if(url.contains(".")) {
            String fileSuffix = url.substring(url.lastIndexOf("."), url.length());
            if(fileSuffix != null && fileSuffix.length() < 7) {
                return fileSuffix;
            } else {
                return ".jpg";
            }
        }
        return ".jpg";
    }

    public static File getAppAlbumDir() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return ApplicationProvider.IMPL.getApp().getExternalFilesDir("Album");
        } else {
            return new File(ApplicationProvider.IMPL.getApp().getFilesDir(), "Album/");
        }
    }
}
