package com.delta.smt.ui.store;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.Constant;
import com.delta.smt.MainActivity;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ArrangeInt;
import com.delta.smt.entity.StoreEmptyMessage;
import com.delta.smt.entity.WarningInt;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.setting.SettingActivity;
import com.delta.smt.ui.store.di.DaggerStoreComponent;
import com.delta.smt.ui.store.di.StoreModule;
import com.delta.smt.ui.store.mvp.StoreContract;
import com.delta.smt.ui.store.mvp.StorePresenter;
import com.delta.smt.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class StoreIssueActivity extends BaseActivity<StorePresenter> implements TabLayout.OnTabSelectedListener, WarningManger.OnWarning, StoreContract.View {

    @Inject
    WarningManger warningManger;
    @BindView(R.id.main_title)
    TabLayout tlTitle;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.fragment)
    FrameLayout fragment;
    private String[] mTitles;
    private WarringFragment mWarringFragment;
    private ArrangeFragment mArrangeFragment;
    private SupportFragment currentFragment;
    private FragmentManager fragmentManager;
    private int arrayint;
    private int warnInt;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerStoreComponent.builder().appComponent(appComponent).storeModule(new StoreModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        if (arrayint == 0 && warnInt == 0) {
            mTitles = new String[]{"预警", "排程"};
        } else {
            mTitles = new String[]{"预警" + arrayint, "排程" + warnInt};
        }
        warningManger.addWarning(Constant.SAMPLEWARING, getClass());
        warningManger.setRecieve(true);
        warningManger.setOnWarning(this);
    }

    @Override
    protected void initView() {
        toolbarTitle.setText("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText(this.getResources().getString(R.string.storetitle));
        for (int i = 0; i < mTitles.length; i++) {
            tlTitle.addTab(tlTitle.newTab());
        }
        ViewUtils.setTabTitle(tlTitle, mTitles);
        tlTitle.addOnTabSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        mWarringFragment = new WarringFragment();
        mArrangeFragment = new ArrangeFragment();
        loadMultipleRootFragment(R.id.fragment, 0, mWarringFragment, mArrangeFragment);
        currentFragment = mWarringFragment;



    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_warning_list;
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
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                Log.i(TAG, "onTabSelected: 0");
                showHideFragment(mWarringFragment, currentFragment);
                currentFragment = mWarringFragment;
                break;
            case 1:
                Log.i(TAG, "onTabSelected: 1");
                showHideFragment(mArrangeFragment, currentFragment);
                currentFragment = mArrangeFragment;
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
    public void warningComing(String message) {
        DialogRelativelayout dialogRelativelayout = new DialogRelativelayout(this);
        //2.传入的是红色字体的标题
        dialogRelativelayout.setStrTitle("预警信息");
        //3.传入的是黑色字体的二级标题
        //dialogRelativelayout.setStrSecondTitle("新发料预警");
        //4.传入的是一个ArrayList<String>
        ArrayList<String> datas = new ArrayList<>();
        datas.add("新发料预警");
        datas.add("H13-00:10:00");
        datas.add("H14-00:10:00");
        datas.add("AI镭雕-00：30：00");
        dialogRelativelayout.setStrContent(datas);
        //5.构建Dialog，setView的时候把这个View set进去。
        new AlertDialog.Builder(this).setView(dialogRelativelayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EventBus.getDefault().post(new StoreEmptyMessage());
            }
        }).show();
    }

    @Override
    public boolean UseEventBus() {
        return true;
    }

    @Override
    protected void onResume() {
        warningManger.registerWReceiver(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        warningManger.unregisterWReceriver(this);
        super.onStop();
    }

    @Subscribe
    public void event(ArrangeInt message) {
        this.arrayint = message.getAnInt();
        if (arrayint == 0 && warnInt == 0) {
            mTitles = null;
            mTitles = new String[]{"预警", "排程"};
        } else if (arrayint != 0 && warnInt == 0) {
            mTitles = null;
            mTitles = new String[]{"预警(" + arrayint + ")", "排程"};
        } else if (arrayint == 0 && warnInt != 0) {
            mTitles = new String[]{"预警", "排程(" + warnInt + ")"};
        } else if (arrayint != 0 && warnInt != 0) {
            mTitles = new String[]{"预警(" + arrayint + ")", "排程(" + warnInt + ")"};
        }
        for (int i = 0; i < mTitles.length; i++) {
            tlTitle.addTab(tlTitle.newTab());
        }
        ViewUtils.setTabTitle(tlTitle, mTitles);
        tlTitle.addOnTabSelectedListener(this);
    }

    @Subscribe
    public void event(WarningInt message) {
        this.warnInt = message.getWarnInt();
    }



}
