package com.delta.smt.ui.store;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ArrangeInt;
import com.delta.smt.entity.StoreEmptyMessage;
import com.delta.smt.entity.WarningContent;
import com.delta.smt.entity.WarningInt;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.store.di.DaggerStoreComponent;
import com.delta.smt.ui.store.di.StoreModule;
import com.delta.smt.ui.store.mvp.StoreContract;
import com.delta.smt.ui.store.mvp.StorePresenter;
import com.delta.smt.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private DialogRelativelayout dialogRelativelayout;
    ArrayList<String> SimpleWarningdatas = new ArrayList<>();
    private SimpleDateFormat dateFormat;


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
        dateFormat = new SimpleDateFormat("hh:mm:ss");
        warningManger.addWarning(Constant.PCB_WAREH_ISSUE_ALARM_FLAG , getClass());
        warningManger.setRecieve(true);
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
        Log.e(TAG, "warningComing: " + message);
        if (alertDialog != null) {
            SimpleWarningdatas.clear();
            ArrayList<WarningContent> warningContents = GsonTools.changeGsonToList(message, WarningContent.class);

            for (WarningContent warningContent : warningContents) {
                if(warningContent.getType()==Constant.SAMPLEWARING){
                    String format = dateFormat.format(new Date(System.currentTimeMillis() - Long.valueOf(warningContent.getMessage().getDeadLine())));
                    SimpleWarningdatas.add(warningContent.getMessage().getProductline()+"--"+format+"\n");
                }

            }
            dialogRelativelayout.setDatas(SimpleWarningdatas);
            alertDialog.show();
        } else {
            alertDialog = createDialog(message);
        }
    }

    public AlertDialog createDialog(String message) {

        Log.e(TAG, "createDialog: "+message);
        dialogRelativelayout = new DialogRelativelayout(this);
        //2.传入的是红色字体的标题
        dialogRelativelayout.setStrTitle("预警信息");
        ArrayList<WarningContent> warningContents = GsonTools.changeGsonToList(message, WarningContent.class);
        for (WarningContent warningContent : warningContents) {
            if(warningContent.getType()==Constant.SAMPLEWARING){
                String format = dateFormat.format(new Date(System.currentTimeMillis() - Long.valueOf(warningContent.getMessage().getDeadLine())));
                SimpleWarningdatas.add(warningContent.getMessage().getProductline()+"--"+format);
            }
        }
        //3.传入的是黑色字体的二级标题
        dialogRelativelayout.setStrSecondTitle("simple预警");
        //4.传入的是一个ArrayList<String>
        dialogRelativelayout.setStrContent(SimpleWarningdatas);
        //5.构建Dialog，setView的时候把这个View set进去。
        return new AlertDialog.Builder(this).setView(dialogRelativelayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EventBus.getDefault().post(new StoreEmptyMessage());
                warningManger.setConsume(true);
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
        tlTitle.getTabAt(1).setText("排程(" + message.getAnInt() + ")");
        tlTitle.addOnTabSelectedListener(this);
    }

    @Subscribe
    public void event(WarningInt message) {
        tlTitle.getTabAt(0).setText("预警(" +message.getWarnInt() + ")");
        tlTitle.addOnTabSelectedListener(this);
    }



}
