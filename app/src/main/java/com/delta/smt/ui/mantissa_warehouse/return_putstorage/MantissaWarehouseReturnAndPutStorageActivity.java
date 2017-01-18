package com.delta.smt.ui.mantissa_warehouse.return_putstorage;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.BacKBarCode;
import com.delta.smt.entity.PutBarCode;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.MantissaWarehousePutstorageFragment;
import com.delta.smt.ui.mantissa_warehouse.return_putstorage.returnto.MantissaWarehouseReturnFragment;
import com.delta.smt.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

public class MantissaWarehouseReturnAndPutStorageActivity extends BaseActivity
        implements TabLayout.OnTabSelectedListener, WarningManger.OnWarning {

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
    private MantissaWarehouseReturnFragment mMantissaWarehouseReturnFragment;
    private MantissaWarehousePutstorageFragment mMantissaWarehousePutstorageFragment;

    private SupportFragment currentFragment;
    private String[] titles;

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initData() {
//此处的Title应该是 从网络获取的数量
        titles = new String[]{"入库(3)", "退入主仓库(3)"};

        //接收那种预警，没有的话自己定义常量
        WarningManger.getInstance().addWarning(Constant.STORAGEREAD, getClass());
        //是否接收预警 可以控制预警时机
        WarningManger.getInstance().setRecieve(true);
        //关键 初始化预警接口
        WarningManger.getInstance().setOnWarning(this);
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText("尾数仓入库及退料");

        for (int i = 0; i < titles.length; i++) {
            mTlTitle.addTab(mTlTitle.newTab());
        }
        ViewUtils.setTabTitle(mTlTitle, titles);
        mTlTitle.addOnTabSelectedListener(this);
        mMantissaWarehousePutstorageFragment = new MantissaWarehousePutstorageFragment();
        mMantissaWarehouseReturnFragment = new MantissaWarehouseReturnFragment();
        loadMultipleRootFragment(R.id.fl_container, 0, mMantissaWarehouseReturnFragment, mMantissaWarehousePutstorageFragment);
        currentFragment = mMantissaWarehouseReturnFragment;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (tab.getPosition()) {
            case 0:
                showHideFragment(mMantissaWarehouseReturnFragment, currentFragment);
                currentFragment = mMantissaWarehouseReturnFragment;
                break;
            case 1:
                showHideFragment(mMantissaWarehousePutstorageFragment, currentFragment);
                currentFragment = mMantissaWarehousePutstorageFragment;
                break;
            default:
                break;
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.tablayout;
    }


    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void onResume() {
        WarningManger.getInstance().registerWReceiver(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        WarningManger.getInstance().unregisterWReceriver(this);
        super.onStop();
    }

    @Override
    public void warningComing(String warningMessage) {

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
    public boolean UseEventBus() {
        return true;
    }

    @Override
    public void onScanSuccess(String barcode) {
        if(currentFragment==mMantissaWarehouseReturnFragment){

            EventBus.getDefault().post(new BacKBarCode(barcode));
        }else {

            EventBus.getDefault().post(new PutBarCode(barcode));
        }
        //super.onScanSuccess(barcode);
    }
}
