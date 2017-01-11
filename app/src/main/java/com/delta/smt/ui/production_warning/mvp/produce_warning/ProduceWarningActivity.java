package com.delta.smt.ui.production_warning.mvp.produce_warning;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.delta.commonlibs.widget.autolayout.AutoTabLayout;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.Constant;
import com.delta.smt.MainActivity;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.BroadcastBegin;
import com.delta.smt.entity.BroadcastCancel;
import com.delta.smt.entity.ProduceWarningMessage;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.production_warning.di.produce_warning.DaggerTitleNumberCompent;
import com.delta.smt.ui.production_warning.di.produce_warning.TitleNumberModule;
import com.delta.smt.ui.production_warning.item.TitleNumber;
import com.delta.smt.ui.production_warning.mvp.produce_breakdown_fragment.ProduceBreakdownFragment;
import com.delta.smt.ui.production_warning.mvp.produce_info_fragment.ProduceInfoFragment;
import com.delta.smt.ui.production_warning.mvp.produce_warning_fragment.ProduceWarningFragment;
import com.delta.smt.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

import static com.delta.smt.R.id.toolbar;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProduceWarningActivity extends BaseActivity<ProduceWarningPresenter> implements
        TabLayout.OnTabSelectedListener, ProduceWarningContract.View, WarningManger.OnWarning {


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar mToolbar;
    @BindView(R.id.tl_title)
    AutoTabLayout mTlTitle;
    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    private ProduceWarningFragment mProduceWarningFragment;
    private ProduceBreakdownFragment mProduceBreakdownFragment;
    private ProduceInfoFragment mProduceInfoFragment;
    private FragmentTransaction mFragmentTransaction;
    private SupportFragment currentFragment;
    private String[] titles;


    @Inject
    WarningManger warningManger;
    private AlertDialog alertDialog;
    private boolean item_run_tag = false;
    private String lastWarningMessage;


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerTitleNumberCompent.builder().appComponent(appComponent).titleNumberModule(new TitleNumberModule(this)).build().inject(this);
    }

    @Override
    public boolean UseEventBus() {
        return true;
    }

    @Override
    protected void initData() {

        getPresenter().getTitileNumber();
        warningManger.addWarning(Constant.PRODUCE_WARNING, getClass());
        warningManger.setRecieve(true);
        warningManger.setOnWarning(this);
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText("故障处理预警");
        for (int i = 0; i < titles.length; i++) {
            mTlTitle.addTab(mTlTitle.newTab());
        }
        ViewUtils.setTabTitle(mTlTitle, titles);
        mTlTitle.addOnTabSelectedListener(this);
        mProduceBreakdownFragment = new ProduceBreakdownFragment();
        mProduceInfoFragment = new ProduceInfoFragment();
        mProduceWarningFragment = new ProduceWarningFragment();
        loadMultipleRootFragment(R.id.fl_container, 0, mProduceWarningFragment, mProduceBreakdownFragment, mProduceInfoFragment);
        currentFragment = mProduceWarningFragment;

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_produce_warning;
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


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (tab.getPosition()) {
            case 0:
                showHideFragment(mProduceWarningFragment, currentFragment);
                currentFragment = mProduceWarningFragment;
                break;
            case 1:
                showHideFragment(mProduceBreakdownFragment, currentFragment);
                currentFragment = mProduceBreakdownFragment;
                break;
            case 2:
                showHideFragment(mProduceInfoFragment, currentFragment);
                currentFragment = mProduceInfoFragment;
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
    public void getTitleDatas(TitleNumber titleNumber) {

        titles = new String[]{"预警(" + titleNumber.getWarning_number() + ")",
                "故障(" + titleNumber.getBreakdown_number() + ")",
                "消息(" + titleNumber.getInfo_number() + ")"};

    }

    @Override
    public void getTitleDatasFailed() {
        titles = new String[]{"预警", "故障", "消息"};
    }

    //就收到预警广播触发的方法
    @Override
    public void warningComing(String warningMessage) {
        if (item_run_tag) {
            lastWarningMessage = warningMessage;
        } else if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = createDialog(warningMessage);
        } else {
            alertDialog = createDialog(warningMessage);
        }
    }

    private AlertDialog createDialog(final String warningMessage) {
        DialogRelativelayout dialogRelativelayout = new DialogRelativelayout(this);
        //3.传入的是黑色字体的二级标题
        dialogRelativelayout.setStrSecondTitle("预警异常");
        //4.传入的是一个ArrayList<String>
        ArrayList<String> datas = new ArrayList<>();
        datas.add("dsfdsf");
        datas.add("sdfsdf1");
        datas.add("dsfsdf2");
        dialogRelativelayout.setStrContent(datas);
        return new AlertDialog.Builder(this).setCancelable(false).setView(dialogRelativelayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EventBus.getDefault().post(new ProduceWarningMessage(warningMessage));
                dialog.dismiss();
            }
        }).show();
    }

    //Fragment点击item触发事件处理
    @Subscribe
    public void event(BroadcastCancel broadcastCancel) {
        item_run_tag = true;
        Log.e(TAG, "event4: ");
    }

    //Fragment中item处理完触发事件处理
    @Subscribe
    public void event(BroadcastBegin broadcastbegin) {
        item_run_tag = false;
        if (lastWarningMessage != null) {
            alertDialog = createDialog(lastWarningMessage);
            lastWarningMessage = null;
        }
        Log.e(TAG, "event5: ");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
