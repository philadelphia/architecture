package com.delta.smt.ui.smt_module.module_up;

import android.content.Intent;
import android.os.Bundle;
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
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.entity.SendMessage;
import com.delta.smt.entity.WaringDialogEntity;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.smt_module.module_up.di.DaggerModuleUpComponent;
import com.delta.smt.ui.smt_module.module_up.di.ModuleUpModule;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpContract;
import com.delta.smt.ui.smt_module.module_up.mvp.ModuleUpPresenter;
import com.delta.smt.ui.smt_module.module_up_binding.ModuleUpBindingActivity;
import com.delta.smt.widget.WarningDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class ModuleUpActivity extends BaseActivity<ModuleUpPresenter> implements
        ModuleUpContract.View, WarningManger.OnWarning, com.delta.libs.adapter.ItemOnclick<ModuleUpWarningItem.RowsBean> {

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
  
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    private List<ModuleUpWarningItem.RowsBean> dataList = new ArrayList<>();
    private ItemCountViewAdapter<ModuleUpWarningItem.RowsBean> myAdapter;
    private WarningDialog warningDialog;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleUpComponent.builder().appComponent(appComponent).moduleUpModule(new ModuleUpModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        //接收那种预警，没有的话自己定义常量
        warningManger.addWarning(Constant.PLUG_MOD_ALARM_FLAG, getClass());

        //是否接收预警 可以控制预警时机
        warningManger.setReceive(true);
        //关键 初始化预警接口
        warningManger.setOnWarning(this);

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
                holder.setText(R.id.tv_product_name_main, "主板: " + moduleUpWarningItem.getProduct_name_main());
                holder.setText(R.id.tv_product_name, "小板: " + moduleUpWarningItem.getProduct_name());
                if ("204".equals(moduleUpWarningItem.getStatus())) {
                    holder.setText(R.id.tv_status, "状态: " + "正在上模组");
                } else if ("205".equals(moduleUpWarningItem.getStatus())) {
                    holder.setText(R.id.tv_status, "状态: " + "上模组完成");
                }
                holder.setText(R.id.tv_forecast_time, "预计上线时间: " + moduleUpWarningItem.getOnline_plan_start_time());
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
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
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
    public void onFailed(ModuleUpWarningItem data) {
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
                onRefresh();
            }
        });
    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
    }


    @Override
    protected void onStop() {
        warningManger.unregisterWReceiver(this);
        super.onStop();
    }

    @Override
    protected void onResume() {
        warningManger.registerWReceiver(this);

        //需要定制的信息
        warningManger.sendMessage(new SendMessage(String.valueOf(Constant.PLUG_MOD_ALARM_FLAG), 0));
        if (null != myAdapter) {
            myAdapter.startRefreshTime();
        }

        super.onResume();
        getPresenter().getAllModuleUpWarningItems();
    }

    //预警
    @Override
    public void warningComing(String warningMessage) {
        if (warningDialog == null) {
            warningDialog = createDialog(warningMessage);
        }
        if (!warningDialog.isShowing()) {
            warningDialog.show();
        }
        updateMessage(warningMessage);
    }

    public WarningDialog createDialog(String message) {

        warningDialog = new WarningDialog(this);
        warningDialog.setOnClickListener(new WarningDialog.OnClickListener() {
            @Override
            public void onclick(View view) {
                warningManger.setConsume(true);
                onRefresh();


            }
        });
        warningDialog.show();

        return warningDialog;
    }


    private void updateMessage(String warningMessage) {

        List<WaringDialogEntity> datas = warningDialog.getDatas();
        datas.clear();
        WaringDialogEntity warningEntity = new WaringDialogEntity();
        warningEntity.setTitle("预警Sample");
        String content = "";
        try {
            JSONArray jsonArray = new JSONArray(warningMessage);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                Object message1 = jsonObject.get("message");
                content = content + message1 + "\n";
            }
            warningEntity.setContent(content + "\n");
            datas.add(warningEntity);
            warningDialog.notifyData();
        } catch (JSONException e) {
            e.printStackTrace();
        }


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

        warningManger.sendMessage(new SendMessage(String.valueOf(Constant.PLUG_MOD_ALARM_FLAG), 1));

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
        bundle.putString(Constant.SIDE, dataList.get(position).getSide());
        bundle.putString(Constant.PRODUCT_NAME_MAIN, dataList.get(position).getProduct_name_main());
        bundle.putString(Constant.PRODUCT_NAME, dataList.get(position).getProduct_name());
        bundle.putString(Constant.LINE_NAME, dataList.get(position).getLine_name());

        Intent intent = new Intent(this, ModuleUpBindingActivity.class);
        intent.putExtras(bundle);
        //this.startActivity(intent);
        startActivityForResult(intent, Constant.ACTIVITY_REQUEST_WORK_ITEM_ID);
    }

    private void onRefresh() {
        getPresenter().getAllModuleUpWarningItems();
    }

}
