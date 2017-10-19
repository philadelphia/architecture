package com.delta.smt.ui.smt_module.module_up;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.delta.smt.ui.smt_module.virtual_line_binding.VirtualLineBindingActivity;
import com.delta.smt.widget.WarningDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * Author Shufeng.Wu
 * Date   2017/1/3
 */
public class ModuleUpActivity extends BaseActivity<ModuleUpPresenter> implements
        ModuleUpContract.View, WarningManger.OnWarning, com.delta.libs.adapter.ItemOnclick<ModuleUpWarningItem> {

    private static final String TAG = "ModuleUpActivity";
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.module_up_warning)
    LinearLayout moduleUpWarning;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerview;
    @Inject
    WarningManger warningManager;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    private final List<ModuleUpWarningItem> dataList = new ArrayList<>();
    private ItemCountViewAdapter<ModuleUpWarningItem> myAdapter;
    private WarningDialog warningDialog;
    private int mType;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleUpComponent.builder().appComponent(appComponent).moduleUpModule(new ModuleUpModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        //接收那种预警，没有的话自己定义常量

        if (getIntent() != null) {
            Bundle mExtras = getIntent().getExtras();
            Log.e(TAG, "initData: ----");
            if (mExtras != null) {
                mType = mExtras.getInt(Constant.SELECT_TYPE, -1);
                Log.e(TAG, "initData: " + mType);
            }
        }
        warningManager.addWarning(Constant.PLUG_MOD_ALARM_FLAG, getClass());

        //是否接收预警 可以控制预警时机
        warningManager.setReceive(true);
        //关键 初始化预警接口
        warningManager.setOnWarning(this);

    }

    @Override
    protected void initView() {
        //headerTitle.setText("上模组");
        toolbar.setTitle("");
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        switch (mType) {
            case 1:
                toolbarTitle.setText("上Feeder");
                break;
            case 2:
                toolbarTitle.setText("虚拟线体绑定");
                break;
            default:
                break;
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }

        myAdapter = new ItemCountViewAdapter<ModuleUpWarningItem>(this, dataList) {
            @Override
            protected int getCountViewId() {
                return R.id.cv_countView;
            }

            @Override
            protected int getLayoutId() {
                return R.layout.item_module_up_warning_list;
            }

            @Override
            protected void convert(com.delta.libs.adapter.ItemTimeViewHolder holder, ModuleUpWarningItem moduleUpWarningItem, int position) {

                holder.setText(R.id.tv_lineID, "线别: " + moduleUpWarningItem.getLineName());
                holder.setText(R.id.tv_workID, "工单号: " + moduleUpWarningItem.getWorkOrder());
                holder.setText(R.id.tv_faceID, "面别: " + moduleUpWarningItem.getSide());
                holder.setText(R.id.tv_product_name_main, "主板: " + moduleUpWarningItem.getProductNameMain());
                holder.setText(R.id.tv_product_name, "小板: " + moduleUpWarningItem.getProductName());
                String status;
                switch (moduleUpWarningItem.getStatus()) {
                    case 203:
                        status = "接料完成";
                        holder.itemView.setBackground(ContextCompat.getDrawable(ModuleUpActivity.this, R.drawable.card_background));
                        break;
                    case 204:
                        status = "正在上模组";
                        holder.itemView.setBackground(ContextCompat.getDrawable(ModuleUpActivity.this, R.drawable.card_background_yellow));
                        break;
                    case 205:
                        status = "上模组完成";
                        holder.itemView.setBackground(ContextCompat.getDrawable(ModuleUpActivity.this, R.drawable.card_background));
                        break;
                    default:
                        status = "未知";
                        holder.itemView.setBackground(ContextCompat.getDrawable(ModuleUpActivity.this, R.drawable.card_background));
                        break;
                }

                holder.setText(R.id.tv_status, "状态: " + status);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String date = format.format(moduleUpWarningItem.getOnlinePlanStartTime());
                Log.i(TAG, "convert: parse " + date);
                holder.setText(R.id.tv_forecast_time, "预计上线时间: " + date);

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
    public void onGetWarningListSuccess(List<ModuleUpWarningItem> dataSource) {
        Log.i(TAG, "onGetWarningListSuccess: 后台返回的数据长度是: " + dataSource.size());
        dataList.clear();
        int size = dataSource.size();
        for (int i = 0; i < size; i++) {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//            try {
////                Date parse = format.parse(String.valueOf(dataSource.get(i).getOnlinePlanStartTime()));
////                Log.i(TAG, "onGetWarningListSuccess: parse " + parse.toString());
////                dataSource.get(i).setEnd_time(parse.getTime());
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            dataSource.get(i).setEnd_time(dataSource.get(i).getOnlinePlanStartTime());
            dataSource.get(i).setEntityId(i);
        }
        dataList.addAll(dataSource);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetWarningListFailed(String message) {
        ToastUtils.showMessage(this, message);
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
        warningManager.unregisterWReceiver(this);
        super.onStop();
    }

    @Override
    protected void onResume() {
        warningManager.registerWReceiver(this);

        //需要定制的信息
        warningManager.sendMessage(new SendMessage(String.valueOf(Constant.PLUG_MOD_ALARM_FLAG), 0));
        if (null != myAdapter) {
            myAdapter.startRefreshTime();
        }

        super.onResume();
        getPresenter().getModuleUpWarningList();
    }

    //预警
    @Override
    public void warningComing(String warningMessage) {
        if (warningDialog == null) {
            warningDialog = createDialog();
        }
        if (!warningDialog.isShowing()) {
            warningDialog.show();
        }
        updateMessage(warningMessage);
    }

    private WarningDialog createDialog() {

        warningDialog = new WarningDialog(this);
        warningDialog.setOnClickListener(new WarningDialog.OnClickListener() {
            @Override
            public void onclick(View view) {
                warningManager.setConsume(true);
                onRefresh();
            }
        });
        warningDialog.show();

        return warningDialog;
    }


    private void updateMessage(String warningMessage) {

        List<WaringDialogEntity> warningList = warningDialog.getDatas();
        warningList.clear();
        WaringDialogEntity warningEntity = new WaringDialogEntity();
        warningEntity.setTitle("上模组预警:");
        StringBuilder sb = new StringBuilder();
        try {
            JSONArray jsonArray = new JSONArray(warningMessage);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                Object message1 = jsonObject.get("message");
                sb.append(message1).append("\n");
            }
            String content = sb.toString();
            warningEntity.setContent(content + "\n");
            warningList.add(warningEntity);
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

        warningManager.sendMessage(new SendMessage(String.valueOf(Constant.PLUG_MOD_ALARM_FLAG), 1));

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


    @SuppressWarnings("all")
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
    public void onItemClick(View item, ModuleUpWarningItem moduleUpWarningItem, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.WORK_ITEM_ID, moduleUpWarningItem.getWorkOrder());
        bundle.putString(Constant.SIDE, moduleUpWarningItem.getSide());
        bundle.putString(Constant.PRODUCT_NAME_MAIN, moduleUpWarningItem.getProductNameMain());
        bundle.putString(Constant.PRODUCT_NAME, moduleUpWarningItem.getProductName());
        bundle.putString(Constant.LINE_NAME, moduleUpWarningItem.getLineName());

        switch (mType) {
            case 1:
                Intent  intent = new Intent(this, ModuleUpBindingActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 2:
                Intent  intent1 = new Intent(this, VirtualLineBindingActivity.class);
                intent1.putExtras(bundle);
                intent1.putExtra(Constant.SELECT_TYPE, mType);
                startActivity(intent1);
                break;
            default:
                break;
        }

    }

    private void onRefresh() {
        getPresenter().getModuleUpWarningList();
    }


}
