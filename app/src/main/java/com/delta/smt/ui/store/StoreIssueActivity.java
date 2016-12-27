package com.delta.smt.ui.store;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.store.mvp.StorePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class StoreIssueActivity extends BaseActiviy<StorePresenter> {


    @BindView(R.id.header_title)
    TextView headerTitle;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private List<Fragment> mFragmentList;
    private  ViewPagerAdapter mViewpagerAdapter;

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initData() {
        mFragmentList=new ArrayList<>();
        mFragmentList.add(new WarringFragment());
        mFragmentList.add(new ArrangeFragment());
//        mViewpagerAdapter.notifyDataSetChanged();

    }

    @Override
    protected void initView() {
        headerTitle.setText(getResources().getString(R.string.storetitle));
        mViewpagerAdapter=new ViewPagerAdapter(mFragmentList);
        viewpager.setAdapter(mViewpagerAdapter);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_warning_list;
    }


}
