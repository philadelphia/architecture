package com.delta.smt.ui.product_tools;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Shaoqiang.Zhang on 2017/2/20.
 */

public class SharedPreferencesUtils {
    public static void putData(Context context,String key,String vaule) {
        SharedPreferences pref = context.getSharedPreferences("pageData", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key,vaule);
        editor.commit();
    }

    public static String getData(Context context,String key){
        SharedPreferences pref = context.getSharedPreferences("pageData",MODE_PRIVATE);
        return  pref.getString(key,"");
    }
}
