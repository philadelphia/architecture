package com.delta.smt.ui.feeder.warning;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.common.ItemOnclick;
import com.delta.smt.common.adapter.ItemCountdownViewAdapter;
import com.delta.smt.common.adapter.ItemTimeViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FeederSupplyWarningItem;

import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.feeder.warning.supply.di.DaggerSupplyComponent;
import com.delta.smt.ui.feeder.handle.feederSupply.FeederSupplyActivity;
import com.delta.smt.ui.feeder.warning.supply.di.SupplyModule;
import com.delta.smt.ui.feeder.warning.supply.mvp.SupplyContract;
import com.delta.smt.ui.feeder.warning.supply.mvp.SupplyPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public class SupplyFragment extends BaseFragment<SupplyPresenter> implements SupplyContract.View, ItemOnclick, WarningManger.OnWarning {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerview;
    private List<FeederSupplyWarningItem> dataList = new ArrayList<>();
    private ItemCountdownViewAdapter<FeederSupplyWarningItem> adapter;
    private static final String TAG = "SupplyFragment";
    private AlertDialog alertDialog;

    @Inject
    WarningManger warningManger;

    @Override
    protected void initView() {
        adapter = new ItemCountdownViewAdapter<FeederSupplyWarningItem>(getContext(), dataList) {
            @Override
            protected int getLayoutId() {
                return R.layout.feeder_supply_list_item;
            }

            @Override
            protected void convert(ItemTimeViewHolder holder, FeederSupplyWarningItem item, int position) {
                holder.setText(R.id.tv_title, "线别: " + item.getLineNumber());
                holder.setText(R.id.tv_line, "工单号: " + item.getWorkItemID());
                holder.setText(R.id.tv_material_station, "面别: " + item.getFaceID());
                holder.setText(R.id.tv_add_count, "状态: " + item.getStatus());
            }

        };

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        recyclerview.setAdapter(adapter);
        adapter.setOnItemTimeOnclck(this);
    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerSupplyComponent.builder().appComponent(appComponent).supplyModule(new SupplyModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        Log.i(TAG, "initData: ");
        //接收那种预警，没有的话自己定义常量
        warningManger.addWarning(Constant.SAMPLEWARING, getActivity().getClass());
        //是否接收预警 可以控制预警时机
        warningManger.setRecieve(true);
        //关键 初始化预警接口
        warningManger.setOnWarning(this);
        getPresenter().getAllSupplyWorkItems();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_material_supply;
    }

    @Override
    public void onSuccess(List<FeederSupplyWarningItem> data) {
        Log.i(TAG, "onSuccess: ");
        Log.i(TAG, "后台返回的数据长度为: " + data.size());
        dataList.clear();
        dataList.addAll(data);
        adapter.notifyDataSetChanged();
        Log.i(TAG, "后台返回的数据长度为: " + dataList.get(0).getCountDownLong());
        Log.i(TAG, "后台返回的数据长度为: " + dataList.get(0).getCountdown());
        Log.i(TAG, "onSuccess: " + dataList.size());
        
    }

    @Override
    public void onFailed(String message) {
        Log.i(TAG, "onFailed: " + message);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.i(TAG, "onHiddenChanged: ");
        super.onHiddenChanged(hidden);
        if (hidden){
            warningManger.unregisterWReceriver(getContext());
        }else {
            warningManger.registerWReceiver(getContext());
        }
    }

    @Override
    public void onItemClick(View item, int position) {
        FeederSupplyWarningItem feederSupplyWarningItem = dataList.get(position);
        String workItemID = feederSupplyWarningItem.getWorkItemID();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.WORK_ITEM_ID,workItemID);
        IntentUtils.showIntent(getmActivity(), FeederSupplyActivity.class,bundle);

    }

    @Override
    public void warningComing(String warningMessage) {
       if (alertDialog!=null&&alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = createDialog(warningMessage);
        } else {
            alertDialog = createDialog(warningMessage);
        }
    }


    private AlertDialog createDialog(final String warningMessage) {
        DialogRelativelayout dialogRelativelayout = new DialogRelativelayout(getContext());
        //3.传入的是黑色字体的二级标题
        dialogRelativelayout.setStrSecondTitle("预警信息");
        //4.传入的是一个ArrayList<String>
        ArrayList<String> datas = new ArrayList<>();
        datas.add("新备料请求:  ");
        datas.add("H13----01:00:00");
        datas.add("H14----01:20:00");
        datas.add("新入库请求: ");
        datas.add("20163847536---00:10:11");
        dialogRelativelayout.setStrContent(datas);
        return new AlertDialog.Builder(getContext()).setCancelable(false).setView(dialogRelativelayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPresenter().getAllSupplyWorkItems();
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void onResume() {
        warningManger.registerWReceiver(getContext());
        Log.i(TAG, "onResume: ");
        super.onResume();
    }


    @Override
    public void onStop() {
        Log.i(TAG, "onResume: ");
        warningManger.unregisterWReceriver(getContext());
        super.onStop();
    }
}
