package com.delta.smt.ui.smt_module.module_down;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.libs.adapter.ItemCountViewAdapter;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModuleDownWarningItem;
import com.delta.smt.entity.SendMessage;
import com.delta.smt.entity.WaringDialogEntity;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.smt_module.module_down.di.DaggerModuleDownComponent;
import com.delta.smt.ui.smt_module.module_down.di.ModuleDownModule;
import com.delta.smt.ui.smt_module.module_down.mvp.ModuleDownContract;
import com.delta.smt.ui.smt_module.module_down.mvp.ModuleDownPresenter;
import com.delta.smt.ui.smt_module.virtual_line_binding.VirtualLineBindingActivity;
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
public class ModuleDownActivity extends BaseActivity<ModuleDownPresenter> implements ModuleDownContract.View, WarningManger.OnWarning, com.delta.libs.adapter.ItemOnclick<ModuleDownWarningItem> {

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
    private List<ModuleDownWarningItem> dataList = new ArrayList<>();
    private ItemCountViewAdapter<ModuleDownWarningItem> myAdapter;

    private WarningDialog warningDialog;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleDownComponent.builder().appComponent(appComponent).moduleDownModule(new ModuleDownModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        //接收那种预警，没有的话自己定义常量
        warningManager.addWarning(Constant.UNPLUG_MOD_ALARM_FLAG, getClass());

        //是否接收预警 可以控制预警时机
        warningManager.setReceive(true);
        //关键 初始化预警接口
        warningManager.setOnWarning(this);


    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.INVISIBLE);
        toolbarTitle.setText("下模组");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        myAdapter = new ItemCountViewAdapter<ModuleDownWarningItem>(this, dataList) {
            @Override
            protected int getCountViewId() {
                return R.id.cv_countView;
            }

            @Override
            protected int getLayoutId() {
                return R.layout.item_module_down_warning_list;
            }

            @Override
            protected void convert(com.delta.libs.adapter.ItemTimeViewHolder holder, ModuleDownWarningItem moduleUpWarningItem, int position) {

                holder.setText(R.id.tv_lineID, "线别: " + moduleUpWarningItem.getLine_name());
                holder.setText(R.id.tv_workID, "工单号: " + moduleUpWarningItem.getWork_order());
                holder.setText(R.id.tv_faceID, "面别: " + moduleUpWarningItem.getSide());
                holder.setText(R.id.tv_product_name_main, "主板: " + moduleUpWarningItem.getProduct_name_main());
                holder.setText(R.id.tv_product_name, "小板: " + moduleUpWarningItem.getProduct_name());
                //holder.setText(R.id.tv_status,"状态: "+moduleUpWarningItem.getStatus());

                String status = null;
                switch (moduleUpWarningItem.getStatus()) {
                    case 210:
                        status = "等待下模组";
                        holder.itemView.setBackground(getResources().getDrawable(R.drawable.card_background));
                        break;

                    case 211:
                        status = "正在下模组";
                        holder.itemView.setBackground(getResources().getDrawable(R.drawable.card_background_yellow));
                        break;

                    default:
                        break;
                }
                holder.setText(R.id.tv_status, "状态: " + status);

            }
        };
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(myAdapter);
        myAdapter.setOnItemTimeOnclick(this);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_module_down_warning;
    }

    @Override
    public void onSuccess(List<ModuleDownWarningItem> dataSource) {
        dataList.clear();
        for (int i = 0; i < dataSource.size(); i++) {
//            if (TextUtils.isEmpty(dataSource.get(i).getUnplug_mod_actual_finish_time())) {
//                dataSource.get(i).setCreat_time(System.currentTimeMillis());
//            } else {
            dataSource.get(i).setCreat_time(dataSource.get(i).getOnline_actual_finish_time());
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//            Log.i(TAG, "onSuccess: " + dataSource.get(i).getUnplug_mod_actual_finish_time());
//            Log.i(TAG, "onSuccess: " + format.format(dataSource.get(i).getUnplug_mod_actual_finish_time()));
////                try {
////                    Date parse = format.parse(dataSource.get(i).getUnplug_mod_actual_finish_time());
////                    dataSource.get(i).setCreat_time(parse.getTime());
//
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
            dataSource.get(i).setEntityId(i);

        }
        dataList.addAll(dataSource);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(String message) {
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
        super.onResume();
        if (null != myAdapter) {
            myAdapter.startRefreshTime();
        }
        warningManager.registerWReceiver(this);
        //需要定制的信息
        warningManager.sendMessage(new SendMessage(String.valueOf(Constant.UNPLUG_MOD_ALARM_FLAG), 0));
        getPresenter().getAllModuleDownWarningItems();
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

    private void updateMessage(String warningMessage) {

        List<WaringDialogEntity> datas = warningDialog.getDatas();
        datas.clear();
        WaringDialogEntity warningEntity = new WaringDialogEntity();
        warningEntity.setTitle("下模组预警:");

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
            datas.add(warningEntity);
            warningDialog.notifyData();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public WarningDialog createDialog() {
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

        warningManager.sendMessage(new SendMessage(String.valueOf(Constant.UNPLUG_MOD_ALARM_FLAG), 1));
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
    public void onItemClick(View item, ModuleDownWarningItem rowsBean, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.WORK_ITEM_ID, rowsBean.getWork_order());
        bundle.putString(Constant.PRODUCT_NAME_MAIN, rowsBean.getProduct_name_main());
        bundle.putString(Constant.PRODUCT_NAME, rowsBean.getProduct_name());
        bundle.putString(Constant.SIDE, rowsBean.getSide());
        bundle.putString(Constant.LINE_NAME, rowsBean.getLine_name());
        IntentUtils.showIntent(this, VirtualLineBindingActivity.class, bundle);
    }

    public void onRefresh() {
        getPresenter().getAllModuleDownWarningItems();
    }
}
