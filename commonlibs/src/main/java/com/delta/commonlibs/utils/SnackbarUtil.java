package com.delta.commonlibs.utils;

import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.delta.commonlibs.R;

public class SnackbarUtil {

    private static Snackbar snackbar;

    public static void show(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        View sv = snackbar.getView();
        TextView tv = (TextView) sv.findViewById(R.id.snackbar_text);
        tv.setGravity(Gravity.CENTER);  // 让文字居中
        snackbar.show();
    }

    public static void showShort(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        View sv = snackbar.getView();
        TextView tv = (TextView) sv.findViewById(R.id.snackbar_text);
        tv.setGravity(Gravity.CENTER);  // 让文字居中
        snackbar.show();
    }

    public static void showMassage(View view, String msg) {
        snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        View sv = snackbar.getView();
        TextView tv = (TextView) sv.findViewById(R.id.snackbar_text);
        tv.setGravity(Gravity.CENTER);  // 让文字居中
        snackbar.show();
    }

    public static void showMassage(View view, String msg, View.OnClickListener listener) {
        snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("确定", listener);
        View sv = snackbar.getView();
        TextView tv = (TextView) sv.findViewById(R.id.snackbar_text);
        tv.setGravity(Gravity.CENTER);  // 让文字居中
        snackbar.show();
    }

    public static void dissSnackbar() {
        if (snackbar != null) {
            if (snackbar.isShown())
                snackbar.dismiss();
        }
    }

}
