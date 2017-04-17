package com.delta.smt.ui.smt_module.module_down_details;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Created by Shu feng.Wu on 2017/1/5.
 */

public class ModuleDownDetailsActivity extends BaseActivity<ModuleDownDetailsPresenter> implements ModuleDownDetailsContract.View {

    private static final String TAG = "ModuleDownDetailsActivi";
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recy_title)
    RecyclerView recyclerViewTitle;
    @BindView(R.id.recy_content)
    RecyclerView recyclerViewContent;
    @BindView(R.id.btn_feederMaintain)
    AppCompatButton btnFeederMaintain;
    @BindView(R.id.tv_work_order)
    TextView tv_workOrder;
    @BindView(R.id.tv_line_name)
    TextView tv_side;
    @BindView(R.id.tv_line_num)
    TextView tv_line;

    String workItemID;
    String side;
    String productNameMain;
    String productName;
    String lineName;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    private CommonBaseAdapter<ModuleDownDetailsItem.RowsBean> adapter;
    private List<ModuleDownDetailsItem.RowsBean> dataList = new ArrayList<>();
    private List<ModuleDownDetailsItem.RowsBean> dataSource = new ArrayList<>();
    private List<ModuleDownDetailsItem.RowsBean> dataSourceForCheckIn = new ArrayList<>();
    private String mCurrentWorkOrder;
    private String mCurrentMaterialID;
    private String mCurrentSerialNumber;
    private String mCurrentQuantity;

    private String mCurrentSlot;
    private int index = -1;

    private int flag = 1;
    private String argument;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleDownDetailsComponent.builder().appComponent(appComponent).moduleDownDetailsModule(new ModuleDownDetailsModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        Intent intent = this.getIntent();
        workItemID = intent.getStringExtra(Constant.WORK_ITEM_ID);
        side = intent.getStringExtra(Constant.SIDE);
        lineName = intent.getStringExtra(Constant.LINE_NAME);
        productName = intent.getStringExtra(Constant.PRODUCT_NAME);
        productNameMain = intent.getStringExtra(Constant.PRODUCT_NAME_MAIN);
        tv_workOrder.setText(getResources().getString(R.string.WorkID) + ":   " + workItemID);
        tv_line.setText(getResources().getString(R.string.Line) + ":   " + lineName);
        tv_side.setText(getResources().getString(R.string.Side) + ":   " + side);
        Map<String, String> map = new HashMap<>();
        map.put("work_order", workItemID);
        map.put("side", side);
        Gson gson = new Gson();
        argument = gson.toJson(map);


        mCurrentWorkOrder = workItemID;
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        toolbarTitle.setText("下模组");

        dataList.add(new ModuleDownDetailsItem.RowsBean("料号", "流水码", "Feeder ID", "模组料站", "归属"));
        CommonBaseAdapter<ModuleDownDetailsItem.RowsBean> adapterTitle = new CommonBaseAdapter<ModuleDownDetailsItem.RowsBean>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleDownDetailsItem.RowsBean item, int position) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleDownDetailsItem.RowsBean item) {
                return R.layout.item_module_down_details;
            }
        };
        recyclerViewTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTitle.setAdapter(adapterTitle);

        adapter = new CommonBaseAdapter<ModuleDownDetailsItem.RowsBean>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleDownDetailsItem.RowsBean item, int position) {
                holder.itemView.setBackgroundColor(Color.WHITE);
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


                if (item.getMaterial_no().equalsIgnoreCase(mCurrentMaterialID) && item.getSerial_no().equalsIgnoreCase(mCurrentSerialNumber)) {
                    Log.i(TAG, "convert: " + item.toString());
                    Log.i(TAG, "position: " + position);

                    holder.itemView.setBackgroundColor(Color.YELLOW);
                    mCurrentSlot = item.getSlot();

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
        recyclerViewContent.setLayoutManager(linearLayoutManager);
        recyclerViewContent.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_module_down_details;
    }

    @Override
    public void onSuccess(ModuleDownDetailsItem data) {
        dataSource.clear();
        dataSourceForCheckIn.clear();

        flag = 1;
        Log.i(TAG, "index: == " + index);
        List<ModuleDownDetailsItem.RowsBean> rowsBean = data.getRows();
        dataSource.addAll(rowsBean);

        for (ModuleDownDetailsItem.RowsBean bean : dataSource) {
            if (bean.getDest().equalsIgnoreCase("1"))
                dataSourceForCheckIn.add(bean);
        }
        Log.i(TAG, "onSuccess: 后台返回的数据长度是" + dataSource.size());
        Log.i(TAG, "onSuccess: 后台返回的待入库数据长度是" + dataSourceForCheckIn.size());
        adapter.notifyDataSetChanged();
        if (dataSourceForCheckIn.isEmpty()) {
            btnFeederMaintain.setEnabled(true);
        }

    }

    @Override
    public void onFailed(String message) {
        flag = 2;
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
        getPresenter().getAllModuleDownDetailsItems(argument);
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
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (flag) {
            case 1:
                try {
                    parseMaterial(barcode, barCodeParseIpml);
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
                    String mCurrentLocation = frameLocation.getSource();
                    Log.i(TAG, "mCurrentLocation: " + frameLocation.toString());
                    VibratorAndVoiceUtils.correctVibrator(this);
                    VibratorAndVoiceUtils.correctVoice(this);
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
                    try {
                        parseMaterial(barcode, barCodeParseIpml);
                    } catch (EntityNotFountException e) {
                        e.printStackTrace();
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

                }
                break;
            default:
                break;
            }
    }

    private void parseMaterial(String barcode, BarCodeParseIpml barCodeParseIpml) throws EntityNotFountException {
        MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, MATERIAL_BLOCK_BARCODE);
        mCurrentMaterialID = materialBlockBarCode.getDeltaMaterialNumber();
        mCurrentSerialNumber = materialBlockBarCode.getStreamNumber();
        mCurrentQuantity = materialBlockBarCode.getCount();
        getMatchedMaterialIndex(materialBlockBarCode);
        adapter.notifyDataSetChanged();
        RecycleViewUtils.scrollToMiddle(linearLayoutManager,index,recyclerViewContent);

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
            if (!dataSourceForCheckIn.isEmpty()) {
                if (isMaterialInFeederCheckInList(materialBlockBarCode)) {
                    VibratorAndVoiceUtils.correctVibrator(this);
                    VibratorAndVoiceUtils.correctVoice(this);
                } else {
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    ToastUtils.showMessage(this, "请先扫描待入库的料盘");
                }
            }
                flag = 2;


        } else {
            flag = 1;
            VibratorAndVoiceUtils.wrongVibrator(this);
            VibratorAndVoiceUtils.wrongVoice(this);
            ToastUtils.showMessage(this, "该料盘不存在此工单，请重新扫描料盘或检查料盘二维码是否损坏");
        }
    }

    public int getMatchedMaterialIndex(MaterialBlockBarCode material) {
        int length = dataSource.size();

        for (int i = 0; i < length; i++) {
            ModuleDownDetailsItem.RowsBean rowsBean = dataSource.get(i);
            if (rowsBean.getMaterial_no().equalsIgnoreCase(material.getDeltaMaterialNumber()) && rowsBean.getSerial_no().equalsIgnoreCase(material.getStreamNumber())) {
                index = i;
                break;
            }
        }
        Log.i(TAG, "getMatchedMaterialIndex: " + index);

        return index;
    }

    public boolean isMaterialExists(MaterialBlockBarCode material) {
        boolean flag = false;
        for (int i = 0; i < dataSource.size(); i++) {
            ModuleDownDetailsItem.RowsBean item = dataSource.get(i);
            if (material.getDeltaMaterialNumber().equalsIgnoreCase(item.getMaterial_no()) && material.getStreamNumber().equalsIgnoreCase(item.getSerial_no())) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }

        return flag;
    }

    public boolean isMaterialInFeederCheckInList(MaterialBlockBarCode material) {
        boolean flag = false;
        for (int i = 0; i < dataSourceForCheckIn.size(); i++) {
            ModuleDownDetailsItem.RowsBean item = dataSourceForCheckIn.get(i);
            if (material.getDeltaMaterialNumber().equalsIgnoreCase(item.getMaterial_no()) && material.getStreamNumber().equalsIgnoreCase(item.getSerial_no())) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }

        return flag;
    }


}