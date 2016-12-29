package com.delta.smt.ui.mantissa_warehouse.return_putstorage;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.delta.smt.MainActivity;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.MantissaWarehousePutstorageFragment;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.MantissaWarehouseReturnFragment;
import com.delta.smt.ui.storage_manger.ready.mvp.StorageReadyPresenter;
import com.delta.smt.utils.ViewUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

public class MantissaWarehouseReturnAndPutStorage extends BaseActiviy<StorageReadyPresenter> implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.tl_title)
    TabLayout mTlTitle;
    @BindView(R.id.header_title)
    TextView mHeaderTitle;
    @BindView(R.id.header_back)
    TextView mHeaderBack;
    @BindView(R.id.header_setting)
    TextView mHeaderSetting;
    private FragmentTransaction mFragmentTransaction;
    private MantissaWarehouseReturnFragment mMantissaWarehouseReturnFragment ;
    private MantissaWarehousePutstorageFragment mMantissaWarehousePutstorageFragment ;

    private Fragment currentFragment;
    private String[] titles;


    @Override
    protected void initView() {
        mHeaderTitle.setText("尾数仓入库及退料");
        for (int i = 0; i < titles.length; i++) {
            mTlTitle.addTab(mTlTitle.newTab());
        }
        ViewUtils.setTabTitle(mTlTitle, titles);
        mTlTitle.addOnTabSelectedListener(this);
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mMantissaWarehouseReturnFragment = new MantissaWarehouseReturnFragment();
        mFragmentTransaction.add(R.id.fl_container, mMantissaWarehouseReturnFragment, "入库");
        mFragmentTransaction.show(mMantissaWarehouseReturnFragment).commit();
        setDispathchKeyEvent(mMantissaWarehouseReturnFragment);
        currentFragment = mMantissaWarehouseReturnFragment;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (tab.getPosition()) {
            case 0:
                if (mMantissaWarehouseReturnFragment == null) {
                    mMantissaWarehouseReturnFragment = new MantissaWarehouseReturnFragment();
                    mFragmentTransaction.add(R.id.fl_container, mMantissaWarehouseReturnFragment, "入库");
                }
                setDispathchKeyEvent(mMantissaWarehouseReturnFragment);
                mFragmentTransaction.show(mMantissaWarehouseReturnFragment).hide(currentFragment).commit();
                currentFragment = mMantissaWarehouseReturnFragment;

                break;
            case 1:
                if (mMantissaWarehousePutstorageFragment == null) {
                    mMantissaWarehousePutstorageFragment = new MantissaWarehousePutstorageFragment();
                    mFragmentTransaction.add(R.id.fl_container, mMantissaWarehousePutstorageFragment, "退入主仓库");
                }
                setDispathchKeyEvent(mMantissaWarehousePutstorageFragment);
                mFragmentTransaction.show(mMantissaWarehousePutstorageFragment).hide(currentFragment).commit();
                currentFragment = mMantissaWarehousePutstorageFragment;
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {

        //此处的Title应该是 从网络获取的数量
        titles = new String[]{"入库(3)","退入主仓库(3)"};

    }


    @Override
    protected int getContentViewId() {
        return R.layout.tablayout;
    }


    @OnClick({R.id.header_back, R.id.header_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.header_setting:
                break;
        }
    }



    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }


}
