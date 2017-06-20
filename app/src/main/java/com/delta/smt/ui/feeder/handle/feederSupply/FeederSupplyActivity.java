package com.delta.smt.ui.feeder.handle.feederSupply;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.RecycleViewUtils;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.DebitData;
import com.delta.smt.entity.FeederMESItem;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.feeder.handle.feederSupply.di.DaggerFeederSupplyComponent;
import com.delta.smt.ui.feeder.handle.feederSupply.di.FeederSupplyModule;
import com.delta.smt.ui.feeder.handle.feederSupply.mvp.FeederSupplyContract;
import com.delta.smt.ui.feeder.handle.feederSupply.mvp.FeederSupplyPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;
import com.delta.smt.utils.ViewUtils;
import com.delta.smt.widget.CustomPopWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.buletoothio.barcode.parse.BarCodeType.MATERIAL_BLOCK_BARCODE;
import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public class FeederSupplyActivity extends BaseActivity<FeederSupplyPresenter> implements FeederSupplyContract.View {
    private static final String TAG = "FeederSupplyActivity";
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.checkBox)
    CheckBox checkBox;

    @BindView(R.id.checkBox_autoUpLoadToMES)
    CheckBox checkBox_autoUpLoadToMES;

    @BindView(R.id.btn_debitManually)
    Button btn_debitManually;
    @BindView(R.id.recy_title)
    RecyclerView recyclerViewTitle;
    @BindView(R.id.recy_content)
    RecyclerView recyclerViewContent;
    @BindView(R.id.hr_scrow)
    HorizontalScrollView hrScrollView;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.tv_moduleID)
    TextView tvModuleID;
    @BindView(R.id.tv_work_order)
    TextView tv_workOrder;
    @BindView(R.id.tv_line_name)
    TextView tv_side;
    @BindView(R.id.tv_line_num)
    TextView tv_line;


    private CommonBaseAdapter<FeederSupplyItem> adapter;
    private final List<FeederSupplyItem> dataList = new ArrayList<>();
    private final List<FeederSupplyItem> dataSource = new ArrayList<>();
    private String workId;
    private String side;
    private String argument;
    private int index = -1;
    private MaterialBlockBarCode mCurrentMaterial;
    private LinearLayoutManager linearLayoutManager;
    private boolean isBeginSupply = false;
    private CustomPopWindow popUpWindow;
    private CommonBaseAdapter<DebitData> unDebitadapter;
    private CommonBaseAdapter<FeederMESItem> unUploadMESAdapter;
    private final List<DebitData> unDebitItemList = new ArrayList<>();
    private final List<FeederMESItem> unUplaodToMESItemList = new ArrayList<>();
    private boolean isAllItemSupplied = false;

    @Override
    protected void handError(String contents) {
        super.handError(contents);
        onFailed(contents);
        showErrorView();

    }

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
        Log.i(TAG, "initData: ");
        Intent intent = getIntent();
        workId = intent.getStringExtra(Constant.WORK_ITEM_ID);
        side = intent.getStringExtra(Constant.SIDE);
        String lineName = intent.getStringExtra(Constant.LINE_NAME);
        tv_workOrder.setText(getResources().getString(R.string.WorkID) + ":   " + workId);
        tv_line.setText(getResources().getString(R.string.Line) + ":   " + lineName);
        tv_side.setText(getResources().getString(R.string.Side) + ":   " + side);
        Log.i(TAG, "workId==: " + workId);
        Log.i(TAG, "side==: " + side);
        Log.i(TAG, "lineName==: " + lineName);

        Map<String, String> map = new HashMap<>();
        map.put("work_order", workId);
        map.put("side", side);
        argument = GsonTools.createGsonListString(map);
        Log.i(TAG, "argument==: " + argument);


    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }

        toolbarTitle.setText(R.string.FeederSupply);
        dataList.add(new FeederSupplyItem());
        CommonBaseAdapter<FeederSupplyItem> adapterTitle = new CommonBaseAdapter<FeederSupplyItem>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, FeederSupplyItem item, int position) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, FeederSupplyItem item) {
                return R.layout.feeder_supply_item;
            }
        };
        recyclerViewTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTitle.setAdapter(adapterTitle);


        adapter = new CommonBaseAdapter<FeederSupplyItem>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, FeederSupplyItem item, int position) {
                holder.setText(R.id.tv_feederID, item.getFeederID());
                holder.setText(R.id.tv_materialID, item.getMaterialID());
                holder.setText(R.id.tv_module, item.getSlot());
                holder.setText(R.id.tv_timestamp, item.getBindTime());
                holder.setText(R.id.tv_status, item.getStatus() == 0 ? "等待上模组" : " 上模组完成");
                switch (item.getStatus()) {
                    case 0:
                        holder.itemView.setBackgroundColor(Color.WHITE);
                        break;
                    case 1:
                        holder.itemView.setBackgroundColor(Color.GREEN);
                        break;
                    default:
                        break;
                }

            }

            @Override
            protected int getItemViewLayoutId(int position, FeederSupplyItem item) {
                return R.layout.feeder_supply_item;
            }

        };
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        recyclerViewContent.setLayoutManager(linearLayoutManager);
        recyclerViewContent.setAdapter(adapter);

    }

    @OnClick({R.id.tv_setting, R.id.btn_upload, R.id.btn_debitManually})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_setting:
                break;
            case R.id.btn_upload:   //获取待上传到MES的列表
                Map<String, Object> map = new HashMap<>();
                map.put("work_order", workId);
                map.put("side", side);
                map.put("is_feeder_buffer", 1);
                if (popUpWindow == null) {
                    createPopupWindow(unDebitItemList);
                }
                getPresenter().getUnUpLoadToMESList(GsonTools.createGsonString(map));
                break;
            case R.id.btn_debitManually:
                if (isBeginSupply) {
                    Map<String, String> map1 = new HashMap<>();
                    map1.put("work_order", workId);
                    map1.put("side", side);
                    map1.put("part", "FeederBuffer");
                    argument = GsonTools.createGsonListString(map1);
                    //获取没有扣账的列表
                    getPresenter().getUnDebitedItemList(argument);
                } else {
                    ToastUtils.showMessage(this, "还未开始发料", Toast.LENGTH_SHORT);
                }
                break;

            case R.id.bt_sheet_cancel:
                break;
            case R.id.bt_sheet_select_all:
                break;
            case R.id.bt_sheet_confirm:
                break;
            default:
                break;

        }
    }

    @Override
    public void onSuccess(List<FeederSupplyItem> data) {
        Log.i(TAG, "onSuccess: ");
        Log.i(TAG, "后台返回的数据长度是: " + data.size());
        statusLayout.setVisibility(View.VISIBLE);
        dataSource.clear();
        dataSource.addAll(data);
        adapter.notifyDataSetChanged();
        isBeginSupply = isBeginSupply(data);
        if (index != -1) {
            RecycleViewUtils.scrollToMiddle(linearLayoutManager, getLastMaterialIndex(mCurrentMaterial, dataSource), recyclerViewContent);
        }
        if (data.size() == 0) {
            isAllItemSupplied = true;
            Log.i(TAG, "feeder全部上模组，开始上传结果: ");
            Map<String, String> map = new HashMap<>();
            map.put("work_order", workId);
            map.put("side", side);

            String argument = GsonTools.createGsonListString(map);
            getPresenter().resetFeederSupplyStatus(argument);
        }

    }

    private boolean isBeginSupply(List<FeederSupplyItem> data) {
        int size = data.size();
        boolean flag = false;
        FeederSupplyItem feederSupplyItem;
        for (int i = 0; i < size; i++) {
            feederSupplyItem = data.get(i);
            if (1 == feederSupplyItem.getStatus()) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public void showUnDebitedItemList(List<DebitData> data) {
        if (isAllItemSupplied) {
            popUpWindow.dissmiss();
            getPresenter().resetFeederSupplyStatus(argument);

        }
        unDebitItemList.clear();
        unDebitItemList.addAll(data);
        unDebitadapter.notifyDataSetChanged();
        if (popUpWindow != null) {
            popUpWindow.showAsDropDown(toolbar);
        }

    }

    @Override
    public void showUnUpLoadToMESItemList(List<FeederMESItem> data) {
        if (0 == data.size() && isAllItemSupplied) {
            popUpWindow.dissmiss();
        }
        unUplaodToMESItemList.clear();
        unUplaodToMESItemList.addAll(data);

        if (popUpWindow == null) {
            createPopupWindowForMES(data);
        }
        popUpWindow.showAsDropDown(toolbar);
    }

    private void createPopupWindowForMES(final List<FeederMESItem> data) {
        Log.i(TAG, "未上传的数据长度为: " + data.size());

        popUpWindow = CustomPopWindow.builder().with(this).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .setAnimationStyle(R.style.popupAnimalStyle)
                .setView(R.layout.dialog_bottom_sheet)
//                .enableBlur(true)
                .build();
        View contentView = popUpWindow.getContentView();
        RecyclerView recyclerView = ViewUtils.findView(contentView, R.id.rv_sheet);
        Button btn_back = ViewUtils.findView(contentView, R.id.bt_sheet_back);
        Button btn_cancel = ViewUtils.findView(contentView, R.id.bt_sheet_select_cancel);
        Button btn_confirm = ViewUtils.findView(contentView, R.id.bt_sheet_confirm);
        Button btn_selectAll = ViewUtils.findView(contentView, R.id.bt_sheet_select_all);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpWindow.dissmiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (DebitData debitData : unDebitItemList) {
                    if (debitData.isChecked())
                        debitData.setChecked(false);
                }

            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                Map<String, String> mapItem = new HashMap<>();
                map.put("work_order", workId);
                map.put("side", side);
                List<Map<String, String>> list = new ArrayList<Map<String, String>>();

                for (FeederMESItem feederMESItem : data) {
                    if (feederMESItem.isChecked())
                        mapItem.put("slot", feederMESItem.getSlot());
                    mapItem.put("material_no", feederMESItem.getMaterialID());
//                    mapItem.put("demand_qty", String.valueOf(feederSupplyItem.getAmount()));
//                    mapItem.put("total_qty", String.valueOf(feederSupplyItem.getIssue_amount()));
                    list.add(mapItem);
                }
                map.put("list", list);
                map.put("part", "FeederBuffer");
                String argument = GsonTools.createGsonListString(map);
                Log.i(TAG, "手动扣账参数为:  " + argument);
                getPresenter().deductionManually(argument);
            }
        });

        btn_selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (FeederMESItem debitData : data) {
                    debitData.setChecked(true);
                }
            }
        });
        unUploadMESAdapter = new CommonBaseAdapter<FeederMESItem>(this, data) {
            @Override
            protected void convert(final CommonViewHolder holder, final FeederMESItem item, int position) {
                holder.setText(R.id.tv_material_id, "料号 :\t" + item.getMaterialID());
                holder.setText(R.id.tv_amount, "流水号 :\t" + String.valueOf(item.getSerialNumber()));
                holder.setText(R.id.tv_slot, "料站 :\t" + item.getSlot());
                holder.setText(R.id.tv_issue, "FeederID :\t" + String.valueOf(item.getFeederID()));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox checkBox = holder.getView(R.id.cb_debit);
                        checkBox.setChecked(!item.isChecked());
                        item.setChecked(!item.isChecked());
                    }
                });

            }

            @Override
            protected int getItemViewLayoutId(int position, FeederMESItem item) {
                return R.layout.item_debit_list;
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(unUploadMESAdapter);
        unUploadMESAdapter.notifyDataSetChanged();
    }

    private void createPopupWindow(final List<DebitData> data) {
        Log.i(TAG, "未扣账的数据长度为: " + data.size());

        popUpWindow = CustomPopWindow.builder().with(this).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .setAnimationStyle(R.style.popupAnimalStyle)
                .setView(R.layout.dialog_bottom_sheet)
//                .enableBlur(true)
                .build();
        View contentView = popUpWindow.getContentView();
        RecyclerView recyclerView = ViewUtils.findView(contentView, R.id.rv_sheet);
        Button btn_back = ViewUtils.findView(contentView, R.id.bt_sheet_back);
        Button btn_cancel = ViewUtils.findView(contentView, R.id.bt_sheet_select_cancel);
        Button btn_confirm = ViewUtils.findView(contentView, R.id.bt_sheet_confirm);
        Button btn_selectAll = ViewUtils.findView(contentView, R.id.bt_sheet_select_all);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpWindow.dissmiss();

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (DebitData debitData : unDebitItemList) {
                    if (debitData.isChecked())
                        debitData.setChecked(false);
                }

                unDebitadapter.notifyDataSetChanged();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                Map<String, String> mapItem = new HashMap<>();
                map.put("work_order", workId);
                map.put("side", side);
                List<Map<String, String>> list = new ArrayList<Map<String, String>>();

                for (DebitData debitData : data) {
                    if (debitData.isChecked()) {
                        mapItem.put("slot", debitData.getSlot());
                        mapItem.put("material_no", debitData.getMaterial_no());
                        mapItem.put("demand_qty", String.valueOf(debitData.getAmount()));
                        mapItem.put("total_qty", String.valueOf(debitData.getIssue_amount()));
                        list.add(mapItem);
                    }

                }
                if (list.size() ==0){
                    ToastUtils.showMessage(FeederSupplyActivity.this, "请选择扣账列表");
                    return;
                }
                map.put("list", list);
                map.put("part", "FeederBuffer");
                String argument = GsonTools.createGsonListString(map);
                Log.i(TAG, "手动扣账参数为:  " + argument);
                getPresenter().deductionManually(argument);
            }
        });

        btn_selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (DebitData debitData : data) {
                    debitData.setChecked(true);
                }
            }
        });
        unDebitadapter = new CommonBaseAdapter<DebitData>(this, data) {
            @Override
            protected void convert(final CommonViewHolder holder, final DebitData item, int position) {
                holder.setText(R.id.tv_material_id, "料号 :\t" + item.getMaterial_no());
                holder.setText(R.id.tv_amount, "数量 :\t" + String.valueOf(item.getAmount()));
                holder.setText(R.id.tv_slot, "料站 :\t" + item.getSlot());
                holder.setText(R.id.tv_issue, "发料量 :\t" + String.valueOf(item.getIssue_amount()));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox checkBox = holder.getView(R.id.cb_debit);
                        checkBox.setChecked(!item.isChecked());
                        item.setChecked(!item.isChecked());
                    }
                });

            }

            @Override
            protected int getItemViewLayoutId(int position, DebitData item) {
                return R.layout.item_debit_list;
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(unDebitadapter);

    }

    @Override
    public void onFailed(String message) {
        Log.i(TAG, "onFailed: " + message);
        ToastUtils.showMessage(this, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void onAllSupplyComplete() {
        ToastUtils.showMessage(this, "所有Feeder已完成发料", Toast.LENGTH_SHORT);
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("所有Feeder已完成发料")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FeederSupplyActivity.this.finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button positiveNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        positiveButton.setBackgroundColor(ContextCompat.getColor(this, R.color.delta_blue));
        positiveNegative.setBackgroundColor(ContextCompat.getColor(this, R.color.delta_blue));

        alertDialog.show();
    }

    @Override
    public void onUpLoadFailed(String message) {
        Log.i(TAG, "onUpLoadFailed: ");
        ToastUtils.showMessage(this, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void showLoadingView() {
        statusLayout.setVisibility(View.VISIBLE);
        statusLayout.showLoadingView();
    }

    @Override
    public void showContentView() {
        statusLayout.setVisibility(View.VISIBLE);
        statusLayout.showContentView();
    }

    @Override
    public void showErrorView() {
        statusLayout.setVisibility(View.VISIBLE);
        statusLayout.showErrorView();
        statusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getAllToBeSuppliedFeeders(workId);
            }
        });

    }

    @Override
    public void showEmptyView() {
        statusLayout.setVisibility(View.VISIBLE);
        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getAllToBeSuppliedFeeders(argument);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getAllToBeSuppliedFeeders(argument);
    }

    @Override
    public void onScanSuccess(String barcode) {
        Log.i(TAG, "onScanSuccess: ");
        Log.i(TAG, "barcode == " + barcode);
        super.onScanSuccess(barcode);
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        try {
            mCurrentMaterial = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, MATERIAL_BLOCK_BARCODE);
            String mCurrentMaterialNumber = mCurrentMaterial.getDeltaMaterialNumber();
            String mCurrentSerialNumber = mCurrentMaterial.getStreamNumber();
            String feederID = getFeederID(mCurrentMaterialNumber, mCurrentSerialNumber);
            String slot = getSlot(mCurrentMaterialNumber, mCurrentSerialNumber);

            Log.i(TAG, "mCurrentMaterialID: " + mCurrentMaterialNumber);
            Log.i(TAG, "mCurrentSerialNumber: " + mCurrentSerialNumber);
            VibratorAndVoiceUtils.correctVibrator(this);
            VibratorAndVoiceUtils.correctVoice(this);
//            adapter.notifyDataSetChanged();

            Map<String, String> map = new HashMap<>();
            map.put("work_order", workId);
            map.put("side", side);
            map.put("material_no", mCurrentMaterialNumber);
            map.put("serial_no", mCurrentSerialNumber);
            map.put("work_order", workId);
            map.put("code", checkBox.isChecked() ? "1" : "0");

            argument = GsonTools.createGsonListString(map);
            Log.i(TAG, "argument== " + argument);

            //上传到MES
            Map<String, String> mapMES = new HashMap<>();
            Map<String, Object> map1 = new HashMap<>();
            Map<String, String> map2 = new HashMap<>();
            map1.put("work_order", workId);
            map1.put("side", side);
            map1.put("mes_mode", checkBox_autoUpLoadToMES.isChecked() ? "1" : "0");
            map1.put("is_feeder_buffer", "1");
            List<Map<String, String>> feeding_list = new ArrayList<>();
            List<Map<String, String>> material_list = new ArrayList<>();
            map2.put("material_no", mCurrentMaterialNumber);
            map2.put("serial_no", mCurrentSerialNumber);
            map2.put("feeder_id", feederID);
            map2.put("slot", slot);
            feeding_list.add(map2);
            map1.put("feeding_list", feeding_list);
            String argument_MES = GsonTools.createGsonListString(map1);

            Log.i(TAG, "argument_MES== " + argument);
            if (isMaterialExists(mCurrentMaterial)) {
                getPresenter().getFeederInsertionToSlotTimeStamp(argument);
                getPresenter().upLoadFeederSupplyToMES(argument_MES);
            } else {
                ToastUtils.showMessage(this, "该料盘不存在，请重新扫描料盘");
            }

        } catch (EntityNotFountException e) {
            VibratorAndVoiceUtils.wrongVibrator(this);
            VibratorAndVoiceUtils.wrongVoice(this);
            Toast.makeText(this, "请扫描料盘", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            VibratorAndVoiceUtils.wrongVibrator(this);
            VibratorAndVoiceUtils.wrongVoice(this);
            Toast.makeText(this, "解析错误,请重新扫描", Toast.LENGTH_SHORT).show();
        }


    }

    private String getFeederID(String mCurrentMaterialNumber, String mCurrentSerialNumber) {
        for (FeederSupplyItem feederSupplyItem : dataSource) {

            if (feederSupplyItem.getSerialNumber().equalsIgnoreCase(mCurrentMaterialNumber) && feederSupplyItem.getMaterialID().equalsIgnoreCase(mCurrentMaterialNumber)) {
                return feederSupplyItem.getFeederID();
            }
        }
        return null;
    }

    private String getSlot(String mCurrentMaterialNumber, String mCurrentSerialNumber) {
        for (FeederSupplyItem feederSupplyItem : dataSource) {

            if (feederSupplyItem.getSerialNumber().equalsIgnoreCase(mCurrentMaterialNumber) && feederSupplyItem.getMaterialID().equalsIgnoreCase(mCurrentMaterialNumber)) {
                return feederSupplyItem.getSlot();
            }
        }
        return null;
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

    private boolean isMaterialExists(MaterialBlockBarCode material) {
        boolean flag = false;
        int length = dataSource.size();
        for (int i = 0; i < length; i++) {
            FeederSupplyItem feederSupplyItem = dataSource.get(i);
            if (material.getDeltaMaterialNumber().equalsIgnoreCase(feederSupplyItem.getMaterialID()) && material.getStreamNumber().equalsIgnoreCase(feederSupplyItem.getSerialNumber())) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    private int getLastMaterialIndex(MaterialBlockBarCode material, List<FeederSupplyItem> dataList) {
        int length = dataList.size();
        for (int i = 0; i < length; i++) {
            FeederSupplyItem item = dataList.get(i);
            if (item.getMaterialID().equalsIgnoreCase(material.getDeltaMaterialNumber()) && item.getSerialNumber().equalsIgnoreCase(material.getStreamNumber())) {
                index = i;
                break;
            }

        }
        return index;
    }


}
