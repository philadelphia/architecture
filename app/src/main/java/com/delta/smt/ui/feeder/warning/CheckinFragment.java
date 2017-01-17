package com.delta.smt.ui.feeder.warning;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.buletoothio.barcode.parse.BarCodeParse;
import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.FrameLocation;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
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
import com.delta.smt.utils.BarCodeUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public class CheckInFragment extends BaseFragment<CheckInPresenter> implements CheckInContract.View, BaseActivity.OnBarCodeSuccess{

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
        dataList.add(new FeederCheckInItem(" ", " "," "," "," ", " ", 0, " ", " "));
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
                holder.setText(R.id.tv_location, item.getShelves());
                holder.setText(R.id.tv_usedTime, item.getUsed_time());
                holder.setText(R.id.tv_chkinTimestamp, item.getCheckInTimeStamp());
                holder.setText(R.id.tv_status, item.getStatus() == 0 ? "等待入库" :"已经入库");
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
        dataSource.clear();
        dataSource.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(String message) {
        Log.i(TAG, "onFailed: ");
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
        Log.i(TAG, "codeType:== " + codeType);
        if (!TextUtils.isEmpty(barcode)) {
            if (codeType != null) {
                switch (codeType) {
                    case MATERIAL_BLOCK_BARCODE: //料号
                        Log.i(TAG, "料号: ");
                        try {
                            MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) new BarCodeParseIpml().getEntity(barcode,BarCodeType.MATERIAL_BLOCK_BARCODE);
                            if (null != materialBlockBarCode){
                                mCurrentMaterialID = materialBlockBarCode.getDeltaMaterialNumber();
                                mCurrentSerialNumber = materialBlockBarCode.getStreamNumber();
                                Log.i(TAG, "mCurrentMaterialID: " + mCurrentMaterialID) ;
                                Log.i(TAG, "mCurrentSerialNumber: " + mCurrentSerialNumber) ;
                            }
                            for (FeederCheckInItem feederCheckInItem : dataSource) {
                                if (materialBlockBarCode.getDeltaMaterialNumber().equalsIgnoreCase(feederCheckInItem.getMaterialID()) && materialBlockBarCode.getStreamNumber().equalsIgnoreCase(feederCheckInItem.getSerial_num()) ) {
                                    Log.i(TAG, "对应的feederCheckInItem: " + feederCheckInItem.toString());
                                    dataSource.set(0, feederCheckInItem);
                                    adapter.notifyDataSetChanged();
                                    Log.i(TAG, "onScanSuccess: " );
                                    Map<String, String> map = new HashMap<>();
                                    map.put("material_num", materialBlockBarCode.getDeltaMaterialNumber());
                                    map.put("serial_num", materialBlockBarCode.getStreamNumber());
                                    Gson gson = new Gson();
                                    String argument = gson.toJson(map);
                                    Log.i(TAG, "argument== " + argument);
                                    getPresenter().getFeederLocation(argument);
                                }
                            }
                        } catch (EntityNotFountException e) {
                            e.printStackTrace();
                        }



                        break;
                    case FRAME_LOCATION: //架位ID
                        FrameLocation frameLocation = null;
                        try {
                            frameLocation = (FrameLocation) new BarCodeParseIpml().getEntity(barcode, BarCodeType.FRAME_LOCATION);
                            if (null != frameLocation){
                                mCurrentLocation = frameLocation.getSource();
                                Log.i(TAG, "onScanSuccess: " + frameLocation.toString());
                                Map<String, String> map = new HashMap<>();
                                map.put("material_num", mCurrentMaterialID);
                                map.put("serial_num", mCurrentSerialNumber);
                                map.put("position", mCurrentLocation);
                                Gson gson = new Gson();
                                String argument = gson.toJson(map);
                                Log.i(TAG, "argument== " + argument);
                                getPresenter().getFeederCheckInTime(argument);
                            }
                        } catch (EntityNotFountException e) {
                            e.printStackTrace();
                        }

                        break;

                    default:
                        break;

                }
            }
        }

    }

}
