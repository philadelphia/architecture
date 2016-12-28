package com.delta.smt.ui.feeder.handle.feederSupply;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.ui.feeder.handle.feederSupply.di.DaggerFeederSupplyComponent;
import com.delta.smt.ui.feeder.handle.feederSupply.di.FeederSupplyModule;
import com.delta.smt.ui.feeder.handle.feederSupply.mvp.FeederSupplyContract;
import com.delta.smt.ui.feeder.handle.feederSupply.mvp.FeederSupplyPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

public class FeederSupplyActivity extends BaseActiviy<FeederSupplyPresenter> implements FeederSupplyContract.View {


    @BindView(R.id.header_back)
    TextView headerBack;
    @BindView(R.id.header_title)
    TextView headerTitle;
    @BindView(R.id.header_setting)
    TextView headerSetting;
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(R.id.recy_contetn)
    RecyclerView recyContetn;
    @BindView(R.id.hr_scrow)
    HorizontalScrollView hrScrow;
    @BindView(R.id.btn_upload)
    Button btnUpload;

    private CommonBaseAdapter<FeederSupplyItem> adapterTitle;
    private CommonBaseAdapter<FeederSupplyItem> adapter;
    private List<FeederSupplyItem> dataList = new ArrayList<>();
    private List<FeederSupplyItem> dataSource = new ArrayList<>();
    private static final String TAG = "FeederSupplyActivity";
    @Override
    protected int getContentViewId() {
        return R.layout.activity_feeder_supply;
    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerFeederSupplyComponent.builder().appComponent(appComponent).feederSupplyModule(new FeederSupplyModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        getPresenter().getAllToBeSuppliedFeeders();
    }

    @Override
    protected void initView() {
        dataList.add(new FeederSupplyItem("", "", "", "", ""));
        adapterTitle = new CommonBaseAdapter<FeederSupplyItem>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, FeederSupplyItem item, int position) {
                holder.itemView.setBackgroundColor(Color.GRAY);
            }

            @Override
            protected int getItemViewLayoutId(int position, FeederSupplyItem item) {
                return R.layout.feeder_supply_item;
            }
        };
        recyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyTitle.setAdapter(adapterTitle);


        adapter = new CommonBaseAdapter<FeederSupplyItem>(getContext(), dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, FeederSupplyItem item, int position) {
                holder.setText(R.id.tv_location, item.getLocation());
                holder.setText(R.id.tv_feederID, item.getFeederID());
                holder.setText(R.id.tv_materialID, item.getMaterialID());
                holder.setText(R.id.tv_module, item.getModuleID());
                holder.setText(R.id.tv_timestamp, item.getTimeStamp());
                holder.setText(R.id.tv_status, item.getStatus());
            }

            @Override
            protected int getItemViewLayoutId(int position, FeederSupplyItem item) {
                return R.layout.feeder_supply_item;
            }

        };
        recyContetn.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        recyContetn.setAdapter(adapter);

    }


    @OnClick({R.id.header_back, R.id.header_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                break;
            case R.id.header_setting:
                break;
        }
    }

    @Override
    public void onSuccess(List<FeederSupplyItem> data) {
        dataSource.clear();
        dataSource.addAll(data);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onFalied() {

    }

    @OnClick(R.id.btn_upload)
    public void onClick() {
        Log.i(TAG, "onClick: ");
    }
}
