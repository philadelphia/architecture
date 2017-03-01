package com.delta.smt.ui.smt_module.module_down_details;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.FeederBuffer;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.demacia.barcode.BarCodeIpml;
import com.delta.demacia.barcode.exception.DevicePairedNotFoundException;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.entity.ModuleDownMaintain;
import com.delta.smt.ui.smt_module.module_down_details.di.DaggerModuleDownDetailsComponent;
import com.delta.smt.ui.smt_module.module_down_details.di.ModuleDownDetailsModule;
import com.delta.smt.ui.smt_module.module_down_details.mvp.ModuleDownDetailsContract;
import com.delta.smt.ui.smt_module.module_down_details.mvp.ModuleDownDetailsPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.buletoothio.barcode.parse.BarCodeType.MATERIAL_BLOCK_BARCODE;
import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shufeng.Wu on 2017/1/5.
 */

public class ModuleDownDetailsActivity extends BaseActivity<ModuleDownDetailsPresenter> implements ModuleDownDetailsContract.View, BarCodeIpml.OnScanSuccessListener {

    private static final String TAG = "ModuleDownDetailsActivi";
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(R.id.recy_content)
    RecyclerView recyContent;
    @BindView(R.id.btn_feederMaintain)
    AppCompatButton btnFeederMaintain;
    String workItemID;
    String side;
    String productNameMain;
    String productName;
    String linName;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    private CommonBaseAdapter<ModuleDownDetailsItem.RowsBean> adapterTitle;
    private CommonBaseAdapter<ModuleDownDetailsItem.RowsBean> adapter;
    private List<ModuleDownDetailsItem.RowsBean> dataList = new ArrayList<>();
    private List<ModuleDownDetailsItem.RowsBean> dataSource = new ArrayList<>();
    private String mCurrentWorkOrder;
    //SharedPreferences preferences=null;
    private String mCurrentMaterialID;
    private String mCurrentSerialNumber;
    private String mCurrentQuantity;
    private String mCurrentLocation;
    private String mCurrentSlot;
    private int index = -1;
    //二维码
    private BarCodeIpml barCodeIpml = new BarCodeIpml();
    private LinearLayoutManager linearLayoutManager;
    private int flag = 1;


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleDownDetailsComponent.builder().appComponent(appComponent).moduleDownDetailsModule(new ModuleDownDetailsModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {

        Intent intent = this.getIntent();
        workItemID = intent.getStringExtra(Constant.WORK_ITEM_ID);
        side = intent.getStringExtra(Constant.SIDE);
        linName = intent.getStringExtra(Constant.LINE_NAME);
        productName = intent.getStringExtra(Constant.PRODUCT_NAME);
        productNameMain = intent.getStringExtra(Constant.PRODUCT_NAME_MAIN);

        /*Map<String, String> map = new HashMap<>();
        map.put("work_order", workItemID);
        map.put("side", side);
        Gson gson = new Gson();
        String argument = gson.toJson(map);

        getPresenter().getAllModuleDownDetailsItems(argument);*/
        barCodeIpml.setOnGunKeyPressListener(this);
//        // TODO: 2017/2/10
        mCurrentWorkOrder = workItemID;
    }

    @Override
    protected void initView() {
        //headerTitle.setText("下模组");
        toolbar.setTitle("");
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("下模组");

        dataList.add(new ModuleDownDetailsItem.RowsBean("工单", "面别", "料号", "流水码", "Feeder ID", "模组料站", "归属", "下模组时间"));
        adapterTitle = new CommonBaseAdapter<ModuleDownDetailsItem.RowsBean>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleDownDetailsItem.RowsBean item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                holder.setText(R.id.tv_work_order, item.getWork_order());
                holder.setText(R.id.tv_side, item.getSide());
                holder.setText(R.id.tv_materialID, item.getMaterial_no());
                holder.setText(R.id.tv_serialID, item.getSerial_no());
                holder.setText(R.id.tv_feederID, item.getFeeder_id());
                holder.setText(R.id.tv_moduleMaterialStationID, item.getSlot());
                holder.setText(R.id.tv_ownership, item.getDest());
                holder.setText(R.id.tv_moduleDownTime, item.getUnbind_time());

                if (mCurrentWorkOrder.equals(item.getMaterial_no()) && mCurrentSerialNumber.equalsIgnoreCase(item.getSerial_no())) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE);

                }

            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleDownDetailsItem.RowsBean item) {
                return R.layout.item_module_down_details;
            }
        };
        recyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyTitle.setAdapter(adapterTitle);
        adapter = new CommonBaseAdapter<ModuleDownDetailsItem.RowsBean>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleDownDetailsItem.RowsBean item, int position) {

                holder.itemView.setBackgroundColor(Color.WHITE);
                holder.setText(R.id.tv_work_order, item.getWork_order());
                holder.setText(R.id.tv_side, item.getSide());
                holder.setText(R.id.tv_materialID, item.getMaterial_no());
                holder.setText(R.id.tv_serialID, item.getSerial_no());
                holder.setText(R.id.tv_feederID, item.getFeeder_id());
                holder.setText(R.id.tv_moduleMaterialStationID, item.getSlot());
                if ("0".equals(item.getDest())) {
                    holder.setText(R.id.tv_ownership, "尾数仓");
                } else if ("1".equals(item.getDest())) {
                    holder.setText(R.id.tv_ownership, "Feeder缓存区");
                } else if ("2".equals(item.getDest())) {
                    holder.setText(R.id.tv_ownership, "Feeder维护区");
                } else {
                    holder.setText(R.id.tv_ownership, item.getDest());
                }
                holder.setText(R.id.tv_moduleDownTime, item.getUnbind_time());

                if (item.getMaterial_no().equalsIgnoreCase(mCurrentMaterialID) && item.getSerial_no().equalsIgnoreCase(mCurrentSerialNumber)) {
                    Log.i(TAG, "convert: " + item.toString());
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                    mCurrentSlot = item.getSlot();
                    index = position;
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }
            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleDownDetailsItem.RowsBean item) {
                return R.layout.item_module_down_details;
            }

        };

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        recyContent.setLayoutManager(linearLayoutManager);

        recyContent.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_module_down_details;
    }

    @Override
    public void onSuccess(ModuleDownDetailsItem data) {
        dataSource.clear();
        flag = 1;
        Log.i(TAG, "index: == " + index);
        List<ModuleDownDetailsItem.RowsBean> rowsBean = data.getRows();
        dataSource.addAll(rowsBean);
        Log.i(TAG, "onSuccess: 后台返回的数据长度是" + dataSource.size());
        adapter.notifyDataSetChanged();
        updateFeederMaintainState();
    }

    @Override
    public void onFailed(String message) {
        flag = 1;
        ToastUtils.showMessage(this, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void onSuccessMaintain(ModuleDownMaintain maintain) {
        ToastUtils.showMessage(this, maintain.getMsg());
    }

    @Override
    public void onFailMaintain(ModuleDownMaintain maintain) {
        ToastUtils.showMessage(this, maintain.getMsg());
    }

    @Override
    public void onNetFailed(Throwable throwable) {
        ToastUtils.showMessage(this, throwable.getMessage());
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
        statusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("work_order", workItemID);
                map.put("side", side);
                Gson gson = new Gson();
                String argument = gson.toJson(map);

                getPresenter().getAllModuleDownDetailsItems(argument);
            }
        });
    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("work_order", workItemID);
                map.put("side", side);
                Gson gson = new Gson();
                String argument = gson.toJson(map);

                getPresenter().getAllModuleDownDetailsItems(argument);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            barCodeIpml.hasConnectBarcode();
        } catch (DevicePairedNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<>();
        map.put("work_order", workItemID);
        map.put("side", side);
        Gson gson = new Gson();
        String argument = gson.toJson(map);
        getPresenter().getAllModuleDownDetailsItems(argument);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        barCodeIpml.onComplete();
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (barCodeIpml.isEventFromBarCode(event)) {
            barCodeIpml.analysisKeyEvent(event);
            return true;
        }
        return super.dispatchKeyEvent(event);
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

    @OnClick({R.id.btn_feederMaintain})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_feederMaintain:
                Map<String, String> map = new HashMap<>();
                map.put("work_order", mCurrentWorkOrder);
                map.put("side", side);
                Gson gson = new Gson();
                String argument = gson.toJson(map);
                getPresenter().getAllModuleDownMaintainResult(argument);
                break;
        }
    }

    @Override
    public void onScanSuccess(String barcode) {
        Log.i(TAG, "onScanSuccess: ");
        Log.i(TAG, "barcode == " + barcode);
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (flag) {
            case 1:
                try {
                    MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, MATERIAL_BLOCK_BARCODE);
                    mCurrentMaterialID = materialBlockBarCode.getDeltaMaterialNumber();
                    mCurrentSerialNumber = materialBlockBarCode.getStreamNumber();
                    mCurrentQuantity = materialBlockBarCode.getCount();
                    adapter.notifyDataSetChanged();
                    recyContent.scrollToPosition(index);
                    Log.i(TAG, "mCurrentMaterialID: " + mCurrentMaterialID);
                    Log.i(TAG, "mCurrentSerialNumber: " + mCurrentSerialNumber);
                    Map<String, String> map = new HashMap<>();
                    map.put("work_order", mCurrentWorkOrder);
                    map.put("material_no", materialBlockBarCode.getDeltaMaterialNumber());
                    map.put("serial_no", materialBlockBarCode.getStreamNumber());
                    map.put("side", side);
                    map.put("qty", mCurrentQuantity);
                    map.put("slot", mCurrentSlot);
                    Gson gson = new Gson();
                    String argument = gson.toJson(map);
                    Log.i(TAG, "argument== " + argument);
                    Log.i(TAG, "料盘已经扫描完成，接下来扫描料架: ");
                    if (isMaterialExists(materialBlockBarCode)) {
                        flag = 2;
                    } else {
                        flag = 1;
                        ToastUtils.showMessage(this, "该料盘不存在，请重新扫描料盘");
                    }
                } catch (EntityNotFountException e) {
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    Toast.makeText(this, "请扫描料盘", Toast.LENGTH_SHORT).show();
                    flag = 1;
                } catch (ArrayIndexOutOfBoundsException e) {
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    flag = 1;
                    Toast.makeText(this, "解析错误,请重新扫描料盘", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                try {
                    FeederBuffer frameLocation = (FeederBuffer) barCodeParseIpml.getEntity(barcode, BarCodeType.FEEDER_BUFFER);
                    mCurrentLocation = frameLocation.getSource();
                    Log.i(TAG, "mCurrentLocation: " + frameLocation.toString());
                    Map<String, String> map = new HashMap<>();
                    map.put("work_order", mCurrentWorkOrder);
                    map.put("side", side);
                    map.put("material_no", mCurrentMaterialID);
                    map.put("serial_no", mCurrentSerialNumber);
                    map.put("shelf_no", mCurrentLocation);
                    map.put("qty", mCurrentQuantity);
                    map.put("slot", mCurrentSlot);
                    Gson gson = new Gson();
                    String argument = gson.toJson(map);
                    Log.i(TAG, "argument== " + argument);
                    Log.i(TAG, "料架已经扫描完成，接下来入库: ");

                    getPresenter().getFeederCheckInTime(argument);


                } catch (EntityNotFountException e1) {
                    e1.printStackTrace();
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    Toast.makeText(this, "请扫描架位", Toast.LENGTH_SHORT).show();
                    flag = 2;
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    Toast.makeText(this, "解析错误,请重新扫描架位", Toast.LENGTH_SHORT).show();
                    flag = 2;
                }

                break;
            default:
                break;
        }
    }

    public void updateFeederMaintainState() {
        boolean state = true;
        for (ModuleDownDetailsItem.RowsBean rb : dataSource) {
            if (rb.getDest().equals("1")) {
                state = false;
                break;
            }
        }
        btnFeederMaintain.setEnabled(state);

    }

    public boolean isMaterialExists(MaterialBlockBarCode material) {
        boolean flag = false;
        for (int i = 0; i < dataSource.size(); i++) {
            ModuleDownDetailsItem.RowsBean item = dataSource.get(i);
            if (mCurrentMaterialID.equalsIgnoreCase(item.getMaterial_no()) && mCurrentSerialNumber.equalsIgnoreCase(item.getSerial_no())){
                flag = true;
                break;
            }else {
                flag = false;
            }
        }
//        for (ModuleDownDetailsItem.RowsBean rowsBean : dataSource) {
//            if (mCurrentMaterialID.equalsIgnoreCase(rowsBean.getMaterial_no()) && mCurrentSerialNumber.equalsIgnoreCase(rowsBean.getSerial_no())) {
//                Log.i(TAG, "isMaterialExists: " + rowsBean.toString());
//                flag = true;
//                break;
//            } else {
//                flag = false;
//                break;
//            }
//        }
//        Log.i(TAG, "isMaterialExists: " + flag);
        return  flag;
    }

}