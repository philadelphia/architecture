package com.delta.smt.ui.smt_module.module_up;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.libs.adapter.ItemCountViewAdapter;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.smt_module.module_up.di.DaggerModuleUpComponent;
import com.delta.smt.ui.smt_module.module_up.di.ModuleUpModule;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpContract;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpPresenter;
import com.delta.smt.ui.smt_module.module_up_binding.ModuleUpBindingActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleUpActivity extends BaseActivity<ModuleUpPresenter> implements
        ModuleUpContract.View, WarningManger.OnWarning,  com.delta.libs.adapter.ItemOnclick<ModuleUpWarningItem.RowsBean> {

    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.module_up_warning)
    LinearLayout moduleUpWarning;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerview;
    @Inject
    WarningManger warningManger;
    //List<String> workOrderIDCacheList = new ArrayList<>();
    String workOrderID = "";
    List<String> status = new ArrayList<>();
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    private List<ModuleUpWarningItem.RowsBean> dataList = new ArrayList<>();
    private ItemCountViewAdapter<ModuleUpWarningItem.RowsBean> myAdapter;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleUpComponent.builder().appComponent(appComponent).moduleUpModule(new ModuleUpModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        //接收那种预警，没有的话自己定义常量
        warningManger.addWarning(Constant.MODULE_UP_WARNING, getClass());
        //是否接收预警 可以控制预警时机
        warningManger.setRecieve(true);
        //关键 初始化预警接口
        warningManger.setOnWarning(this);
        //getPresenter().getAllModuleUpWarningItems();


    }

    @Override
    protected void initView() {
        //headerTitle.setText("上模组");
        toolbar.setTitle("");
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("上模组");

        myAdapter = new ItemCountViewAdapter<ModuleUpWarningItem.RowsBean>(this, dataList) {
            @Override
            protected int getCountViewId() {
                return R.id.cv_countView;
            }

            @Override
            protected int getLayoutId() {
                return R.layout.item_module_up_warning_list;
            }

            @Override
            protected void convert(com.delta.libs.adapter.ItemTimeViewHolder holder, ModuleUpWarningItem.RowsBean moduleUpWarningItem, int position) {

                holder.setText(R.id.tv_lineID, "线别: " + moduleUpWarningItem.getLine_name());
                holder.setText(R.id.tv_workID, "工单号: " + moduleUpWarningItem.getWork_order());
                holder.setText(R.id.tv_faceID, "面别: " + moduleUpWarningItem.getSide());
                holder.setText(R.id.tv_product_name_main, "主板: "+moduleUpWarningItem.getProduct_name_main());
                holder.setText(R.id.tv_product_name, "小板: "+moduleUpWarningItem.getProduct_name());
                if("204".equals(moduleUpWarningItem.getStatus())){
                    holder.setText(R.id.tv_status,"状态: "+"正在上模组");
                }else if("205".equals(moduleUpWarningItem.getStatus())){
                    holder.setText(R.id.tv_status,"状态: "+"上模组完成");
                }
                holder.setText(R.id.tv_forecast_time,"预计上线时间: "+moduleUpWarningItem.getOnline_plan_start_time());
            }
        };
        myAdapter.setOnItemTimeOnclick(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(myAdapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_module_up_warning;
    }

    @Override
    public void onSuccess(ModuleUpWarningItem data) {
        dataList.clear();
        List<ModuleUpWarningItem.RowsBean> rows = data.getRows();
        for (int i = 0; i < rows.size(); i++) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date parse = format.parse(rows.get(i).getOnline_plan_start_time());
                rows.get(i).setEnd_time(parse.getTime());
                rows.get(i).setEntityId(i);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        dataList.addAll(rows);
        myAdapter.notifyDataSetChanged();
        ToastUtils.showMessage(this, data.getMsg());
    }

    @Override
    public void onFalied(ModuleUpWarningItem data) {
        ToastUtils.showMessage(this, data.getMsg());
    }

    @Override
    public void onNetFailed(Throwable throwable) {
        ToastUtils.showMessage(this, throwable.getMessage());
    }

    @Override
    public void showLoadingView() {
        statusLayout.showLoadingView();
    }

    @Override
    public void showContentView() {
        statusLayout.showContentView();
    }

    @Override
    public void showErrorView() {
        statusLayout.showErrorView();
        statusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getAllModuleUpWarningItems();
            }
        });
    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getAllModuleUpWarningItems();
            }
        });
    }


    @Override
    protected void onStop() {
        warningManger.unregisterWReceriver(this);
        super.onStop();
    }

    @Override
    protected void onResume() {
        warningManger.registerWReceiver(this);
        if (null != myAdapter) {
            myAdapter.startRefreshTime();
        }

        super.onResume();
        getPresenter().getAllModuleUpWarningItems();
    }

    //预警
    @Override
    public void warningComing(String warningMessage) {
        showDialog(warningMessage);
    }

    public void showDialog(String message) {
        //1.创建这个DialogRelativelayout
        DialogRelativelayout dialogRelativelayout = new DialogRelativelayout(this);
        //2.传入的是红色字体的标题
        dialogRelativelayout.setStrTitle("");
        //3.传入的是黑色字体的二级标题
        dialogRelativelayout.setStrSecondTitle("新工单");
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
                        //if (workOrderID.length()!=0){
                        getPresenter().getAllModuleUpWarningItems();
                        //}
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

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.ACTIVITY_REQUEST_WORK_ITEM_ID) {
            if (resultCode == Constant.ACTIVITY_RESULT_WORK_ITEM_ID) {
                String result = data.getStringExtra(Constant.WORK_ITEM_ID);

                for (int i = 0; i < dataList.size(); i++) {
                    if (dataList.get(i).getWork_order().equals(result)) {
                        ModuleUpWarningItem.RowsBean rb = dataList.get(i);
                        rb.setStart_time_plan("");
                        dataList.set(i, rb);
                    }
                }
                myAdapter.notifyDataSetChanged();
                //deleteItemByWorkItemID(result);
                //deleteWorkOrderIDCacheList(result);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

    /*public void deleteItemByWorkItemID(String workItemID){
        for(ModuleUpWarningItem.RowsBean list_item:dataList){
            if(list_item.getWork_order().equals(workItemID)){
                dataList.remove(list_item);
                break;
            }
        }
        myAdapter.notifyDataSetChanged();
    }*/

    /*public void deleteWorkOrderIDCacheList(String workOrderID){
        for (String item:workOrderIDCacheList){
            if(item.equals(workOrderID)){
                dataList.remove(item);
                break;
            }
        }
    }*/

    public String getWorkOrderIDCacheStr(List<String> workOrderIDCacheList) {
        String res = "";
        if (workOrderIDCacheList.size() > 0) {
            for (int i = 0; i < workOrderIDCacheList.size() - 1; i++) {
                res += workOrderIDCacheList.get(i) + ",";
            }
            res += workOrderIDCacheList.get(workOrderIDCacheList.size() - 1);
        }
        return res;
    }


    @Override
    public void onItemClick(View item, ModuleUpWarningItem.RowsBean rowsBean, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.WORK_ITEM_ID, dataList.get(position).getWork_order());
        bundle.putString(Constant.SIDE,dataList.get(position).getSide());
        bundle.putString(Constant.PRODUCT_NAME_MAIN,dataList.get(position).getProduct_name_main());
        bundle.putString(Constant.PRODUCT_NAME,dataList.get(position).getProduct_name());
        bundle.putString(Constant.LINE_NAME,dataList.get(position).getLine_name());

        Intent intent = new Intent(this, ModuleUpBindingActivity.class);
        intent.putExtras(bundle);
        //this.startActivity(intent);
        startActivityForResult(intent, Constant.ACTIVITY_REQUEST_WORK_ITEM_ID);
    }
}
