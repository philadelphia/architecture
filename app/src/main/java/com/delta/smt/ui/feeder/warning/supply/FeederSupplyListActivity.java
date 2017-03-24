package com.delta.smt.ui.feeder.warning.supply;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.WrapperListAdapter;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.libs.adapter.ItemCountViewAdapter;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FeederSupplyWarningItem;
import com.delta.smt.entity.SendMessage;
import com.delta.smt.entity.WaringDialogEntity;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.feeder.handle.feederSupply.FeederSupplyActivity;
import com.delta.smt.ui.feeder.warning.supply.di.DaggerSupplyComponent;
import com.delta.smt.ui.feeder.warning.supply.di.SupplyModule;
import com.delta.smt.ui.feeder.warning.supply.mvp.SupplyContract;
import com.delta.smt.ui.feeder.warning.supply.mvp.SupplyPresenter;
import com.delta.smt.widget.WarningDialog;

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

public class FeederSupplyListActivity extends BaseActivity<SupplyPresenter> implements SupplyContract.View, com.delta.libs.adapter.ItemOnclick, WarningManger.OnWarning {

    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerview;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    private List<FeederSupplyWarningItem> dataList = new ArrayList<>();
    private ItemCountViewAdapter<FeederSupplyWarningItem> adapter;
    private static final String TAG = "FeederSupplyList";
    private WarningDialog warningDialog;

    @Inject
    WarningManger warningManger;


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerSupplyComponent.builder().appComponent(appComponent).supplyModule(new SupplyModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        Log.i(TAG, "initData: ");
        //接收那种预警，没有的话自己定义常量
        warningManger.addWarning(Constant.FEEDER_BUFF_ALARM_FLAG, this.getClass());
        //是否接收预警 可以控制预警时机
        warningManger.setReceive(true);
        //关键 初始化预警接口
        warningManger.setOnWarning(this);

    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("Feeder 暂存区备料");

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
            protected void convert(com.delta.libs.adapter.ItemTimeViewHolder holder, FeederSupplyWarningItem feederSupplyWarningItem, int position) {
                holder.setText(R.id.tv_title, "线别: " + feederSupplyWarningItem.getLineName());
                holder.setText(R.id.tv_line, "工单号: " + feederSupplyWarningItem.getWorkOrder());
                holder.setText(R.id.tv_material_station, "面别: " + feederSupplyWarningItem.getSide());
                holder.setText(R.id.tv_add_count, "状态: " + (feederSupplyWarningItem.getStatus() == 2 ? "未开始备料" : "备料中"));
            }
//


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
            warningDialog = createDialog(warningMessage);
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
        warningEntity.setTitle("Feeder预警");
        String content ="";
        try {
            JSONArray jsonArray = new JSONArray(warningMessage);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                //可能有多种预警的情况

                    Object message1 = jsonObject.get("message");
                    content=content+message1+"\n";


            }
            warningEntity.setContent(content + "\n");
            datas.add(warningEntity);
            warningDialog.notifyData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onSuccess(List<FeederSupplyWarningItem> data) {
        Log.i(TAG, "onSuccess: ");
        Log.i(TAG, "后台返回的数据长度为: " + data.size());
        dataList.clear();
        for (int i = 0; i < data.size(); i++) {
            FeederSupplyWarningItem entity = data.get(i);
            entity.setEntityId(i);
            long time = System.currentTimeMillis();
            entity.setEnd_time(time + entity.getRemainTime() * 1000);
            Log.e(TAG, "onSuccess: " + entity.toString());
            dataList.add(entity);

        }

        adapter.notifyDataSetChanged();
        Log.i(TAG, "后台返回的数据长度为: " + dataList.get(0).getRemainTime());
        Log.i(TAG, "onSuccess: " + dataList.size());

    }


    @Override
    public void onFailed(String message) {
        Log.i(TAG, "onFailed: " + message);
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


    private WarningDialog createDialog(final String warningMessage) {
//        DialogLayout dialogLayout = new DialogLayout(this);
//        //3.传入的是黑色字体的二级标题
//        dialogLayout.setStrSecondTitle("预警信息");
//        //4.传入的是一个ArrayList<String>
//        ArrayList<String> datas = new ArrayList<>();
//        datas.add("新备料请求:  ");
//        datas.add("H13----01:00:00");
//        datas.add("H14----01:20:00");
//        datas.add("新入库请求: ");
//        datas.add("20163847536---00:10:11");
//        dialogLayout.setStrContent(datas);
//        return new AlertDialog.Builder(this).setCancelable(false).setView(dialogLayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                getPresenter().getAllSupplyWorkItems();
//                dialog.dismiss();
//            }
//        }).show();

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


    @Override
    public void onResume() {
        warningManger.registerWReceiver(this);
        warningManger.sendMessage(new SendMessage(String.valueOf(Constant.FEEDER_BUFF_ALARM_FLAG),0));

        Log.i(TAG, "onResume: ");
        super.onResume();

        if (null != adapter) {
            adapter.startRefreshTime();
        }
        getPresenter().getAllSupplyWorkItems();

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
        warningManger.unregisterWReceiver(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != adapter) {
            adapter.cancelRefreshTime();
        }

        warningManger.sendMessage(new SendMessage(String.valueOf(Constant.FEEDER_BUFF_ALARM_FLAG), 1));
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


    public void onRefresh(){
        getPresenter().getAllSupplyWorkItems();
    }

}
