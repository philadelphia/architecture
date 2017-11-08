package com.example.app.ui.feeder.handle.feederSupply;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.DCTimeFormatException;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.example.commonlibs.utils.DialogUtils;
import com.example.commonlibs.utils.GsonTools;
import com.example.commonlibs.utils.RecycleViewUtils;
import com.example.commonlibs.utils.ToastUtils;
import com.example.commonlibs.widget.autolayout.AutoToolbar;
import com.example.commonlibs.widget.statusLayout.StatusLayout;
import com.example.app.Constant;
import com.example.app.R;
import com.example.app.base.BaseActivity;
import com.example.app.common.CommonBaseAdapter;
import com.example.app.common.CommonViewHolder;
import com.example.app.di.component.AppComponent;
import com.example.app.entity.DebitData;
import com.example.app.entity.FeederSupplyItem;
import com.example.app.ui.feeder.handle.feederSupply.di.DaggerFeederSupplyComponent;
import com.example.app.ui.feeder.handle.feederSupply.di.FeederSupplyModule;
import com.example.app.ui.feeder.handle.feederSupply.mvp.FeederSupplyContract;
import com.example.app.ui.feeder.handle.feederSupply.mvp.FeederSupplyPresenter;
import com.example.app.utils.VibratorAndVoiceUtils;
import com.example.commonlibs.widget.CustomPopWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.buletoothio.barcode.parse.BarCodeType.MATERIAL_BLOCK_BARCODE;
import static com.example.app.base.BaseApplication.getContext;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public class FeederSupplyActivity extends BaseActivity<FeederSupplyPresenter> implements FeederSupplyContract.View, View.OnClickListener {
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
//    @BindView(R.id.checkBox)
//    CheckBox checkBox;

    @BindView(R.id.checkBox_autoUpLoadToMES)
    CheckBox checkBox_autoUpLoadToMES;

    //    @BindView(R.id.btn_debitManually)
//    Button btn_debitManually;
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
    private final List<DebitData> unDebitItemList = new ArrayList<>();
    private boolean isAllItemSupplied = false;
    private String argument_MES;
    private Dialog loadingDialog;

    @Override
    protected void handError(String contents) {
        super.handError(contents);
        onGetFeederListFailed(contents);
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
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.VISIBLE);
        toolbar.findViewById(R.id.tv_setting).setClickable(true);
        ((TextView) toolbar.findViewById(R.id.tv_setting)).setText("跳过");
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
//                holder.setText(R.id.tv_timestamp, item.getBindTime());
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

    @OnClick({R.id.tv_setting, R.id.btn_upload})
    public void onClicks(View view) {
        Map<String, String> map = new HashMap<>();
        Map<String, Object> map_argument = new HashMap<>();
        switch (view.getId()) {
            case R.id.tv_setting:
                map.put("work_order", workId);
                map.put("side", side);
                map.put("code", "B");
                getPresenter().resetFeederSupplyStatus(GsonTools.createGsonListString(map));
                map.clear();
                map.put("work_order", workId);
                map.put("side", side);
                getPresenter().jumpMES(GsonTools.createGsonListString(map));
                break;
            case R.id.btn_upload:   //请求待上传到MES的列表
                map_argument.clear();
                map_argument.put("work_order", workId);
                map_argument.put("side", side);
                map_argument.put("is_feeder_buffer", 1);
                if (popUpWindow == null) {
                }
//                getPresenter().getUnUpLoadToMESList(GsonTools.createGsonListString(map_argument));
                break;

            default:
                break;

        }
    }

    @Override
    public void onGetFeederListSuccess(List<FeederSupplyItem> data) {
        Log.i(TAG, "onGetWarningListSuccess: ");
        Log.i(TAG, "后台返回的数据长度是: " + data.size());
        statusLayout.setVisibility(View.VISIBLE);
        dataSource.clear();
        dataSource.addAll(data);
        adapter.notifyDataSetChanged();
        isBeginSupply = isBeginSupply(data);
        if (index != -1) {
            RecycleViewUtils.scrollToMiddle(linearLayoutManager, getLastMaterialIndex(mCurrentMaterial, dataSource), recyclerViewContent);
        }

        if (checkBox_autoUpLoadToMES.isChecked()) {
            if (argument_MES != null) {
                getPresenter().upLoadFeederSupplyToMES(argument_MES);
            }
        }
                                                                                                                                                                  

    }

    @Override
    public void onFeederSupplySuccess(List<FeederSupplyItem> data) {
            Log.i(TAG, "onGetWarningListSuccess: ");
            Log.i(TAG, "后台返回的数据长度是: " + data.size());
            VibratorAndVoiceUtils.correctVibrator(FeederSupplyActivity.this);
            VibratorAndVoiceUtils.correctVoice(FeederSupplyActivity.this);
            statusLayout.setVisibility(View.VISIBLE);
            tvModuleID.setVisibility(View.GONE);
            dataSource.clear();
            dataSource.addAll(data);
            adapter.notifyDataSetChanged();
            isBeginSupply = isBeginSupply(data);
            if (index != -1) {
                RecycleViewUtils.scrollToMiddle(linearLayoutManager, getLastMaterialIndex(mCurrentMaterial, dataSource), recyclerViewContent);
            }

            if (checkBox_autoUpLoadToMES.isChecked()) {
                if (argument_MES != null) {
                    getPresenter().upLoadFeederSupplyToMES(argument_MES);
                }
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
        Log.i(TAG, "showUnDebitedItemList: ");
        if (isAllItemSupplied) {
            popUpWindow.dissmiss();
            getPresenter().resetFeederSupplyStatus(GsonTools.createGsonListString(argument));

        }
        unDebitItemList.clear();
        unDebitItemList.addAll(data);

        Log.i(TAG, "返回列表长度: " + data.size());
        Log.i(TAG, "待扣账列表长度: " + unDebitItemList.size());
        if (popUpWindow != null) {
            unDebitadapter.notifyDataSetChanged();
            popUpWindow.showAsDropDown(toolbar);
        }

    }



    @Override
    public void onGetFeederListFailed(String message) {
        Log.i(TAG, "onGetWarningListFailed: " + message);
        tvModuleID.setVisibility(View.VISIBLE);
        tvModuleID.setText(message);
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
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

        statusLayout.showLoadingView();
    }

    @Override
    public void showContentView() {
        statusLayout.showContentView();
    }

    @Override
    public void showErrorView() {
        statusLayout.showErrorView();
        statusLayout.setErrorClick(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getFeederList(workId);
            }
        });

    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getFeederList(argument);
            }
        });

    }

    @Override
    public void upLoading() {
        loadingDialog = DialogUtils.createProgressDialog(this);
        if (loadingDialog == null){
            loadingDialog = DialogUtils.createProgressDialog(this);
        }
        if (!loadingDialog.isShowing()){
            loadingDialog.show();
        }
    }

    @Override
    public void upLoadFailed(String mMessage) {
        loadingDialog.dismiss();
        ToastUtils.showMessage(this, mMessage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getFeederList(argument);
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

//            adapter.notifyDataSetChanged();

            Map<String, String> map = new HashMap<>();
            map.put("work_order", workId);
            map.put("side", side);
            map.put("material_no", mCurrentMaterialNumber);
            map.put("serial_no", mCurrentSerialNumber);
            map.put("work_order", workId);
//            map.put("code", checkBox.isChecked() ? "1" : "0");

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
            argument_MES = GsonTools.createGsonListString(map1);

            Log.i(TAG, "argument_MES== " + argument);
            if (isMaterialExists(mCurrentMaterial)) {
                tvModuleID.setVisibility(View.VISIBLE);
                tvModuleID.setText("料站：" + slot);
                getPresenter().getFeederInsertionToSlotTimeStamp(argument);

            } else {
                VibratorAndVoiceUtils.wrongVibrator(FeederSupplyActivity.this);
                VibratorAndVoiceUtils.wrongVoice(FeederSupplyActivity.this);
                tvModuleID.setVisibility(View.VISIBLE);
                tvModuleID.setText("该料盘不存在，请重新扫描料盘");
            }

        } catch (DCTimeFormatException e){
            tvModuleID.setText(e.getMessage());
            tvModuleID.setVisibility(View.VISIBLE);
            ToastUtils.showMessage(this, e.getMessage());
            VibratorAndVoiceUtils.wrongVibrator(this);
            VibratorAndVoiceUtils.wrongVoice(this);

        }catch (EntityNotFountException e) {
            VibratorAndVoiceUtils.wrongVibrator(this);
            VibratorAndVoiceUtils.wrongVoice(this);
            tvModuleID.setVisibility(View.VISIBLE);
            tvModuleID.setText("请扫描料盘");
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            VibratorAndVoiceUtils.wrongVibrator(this);
            VibratorAndVoiceUtils.wrongVoice(this);
            tvModuleID.setVisibility(View.VISIBLE);
            tvModuleID.setText("解析错误,请重新扫描");
        }


    }

    private String getFeederID(String mCurrentMaterialNumber, String mCurrentSerialNumber) {
        for (FeederSupplyItem feederSupplyItem : dataSource) {

            if (feederSupplyItem.getSerialNumber().equalsIgnoreCase(mCurrentSerialNumber) && feederSupplyItem.getMaterialID().equalsIgnoreCase(mCurrentMaterialNumber)) {
                return feederSupplyItem.getFeederID();
            }
        }
        return null;
    }

    private String getSlot(String mCurrentMaterialNumber, String mCurrentSerialNumber) {
        for (FeederSupplyItem feederSupplyItem : dataSource) {

            if (feederSupplyItem.getSerialNumber().equalsIgnoreCase(mCurrentSerialNumber) && feederSupplyItem.getMaterialID().equalsIgnoreCase(mCurrentMaterialNumber)) {
                return feederSupplyItem.getSlot();
            }
        }
        return null;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                lightOff();
                finish();
                break;

            case R.id.tv_setting:
                Log.i(TAG, "onOptionsItemSelected: ");

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



    private void setAdapter(RecyclerView rv_debit, CommonBaseAdapter mUndoList_adapter) {
        rv_debit.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        rv_debit.setLayoutManager(linearLayoutManager);
        rv_debit.setAdapter(mUndoList_adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_sheet_back:
                if (popUpWindow != null && popUpWindow.isShowing()) {
                    popUpWindow.dissmiss();
                }
                break;
            case R.id.bt_sheet_confirm:


                break;
            case R.id.bt_sheet_select_all:

                break;
            case R.id.bt_sheet_select_cancel:


                break;

            case R.id.tv_moduleID:
                tvModuleID.setVisibility(View.GONE);
            default:
                break;
        }
    }

    private boolean isAllFeederSupplied(List<FeederSupplyItem> dataList){
        boolean isAllFeederSupplied = false;
        for (FeederSupplyItem feederSupplyItem : dataList) {
            if (feederSupplyItem.getStatus() != 1){
                isAllFeederSupplied = false;
                break;
            }
            else
                isAllFeederSupplied = true;
        }

        return isAllFeederSupplied;
    }


    public void lightOff(){
        Map<String, Object> map = new HashMap<>();
        map.put("work_order", workId);
        map.put("side", side);
        getPresenter().lightOff(GsonTools.createGsonListString(map));
    }


    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        lightOff();
    }

}
