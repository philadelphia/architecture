package com.delta.libs.adapter;

import android.view.View;

/**
 * Created by Lin.Hou on 2016-12-28.
 */

public interface ItemOnclick <T>{
    void onItemClick(View item, T t, int position);
}
