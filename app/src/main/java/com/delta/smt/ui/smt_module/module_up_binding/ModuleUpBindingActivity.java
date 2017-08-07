package com.delta.smt.ui.smt_module.module_up_binding;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.Feeder;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.DialogUtils;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.RecycleViewUtils;
import com.delta.commonlibs.utils.SingleClick;
import com.delta.commonlibs.utils.SpUtil;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.utils.ViewUtils;
import com.delta.commonlibs.widget.CustomPopWindow;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.common.MESAdapter;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.UpLoadEntity;
import com.delta.smt.entity.UploadMESParams;
import com.delta.smt.ui.smt_module.module_up_binding.di.DaggerModuleUpBindingComponent;
import com.delta.smt.ui.smt_module.module_up_binding.di.ModuleUpBindingModule;
import com.delta.smt.ui.smt_module.module_up_binding.mvp.ModuleUpBindingContract;
import com.delta.smt.ui.smt_module.module_up_binding.mvp.ModuleUpBindingPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.commonlibs.utils.SpUtil.getBooleanSF;
import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class ModuleUpBindingActivity extends BaseActivity<ModuleUpBindingPresenter> implements ModuleUpBindingContract.View, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.rl)
    LinearLayout mRl;
    private boolean isAutomaticUpload = false;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recy_title)
    RecyclerView recyclerViewTitle;
    @BindView(R.id.recy_content)
    RecyclerView recyclerViewContent;

    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    FrameLayout frameLayout;
    @BindView(R.id.automatic_upload)
    AppCompatCheckBox ckb_automaticUpload;
    @BindView(R.id.btn_upload)
    AppCompatButton btn_upLoad_mes;


    @BindView(R.id.tv_work_order)
    TextView tv_workOrder;
    @BindView(R.id.tv_line_num)
    TextView tv_side;
    @BindView(R.id.tv_line_name)
    TextView tv_line;

    private int state = 1;
    @BindView(R.id.showMessage)
    TextView showMessage;
    private CommonBaseAdapter<ModuleUpBindingItem> adapterTitle;
    private CommonBaseAdapter<ModuleUpBindingItem> adapter;
    private final List<ModuleUpBindingItem> dataList = new ArrayList<>();
    private final List<ModuleUpBindingItem> dataSource = new ArrayList<>();
    private int scan_position = -1;
    private String workItemID;
    private String side;

    private String lineName;
    private String materialBlockNumber;
    private String serialNo;
    private String argument;
    private static final String TAG = "ModuleUpBindingActivity";
    private LinearLayoutManager linearLayoutManager;
    private String quantaty;
    private CustomPopWindow mCustomPopWindow;
    private List<UpLoadEntity.FeedingListBean> mFeedingListBean = new ArrayList<>();
    private List<UpLoadEntity.MaterialListBean> mMaterialListBean = new ArrayList<>();
    private UploadMESParams mUploadMESParamsA;
    private boolean isAllItemBound = false;
    private MESAdapter mesAdapter;
    private Dialog loadingDialog;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerModuleUpBindingComponent.builder().appComponent(appComponent).moduleUpBindingModule(new ModuleUpBindingModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        ckb_automaticUpload.setOnCheckedChangeListener(this);
        isAutomaticUpload = getBooleanSF(ModuleUpBindingActivity.this, "module_up_automatic_upload");
        Intent intent = ModuleUpBindingActivity.this.getIntent();
        workItemID = intent.getStringExtra(Constant.WORK_ITEM_ID);
        side = intent.getStringExtra(Constant.SIDE);
        lineName = intent.getStringExtra(Constant.LINE_NAME);
//        productName = intent.getStringExtra(Constant.PRODUCT_NAME);
//        productNameMain = intent.getStringExtra(Constant.PRODUCT_NAME_MAIN);
        tv_workOrder.setText(getResources().getString(R.string.WorkID) + ":   " + workItemID);
        tv_line.setText(getResources().getString(R.string.Line) + ":   " + lineName);
        tv_side.setText(getResources().getString(R.string.Side) + ":   " + side);
        Map<String, String> map = new HashMap<>();
        map.put("work_order", workItemID);
        map.put("side", side);

        argument = GsonTools.createGsonListString(map);
    }

    private void jumpOver() {
        mTvSetting.setText("跳过");
        mTvSetting.setVisibility(View.VISIBLE);
        mTvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> mMap = new HashMap<>();

                mMap.put("work_order", workItemID);
                mMap.put("side", side);
                getPresenter().jumpOver(GsonTools.createGsonListString(mMap));
            }
        });
    }

    @Override
    protected void initView() {
        isAutomaticUpload = SpUtil.getBooleanSF(this, "module_up_automatic_upload");
        ckb_automaticUpload.setChecked(isAutomaticUpload);

        toolbar.setTitle("");
        toolbar.findViewById(R.id.tv_setting).setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        toolbarTitle.setText("上模组");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        jumpOver();
        dataList.add(new ModuleUpBindingItem());
        adapterTitle = new CommonBaseAdapter<ModuleUpBindingItem>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleUpBindingItem item, int position) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleUpBindingItem item) {
                return R.layout.item_module_up_binding;
            }
        };
        recyclerViewTitle.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTitle.setAdapter(adapterTitle);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerViewContent.setLayoutManager(linearLayoutManager);
        adapter = new CommonBaseAdapter<ModuleUpBindingItem>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, ModuleUpBindingItem item, int position) {
                if (scan_position == -1) {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                } else if (scan_position == position) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }
                holder.setText(R.id.tv_materialID, item.getMaterial_no());
                holder.setText(R.id.tv_feederID, item.getFeeder_id());
                holder.setText(R.id.tv_moduleMaterialStationID, item.getSlot());

                if (item.getStatus() == 1) {
                    holder.itemView.setBackgroundColor(Color.GREEN);
                }

            }

            @Override
            protected int getItemViewLayoutId(int position, ModuleUpBindingItem item) {
                return R.layout.item_module_up_binding;
            }

        };

        recyclerViewContent.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_module_up_binding;
    }

    @Override
    public void onSuccess(List<ModuleUpBindingItem> data) {
        state = 1;
        scan_position = -1;
        dataSource.clear();
        dataSource.addAll(data);
        if (dataSource.size() == 0) {
            btn_upLoad_mes.setClickable(false);
        }
        adapter.notifyDataSetChanged();
    }

    private boolean isAllItemIsBound(List<ModuleUpBindingItem> data) {
        isAllItemBound = false;
        int size = data.size();
        for (int i = 0; i < size; i++) {
            ModuleUpBindingItem item = data.get(i);
            if (TextUtils.isEmpty(item.getFeeder_id())) {
                isAllItemBound = false;
                break;
            }
            isAllItemBound = true;
        }
        return isAllItemBound;
    }

    @Override
    public void onFailed(String message) {
        ToastUtils.showMessage(this, message);
        VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
        VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
    }

    @Override
    public void onNetFailed(Throwable throwable) {
//        ToastUtils.showMessage(this, throwable.getMessage());
    }

    @SuppressWarnings("all")
    @Override
    public void onSuccessBinding(List<ModuleUpBindingItem> list) {
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
        dataSource.clear();
        dataSource.addAll(list);
        scan_position = -1;
        adapter.notifyDataSetChanged();
        state = 1;
        if (isAllItemIsBound(list)) {
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("工单" + workItemID + "上模组完成！")
                    .setCancelable(false)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ModuleUpBindingActivity.this.finish();
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
        if (ckb_automaticUpload.isChecked() && mUploadMESParamsA != null) {
            getPresenter().upLoadToMESManually(GsonTools.createGsonListString(mUploadMESParamsA));
        }

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
                getPresenter().getAllModuleUpBindingItems(argument);
            }
        });
    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getAllModuleUpBindingItems(argument);
            }
        });
    }

    @Override
    public void showMessage(String message) {
        ToastUtils.showMessage(this, message);
    }

    @Override
    public void getNeedUpLoadToMESMaterialsSuccess(UpLoadEntity mT) {

        mFeedingListBean.clear();
        mMaterialListBean.clear();
        if (mT.getFeeding_list() == null && mT.getMaterial_list() == null) {
            ToastUtils.showMessage(this, "没有需要上传到MES列表");
            if (mCustomPopWindow != null && mCustomPopWindow.isShowing()) {
                mCustomPopWindow.dissmiss();
            }
            return;
        }
        if (mT.getFeeding_list().size() == 0 && mT.getMaterial_list().size() == 0) {
            ToastUtils.showMessage(this, "没有需要上传到MES列表");
            if (mCustomPopWindow != null && mCustomPopWindow.isShowing()) {
                mCustomPopWindow.dissmiss();
            }
            return;
        }
        if (mT.getFeeding_list() == null || mT.getFeeding_list().size() == 0) {

            UploadMESParams mUploadMESParams = new UploadMESParams();
            mUploadMESParams.setSide(side);
            mUploadMESParams.setIs_feeder_buffer("0");
            mUploadMESParams.setMes_mode("0");
            mUploadMESParams.setWork_order(workItemID);
            mUploadMESParams.setFeeding_list(mFeedingListBean);
            if (mT.getMaterial_list() != null) {
                mUploadMESParams.setMaterial_list(mT.getMaterial_list());
            }
            getPresenter().upLoadToMESManually(GsonTools.createGsonListString(mUploadMESParams));
            return;
        }

        if (mT.getFeeding_list() != null) {
            mFeedingListBean.addAll(mT.getFeeding_list());
        }

        if (mT.getMaterial_list() != null) {
            mMaterialListBean.addAll(mT.getMaterial_list());
        }

        mesAdapter.notifyDataSetChanged();

        if (mCustomPopWindow != null) {
            mCustomPopWindow.showAsDropDown(toolbar);
        }

    }

    @Override
    public void getNeedUpLoadTOMESMaterialsFailed(String mMsg) {
        ToastUtils.showMessage(this, mMsg);
    }

    @Override
    public void uploadSuccess(String mMessage) {
        loadingDialog.dismiss();
        Map<String, Object> map = new HashMap<>();
        map.put("work_order", workItemID);
        map.put("side", side);
        map.put("is_feeder_buffer", 0);
        String argument = GsonTools.createGsonListString(map);
        getPresenter().getneeduploadtomesmaterials(argument);
    }

    @Override
    public void upLoadFailed(String mMessage) {
        loadingDialog.dismiss();
        ToastUtils.showMessage(this, mMessage);
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


    @OnClick({R.id.btn_upload, R.id.showMessage})
    public void onClicks(View view) {
        Map<String, Object> map = new HashMap<>();
        switch (view.getId()) {
            case R.id.btn_upload:
                map.put("work_order", workItemID);
                map.put("side", side);
                map.put("is_feeder_buffer", 0);
                String argument = GsonTools.createGsonListString(map);
                //
                if (mCustomPopWindow == null) {
                    createCustomPopWindow();

                }
                if (SingleClick.isSingle(1000)) {
                    getPresenter().getneeduploadtomesmaterials(argument);
                }

                break;
            case R.id.showMessage:
                showMessage.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_sheet_back:
                if (mCustomPopWindow != null && mCustomPopWindow.isShowing()) {
                    mCustomPopWindow.dissmiss();
                }
                break;
            case R.id.bt_sheet_confirm:
                List<UpLoadEntity.FeedingListBean> mFeedingListBeans = new ArrayList<>();
                List<UpLoadEntity.MaterialListBean> mMaterialListBeans = new ArrayList<>();
                if (mFeedingListBean.size() != 0) {
                    for (UpLoadEntity.FeedingListBean mListBean : mFeedingListBean) {
                        if (mListBean.isChecked()) {
                            mFeedingListBeans.add(mListBean);
                        }
                    }

                }

//                if (mMaterialListBean.size() != 0) {
//                    for (UpLoadEntity.MaterialListBean mListBean : mMaterialListBean) {
//                        if (mListBean.isChecked()) {
//                            mMaterialListBeans.add(mListBean);
//                        }
//                    }
//
//                }

                if (mFeedingListBeans.size() == 0) {
                    ToastUtils.showMessage(this, "请选择上料列表！");
                    return;
                }

                UploadMESParams mUploadMESParams = new UploadMESParams();
                mUploadMESParams.setSide(side);
                mUploadMESParams.setWork_order(workItemID);
                mUploadMESParams.setIs_feeder_buffer("0");
                mUploadMESParams.setMes_mode("0");
                mUploadMESParams.setFeeding_list(mFeedingListBeans);
//                mUploadMESParams.setMaterial_list(mMaterialListBeans);
                getPresenter().upLoadToMESManually(GsonTools.createGsonListString(mUploadMESParams));

                break;
            case R.id.bt_sheet_select_all:
                if (mCustomPopWindow != null && mCustomPopWindow.isShowing()) {
                    if (mFeedingListBean != null && mFeedingListBean.size() != 0) {
                        for (UpLoadEntity.FeedingListBean mListBean : mFeedingListBean) {
                            mListBean.setChecked(true);
                        }
                        mesAdapter.notifyDataSetChanged();
                    }

                    //不上传发料列表
//                    if (mMaterialListBean != null && mMaterialListBean.size() != 0) {
//                        for (UpLoadEntity.MaterialListBean materialListBean : mMaterialListBean) {
//                            materialListBean.setChecked(true);
//                        }
//                        unSend_adapter.notifyDataSetChanged();
//                    }
                }
                break;
            case R.id.bt_sheet_select_cancel:
                if (mCustomPopWindow != null && mCustomPopWindow.isShowing()) {
                    if (mFeedingListBean != null && mFeedingListBean.size() != 0) {
                        for (UpLoadEntity.FeedingListBean mListBean : mFeedingListBean) {
                            mListBean.setChecked(false);
                        }
                       mesAdapter.notifyDataSetChanged();
                    }
                }
//                if (mMaterialListBean != null && mMaterialListBean.size() != 0) {
//                    for (UpLoadEntity.MaterialListBean materialListBean : mMaterialListBean) {
//                        materialListBean.setChecked(false);
//                    }
//                    unSend_adapter.notifyDataSetChanged();
//                }

                break;
            default:
                break;
        }
    }

    private void createCustomPopWindow() {
        mCustomPopWindow = CustomPopWindow.builder().with(this).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).setAnimationStyle(R.style.popupAnimalStyle).setView(R.layout.dialog_upload_mes).build();
        View mContentView = mCustomPopWindow.getContentView();
        RecyclerView recyclerView = ViewUtils.findView(mContentView, R.id.recyclerView);

        Button bt_cancel = ViewUtils.findView(mContentView, R.id.bt_sheet_back);
        Button bt_confirm = ViewUtils.findView(mContentView, R.id.bt_sheet_confirm);
        Button bt_select_all = ViewUtils.findView(mContentView, R.id.bt_sheet_select_all);
        ViewUtils.findView(mContentView, R.id.bt_sheet_select_cancel).setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
        bt_confirm.setOnClickListener(this);
        bt_select_all.setOnClickListener(this);

        mesAdapter = new MESAdapter(ModuleUpBindingActivity.this, mFeedingListBean, mMaterialListBean );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mesAdapter);
    }

    private void setAdapter(RecyclerView rv_debit, CommonBaseAdapter mUndoList_adapter) {
        rv_debit.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
//                return super.canScrollVertically();
                return false;
            }
        };
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        rv_debit.setLayoutManager(linearLayoutManager);
        rv_debit.setAdapter(mUndoList_adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getAllModuleUpBindingItems(argument);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressWarnings("all")
    @Override
    public void onScanSuccess(String barcode) {
        showMessage.setVisibility(View.GONE);
        Log.i(TAG, "onScanSuccess: " + barcode);
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (state) {
            //扫描料盘
            case 1:
                try {
                    parseMaterial(barcode, barCodeParseIpml);

                } catch (EntityNotFountException e) {
                    VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
                    VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
                    showMessage.setVisibility(View.VISIBLE);
                    showMessage.setText("请先扫描料盘码！");
                    state = 1;
                } catch (ArrayIndexOutOfBoundsException e) {
                    VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
                    VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
                    showMessage.setVisibility(View.VISIBLE);
                    showMessage.setText("请先扫描料盘码！");
                    state = 1;
                }
                break;
            //扫描Feeder架位
            case 2:
                try {
                    Feeder feederCode = (Feeder) barCodeParseIpml.getEntity(barcode, BarCodeType.FEEDER);
                    showMessage.setVisibility(View.GONE);
                    JsonArray jsonArray = new JsonArray();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("work_order", workItemID);
                    jsonObject.addProperty("material_no", materialBlockNumber);
                    jsonObject.addProperty("feeder_id", barcode);
                    jsonObject.addProperty("serial_no", serialNo);
                    jsonObject.addProperty("side", side);
                    jsonObject.addProperty("qty", quantaty);
                    jsonObject.addProperty("code", isAutomaticUpload ? "1" : "0");
                    jsonObject.addProperty("is_feeder_buffer", "0");
                    jsonArray.add(jsonObject);
                    String argument = jsonArray.toString();
                    Log.i(TAG, "argument==  " + argument);
                    getPresenter().getMaterialAndFeederBindingResult(argument);
                    mUploadMESParamsA = new UploadMESParams();
                    mUploadMESParamsA.setSide(side);
                    mUploadMESParamsA.setWork_order(workItemID);
                    mUploadMESParamsA.setIs_feeder_buffer("0");
                    mUploadMESParamsA.setMes_mode(isAutomaticUpload ? "1" : "0");
                    UpLoadEntity.FeedingListBean mFeedingListBeanA = new UpLoadEntity.FeedingListBean();
                    mFeedingListBeanA.setFeeder_id(barcode);
                    mFeedingListBeanA.setMaterial_no(materialBlockNumber);
                    mFeedingListBeanA.setSerial_no(serialNo);
                    List<UpLoadEntity.FeedingListBean> data = new ArrayList<>();
                    data.add(mFeedingListBeanA);
                    mUploadMESParamsA.setFeeding_list(data);
                } catch (EntityNotFountException e) {

                    try {
                        parseMaterial(barcode, barCodeParseIpml);

                    } catch (EntityNotFountException ee) {
                        ee.printStackTrace();
                        VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
                        VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
                        showMessage.setText("此处不能识别此码！");
                        showMessage.setVisibility(View.VISIBLE);
                    } catch (ArrayIndexOutOfBoundsException ee) {
                        ee.printStackTrace();
                        VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
                        VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
                        showMessage.setText("此处不能识别此码！");
                        showMessage.setVisibility(View.VISIBLE);
                    }
                    e.printStackTrace();
                }
                break;
        }
    }

    private void parseMaterial(String barcode, BarCodeParseIpml barCodeParseIpml) throws EntityNotFountException {
        MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
        Log.i(TAG, "onScanSuccess: " + barcode);
        showMessage.setVisibility(View.GONE);
        materialBlockNumber = materialBlockBarCode.getDeltaMaterialNumber();
        serialNo = materialBlockBarCode.getStreamNumber();
        quantaty = materialBlockBarCode.getCount();
        Log.i(TAG, "materialBlockNumber: " + materialBlockNumber);
        Log.i(TAG, "serialNo: " + serialNo
        );
        if (!isExistInDataSourceAndHighLight(materialBlockNumber, serialNo, dataSource)) {
            VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
            VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
            showMessage.setText("该料盘不属于此套工单，请确认工单及扫描是否正确！");
            showMessage.setVisibility(View.VISIBLE);
            state = 1;
        } else {
            //料盘已经绑定
            if (isMaterialBound(materialBlockBarCode)) {
                VibratorAndVoiceUtils.wrongVibrator(ModuleUpBindingActivity.this);
                VibratorAndVoiceUtils.wrongVoice(ModuleUpBindingActivity.this);
                showMessage.setText("注意:该料盘已经绑定");
                showMessage.setVisibility(View.VISIBLE);
            } else {
                //料盘尚未绑定
                VibratorAndVoiceUtils.correctVibrator(ModuleUpBindingActivity.this);
                VibratorAndVoiceUtils.correctVoice(ModuleUpBindingActivity.this);
                String slot = getModuleID(materialBlockBarCode);
                showMessage.setText("模组料站：" + slot);
                showMessage.setVisibility(View.VISIBLE);
            }
            state = 2;
            Log.i(TAG, "onScanSuccess: " + "开始扫描Feeder");
        }
    }


    public void setItemHighLightBasedOnMID(int position) {
        scan_position = position;
        RecycleViewUtils.scrollToMiddle(linearLayoutManager, position, recyclerViewContent);
        adapter.notifyDataSetChanged();
    }

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

    public boolean isExistInDataSourceAndHighLight(String item_m, String item_s, List<ModuleUpBindingItem> list) {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getMaterial_no().equals(item_m) && list.get(i).getSerial_no().equals(item_s)) {
                    setItemHighLightBasedOnMID(i);
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }

    }

    @SuppressWarnings("all")
    public boolean isAllFeederBound() {
        boolean res = true;
        if (dataSource.size() > 0) {
            for (ModuleUpBindingItem listItem : dataSource) {
                if (listItem.getFeeder_id() != null && listItem.getFeeder_id().length() > 0) {
                } else {
                    res = false;
                    break;
                }
            }
        } else {
            res = false;
        }
        return res;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == ckb_automaticUpload) {
            if (isChecked) {
                ckb_automaticUpload.setChecked(true);
                SpUtil.SetBooleanSF(ModuleUpBindingActivity.this, "module_up_automatic_upload", true);
                isAutomaticUpload = true;
            } else {
                ckb_automaticUpload.setChecked(false);
                SpUtil.SetBooleanSF(ModuleUpBindingActivity.this, "module_up_automatic_upload", false);
                isAutomaticUpload = false;
            }
        }
    }

    public boolean isMaterialBound(MaterialBlockBarCode materialBlockBarCode) {
        boolean flag = false;
        for (ModuleUpBindingItem rowsBean : dataSource) {
            if (rowsBean.getMaterial_no().equalsIgnoreCase(materialBlockBarCode.getDeltaMaterialNumber()) && rowsBean.getSerial_no().equalsIgnoreCase(materialBlockBarCode.getStreamNumber())) {
                if (!TextUtils.isEmpty(rowsBean.getFeeder_id())) {
                    flag = true;
                    break;
                }

            }
        }
        return flag;
    }

    public String getModuleID(MaterialBlockBarCode materialBlockBarCode) {
        for (ModuleUpBindingItem rowsBean : dataSource) {
            if (rowsBean.getMaterial_no().equalsIgnoreCase(materialBlockBarCode.getDeltaMaterialNumber()) && rowsBean.getSerial_no().equalsIgnoreCase(materialBlockBarCode.getStreamNumber())) {
                return rowsBean.getSlot();
            }

        }
        return null;
    }


}
