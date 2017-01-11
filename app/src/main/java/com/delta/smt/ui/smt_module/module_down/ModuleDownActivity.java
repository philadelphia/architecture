package com.delta.smt.ui.smt_module.module_down;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.common.ItemOnclick;
import com.delta.smt.common.adapter.ItemCountdownViewAdapter;
import com.delta.smt.common.adapter.ItemTimeViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModuleDownWarningItem;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.smt_module.module_down.di.DaggerModuleDownComponent;
import com.delta.smt.ui.smt_module.module_down.di.ModuleDownModule;
import com.delta.smt.ui.smt_module.module_down.mvp.ModuleDownContract;
import com.delta.smt.ui.smt_module.module_down.mvp.ModuleDownPresenter;
import com.delta.smt.ui.smt_module.virtual_line_binding.VirtualLineBindingActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleDownActivity extends BaseActivity<ModuleDownPresenter> implements ModuleDownContract.View,WarningManger.OnWarning,ItemOnclick{

    @BindView(R.id.header_back)
    RelativeLayout headerBack;
    @BindView(R.id.header_title)
    TextView headerTitle;
    @BindView(R.id.header_setting)
    TextView headerSetting;
    @BindView(R.id.module_up_warning)
    LinearLayout moduleUpWarning;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerview;
    private List<ModuleDownWarningItem> dataList = new ArrayList<>();
    private ItemCountdownViewAdapter<ModuleDownWarningItem> myAdapter;

    @Inject
    WarningManger warningManger;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleDownComponent.builder().appComponent(appComponent).moduleDownModule(new ModuleDownModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        //接收那种预警，没有的话自己定义常量
        warningManger.addWarning(Constant.SAMPLEWARING, getClass());
        //是否接收预警 可以控制预警时机
        warningManger.setRecieve(true);
        //关键 初始化预警接口
        warningManger.setOnWarning(this);
        getPresenter().getAllModuleDownWarningItems();

    }

    @Override
    protected void initView() {
        headerTitle.setText("下模组");

        myAdapter = new ItemCountdownViewAdapter<ModuleDownWarningItem>(this, dataList) {
            @Override
            protected int getLayoutId() {
                return R.layout.item_module_down_warning_list;
            }

            @Override
            protected void convert(ItemTimeViewHolder holder, ModuleDownWarningItem moduleUpWarningItem, int position) {

                holder.setText(R.id.tv_lineID, "线别: " + moduleUpWarningItem.getLineNumber());
                holder.setText(R.id.tv_workID, "工单号: " + moduleUpWarningItem.getWorkItemID());
                holder.setText(R.id.tv_faceID, "面别: " + moduleUpWarningItem.getFaceID());
                holder.setText(R.id.tv_status, "状态: " + moduleUpWarningItem.getStatus());

            }
        };
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(myAdapter);
        myAdapter.setOnItemTimeOnclck(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_module_down_warning;
    }

    @Override
    public void onSuccess(List<ModuleDownWarningItem> data) {
        dataList.clear();
        dataList.addAll(data);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFalied() {

    }

    @OnClick({R.id.header_back, R.id.header_title, R.id.header_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                onBackPressed();
                break;
            case R.id.header_title:
                break;
            case R.id.header_setting:
                break;
        }
    }

    @Override
    protected void onStop() {
        warningManger.unregisterWReceriver(this);
        super.onStop();
    }

    @Override
    protected void onResume() {
        if (null != myAdapter) {
            myAdapter.startRefreshTime();
        }
        warningManger.registerWReceiver(this);
        super.onResume();
    }

    //预警
    @Override
    public void warningComing(String warningMessage) {
        showDialog(warningMessage);
    }

    public void showDialog(String message){
        //1.创建这个DialogRelativelayout
        DialogRelativelayout dialogRelativelayout = new DialogRelativelayout(this);
        //2.传入的是红色字体的标题
        dialogRelativelayout.setStrTitle("");
        //3.传入的是黑色字体的二级标题
        dialogRelativelayout.setStrSecondTitle("下模组提醒");
        //4.传入的是一个ArrayList<String>
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add(message);
        dialogRelativelayout.setStrContent(titleList);
        //5.构建Dialog，setView的时候把这个View set进去。
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setView(dialogRelativelayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().getAllModuleDownWarningItems();
            }
        }).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != myAdapter) {
            myAdapter.cancelRefreshTime();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != myAdapter) {
            myAdapter.cancelRefreshTime();
        }
    }

    @Override
    public void onItemClick(View item, int position) {
        IntentUtils.showIntent(this, VirtualLineBindingActivity.class);
    }
}
