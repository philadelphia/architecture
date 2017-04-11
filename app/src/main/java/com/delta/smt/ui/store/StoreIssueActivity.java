package com.delta.smt.ui.store;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ArrangeInt;
import com.delta.smt.entity.SendMessage;
import com.delta.smt.entity.SentRefreshRequest;
import com.delta.smt.entity.StoreEmptyMessage;
import com.delta.smt.entity.WaringDialogEntity;
import com.delta.smt.entity.WarningInt;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.store.di.DaggerStoreComponent;
import com.delta.smt.ui.store.di.StoreModule;
import com.delta.smt.ui.store.mvp.StoreContract;
import com.delta.smt.ui.store.mvp.StorePresenter;
import com.delta.smt.utils.ViewUtils;
import com.delta.smt.widget.DialogLayout;
import com.delta.smt.widget.WarningDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
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

    private AlertDialog alertDialog;
    private DialogLayout dialogLayout;
    ArrayList<String> SimpleWarningdatas = new ArrayList<>();
    private SimpleDateFormat dateFormat;
    private WarningDialog warningDialog;


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


        //接收那种预警
        warningManger.addWarning(Constant.PCB_WARE_ISSUE_ALARM_FLAG, getClass());
        //需要定制的信息
        warningManger.sendMessage(new SendMessage(Constant.PCB_WARE_ISSUE_ALARM_FLAG,0));
        //是否接收预警 可以控制预警时机
        warningManger.setReceive(true);
        //关键 初始化预警接口
        warningManger.setOnWarning(this);

    }




    @Override
    protected void initView() {
        toolbar.setTitle("");
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
                EventBus.getDefault().post(new SentRefreshRequest());
                break;
            case 1:
                Log.i(TAG, "onTabSelected: 1");
                showHideFragment(mArrangeFragment, currentFragment);
                currentFragment = mArrangeFragment;
                EventBus.getDefault().post(new SentRefreshRequest());
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

    public WarningDialog createDialog(String message) {
        warningDialog = new WarningDialog(this);
        warningDialog.setOnClickListener(new WarningDialog.OnClickListener() {
            @Override
            public void onclick(View view) {
                warningManger.setConsume(true);
                EventBus.getDefault().post(new SentRefreshRequest());
                if (warningDialog.isShowing()){
                    warningDialog.dismiss();
                }
            }
        });
        warningDialog.show();

        return warningDialog;
    }

    @Override
    public void warningComing(String message) {
        Log.e(TAG, "warningComing: " + message);

        if (warningDialog == null) {
            warningDialog = createDialog(message);
        }
        if(!warningDialog.isShowing()){
            warningDialog.show();
        }
        updateMessage(message);

    }

    /**
     * type == 9  代表你要发送的是哪个
     * @param message
     */
    private void updateMessage(String message) {
        List<WaringDialogEntity> datas = warningDialog.getDatas();
        datas.clear();
        WaringDialogEntity warningEntity = new WaringDialogEntity();
        warningEntity.setTitle("PCB发料预警");
        String content ="";
        try {
            JSONArray jsonArray = new JSONArray(message);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                int type = jsonObject.getInt("type");
                //可能有多种预警的情况
                if (type == 0) {
                    Object message1 = jsonObject.get("message");
                    content=content+message1+"\n";

                }
            }
            warningEntity.setContent(content + "\n");
            datas.add(warningEntity);
            warningDialog.notifyData();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean UseEventBus() {
        return true;
    }

    @Override
    protected void onResume() {
        warningManger.registerWReceiver(this);
        super.onResume();
        EventBus.getDefault().post(new StoreEmptyMessage());
    }

    @Override
    protected void onStop() {
        warningManger.unregisterWReceiver(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        warningManger.sendMessage(new SendMessage(Constant.PCB_WARE_ISSUE_ALARM_FLAG,1));
    }

    @Subscribe
    public void event(ArrangeInt message) {
        tlTitle.getTabAt(1).setText("排程(" + message.getAnInt() + ")");
        tlTitle.addOnTabSelectedListener(this);
    }

    @Subscribe
    public void event(WarningInt message) {
        tlTitle.getTabAt(0).setText("预警(" +message.getWarnInt() + ")");
        tlTitle.addOnTabSelectedListener(this);
    }



}
