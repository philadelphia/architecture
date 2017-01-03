package com.delta.smt.ui.store;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.store.mvp.StorePresenter;
import com.delta.smt.utils.ViewUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class StoreIssueActivity extends BaseActiviy<StorePresenter> implements TabLayout.OnTabSelectedListener, WarningManger.OnWarning {


    @BindView(R.id.header_title)
    TextView headerTitle;
    @BindView(R.id.main_title)
    TabLayout tlTitle;



    private String[] mTitles;
    private FragmentTransaction fragmentTransaction;
    private WarringFragment mWarringFragment;
    private ArrangeFragment mArrangeFragment;
    private Fragment currentFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initData() {
        mTitles = new String[]{"预警", "排程"};
        WarningManger.getInstance().addWarning(Constant.SAMPLEWARING, getClass());
        WarningManger.getInstance().setRecieve(true);
        WarningManger.getInstance().setOnWarning(this);
    }

    @Override
    protected void initView() {
        for (int i = 0; i < mTitles.length; i++) {
            tlTitle.addTab(tlTitle.newTab());
        }
        ViewUtils.setTabTitle(tlTitle, mTitles);
        tlTitle.addOnTabSelectedListener(this);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        mWarringFragment = new WarringFragment();
        fragmentTransaction.add(R.id.fragment, mWarringFragment, "预警");
        fragmentTransaction.show(mWarringFragment).commit();
        currentFragment = mWarringFragment;
        headerTitle.setText(this.getResources().getString(R.string.storetitle));


    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_warning_list;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (tab.getPosition()) {
            case 0:
                Log.i(TAG, "onTabSelected: 0");
                if (mWarringFragment == null) {
                    mWarringFragment = new WarringFragment();
                    fragmentTransaction.add(R.id.fragment, mWarringFragment, "预警");
                }

                fragmentTransaction.show(mWarringFragment).hide(currentFragment).commit();
                currentFragment = mWarringFragment;
                break;
            case 1:
                Log.i(TAG, "onTabSelected: 1");
                if (mArrangeFragment == null) {
                    mArrangeFragment = new ArrangeFragment();
                    fragmentTransaction.add(R.id.fragment, mArrangeFragment, "排程");
                }
                fragmentTransaction.show(mArrangeFragment).hide(currentFragment).commit();
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
    public void warningComming() {
        DialogRelativelayout dialogRelativelayout = new DialogRelativelayout(this);
        //2.传入的是红色字体的标题
        dialogRelativelayout.setStrTitle("测试标题");
        //3.传入的是黑色字体的二级标题
        dialogRelativelayout.setStrSecondTitle("预警异常");
        //4.传入的是一个ArrayList<String>
        ArrayList<String> datas = new ArrayList<>();
        datas.add("dsfdsf");
        datas.add("sdfsdf1");
        datas.add("dsfsdf2");
        dialogRelativelayout.setStrContent(datas);
        //5.构建Dialog，setView的时候把这个View set进去。
        new AlertDialog.Builder(this).setView(dialogRelativelayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }
    @Override
    protected void onResume() {
        WarningManger.getInstance().registWReceiver(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        WarningManger.getInstance().unregistWReceriver(this);
        super.onStop();
    }
}
