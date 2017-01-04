package com.delta.smt.ui.storage_manger;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.delta.smt.MainActivity;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.storage_manger.ready.StorageReadyFragment;
import com.delta.smt.ui.storage_manger.ready.StorageReturnFragment;
import com.delta.smt.ui.storage_manger.ready.mvp.StorageReadyPresenter;
import com.delta.smt.utils.ViewUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Zhenyu.Liu on 2016/12/21.
 */

public class StorageWarningActivity extends BaseActiviy<StorageReadyPresenter> implements TabLayout.OnTabSelectedListener , WarningManger.OnWarning {


    @BindView(R.id.tl_title)
    TabLayout mTlTitle;
    @BindView(R.id.header_title)
    TextView mHeaderTitle;
    @BindView(R.id.header_back)
    TextView mHeaderBack;
    @BindView(R.id.header_setting)
    TextView mHeaderSetting;
    private FragmentTransaction mFragmentTransaction;
    private StorageReadyFragment mStorageReadyFragment;
    private StorageReturnFragment mStorageReturnFragment;

    private Fragment currentFragment;
    private String[] titles;

    @Override
    protected void initView() {
        mHeaderTitle.setText("仓库A备料");
        for (int i = 0; i < titles.length; i++) {
            mTlTitle.addTab(mTlTitle.newTab());
        }
        ViewUtils.setTabTitle(mTlTitle, titles);
        mTlTitle.addOnTabSelectedListener(this);
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mStorageReadyFragment = new StorageReadyFragment();
        mFragmentTransaction.add(R.id.fl_container, mStorageReadyFragment, "备料");
        mFragmentTransaction.show(mStorageReadyFragment).commit();
       // setDispathchKeyEvent(mStorageReadyFragment);
        currentFragment = mStorageReadyFragment;

    }

    @Override
    protected void initData() {

        //此处的Title应该是 从网络获取的数量
        titles = new String[]{"备料(3)"};
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
        switch (tab.getPosition()) {
            case 0:
                if (mStorageReadyFragment == null) {
                    mStorageReadyFragment = new StorageReadyFragment();
                    mFragmentTransaction.add(R.id.fl_container, mStorageReadyFragment, "备料");
                }

                mFragmentTransaction.show(mStorageReadyFragment).hide(currentFragment).commit();
                currentFragment = mStorageReadyFragment;

                break;
//            case 1:
//                if (mStorageReturnFragment == null) {
//                    mStorageReturnFragment = new StorageReturnFragment();
//                    mFragmentTransaction.add(R.id.fl_container, mStorageReturnFragment, "入库");
//                }
//                mFragmentTransaction.show(mStorageReturnFragment).hide(currentFragment).commit();
//                currentFragment = mStorageReturnFragment;
//                break;
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
    public void warningComming(String warningMessage) {
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
}