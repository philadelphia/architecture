package com.delta.commonlibs.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackbarUtil {

    private static Snackbar snackbar;

    public static void show(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    public static void showShort(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }
    public static void showMassage(View view, String msg) {
        snackbar=Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    public static void dissSnackbar(){
        if (snackbar!=null){
            if (snackbar.isShown())
                snackbar.dismiss();
        }
    }

}
