package com.delta.smt.ui.feeder.warning;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FeederCheckInItem;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.ui.feeder.warning.checkin.di.CheckInModule;
import com.delta.smt.ui.feeder.warning.checkin.di.DaggerCheckInComponent;
import com.delta.smt.ui.feeder.warning.checkin.mvp.CheckInContract;
import com.delta.smt.ui.feeder.warning.checkin.mvp.CheckInPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class CheckinFragment extends BaseFragment<CheckInPresenter> implements CheckInContract.View, BaseActiviy.OnBarCodeSucess {

    private static final String TAG = "CheckinFragment";
    private BaseActiviy baseActiviy;
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(R.id.recy_contetn)
    RecyclerView recyContetn;
    @BindView(R.id.hr_scrow)
    HorizontalScrollView hrScrow;

    private CommonBaseAdapter<FeederCheckInItem> adapterTitle;
    private CommonBaseAdapter<FeederCheckInItem> adapter;
    private List<FeederCheckInItem> dataList = new ArrayList<>();
    private List<FeederCheckInItem> dataSource = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActiviy) {
            this.baseActiviy = (BaseActiviy) context;
            baseActiviy.addOnBarCodeSucess(this);
        }

    }

    @Override
    protected void initView() {
        dataList.add(new FeederCheckInItem("", "", "", "", ""));
        adapterTitle = new CommonBaseAdapter<FeederCheckInItem>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, FeederCheckInItem item, int position) {
//                holder.setText(R.id.tv_workItemID, "工单ID: " + item.getWorkItemID());
//                holder.setText(R.id.tv_feederID, "FeederID: " + item.getFeederID());
//                holder.setText(R.id.tv_materialID, "料号: " + item.getMaterialID());
//                holder.setText(R.id.tv_location, "架位: " + item.getLocation());
//                holder.setText(R.id.tv_chkinTimestamp, "入库时间: " + item.getCheckInTimeStamp());
//                holder.setText(R.id.tv_status, "状态: " + item.getStatus());
                holder.itemView.setBackgroundColor(Color.GRAY);

            }

            @Override
            protected int getItemViewLayoutId(int position, FeederCheckInItem item) {
                return R.layout.feeder_checkin_item;
            }
        };

        recyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        recyTitle.setAdapter(adapterTitle);


        adapter = new CommonBaseAdapter<FeederCheckInItem>(getContext(), dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, FeederCheckInItem item, int position) {
                holder.setText(R.id.tv_workItemID, item.getWorkItemID());
                holder.setText(R.id.tv_feederID, item.getFeederID());
                holder.setText(R.id.tv_materialID, item.getMaterialID());
                holder.setText(R.id.tv_location, item.getLocation());
                holder.setText(R.id.tv_chkinTimestamp, item.getCheckInTimeStamp());
                holder.setText(R.id.tv_status, item.getStatus());
            }

            @Override
            protected int getItemViewLayoutId(int position, FeederCheckInItem item) {
                return R.layout.feeder_checkin_item;
            }

        };
        recyContetn.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        recyContetn.setAdapter(adapter);

    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerCheckInComponent.builder().appComponent(appComponent).checkInModule(new CheckInModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        Log.i(TAG, "initData: ");
        getPresenter().getAllCheckedInFeeders();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_checkin;
    }

    @Override
    public void onSuccess(List<FeederCheckInItem> datas) {
        dataSource.clear();
        dataSource.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed() {

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            baseActiviy.removeOnBarCodeSuecss(this);
        } else {
            baseActiviy.addOnBarCodeSucess(this);
        }

    }

    @Override
    public void onScanSucess(String barcode) {

    }
}
