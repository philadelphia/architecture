package com.delta.smt.ui.production_warning.mvp.produce_warning;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.delta.smt.Constant;
import com.delta.smt.MainActivity;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.production_warning.di.produce_warning.DaggerTitleNumberCompent;
import com.delta.smt.ui.production_warning.di.produce_warning.TitleNumberModule;
import com.delta.smt.ui.production_warning.item.TitleNumber;
import com.delta.smt.ui.production_warning.mvp.produce_breakdown_fragment.ProduceBreakdownFragment;
import com.delta.smt.ui.production_warning.mvp.produce_info_fragment.ProduceInfoFragment;
import com.delta.smt.ui.production_warning.mvp.produce_warning_fragment.ProduceWarningFragment;
import com.delta.smt.utils.ViewUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public class ProduceWarningActivity extends BaseActiviy<ProduceWarningPresenter> implements
        TabLayout.OnTabSelectedListener,ProduceWarningContract.View, WarningManger.OnWarning {

    @BindView(R.id.tl_title)
    TabLayout tlTitle;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.header_back)
    TextView mHeaderBack;
    @BindView(R.id.header_title)
    TextView mHeaderTitle;
    @BindView(R.id.header_setting)
    TextView mHeaderSetting;
    private ProduceWarningFragment mProduceWarningFragment;
    private ProduceBreakdownFragment mProduceBreakdownFragment;
    private ProduceInfoFragment mProduceInfoFragment;
    private FragmentTransaction mFragmentTransaction;
    private SupportFragment currentFragment;
    private String[] titles;
    private boolean tag=false;


    @Inject
    WarningManger warningManger;
    private AlertDialog alertDialog;


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerTitleNumberCompent.builder().appComponent(appComponent).titleNumberModule(new TitleNumberModule(this)).build().inject(this);
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
            mHeaderTitle.setText("生产中预警");
            for (int i = 0; i < titles.length; i++) {
                tlTitle.addTab(tlTitle.newTab());
            }
            ViewUtils.setTabTitle(tlTitle, titles);
            tlTitle.addOnTabSelectedListener(this);
            mProduceBreakdownFragment=new ProduceBreakdownFragment();
            mProduceInfoFragment=new ProduceInfoFragment();
            mProduceWarningFragment = new ProduceWarningFragment();
            loadMultipleRootFragment(R.id.fl_container,0,mProduceWarningFragment,mProduceBreakdownFragment,mProduceInfoFragment);
            currentFragment = mProduceWarningFragment;

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_produce_warning;
    }

    @Override
    protected void onResume() {
        warningManger.registWReceiver(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        warningManger.unregistWReceriver(this);
        super.onStop();
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (tab.getPosition()) {
            case 0:
                showHideFragment(mProduceWarningFragment,currentFragment);
                currentFragment=mProduceWarningFragment;
                break;
            case 1:
                showHideFragment(mProduceBreakdownFragment,currentFragment);
                currentFragment=mProduceBreakdownFragment;
                break;
            case 2:
                showHideFragment(mProduceInfoFragment,currentFragment);
                currentFragment=mProduceInfoFragment;
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
    public void getTitleDatas(TitleNumber titleNumber) {

        titles = new String[]{"预警(" + titleNumber.getWarning_number() + ")",
                "故障(" + titleNumber.getBreakdown_number() + ")",
                "消息(" + titleNumber.getInfo_number() + ")"};

    }

    @Override
    public void getTitleDatasFailed() {
        titles = new String[]{"预警", "故障", "消息"};
    }


    @Override
    public void warningComming(String warningMessage) {
        if (alertDialog!=null&&alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = createDialog(warningMessage);
        } else {
            alertDialog = createDialog(warningMessage);
        }
    }

    private AlertDialog createDialog(String warningMessage) {
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
                dialog.dismiss();
            }
        }).show();
    }
}
