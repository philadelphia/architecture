package com.delta.smt.ui.store;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.StoreEmptyMessage;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.store.di.DaggerStoreComponent;
import com.delta.smt.ui.store.di.StoreModule;
import com.delta.smt.ui.store.mvp.StoreContract;
import com.delta.smt.ui.store.mvp.StorePresenter;
import com.delta.smt.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class StoreIssueActivity extends BaseActiviy<StorePresenter> implements TabLayout.OnTabSelectedListener, WarningManger.OnWarning, StoreContract.View {
    @BindView(R.id.header_title)
    TextView headerTitle;
    @BindView(R.id.main_title)
    TabLayout tlTitle;
    @Inject
    WarningManger warningManger;
    private String[] mTitles;
    private WarringFragment mWarringFragment;
    private ArrangeFragment mArrangeFragment;
    private SupportFragment currentFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerStoreComponent.builder().appComponent(appComponent).storeModule(new StoreModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        mTitles = new String[]{"预警", "排程"};
        warningManger.addWarning(Constant.SAMPLEWARING, getClass());
        warningManger.setRecieve(true);
        warningManger.setOnWarning(this);
    }

    @Override
    protected void initView() {
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
        headerTitle.setText(this.getResources().getString(R.string.storetitle));
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_warning_list;
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
    public void warningComming(String message) {
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
        warningManger.registWReceiver(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        warningManger.unregistWReceriver(this);
        super.onStop();
    }
}
