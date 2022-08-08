package com.huiyao.gamecenter.module.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by 0048104325 on 2017/7/18.
 */

public class DownloadUtils {
    private String fileDir,fileName;
    private static DownloadUtils instance;

    public static DownloadUtils getInstance(String fileDir, String fileName) {
        if(instance == null){
            instance = new DownloadUtils(fileDir,fileName);
        }
        return instance;
    }

    public DownloadUtils(String fileDir, String fileName) {
        this.fileDir = fileDir;
        this.fileName = fileName;
    }

    public File saveFile(ResponseBody response) throws IOException{
        InputStream in = null;
        FileOutputStream out = null;
        byte[] buf = new byte[2048*10];
        int len;
        File file = null;
        try {
            File dir = new File(fileDir);
            if (!dir.exists()) {// 如果文件不存在新建一个
                dir.mkdirs();
            }
            in = response.byteStream();
            file = new File(dir,fileName);
            out = new FileOutputStream(file);
            while ((len = in.read(buf)) != -1){
                out.write(buf,0,len);
            }
            in.close();
            out.close();
            return file;
        }finally {
            in.close();
            out.close();
        }
    }
}
