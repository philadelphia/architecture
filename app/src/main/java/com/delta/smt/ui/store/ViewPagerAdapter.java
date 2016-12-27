package com.delta.smt.ui.store;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private List<Fragment> mList;
    public ViewPagerAdapter(List<Fragment> list){
        this.mList=list;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return false;
    }
    public Object instantiateItem(ViewGroup view, int position) {

        return mList.get(position);
    }
}
