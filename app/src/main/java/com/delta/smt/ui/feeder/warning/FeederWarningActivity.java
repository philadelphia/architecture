package com.delta.smt.ui.feeder.warning;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.commonlibs.widget.autolayout.AutoTabLayout;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.utils.ViewUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public class FeederWarningActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.tl_title)
    AutoTabLayout tlTitle;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.activity_feeder_warning)
    LinearLayout activityFeederWarning;
    private String[] mTitles;
    private CheckInFragment checkinFragment;
    private SupplyFragment supplyFragment;
    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private FragmentTransaction fragmentTransaction;
    private static final String TAG = "FeederWarningActivity";

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
                if (supplyFragment == null) {
                    supplyFragment = new SupplyFragment();
                    fragmentTransaction.add(R.id.fl_container, supplyFragment, "备料");
                }

                fragmentTransaction.show(supplyFragment).hide(currentFragment).commit();
                currentFragment = supplyFragment;

                break;
            case 1:
                Log.i(TAG, "onTabSelected: 1");
                if (checkinFragment == null) {
                    checkinFragment = new CheckInFragment();
                    fragmentTransaction.add(R.id.fl_container, checkinFragment, "入库");
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
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("Feeder 暂存区预警");
        for (int i = 0; i < mTitles.length; i++) {
            tlTitle.addTab(tlTitle.newTab());
        }
        ViewUtils.setTabTitle(tlTitle, mTitles);
        tlTitle.addOnTabSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        supplyFragment = new SupplyFragment();
        fragmentTransaction.add(R.id.fl_container, supplyFragment, "备料");
        fragmentTransaction.show(supplyFragment).commit();
        currentFragment = supplyFragment;

    }


    @OnClick({ R.id.tv_setting, R.id.tl_title})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_setting:
                break;
            case R.id.tl_title:
                break;
        }
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
