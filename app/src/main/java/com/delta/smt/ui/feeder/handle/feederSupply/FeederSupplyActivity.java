package com.delta.smt.ui.feeder.handle.feederSupply;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.ui.feeder.handle.feederSupply.di.DaggerFeederSupplyComponent;
import com.delta.smt.ui.feeder.handle.feederSupply.di.FeederSupplyModule;
import com.delta.smt.ui.feeder.handle.feederSupply.mvp.FeederSupplyContract;
import com.delta.smt.ui.feeder.handle.feederSupply.mvp.FeederSupplyPresenter;
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
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public class FeederSupplyActivity extends BaseActivity<FeederSupplyPresenter> implements FeederSupplyContract.View {
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
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(R.id.recy_content)
    RecyclerView recyContent;
    @BindView(R.id.hr_scrow)
    HorizontalScrollView hrScrow;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.tv_moduleID)
    TextView tvModuleID;
    private CommonBaseAdapter<FeederSupplyItem> adapter;
    private List<FeederSupplyItem> dataList = new ArrayList<>();
    private List<FeederSupplyItem> dataSource = new ArrayList<>();
    private static final String TAG = "FeederSupplyActivity";
    private boolean isAllHandleOVer = false;
    private String mCurrentSerialNumber;
    private String mCurrentMaterialNumber;
    private int index = -1;
    private String workId;


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
        String side = intent.getStringExtra(Constant.SIDE);
        Log.i(TAG, "workId==: " + workId);
        Log.i(TAG, "side==: " + side);
        Map<String, String> map = new HashMap<>();
        map.put("work_order", workId);
        map.put("side", side);


        String argument = new Gson().toJson(map);
        Log.i(TAG, "argument==: " + argument);
        getPresenter().getAllToBeSuppliedFeeders(argument);
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("备料");
        dataList.add(new FeederSupplyItem(" ", " ", "  ", " ", "", 0));
        CommonBaseAdapter<FeederSupplyItem> adapterTitle = new CommonBaseAdapter<FeederSupplyItem>(getContext(), dataList) {
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
//                holder.setText(R.id.tv_location, item.getPosition());
                holder.setText(R.id.tv_feederID, item.getFeederID());
                holder.setText(R.id.tv_materialID, item.getMaterialID());
                holder.setText(R.id.tv_module, item.getSlot());
                holder.setText(R.id.tv_timestamp, item.getBindTime());
                holder.setText(R.id.tv_status, item.getStatus() == 0 ? "等待上模组" : " 上模组完成");

                if (item.getMaterialID().equalsIgnoreCase(mCurrentMaterialNumber) && item.getSerialNumber().equalsIgnoreCase(mCurrentSerialNumber)) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                    tvModuleID.setText("模组料站: " + item.getSlot());
                    index = position;

                    Log.i(TAG, "对应的item: " + item.toString());
                } else {

                    holder.itemView.setBackgroundColor(Color.WHITE);
                }

            }

            @Override
            protected int getItemViewLayoutId(int position, FeederSupplyItem item) {
                return R.layout.feeder_supply_item;
            }

        };
        recyContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        recyContent.setAdapter(adapter);


    }


    @OnClick({R.id.tv_setting, R.id.btn_upload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_setting:
                break;
            case R.id.btn_upload:
                getPresenter().upLoadToMES();
                break;
        }
    }

    @Override
    public void onSuccess(List<FeederSupplyItem> data) {
        Log.i(TAG, "onSuccess: ");
        Log.i(TAG, "后台返回的数据长度是: " + data.size());

        dataSource.clear();
        dataSource.addAll(data);
        adapter.notifyDataSetChanged();
        recyContent.scrollToPosition(index);
        for (FeederSupplyItem item : dataSource) {
            if (item.getStatus() == 0) {
                isAllHandleOVer = false;
                break;
            } else {
                isAllHandleOVer = true;
            }
        }

        if (isAllHandleOVer) {
            Log.i(TAG, "feeder全部上模组，开始上传结果: ");
//            getPresenter().upLoadToMES();
        }
//        for (int i = 0; i < dataSource.size(); i++) {
//            FeederSupplyItem feederSupplyItem = dataSource.get(i);
//            if (feederSupplyItem.getMaterialID().equalsIgnoreCase(mCurrentMaterialNumber) && feederSupplyItem.getSerialNumber().equalsIgnoreCase(mCurrentSerinalNumber)) {
//                index = i;
//                adapter.notifyDataSetChanged();
//            }
//        }




    }


    @Override
    public void onFailed(String message) {
        Log.i(TAG, "onFailed: " + message);
        ToastUtils.showMessage(this, message, Toast.LENGTH_SHORT);
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
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getAllToBeSuppliedFeeders(workId);
            }
        });

    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getAllToBeSuppliedFeeders(workId);
            }
        });

    }


    @Override
    public void onScanSuccess(String barcode) {
        Log.i(TAG, "onScanSuccess: ");
        Log.i(TAG, "barcode == " + barcode);
        super.onScanSuccess(barcode);
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        try {
            MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, MATERIAL_BLOCK_BARCODE);
            mCurrentMaterialNumber = materialBlockBarCode.getDeltaMaterialNumber();
            mCurrentSerialNumber = materialBlockBarCode.getStreamNumber();

            Log.i(TAG, "mCurrentMaterialID: " + mCurrentMaterialNumber);
            Log.i(TAG, "mCurrentSerialNumber: " + mCurrentSerialNumber);
            VibratorAndVoiceUtils.correctVibrator(this);
            VibratorAndVoiceUtils.correctVoice(this);
            adapter.notifyDataSetChanged();

            Map<String, String> map = new HashMap<>();
            map.put("material_no", mCurrentMaterialNumber);
            map.put("serial_no", mCurrentSerialNumber);
            Gson gson = new Gson();
            String argument = gson.toJson(map);
            Log.i(TAG, "argument== " + argument);
            getPresenter().getFeederInsertionToSlotTimeStamp(argument);

        } catch (EntityNotFountException e) {
            VibratorAndVoiceUtils.wrongVibrator(this);
            VibratorAndVoiceUtils.wrongVoice(this);
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            VibratorAndVoiceUtils.wrongVibrator(this);
            VibratorAndVoiceUtils.wrongVoice(this);
            Toast.makeText(this, "解析错误", Toast.LENGTH_SHORT).show();
        }


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


}
