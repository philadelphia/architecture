package com.delta.smt.ui.feederwarning;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.di.component.AppComponent;

import com.delta.smt.utils.ViewUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class FeederWarningActivity extends BaseActiviy implements TabLayout.OnTabSelectedListener {
    @BindView(R.id.header_back)
    TextView headerBack;
    @BindView(R.id.header_title)
    TextView headerTitle;
    @BindView(R.id.header_setting)
    TextView headerSetting;
    @BindView(R.id.tl_title)
    TabLayout tlTitle;
    @BindView(R.id.activity_feeder_warning)
    LinearLayout activityFeederWarning;
    private String[] mTitles;
    private CheckinFragment checkinFragment;
    private FeederSupplyFragment feederSupplyFragment;
    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private FragmentTransaction fragmentTransaction;
    private  static final String TAG = "FeederWarningActivity";

    @Override
    protected int getContentViewId() {
        return R.layout.activity_feeder_warning;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (tab.getPosition()) {
            case 0:
                Log.i(TAG, "onTabSelected: 0");
                if (feederSupplyFragment == null) {
                    feederSupplyFragment = new FeederSupplyFragment();
                    fragmentTransaction.add(R.id.fl_container, feederSupplyFragment, "备料");
                }

                fragmentTransaction.show(feederSupplyFragment).hide(currentFragment).commit();
                currentFragment = feederSupplyFragment;

                break;
            case 1:
                Log.i(TAG, "onTabSelected: 1");
                if (checkinFragment == null) {
                    checkinFragment = new CheckinFragment();
                    fragmentTransaction.add(R.id.fl_container, checkinFragment,"入库");
                }
                fragmentTransaction.show(checkinFragment).hide(currentFragment).commit();
                currentFragment = checkinFragment;
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
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initData() {
        mTitles = new String[]{"备料", "入库"};
    }

    @Override
    protected void initView() {
        for (int i = 0; i < mTitles.length; i++) {
            tlTitle.addTab(tlTitle.newTab());
        }
        ViewUtils.setTabTitle(tlTitle, mTitles);
        tlTitle.addOnTabSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction() ;
        feederSupplyFragment = new FeederSupplyFragment();
        fragmentTransaction.add(R.id.fl_container,feederSupplyFragment,"备料");
        fragmentTransaction.show(feederSupplyFragment).commit();
        currentFragment = feederSupplyFragment;

    }



    @OnClick({R.id.header_back, R.id.header_title, R.id.header_setting, R.id.tl_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                onBackPressed();
                break;
            case R.id.header_title:
                break;
            case R.id.header_setting:
                break;
            case R.id.tl_title:
                break;
        }
    }

}
