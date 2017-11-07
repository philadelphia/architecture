package com.delta.app.utils;

import android.content.Context;

import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author:   tao.zt.zhang
 * Date:     2017/5/15.
 */

public class FileUtil {

    //写数据
    public static void writeFile(Context context,String fileName, String writestr) throws IOException {
        try{

            FileOutputStream fout =context.openFileOutput(fileName, Context.MODE_PRIVATE);

            byte [] bytes = writestr.getBytes();

            fout.write(bytes);

            fout.close();
        }

        catch(Exception e){
            e.printStackTrace();
        }
    }

    //读数据
    public static String readFile(Context context, String fileName) throws IOException{
        String res="";
        try{
            FileInputStream fin = context.openFileInput(fileName);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }
}
