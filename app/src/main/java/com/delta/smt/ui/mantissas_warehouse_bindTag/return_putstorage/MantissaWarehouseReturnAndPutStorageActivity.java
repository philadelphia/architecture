package com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.delta.commonlibs.utils.ViewUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.BacKBarCode;
import com.delta.smt.entity.PutBarCode;
import com.delta.smt.entity.SendMessage;
import com.delta.smt.entity.WaringDialogEntity;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.put_storage.MantissaWarehousePutstorageFragment;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.schedule.ScheduleFragment;
import com.delta.smt.widget.DialogLayout;
import com.delta.smt.widget.WarningDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

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
    private ScheduleFragment mScheduleFragment;
    private MantissaWarehousePutstorageFragment mMantissaWarehousePutstorageFragment;

    private SupportFragment currentFragment;
    private String[] titles;
    private int currentTab = 0;

    @Inject
    WarningManger warningManger;
    private WarningDialog warningDialog;
    private DialogLayout dialogLayout;

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initData() {
        //此处的Title应该是 从网络获取的数量
        titles = new String[]{"入库", "退入主仓库"};
        //接收那种预警，没有的话自己定义常量
        warningManger = WarningManger.getInstance();
        WarningManger.getInstance().addWarning(Constant.WARE_MAIN_WARE_ALARM_FLAG, getClass());
        WarningManger.getInstance().addWarning(Constant.WAREH_MANTISSA_ALARM_FLAG, getClass());
        warningManger.sendMessage(new SendMessage(Constant.WARE_MAIN_WARE_ALARM_FLAG,0));
        warningManger.sendMessage(new SendMessage(Constant.WAREH_MANTISSA_ALARM_FLAG,0));

        //是否接收预警 可以控制预警时机
        WarningManger.getInstance().setReceive(true);
        //关键 初始化预警接口
        WarningManger.getInstance().setOnWarning(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) { //表示按返回键 时的操作
                // 监听到返回按钮点击事件
                if (mScheduleFragment != null) {
                  //  mScheduleFragment.onBack();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        warningManger.sendMessage(new SendMessage(Constant.WARE_MAIN_WARE_ALARM_FLAG,1));
        warningManger.sendMessage(new SendMessage(Constant.WAREH_MANTISSA_ALARM_FLAG,1));
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentTab = savedInstanceState.getInt("temp");
        }
        Log.e(TAG, "onCreate: "+currentTab);
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void initView() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText("尾数仓入库及退料");
        mToolbarTitle.setTextSize(40);
        for (int i = 0; i < titles.length; i++) {
            mTlTitle.addTab(mTlTitle.newTab());
        }
        ViewUtils.setTabTitle(mTlTitle, titles);
        mTlTitle.addOnTabSelectedListener(this);
        mMantissaWarehousePutstorageFragment = new MantissaWarehousePutstorageFragment();
        mScheduleFragment = new ScheduleFragment();
        loadMultipleRootFragment(R.id.fl_container, 0, mScheduleFragment, mMantissaWarehousePutstorageFragment);
        currentFragment = mScheduleFragment;
        mTlTitle.getTabAt(currentTab).select();
    }

    public AutoToolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        currentTab =mTlTitle.getSelectedTabPosition();
        outState.putInt("temp", currentTab);
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: "+currentTab);
    }



    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (tab.getPosition()) {
            case 0:
               // currentTab = 0;
                showHideFragment(mScheduleFragment, currentFragment);
                currentFragment = mScheduleFragment;
                Log.e(TAG, "onTabSelected: ");
                break;
            case 1:
               // currentTab = 1;
                showHideFragment(mMantissaWarehousePutstorageFragment, currentFragment);
                currentFragment = mMantissaWarehousePutstorageFragment;
                Log.e(TAG, "onTabSelected: ");
                break;
            default:
                break;
        }
        mFragmentTransaction.commit();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.tablayout;
    }


    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        Log.e(TAG, "onTabUnselected: ");
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Log.e(TAG, "onTabReselected: ");
    }

    @Override
    protected void onResume() {
        mTlTitle.getTabAt(currentTab).select();
        WarningManger.getInstance().registerWReceiver(this);
        super.onResume();
        Log.e(TAG, "onResume: "+currentTab);
    }

    @Override
    protected void onStop() {
        WarningManger.getInstance().unregisterWReceiver(this);

        super.onStop();
        Log.e(TAG, "onStop: "+currentTab);
    }

    @Override
    public void warningComing(String message) {

        if (warningDialog == null) {
            warningDialog = createDialog(message);
        }
        if(!warningDialog.isShowing()){
            warningDialog.show();
        }
        updateMessage(message);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mScheduleFragment != null) {
                 //   mScheduleFragment.onBack();
                }
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
        if (currentFragment == mScheduleFragment) {

            EventBus.getDefault().post(new BacKBarCode(barcode));
        } else {

            EventBus.getDefault().post(new PutBarCode(barcode));
        }
        //super.onScanSuccess(barcode);
    }


    public WarningDialog createDialog(String message) {
        warningDialog = new WarningDialog(this);
        warningDialog.setOnClickListener(new WarningDialog.OnClickListener() {
            @Override
            public void onclick(View view) {
                warningManger.setConsume(true);
            }
        });
        warningDialog.show();

        return warningDialog;
    }

    /**
     * type == 9  代表你要发送的是哪个
     * @param message
     */
    private void updateMessage(String message) {
        List<WaringDialogEntity> datas = warningDialog.getDatas();
        datas.clear();
        WaringDialogEntity warningEntity = new WaringDialogEntity();
        warningEntity.setTitle("入库预警");
        WaringDialogEntity putstorageEntity = new WaringDialogEntity();
        putstorageEntity.setTitle("退入主仓库预警");
        String content ="";
        String putstoragecontent ="";
        try {
            JSONArray jsonArray = new JSONArray(message);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String type = jsonObject.getString("type");
                //可能有多种预警的情况
                if (Constant.WARE_MAIN_WARE_ALARM_FLAG.equals(type)) {
                    Object message1 = jsonObject.get("message");
                    content=content+message1+"\n";
                }else {
                    Object message1 = jsonObject.get("message");
                    putstoragecontent=putstoragecontent+message1+"\n";
                }
            }
            warningEntity.setContent(content + "\n");
            putstorageEntity.setContent(putstoragecontent + "\n");
            datas.add(warningEntity);
            datas.add(putstorageEntity);
            warningDialog.notifyData();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
