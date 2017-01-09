package com.delta.smt.ui.feeder.warning;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.common.ItemOnclick;
import com.delta.smt.common.adapter.ItemCountdownViewAdapter;
import com.delta.smt.common.adapter.ItemTimeViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FeederSupplyWarningItem;

import com.delta.smt.ui.feeder.warning.supply.di.DaggerSupplyComponent;
import com.delta.smt.ui.feeder.handle.feederSupply.FeederSupplyActivity;
import com.delta.smt.ui.feeder.warning.supply.di.SupplyModule;
import com.delta.smt.ui.feeder.warning.supply.mvp.SupplyContract;
import com.delta.smt.ui.feeder.warning.supply.mvp.SupplyPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class SupplyFragment extends BaseFragment<SupplyPresenter> implements SupplyContract.View, ItemOnclick {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerview;
    private List<FeederSupplyWarningItem> dataList = new ArrayList<>();
    private ItemCountdownViewAdapter<FeederSupplyWarningItem> adapter;
    private static final String TAG = "SupplyFragment";


    @Override
    protected void initView() {
        adapter = new ItemCountdownViewAdapter<FeederSupplyWarningItem>(getContext(), dataList) {
            @Override
            protected int getLayoutId() {
                return R.layout.feeder_supply_list_item;
            }

            @Override
            protected void convert(ItemTimeViewHolder holder, FeederSupplyWarningItem item, int position) {
                holder.setText(R.id.tv_title, "线别: " + String.valueOf(item.getLineNumber()));
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
        getPresenter().getAllSupplyWorkItems();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_material_supply;
    }

    @Override
    public void onSuccess(List<FeederSupplyWarningItem> data) {
        Log.i(TAG, "onSuccess: ");
        dataList.clear();
        dataList.addAll(data);
        adapter.notifyDataSetChanged();
        Log.i(TAG, "onSuccess: " + dataList.size());
        
    }

    @Override
    public void onFalied() {

    }


    @Override
    public void onItemClick(View item, int position) {
        FeederSupplyWarningItem feederSupplyWarningItem = dataList.get(position);
        String workItemID = feederSupplyWarningItem.getWorkItemID();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.WORK_ITEM_ID,workItemID);
        IntentUtils.showIntent(getmActivity(), FeederSupplyActivity.class);

    }
}
