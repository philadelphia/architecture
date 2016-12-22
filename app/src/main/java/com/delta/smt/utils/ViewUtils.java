package com.delta.smt.utils;

import android.support.design.widget.TabLayout;

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
}
