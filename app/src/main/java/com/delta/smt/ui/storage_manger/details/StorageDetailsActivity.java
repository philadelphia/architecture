package com.delta.smt.ui.storage_manger.details;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.BackupMaterialCar;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.DialogUtils;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.SingleClick;
import com.delta.commonlibs.utils.SpUtil;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.BindPrepCarIDByWorkOrderResult;
import com.delta.smt.entity.IssureToWarehBody;
import com.delta.smt.entity.MaterialCar;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.StorageDetails;
import com.delta.smt.ui.storage_manger.details.di.DaggerStorageDetailsComponent;
import com.delta.smt.ui.storage_manger.details.di.StorageDetailsModule;
import com.delta.smt.ui.storage_manger.details.mvp.StorageDetailsContract;
import com.delta.smt.ui.storage_manger.details.mvp.StorageDetailsPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;
import com.delta.smt.utils.ViewUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

public class StorageDetailsActivity extends BaseActivity<StorageDetailsPresenter> implements StorageDetailsContract.View {

    @BindView(R.id.recy_title)
    RecyclerView mRecyTitle;
    @BindView(R.id.recy_contetn)
    RecyclerView mRecyContetn;
    @BindView(R.id.hr_scrow)
    HorizontalScrollView mHrScrow;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar mToolbar;
    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.textView2)
    TextView mTextView2;
    @BindView(R.id.tv_hint)
    TextView tv_hint;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    @BindView(R.id.btn_switch)
    CheckBox btnSwitch;
    private List<StorageDetails> dataList = new ArrayList();
    private List<StorageDetails> dataList2 = new ArrayList();
    private List<StorageDetails> undebitDataList = new ArrayList<>();
    private CommonBaseAdapter<StorageDetails> adapter;
    private CommonBaseAdapter<StorageDetails> adapter2;
    private BarCodeParseIpml barCodeImp;
    private String work_order;
    private String part;
    private MaterialBlockBarCode materialblockbarcode;
    private String currentDeltaMaterialNumber = "";
    private int currentPostion = -1;
    private String side;
    private boolean isHaveIssureOver;
    private boolean ischecked = true;
    private boolean isOver;
    private String unSendingMessage;
    private BottomSheetDialog bottomSheetDialog;
    private CommonBaseAdapter<StorageDetails> undoList_adapter;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerStorageDetailsComponent.builder().appComponent(appComponent).storageDetailsModule(new StorageDetailsModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {
        barCodeImp = new BarCodeParseIpml();
        work_order = getIntent().getStringExtra(Constant.WORK_ORDER);
        part = getIntent().getStringExtra(Constant.WARE_HOUSE_NAME);
        side = getIntent().getStringExtra(Constant.SIDE);
        Map<String, String> mMap = new HashMap<>();
        mMap.put("part", part);
        mMap.put("work_order", work_order);
        mMap.put("side", side);
        Gson mGson = new Gson();
        String mS = mGson.toJson(mMap);
        Log.i("aaa", mS);
        getPresenter().getStorageDetails(mS);
        getPresenter().queryMaterailCar(mS);
        ischecked = SpUtil.getBooleanSF(this, part + "checked");

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        Log.e(TAG, "onConfigurationChanged: " + newConfig.toString());
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText("仓库" + part);
        dataList.add(new StorageDetails("", "", 0, 0));
        btnSwitch.setChecked(ischecked);
        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SpUtil.SetBooleanSF(StorageDetailsActivity.this, part + "checked", b);
            }
        });
        adapter = new CommonBaseAdapter<StorageDetails>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, StorageDetails item, int position) {
                holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, StorageDetails item) {
                return R.layout.details_item;
            }
        };
        mRecyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyTitle.setAdapter(adapter);


        adapter2 = new CommonBaseAdapter<StorageDetails>(getContext(), dataList2) {
            @Override
            protected void convert(CommonViewHolder holder, StorageDetails item, int position) {
                holder.setText(R.id.tv_number, item.getMaterial_no());
                holder.setText(R.id.tv_location, item.getShelf_no());
                holder.setText(R.id.tv_needNumber, String.valueOf(item.getAmount()));
                holder.setText(R.id.tv_shipments, String.valueOf(item.getIssue_amount()));
                switch (item.getStatus()) {
                    case 0:
                        holder.itemView.setBackgroundColor(Color.YELLOW);
                        break;
                    case 1:
                        holder.itemView.setBackgroundColor(Color.WHITE);
                        break;
                    case 2:
                        holder.itemView.setBackgroundColor(Color.GREEN);
                        break;
                    case 3:
                        holder.itemView.setBackgroundColor(Color.RED);
                        break;
                }
            }

            @Override
            protected int getItemViewLayoutId(int position, StorageDetails item) {
                return R.layout.details_item;
            }

        };
        mRecyContetn.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mRecyContetn.setAdapter(adapter2);
        createBottomSheetDialog();

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_storage_details;
    }

    @Override
    public void getSucess(Result<StorageDetails> storageDetailses) {
        dataList2.clear();
        dataList2.addAll(storageDetailses.getRows());
        adapter2.notifyDataSetChanged();
    }

    @Override
    public void getFailed(String message) {
//        Log.e(TAG, "getFailed: "+message);
        tv_hint.setText(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        adapter2.notifyDataSetChanged();
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void bindMaterialCarSucess(List<BindPrepCarIDByWorkOrderResult.RowsBean> data) {
        //绑定料车成功状态2
        state = 2;
        if (data.size() != 0) {
            mTextView2.setText(data.get(0).getCar_name());
            tv_hint.setText("绑定备料车" + data.get(0).getCar_name());
        }

    }


    @Override
    public void issureToWarehSuccess(Result<StorageDetails> rows) {
        issureToWareh(rows);

    }

    private void createBottomSheetDialog() {

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_sheet, null);
        undoList_adapter = new CommonBaseAdapter<StorageDetails>(getContext(), undebitDataList) {
            @Override
            protected void convert(CommonViewHolder holder, StorageDetails item, int position) {
                holder.setText(R.id.tv_number, item.getMaterial_no());
                holder.setText(R.id.tv_location, item.getShelf_no());
                holder.setText(R.id.tv_needNumber, String.valueOf(item.getAmount()));
                holder.setText(R.id.tv_shipments, String.valueOf(item.getIssue_amount()));
                switch (item.getStatus()) {
                    case 0:
                        holder.itemView.setBackgroundColor(Color.YELLOW);
                        break;
                    case 1:
                        holder.itemView.setBackgroundColor(Color.WHITE);
                        break;
                    case 2:
                        holder.itemView.setBackgroundColor(Color.GREEN);
                        break;
                    case 3:
                        holder.itemView.setBackgroundColor(Color.RED);
                        break;
                }
            }

            @Override
            protected int getItemViewLayoutId(int position, StorageDetails item) {
                return R.layout.details_item;
            }

        };
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        RecyclerView rv_title = ViewUtils.findView(this, R.id.rv_sheet_title);
        mRecyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyTitle.setAdapter(adapter);
        RecyclerView mRecycleView = ViewUtils.findView(this,R.id.rv_sheet);
        mRecycleView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.setAdapter(undoList_adapter);
        //从bottomSheetDialog拿到behavior
        final BottomSheetBehavior<View> mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet));
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    bottomSheetDialog.dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void issureToWareh(Result<StorageDetails> rows) {
        undebitDataList.clear();
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("还有未发完的料，是否还要继续扣账？\n\n");
        isOver = true;
        isHaveIssureOver = false;
        dataList2.clear();
        dataList2.addAll(rows.getRows());
        int position = 0;
        boolean isFirstUndo = true;
        for (int i = 0; i < dataList2.size(); i++) {
            if (dataList2.get(i).getStatus() == 1) {
                if (isFirstUndo) {
                    position = i;
                    isFirstUndo = false;
                }
            }
            if (dataList2.get(i).getStatus() == 2) {

                isHaveIssureOver = true;
            } else {
                undebitDataList.add(dataList.get(i));
                stringbuffer.append("料号：" + dataList2.get(i).getMaterial_no() + "--架位：" + dataList2.get(i).getShelf_no() + "--料站：" + dataList2.get(i).getSlot() + "\n");
                isOver = false;
            }
        }

        if (btnSwitch.isChecked()) {
            getPresenter().deduction();
        }
        if (isOver) {
            getPresenter().issureToWarehFinish();
        }
        unSendingMessage = stringbuffer.toString();
        tv_hint.setText(rows.getMessage());
        mRecyContetn.scrollToPosition(position);
        adapter2.notifyDataSetChanged();
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
    }

    @Override
    public void issureToWarehFinishSuccess(String msg) {

        ToastUtils.showMessage(this, "扣帐结果" + msg);
        tv_hint.setText("扣账成功");
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
    }

    @Override
    public void queryMaterailCar(List<MaterialCar.RowsBean> rows) {
        if (rows.size() != 0) {
            mTextView2.setText(rows.get(0).getCar_name());
            tv_hint.setText(rows.get(0).getCar_name());
        }
        state = 2;

    }

    @Override
    public void queryMaterailCarFailed(String msg) {
        ToastUtils.showMessage(this, msg);
        tv_hint.setText(msg);
        state = 1;

    }

    @Override
    public void bindMaterialCarFailed(String msg) {
        state = 1;
        mTextView2.setText("无");
        tv_hint.setText(msg);
        ToastUtils.showMessage(this, msg);
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void jumpMaterialsSuccess(Result<StorageDetails> result) {
        issureToWareh(result);
    }

    @Override
    public void jumpMaterialsFailed(String message) {
        tv_hint.setText(message);
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void issureToWarehFailedWithoutJumpMaterials(String message) {

        state = 2;
        ToastUtils.showMessage(this, message);

        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void issureToWarehFailedWithjumpMaterials(String message) {
        state = 2;
        ToastUtils.showMessage(this, message);

        DialogUtils.showConfirmDialog(this, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getPresenter().jumpMaterials();
            }
        });
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void issureToWarehFinishFaildSure(String message) {
        state = 2;
        ToastUtils.showMessage(this, message);

        DialogUtils.showConfirmDialog(this, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getPresenter().sureCompleteIssue();
            }
        });
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void issureToWarehFinishFailedWithoutSure(String message) {
        state = 2;
        ToastUtils.showMessage(this, message);

        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void sureCompleteIssueSucess(String message) {
        ToastUtils.showMessage(this, message);
        tv_hint.setText(message);
    }

    @Override
    public void sureCompleteIssueFailed(String message) {
        ToastUtils.showMessage(this, message);

        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
        DialogUtils.showCommonDialog(this, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getPresenter().sureCompleteIssue();
            }
        });
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
    }

    @Override
    public void showEmptyView() {

        statusLayout.showEmptyView();
    }

    @Override
    public void deductionFailed(String message) {
        tv_hint.setText("扣账失败");
    }

    @Override
    public void deductionSuccess() {

        tv_hint.setText("扣账成功");

    }

    int state = 1;

    @Override
    public void onScanSuccess(String barcode) {

        switch (state) {
            case 1:
                Log.e(TAG, "onScanSuccess: " + state);
                BackupMaterialCar car = null;
                try {
                    car = ((BackupMaterialCar) barCodeImp.getEntity(barcode, BarCodeType.BACKUP_MATERIAL_CAR));
                    mTextView2.setText(car.getSource());
                    Map<String, String> maps = new HashMap<>();
                    maps.put("work_order", work_order);
                    maps.put("part", part);
                    maps.put("side", side);
                    maps.put("pre_car", car.getSource());
                    getPresenter().bindBoundPrepCar(GsonTools.createGsonString(maps));
                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                    state = 1;
                    ToastUtils.showMessage(this, "请扫描料车");
                    tv_hint.setText("请扫描料车");
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                }
                break;
            case 2:
                Log.e(TAG, "onScanSuccess: " + state);
                //扫描料盘
                try {
                    materialblockbarcode = (MaterialBlockBarCode) barCodeImp.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                    IssureToWarehBody issureToWarehBody = new IssureToWarehBody();
                    issureToWarehBody.setMaterial_no(materialblockbarcode.getDeltaMaterialNumber());
                    issureToWarehBody.setSerial_no(materialblockbarcode.getStreamNumber());
                    issureToWarehBody.setUnit(materialblockbarcode.getUnit());
                    issureToWarehBody.setDc(materialblockbarcode.getDC());
                    issureToWarehBody.setLc(materialblockbarcode.getDC());
                    issureToWarehBody.setVendor(materialblockbarcode.getDC());
                    issureToWarehBody.setVendor(materialblockbarcode.getVendor());
                    issureToWarehBody.setTrasaction_code(materialblockbarcode.getBusinessCode());
                    issureToWarehBody.setPo(materialblockbarcode.getPO());
                    issureToWarehBody.setQuantity(materialblockbarcode.getCount());
                    currentDeltaMaterialNumber = materialblockbarcode.getDeltaMaterialNumber();
                    getPresenter().issureToWareh(GsonTools.createGsonString(issureToWarehBody));

                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                    ToastUtils.showMessage(this, "请扫描对应价位的料盘");
                    tv_hint.setText("请扫描对应价位的料盘");
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                }
                state = 2;
                break;
            default:
                break;
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


    @OnClick(R.id.button2)
    public void onClick() {


        if (isHaveIssureOver == false) {
            ToastUtils.showMessage(this, getString(R.string.unfinished_station));
            tv_hint.setText(getString(R.string.unfinished_station));
            return;
        }

        if (SingleClick.isSingle(1000)) {
            if (isOver == false) {

                if (bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                } else {
                    undoList_adapter.notifyDataSetChanged();
                    bottomSheetDialog.show();
                }
//                DialogUtils.showConfirmDialog(this, unSendingMessage, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        getPresenter().deduction();
//                    }
//                });
            }

        }
    }


}
