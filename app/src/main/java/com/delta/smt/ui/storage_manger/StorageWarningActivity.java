package com.delta.smt.ui.storage_manger;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.login.mvp.LoginPresenter;

import butterknife.BindView;

/**
 * Created by Zhenyu.Liu on 2016/12/21.
 */

public class StorageWarningActivity extends BaseActiviy<LoginPresenter> implements TabLayout.OnTabSelectedListener {


    @BindView(R.id.tl_title)
    TabLayout mTlTitle;
    private FragmentTransaction mFragmentTransaction;
    private StorageReadyFragment mStorageReadyFragment;
    private StorageReturnFragment mStorageReturnFragment;


    @Override
    protected void initView() {

        mTlTitle.addTab(mTlTitle.newTab().setText("备料"));
        mTlTitle.addTab(mTlTitle.newTab().setText("退料"));
        mTlTitle.addOnTabSelectedListener(this);
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mStorageReadyFragment = new StorageReadyFragment();
        mStorageReturnFragment = new StorageReturnFragment();
        mFragmentTransaction.add(R.id.fl_container,mStorageReadyFragment,"备料");
        mFragmentTransaction.add(R.id.fl_container,mStorageReturnFragment, "退料");
        mFragmentTransaction.show(mStorageReadyFragment);
        mFragmentTransaction.hide(mStorageReturnFragment).commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.tablayout;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        tab.getText();

        if(tab.getText().equals("备料")){
            mFragmentTransaction.show(mStorageReadyFragment);
            mFragmentTransaction.hide(mStorageReturnFragment);

        }else if (tab.getText().equals("退料")){

            mFragmentTransaction.show(mStorageReturnFragment);
            mFragmentTransaction.hide(mStorageReadyFragment);

        }
        mFragmentTransaction.commit();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}