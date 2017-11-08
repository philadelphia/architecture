package com.example.commonlibs.utils;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.example.commonlibs.widget.statusLayout.StatusLayout;

import java.util.List;

/**
 * @description :
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

    public static void showContentView(StatusLayout statusLayout, List list) {
        if (list.size() == 0) {
            statusLayout.showEmptyView();
        } else {
            statusLayout.showContentView();
        }
    }

    public static View getRootViewWithActivity(Activity mActivity) {
        return mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
    }


}
