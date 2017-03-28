package com.delta.smt.ui.storage_manger;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.delta.commonlibs.utils.SpUtil;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseCommonActivity;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.storage_manger.ready.StorageReadyFragment;
import com.delta.smt.utils.ViewUtils;
import com.delta.smt.widget.WarningDialog;

import butterknife.BindView;

/**
 * Created by Zhenyu.Liu on 2016/12/21.
 */

public class StorageWarningActivity extends BaseCommonActivity implements TabLayout.OnTabSelectedListener, WarningManger.OnWarning {


    @BindView(R.id.tl_title)
    TabLayout mTlTitle;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar mToolbar;
    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;

    private FragmentTransaction mFragmentTransaction;
    private StorageReadyFragment mStorageReadyFragment;

    private Fragment currentFragment;
    private String[] titles;
    private WarningDialog warningDialog;
    private String storage_name;
    private String mS;

    @Override
    protected int getContentViewId() {
        return R.layout.tablayout;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (tab.getPosition()) {
            case 0:
                if (mStorageReadyFragment == null) {
                    mStorageReadyFragment = new StorageReadyFragment();
                    mFragmentTransaction.add(R.id.fl_container, mStorageReadyFragment, "备料");
                }
                mFragmentTransaction.show(mStorageReadyFragment).hide(currentFragment).commit();
                currentFragment = mStorageReadyFragment;
                break;
            default:
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
    protected void initCView() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
       // Bundle extras = getIntent().getExtras();
        storage_name = SpUtil.getStringSF(this,Constant.STORAGE_NAME);
        mToolbarTitle.setText("仓库" + storage_name);
        for (int i = 0; i < titles.length; i++) {
            mTlTitle.addTab(mTlTitle.newTab());
        }
        ViewUtils.setTabTitle(mTlTitle, titles);
        mTlTitle.addOnTabSelectedListener(this);
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mStorageReadyFragment = new StorageReadyFragment();
      //  mStorageReadyFragment.setArguments(extras);
        mFragmentTransaction.add(R.id.fl_container, mStorageReadyFragment, "备料");
        mFragmentTransaction.show(mStorageReadyFragment).commit();
        // setDispathchKeyEvent(mStorageReadyFragment);
        currentFragment = mStorageReadyFragment;

    }

    @Override
    protected void initCData() {
        //此处的Title应该是 从网络获取的数量
        titles = new String[]{"备料"};
        //接收那种预警，没有的话自己定义常量

    }

    @Override
    protected void onResume() {
        WarningManger.getInstance().registerWReceiver(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        WarningManger.getInstance().unregisterWReceiver(this);
        super.onStop();
    }

    @Override
    public void warningComing(String message) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}