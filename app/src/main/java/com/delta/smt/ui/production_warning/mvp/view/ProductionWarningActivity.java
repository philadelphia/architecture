package com.delta.smt.ui.production_warning.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.delta.smt.MainActivity;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.production_warning.mvp.presenter.ProductionWarningPresenter;
import com.delta.smt.ui.storage_manger.StorageReadyFragment;
import com.delta.smt.ui.storage_manger.StorageReturnFragment;
import com.delta.smt.utils.ViewUtils;
import com.squareup.haha.perflib.Main;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProductionWarningActivity extends BaseActiviy<ProductionWarningPresenter> implements TabLayout.OnTabSelectedListener{

    @BindView(R.id.tl_title)
    TabLayout tlTitle;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    private ProductionWarningFragment mProductionWarningFragment;
    private ProductionBreakdownFragment mProductionBreakdownFragment;
    private ProductionInfoFragment mProductionInfoFragment;

    private FragmentTransaction mFragmentTransaction;
    private Fragment currentFragment;
    private String[] titles;

    private int warning_number=3;
    private int breakdown_number=1;
    private int info_number=2;

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initData() {
        titles = new String[]{"预警("+warning_number+")", "故障("+breakdown_number+")","消息("+info_number+")"};

    }

    @Override
    protected void initView() {
        for (int i = 0; i < titles.length; i++) {
            tlTitle.addTab(tlTitle.newTab());
        }
        ViewUtils.setTabTitle(tlTitle, titles);
        tlTitle.addOnTabSelectedListener(this);
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mProductionWarningFragment=new ProductionWarningFragment();
        mFragmentTransaction.add(R.id.fl_container,mProductionWarningFragment);
        mFragmentTransaction.show(mProductionWarningFragment).commit();
        currentFragment = mProductionWarningFragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_production_warning;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_setting, R.id.tv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_setting:
                break;
            case R.id.tv_back:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (tab.getPosition()) {
            case 0:
                if (mProductionWarningFragment== null) {
                    mProductionWarningFragment = new ProductionWarningFragment();
                    mFragmentTransaction.add(R.id.fl_container, mProductionWarningFragment);
                }

                mFragmentTransaction.show(mProductionWarningFragment).hide(currentFragment).commit();
                currentFragment = mProductionWarningFragment;

                break;
            case 1:
                if (mProductionBreakdownFragment == null) {
                    mProductionBreakdownFragment = new ProductionBreakdownFragment();
                    mFragmentTransaction.add(R.id.fl_container, mProductionBreakdownFragment);
                }
                mFragmentTransaction.show(mProductionBreakdownFragment).hide(currentFragment).commit();
                currentFragment = mProductionBreakdownFragment;
                break;
            case 2:
                if (mProductionInfoFragment == null) {
                    mProductionInfoFragment = new ProductionInfoFragment();
                    mFragmentTransaction.add(R.id.fl_container, mProductionInfoFragment);
                }
                mFragmentTransaction.show(mProductionInfoFragment).hide(currentFragment).commit();
                currentFragment = mProductionInfoFragment;
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
