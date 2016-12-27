package com.delta.smt.ui.feeder.feederWarning;

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
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FeederSupplyWorkItem;
import com.delta.smt.ui.feeder.feederWarning.FeederSupply.di.DaggerFeederSupplyComponent;
import com.delta.smt.ui.feeder.feederWarning.FeederSupply.di.FeederSupplyModule;
import com.delta.smt.ui.feeder.feederWarning.FeederSupply.mvp.FeederSupplyContract;
import com.delta.smt.ui.feeder.feederWarning.FeederSupply.mvp.FeederSupplyPresenter;
import com.delta.smt.ui.feeder.feederWorkItemHandle.feederCheckIn.FeederCheckInActivity;
import com.delta.smt.ui.feeder.feederWorkItemHandle.feederSupply.FeederSupplyActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class SupplyFragment extends BaseFragment<FeederSupplyPresenter> implements FeederSupplyContract.View , CommonBaseAdapter.OnItemClickListener<FeederSupplyWorkItem>{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerview;
    private List<FeederSupplyWorkItem> dataList = new ArrayList<>();
    private CommonBaseAdapter<FeederSupplyWorkItem> adapter;
    private static final String TAG = "SupplyFragment";


    @Override
    protected void initView() {
        adapter = new CommonBaseAdapter<FeederSupplyWorkItem>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, FeederSupplyWorkItem item, int position) {
                    holder.setText(R.id.info_text, "线别: " + String.valueOf(item.getLineNumber()));
                    holder.setText(R.id.info_text_2, "工单号: " + item.getWorkItemID());
                    holder.setText(R.id.info_text_3, "面别: " + item.getFaceID());
                    holder.setText(R.id.info_text_4, "状态: " + item.getStatus());
            }

            @Override
            protected int getItemViewLayoutId(int position, FeederSupplyWorkItem item) {
                return R.layout.feeder_supply_list_item;
            }
        };

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerFeederSupplyComponent.builder().appComponent(appComponent).feederSupplyModule(new FeederSupplyModule(this)).build().inject(this);
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
    public void onSuccess(List<FeederSupplyWorkItem> data) {
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
    public void onItemClick(View view, FeederSupplyWorkItem item, int position) {
        Log.i(TAG, "onItemClick: ");
        Log.i(TAG, "onItemClick: " + view.getClass().getSimpleName() + position);
        String workItemID = item.getWorkItemID();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.WORK_ITEM_ID,workItemID);
        IntentUtils.showIntent(getmActivity(), FeederSupplyActivity.class);
    }
}
