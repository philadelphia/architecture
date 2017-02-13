package com.delta.smt.ui.feeder.warning;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.FeederBuffer;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FeederCheckInItem;
import com.delta.smt.ui.feeder.warning.checkin.di.CheckInModule;
import com.delta.smt.ui.feeder.warning.checkin.di.DaggerCheckInComponent;
import com.delta.smt.ui.feeder.warning.checkin.mvp.CheckInContract;
import com.delta.smt.ui.feeder.warning.checkin.mvp.CheckInPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.delta.buletoothio.barcode.parse.BarCodeType.MATERIAL_BLOCK_BARCODE;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public class CheckInFragment extends BaseFragment<CheckInPresenter> implements CheckInContract.View, BaseActivity.OnBarCodeSuccess {

    private static final String TAG = "CheckInFragment";
    private BaseActivity baseActiviy;
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(R.id.recy_contetn)
    RecyclerView recyContetn;
    @BindView(R.id.hr_scrow)
    HorizontalScrollView hrScrow;

    private CommonBaseAdapter<FeederCheckInItem> adapter;
    private List<FeederCheckInItem> dataList = new ArrayList<>();
    private List<FeederCheckInItem> dataSource = new ArrayList<>();
    private String mCurrentMaterialID;
    private String mCurrentSerialNumber;
    private String mCurrentLocation;
    private boolean flag1;
    private boolean flag2;
    private int index = -1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.baseActiviy = (BaseActivity) context;
            baseActiviy.addOnBarCodeSuccess(this);
        }

    }


    @Override
    protected void initView() {
        Log.i(TAG, "initView: ");
        dataList.add(new FeederCheckInItem(" ", " ", " ", " ", " ", " ", 0, " ", " "));
        CommonBaseAdapter<FeederCheckInItem> adapterTitle = new CommonBaseAdapter<FeederCheckInItem>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, FeederCheckInItem item, int position) {
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
                holder.setText(R.id.tv_location, item.getShelves());
                holder.setText(R.id.tv_usedTime, item.getUsed_time());
                holder.setText(R.id.tv_chkinTimestamp, item.getCheckInTimeStamp());
                holder.setText(R.id.tv_status, item.getStatus() == 0 ? "等待入库" : "已经入库");

                if (position == index) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);

                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }

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
        Log.i(TAG, "getContentViewId: ");
        return R.layout.fragment_checkin;
    }

    @Override
    public void onSuccess(List<FeederCheckInItem> data) {
        Log.i(TAG, "onSuccess: ");
        Log.i(TAG, "从后台返回的数据长度是: " + data.size());
        dataSource.clear();
        dataSource.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(String message) {
        Log.i(TAG, "onFailed: ");
    }

    @Override
    public void getFeederCheckInTimeFailed(String msg) {
        ToastUtils.showMessage(getActivity(), msg);
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
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        if (!flag1) {
            try {
                MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, MATERIAL_BLOCK_BARCODE);
                flag1 = true;

                mCurrentMaterialID = materialBlockBarCode.getDeltaMaterialNumber();
                mCurrentSerialNumber = materialBlockBarCode.getStreamNumber();
                Log.i(TAG, "mCurrentMaterialID: " + mCurrentMaterialID);
                Log.i(TAG, "mCurrentSerialNumber: " + mCurrentSerialNumber);
                for (FeederCheckInItem feederCheckInItem : dataSource) {
                    if (mCurrentMaterialID.equalsIgnoreCase(feederCheckInItem.getMaterialID()) && mCurrentSerialNumber.equalsIgnoreCase(feederCheckInItem.getSerial_num())) {
                        index = dataSource.indexOf(feederCheckInItem);
                        Log.i(TAG, "对应的feederCheckInItem: " + feederCheckInItem.toString());
                        adapter.notifyDataSetChanged();
                        Log.i(TAG, "onScanSuccess: ");
                        Map<String, String> map = new HashMap<>();
                        map.put("material_num", materialBlockBarCode.getDeltaMaterialNumber());
                        map.put("serial_num", materialBlockBarCode.getStreamNumber());
                        Gson gson = new Gson();
                        String argument = gson.toJson(map);
                        Log.i(TAG, "argument== " + argument);
                        Log.i(TAG, "料盘已经扫描完成，接下来扫描料架: ");

                    }
                }

            } catch (EntityNotFountException e) {
                e.printStackTrace();
            }
        }

        if (!flag2) {
            try {
                FeederBuffer frameLocation = (FeederBuffer) barCodeParseIpml.getEntity(barcode, BarCodeType.FEEDER_BUFFER);
                flag2 = true;
                mCurrentLocation = frameLocation.getSource();
                Log.i(TAG, "mCurrentLocation: " + frameLocation.toString());
                Map<String, String> map = new HashMap<>();
                map.put("material_num", mCurrentMaterialID);
                map.put("serial_num", mCurrentSerialNumber);
                map.put("position", mCurrentLocation);
                Gson gson = new Gson();
                String argument = gson.toJson(map);
                Log.i(TAG, "argument== " + argument);
                Log.i(TAG, "料架已经扫描完成，接下来入库: ");
                getPresenter().getFeederCheckInTime(argument);
                flag1 = false;
                flag2 = false;
            } catch (EntityNotFountException e1) {
                e1.printStackTrace();
            }
        }
    }
}
