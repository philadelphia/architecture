package com.delta.smt.ui.smt_module.module_up;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.common.ItemOnclick;
import com.delta.smt.common.adapter.ItemCountdownViewAdapter;
import com.delta.smt.common.adapter.ItemTimeViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.entity.OverReceiveWarning;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.smt_module.module_up.di.DaggerModuleUpComponent;
import com.delta.smt.ui.smt_module.module_up.di.ModuleUpModule;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpContract;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpPresenter;
import com.delta.smt.ui.smt_module.module_up_binding.ModuleUpBindingActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleUpActivity extends BaseActivity<ModuleUpPresenter> implements ModuleUpContract.View, ItemOnclick, WarningManger.OnWarning{

    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.module_up_warning)
    LinearLayout moduleUpWarning;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerview;
    private List<ModuleUpWarningItem.RowsBean> dataList = new ArrayList<>();
    private ItemCountdownViewAdapter <ModuleUpWarningItem.RowsBean> myAdapter;

    @Inject
    WarningManger warningManger;

    //List<String> workOrderIDCacheList = new ArrayList<>();
    String workOrderID = "";
    List<String> status = new ArrayList<>();

    @BindView(R.id.showNetState)
    TextView showNetState;




    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleUpComponent.builder().appComponent(appComponent).moduleUpModule(new ModuleUpModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        //通过预警获得工单号
        //workOrderIDCacheList.add("1");
        //workOrderIDCacheList.add("2");

        //接收那种预警，没有的话自己定义常量
        warningManger.addWarning(Constant.MODULE_UP_WARNING, getClass());
        //是否接收预警 可以控制预警时机
        warningManger.setRecieve(true);
        //关键 初始化预警接口
        warningManger.setOnWarning(this);


        //workOrderID = getWorkOrderIDCacheStr(workOrderIDCacheList);
        //if (workOrderID.length()!=0){
        getPresenter().getAllModuleUpWarningItems();
        //}


    }

    @Override
    protected void initView() {
        //headerTitle.setText("上模组");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("上模组");

        myAdapter = new ItemCountdownViewAdapter<ModuleUpWarningItem.RowsBean>(this, dataList) {
            @Override
            protected int getLayoutId() {
                return R.layout.item_module_up_warning_list;
            }

            @Override
            protected void convert(ItemTimeViewHolder holder, ModuleUpWarningItem.RowsBean moduleUpWarningItem, int position) {

                holder.setText(R.id.tv_lineID, "线别: " + moduleUpWarningItem.getLine());
                holder.setText(R.id.tv_workID, "工单号: " + moduleUpWarningItem.getWork_order());
                holder.setText(R.id.tv_faceID, "面别: " + moduleUpWarningItem.getFace());
                if(moduleUpWarningItem.getStart_time_plan().equals("")){
                    holder.setText(R.id.tv_status, "状态: " + "上模组完成");
                }else{
                    holder.setText(R.id.tv_status, "状态: " + "仓库物料正在上模组");
                }
                //Toast.makeText(ModuleUpActivity.this, moduleUpWarningItem.getStart_time_plan()+"", Toast.LENGTH_SHORT).show();
            }
        };
        myAdapter.setOnItemTimeOnclck(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(myAdapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_module_up_warning;
    }

    @Override
    public void onSuccess(ModuleUpWarningItem data) {
        if (data.getMsg().toLowerCase().equals("success")){
            dataList.clear();
            List<ModuleUpWarningItem.RowsBean> rows = data.getRows();
            dataList.addAll(rows);
            myAdapter.notifyDataSetChanged();
            showNetState.setVisibility(View.GONE);
        }

    }

    @Override
    public void onFalied() {

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

    @Override
    public void onItemClick(View item, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.WORK_ITEM_ID,dataList.get(position).getWork_order());
        Intent intent = new Intent(this, ModuleUpBindingActivity.class);
        intent.putExtras(bundle);
        //this.startActivity(intent);
        startActivityForResult(intent, Constant.ACTIVITY_REQUEST_WORK_ITEM_ID);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Constant.ACTIVITY_REQUEST_WORK_ITEM_ID) {
            if(resultCode==Constant.ACTIVITY_RESULT_WORK_ITEM_ID) {
                String result=data.getStringExtra(Constant.WORK_ITEM_ID);

                for(int i=0;i<dataList.size();i++){
                    if(dataList.get(i).getWork_order().equals(result)){
                        ModuleUpWarningItem.RowsBean rb = dataList.get(i);
                        rb.setStart_time_plan("");
                        dataList.set(i,rb);
                    }
                }
                myAdapter.notifyDataSetChanged();
                //deleteItemByWorkItemID(result);
                //deleteWorkOrderIDCacheList(result);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

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

    public String getWorkOrderIDCacheStr(List<String> workOrderIDCacheList){
        String res = "";
        if(workOrderIDCacheList.size()>0){
            for (int i=0;i<workOrderIDCacheList.size()-1;i++){
                res+=workOrderIDCacheList.get(i)+",";
            }
            res+=workOrderIDCacheList.get(workOrderIDCacheList.size()-1);
        }
        return res;
    }


}
