package com.delta.smt.ui.feeder.warning;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FeederCheckInItem;
import com.delta.smt.ui.feeder.warning.checkin.di.CheckInModule;
import com.delta.smt.ui.feeder.warning.checkin.di.DaggerCheckInComponent;
import com.delta.smt.ui.feeder.warning.checkin.mvp.CheckInContract;
import com.delta.smt.ui.feeder.warning.checkin.mvp.CheckInPresenter;
import com.delta.smt.utils.BarCodeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public class CheckInFragment extends BaseFragment<CheckInPresenter> implements CheckInContract.View, BaseActiviy.OnBarCodeSuccess{

    private static final String TAG = "CheckInFragment";
    private BaseActiviy baseActiviy;
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(R.id.recy_contetn)
    RecyclerView recyContetn;
    @BindView(R.id.hr_scrow)
    HorizontalScrollView hrScrow;

    private CommonBaseAdapter<FeederCheckInItem> adapter;
    private List<FeederCheckInItem> dataList = new ArrayList<>();
    private List<FeederCheckInItem> dataSource = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActiviy) {
            this.baseActiviy = (BaseActiviy) context;
            baseActiviy.addOnBarCodeSuccess(this);
        }

    }

    @Override
    protected void initView() {
        dataList.add(new FeederCheckInItem("", "", "", "", ""));
        CommonBaseAdapter<FeederCheckInItem> adapterTitle = new CommonBaseAdapter<FeederCheckInItem>(getContext(), dataList) {
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
    public void onSuccess(List<FeederCheckInItem> data) {
        dataSource.clear();
        dataSource.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed() {

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            baseActiviy.removeOnBarCodeSuccess(this);
        } else {
            baseActiviy.addOnBarCodeSuccess(this);
        }

    }

    @Override
    public void onScanSuccess(String barcode) {
        Log.i(TAG, "onScanSuccess: ");
        Log.i(TAG, "barcode == " + barcode);
        BarCodeType codeType = BarCodeUtils.barCodeType(barcode);

        if (!TextUtils.isEmpty(barcode)) {
            if (codeType != null) {
                switch (codeType) {
                    case MATERIAL_BLOCK_BARCODE: //料号
                        for (FeederCheckInItem feederCheckInItem : dataSource) {
                            if (barcode.trim().equalsIgnoreCase(feederCheckInItem.getMaterialID())) {
                                dataSource.set(0, feederCheckInItem);
                            }
                        }

                        break;
                    case FRAME_LOCATION: //架位ID
                        if (dataSource.get(0).getLocation().equalsIgnoreCase(barcode)) {
                            //上传到后台
                        } else {

                        }
                        break;

                    default:
                        break;

                }
            }
        }

    }

}
