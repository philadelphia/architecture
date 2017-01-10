package com.delta.smt.utils;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.view.View;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/22 16:26
 */


public class ViewUtils {

    public static void setTabTitle(TabLayout tablayout, String[] titles) {

        for (int i = 0; i < tablayout.getTabCount(); i++) {
            tablayout.getTabAt(i).setText(titles[i]);
        }
    }

    public static <T extends View> T findView(View view, int resId) {

        return (T) view.findViewById(resId);
    }

    public static <T extends View> T findView(Activity activity, int resId) {

        return (T) activity.findViewById(resId);
    }
}
