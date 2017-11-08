package com.example.app.ui.feeder.warning.supply;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.entity.SendMessage;
import com.example.app.ui.feeder.warning.supply.di.DaggerSupplyComponent;
import com.example.commonlibs.utils.IntentUtils;
import com.example.commonlibs.widget.autolayout.AutoToolbar;
import com.example.commonlibs.widget.statusLayout.StatusLayout;
import com.example.libs.adapter.ItemCountViewAdapter;
import com.example.app.Constant;
import com.example.app.base.BaseActivity;
import com.example.app.di.component.AppComponent;
import com.example.app.entity.FeederSupplyWarningItem;
import com.example.app.entity.WaringDialogEntity;
import com.example.app.manager.WarningManger;
import com.example.app.ui.feeder.handle.feederSupply.FeederSupplyActivity;
import com.example.app.ui.feeder.warning.supply.di.SupplyModule;
import com.example.app.ui.feeder.warning.supply.mvp.SupplyContract;
import com.example.app.ui.feeder.warning.supply.mvp.SupplyPresenter;
import com.example.app.widget.WarningDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public class FeederSupplyListActivity extends BaseActivity<SupplyPresenter> implements SupplyContract.View, com.example.libs.adapter.ItemOnclick, WarningManger.OnWarning {

    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerview;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    private final List<FeederSupplyWarningItem> dataList = new ArrayList<>();
    private ItemCountViewAdapter<FeederSupplyWarningItem> adapter;
    private static final String TAG = "FeederSupplyList";
    private WarningDialog warningDialog;

    @Inject
    WarningManger warningManager;


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerSupplyComponent.builder().appComponent(appComponent).supplyModule(new SupplyModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        Log.i(TAG, "initData: ");
        //接收那种预警，没有的话自己定义常量
        warningManager.addWarning(Constant.FEEDER_BUFF_ALARM_FLAG, this.getClass());
        //是否接收预警 可以控制预警时机
        warningManager.setReceive(true);
        //关键 初始化预警接口
        warningManager.setOnWarning(this);

    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbarTitle.setText(R.string.FeederCache);

        adapter = new ItemCountViewAdapter<FeederSupplyWarningItem>(this, dataList) {
            @Override
            protected int getCountViewId() {
                return R.id.cv_countView;
            }

            @Override
            protected int getLayoutId() {
                return R.layout.feeder_supply_list_item;
            }

            @Override
            protected void convert(com.example.libs.adapter.ItemTimeViewHolder holder, FeederSupplyWarningItem feederSupplyWarningItem, int position) {
                holder.setText(R.id.tv_title, "线别: " + feederSupplyWarningItem.getLineName());
                holder.setText(R.id.tv_line, "工单号: " + feederSupplyWarningItem.getWorkOrder());
                holder.setText(R.id.tv_material_station, "面别: " + feederSupplyWarningItem.getSide());

                String status = null;
                switch (feederSupplyWarningItem.getStatus()){
                    case 3:
                        status = "未开始备料";
                        break;
                    case 4:
                        status = "正在备料";
                        holder.itemView.setBackground(ContextCompat.getDrawable(FeederSupplyListActivity.this, R.drawable.card_background_yellow));
                        break;
                    default:
                        break;

                }

                holder.setText(R.id.tv_add_count, "状态: " + status);
            }


        };

        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyclerview.setAdapter(adapter);
        adapter.setOnItemTimeOnclick(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_material_supply;
    }

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

    @Override
    public void onGetSupplyWorkItemListSuccess(List<FeederSupplyWarningItem> data) {
        Log.i(TAG, "onGetWarningListSuccess: ");
        Log.i(TAG, "后台返回的数据长度为: " + data.size());
        dataList.clear();
        for (int i = 0; i < data.size(); i++) {
            FeederSupplyWarningItem entity = data.get(i);
            entity.setEntityId(i);
            long time = System.currentTimeMillis();
            entity.setEnd_time(Math.round(time + entity.getRemainTime() * 1000));
            Log.e(TAG, "onGetWarningListSuccess: " + entity.toString());
            dataList.add(entity);

        }

        adapter.notifyDataSetChanged();
        Log.i(TAG, "后台返回的数据长度为: " + dataList.get(0).getRemainTime());
        Log.i(TAG, "onGetWarningListSuccess: " + dataList.size());

    }

    @Override
    public void onGetSupplyWorkItemListFailed(String message) {
        Log.i(TAG, "onGetWarningListFailed: " + message);
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
    public void onResume() {
        warningManager.registerWReceiver(this);
        warningManager.sendMessage(new SendMessage(String.valueOf(Constant.FEEDER_BUFF_ALARM_FLAG),0));

        Log.i(TAG, "onResume: ");
        super.onResume();

        if (null != adapter) {
            adapter.startRefreshTime();
        }
        getPresenter().getSupplyWorkItemList();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != adapter) {
            adapter.cancelRefreshTime();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onResume: ");
        warningManager.unregisterWReceiver(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != adapter) {
            adapter.cancelRefreshTime();
        }

        warningManager.sendMessage(new SendMessage(String.valueOf(Constant.FEEDER_BUFF_ALARM_FLAG), 1));
    }

    @Override
    public void onItemClick(View item, Object o, int position) {
        FeederSupplyWarningItem feederSupplyWarningItem = dataList.get(position);
        String workItemID = feederSupplyWarningItem.getWorkOrder();
        String side = feederSupplyWarningItem.getSide();
        String line = feederSupplyWarningItem.getLineName();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.WORK_ITEM_ID, workItemID);
        bundle.putString(Constant.SIDE, side);
        bundle.putString(Constant.LINE_NAME, line);
        IntentUtils.showIntent(this, FeederSupplyActivity.class, bundle);
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

    private void onRefresh(){
        getPresenter().getSupplyWorkItemList();
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
        List<WaringDialogEntity> dataList = warningDialog.getDatas();
        dataList.clear();
        WaringDialogEntity warningEntity = new WaringDialogEntity();
        warningEntity.setTitle("Feeder预警:");
        StringBuilder sb = new StringBuilder();
        try {
            JSONArray jsonArray = new JSONArray(warningMessage);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                //可能有多种预警的情况
                Object message1 = jsonObject.get("message");
                sb.append(message1).append("\n");

            }
            String content = sb.toString();
            warningEntity.setContent(content + "\n");
            dataList.add(warningEntity);
            warningDialog.notifyData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
