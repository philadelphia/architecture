package com.delta.smt.ui.storage_manger.details;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.BackupMaterialCar;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
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
import com.delta.smt.entity.DebitData;
import com.delta.smt.entity.DebitParameters;
import com.delta.smt.entity.IssureToWarehBody;
import com.delta.smt.entity.MaterialCar;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.StorageDetails;
import com.delta.smt.ui.storage_manger.details.di.DaggerStorageDetailsComponent;
import com.delta.smt.ui.storage_manger.details.di.StorageDetailsModule;
import com.delta.smt.ui.storage_manger.details.mvp.StorageDetailsContract;
import com.delta.smt.ui.storage_manger.details.mvp.StorageDetailsPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;
import com.delta.commonlibs.utils.ViewUtils;
import com.delta.commonlibs.widget.CustomPopWindow;
import com.delta.ttsmanager.TextToSpeechManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

public class StorageDetailsActivity extends BaseActivity<StorageDetailsPresenter> implements StorageDetailsContract.View, View.OnClickListener {

    @BindView(R.id.recy_title)
    RecyclerView mRecycleTitle;
    @BindView(R.id.recy_contetn)
    RecyclerView mRecycleContent;
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
    @BindView(R.id.tv_work_order)
    TextView tvWorkOrder;
    @BindView(R.id.tv_line_name)
    TextView tvLineName;
    @BindView(R.id.tv_line_num)
    TextView tvLineNum;
    @BindView(R.id.textView)
    TextView textView;
    private List<StorageDetails> dataList = new ArrayList<>();
    private List<StorageDetails> mStorageDetailses = new ArrayList<>();
    private List<DebitData> mDebitDatas = new ArrayList<>();
    private CommonBaseAdapter<StorageDetails> title_adapter;
    private CommonBaseAdapter<StorageDetails> content_adapter;
    private BarCodeParseIpml barCodeImp;
    private String work_order;
    private String part;
    private MaterialBlockBarCode materialblockbarcode;
    private String side;
    private boolean isHaveIssureOver;
    private boolean ischeck = true;
    private boolean isOver;
    private CommonBaseAdapter<DebitData> undoList_adapter;
    private String mS;
    private String line_name;
    private CustomPopWindow mCustomPopWindow;
    private int state = 1;
    @Inject
    TextToSpeechManager textToSpeechManager;


    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerStorageDetailsComponent.builder().appComponent(appComponent).storageDetailsModule(new StorageDetailsModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {
        //textToSpeechManager = new RawTextToSpeech(App.getmContenxt());
        barCodeImp = new BarCodeParseIpml();
        part = getIntent().getStringExtra(Constant.WARE_HOUSE_NAME);
        work_order = getIntent().getStringExtra(Constant.WORK_ORDER);
        line_name = getIntent().getStringExtra(Constant.LINE_NAME);
        side = getIntent().getStringExtra(Constant.SIDE);
        Map<String, String> mMap = new HashMap<>();
        mMap.put("part", part);
        mMap.put("work_order", work_order);
        mMap.put("side", side);
        mS = GsonTools.createGsonListString(mMap);
        getPresenter().getStorageDetails(mS);
        getPresenter().queryMaterailCar(mS);
        ischeck = SpUtil.getBooleanSF(this, part + "checked");

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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        mToolbarTitle.setText("仓库" + part);
        JumpOver();
        tvWorkOrder.setText("工单：" + work_order);
        tvLineName.setText("线别：" + line_name);
        tvLineNum.setText("面别：" + side);
        dataList.add(new StorageDetails("", "", 0, 0));
        btnSwitch.setChecked(ischeck);
        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SpUtil.SetBooleanSF(StorageDetailsActivity.this, part + "checked", b);
            }
        });
        title_adapter = new CommonBaseAdapter<StorageDetails>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, StorageDetails item, int position) {
                holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, StorageDetails item) {
                return R.layout.details_item;
            }
        };
        mRecycleTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecycleTitle.setAdapter(title_adapter);


        content_adapter = new CommonBaseAdapter<StorageDetails>(getContext(), mStorageDetailses) {
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
        mRecycleContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mRecycleContent.setAdapter(content_adapter);
    }

    private void JumpOver() {
        mTvSetting.setText("跳过");
        mTvSetting.setVisibility(View.VISIBLE);
        mTvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> mMap = new HashMap<>();
                mMap.put("part", part);
                mMap.put("work_order", work_order);
                mMap.put("side", side);
                mMap.put("code","A");
                getPresenter().issureToWarehFinish(GsonTools.createGsonListString(mMap));
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_storage_details;
    }

    @Override
    public void getSuccess(Result<StorageDetails> storageDetails) {
        issureToWareh(storageDetails);
    }

    @Override
    public void getFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        content_adapter.notifyDataSetChanged();
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void bindMaterialCarSuccess(List<BindPrepCarIDByWorkOrderResult.RowsBean> data) {
        //绑定料车成功状态2
        state = 2;
        if (data.size() != 0) {
            mTextView2.setText(data.get(0).getCar_name());
            tv_hint.setText(getString(R.string.bindMatericalcar) + data.get(0).getCar_name());
            textToSpeechManager.readMessage(getString(R.string.bindMatericalcar) + data.get(0).getCar_name());
        }

        VibratorAndVoiceUtils.correctVoice(this);
        VibratorAndVoiceUtils.correctVibrator(this);
    }

    @Override
    public void issureToWarehSuccess(Result<StorageDetails> rows) {
        issureToWareh(rows);
        tv_hint.setText(rows.getMessage());
        textToSpeechManager.stop();
        textToSpeechManager.readMessage(rows.getMessage());
        if (isOver) {
            getPresenter().issureToWarehFinish(mS);
        }
    }


    @Override
    protected void onDestroy() {
        //textToSpeechManager.freeSource();
        super.onDestroy();
    }

    private void issureToWareh(Result<StorageDetails> rows) {
        isOver = true;
        isHaveIssureOver = false;
        mStorageDetailses.clear();
        mStorageDetailses.addAll(rows.getRows());
        int position = 0;
        boolean isFirstUndo = true;
        for (int i = 0; i < mStorageDetailses.size(); i++) {
            if (mStorageDetailses.get(i).getStatus() == 1) {
                if (isFirstUndo) {
                    position = i;
                    isFirstUndo = false;
                }
            }
            if (mStorageDetailses.get(i).getStatus() == 2) {
                isHaveIssureOver = true;
            } else {
                isOver = false;
            }
        }
        content_adapter.notifyDataSetChanged();
        mRecycleContent.scrollToPosition(position);
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
    }

    @Override
    public void issureToWarehFinishSuccess(String msg) {

        ToastUtils.showMessage(this, "发料完成");
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
    }

    @Override
    public void queryMaterailCar(List<MaterialCar> rows) {
        if (rows.size() != 0) {
            mTextView2.setText(rows.get(0).getCar_name());
            //  tv_hint.setText(rows.get(0).getCar_name());
        }
        state = 2;

    }

    @Override
    public void queryMaterailCarFailed(String msg) {
        ToastUtils.showMessage(this, msg);
        tv_hint.setText(msg);
        textToSpeechManager.readMessage(msg);
        state = 1;

    }

    @Override
    public void bindMaterialCarFailed(String msg) {
        state = 1;
        tv_hint.setText(msg);
        ToastUtils.showMessage(this, msg);
        textToSpeechManager.readMessage(msg);
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void jumpMaterialsSuccess(Result<StorageDetails> result) {
        issureToWareh(result);
        tv_hint.setText(result.getMessage());
        if (isOver) {
            getPresenter().issureToWarehFinish(mS);
        }
    }

    @Override
    public void jumpMaterialsFailed(String message) {
        ToastUtils.showMessage(this, message);

        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void issureToWarehFailedWithoutJumpMaterials(String message) {

        state = 2;
        ToastUtils.showMessage(this, message);
        tv_hint.setText(message);
        textToSpeechManager.readMessage(message);
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
                getPresenter().jumpMaterials(mS);
            }
        });
        textToSpeechManager.readMessage(message);
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void issureToWarehFinishFaildSure(String message) {
        state = 2;
        ToastUtils.showMessage(this, message);
        textToSpeechManager.readMessage(message);
        DialogUtils.showConfirmDialog(this, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //getPresenter().sureCompleteIssue();
                dialogInterface.dismiss();
            }
        });
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void issureToWarehFinishFailedWithoutSure(String message) {
        state = 2;
        ToastUtils.showMessage(this, message);
        textToSpeechManager.readMessage(message);
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void sureCompleteIssueSucess(String message) {
        ToastUtils.showMessage(this, message);
        textToSpeechManager.readMessage(message);
        tv_hint.setText(message);
    }

    @Override
    public void sureCompleteIssueFailed(String message) {
        ToastUtils.showMessage(this, message);
        textToSpeechManager.readMessage(message);
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
        DialogUtils.showCommonDialog(this, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               dialogInterface.dismiss();
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
        statusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getStorageDetails(mS);
                getPresenter().queryMaterailCar(mS);
            }
        });
    }

    @Override
    public void showEmptyView() {

        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getStorageDetails(mS);
                getPresenter().queryMaterailCar(mS);
            }
        });
    }

    @Override
    public void deductionFailed(String message) {
        ToastUtils.showMessage(this, message);

    }

    @Override
    public void deductionSuccess(List<DebitData> mRows) {
        if (mRows.size() == 0 && isOver) {
            getPresenter().issureToWarehFinish(mS);
        }
        mDebitDatas.clear();
        mDebitDatas.addAll(mRows);
        undoList_adapter.notifyDataSetChanged();

    }

    @Override
    public void queryCarFailed(String message) {
        //tv_hint.setText(message);
        ToastUtils.showMessage(this, message);
        // Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        content_adapter.notifyDataSetChanged();
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void getDebitDataSuccess(List<DebitData> mDebitDataResult) {

        if (mDebitDataResult == null || mDebitDataResult.size() == 0) {
            if (isOver) {
                ToastUtils.showMessage(this, "已完成所有扣账,没有扣账列表");
            }
            return;
        }
        mCustomPopWindow.showAsDropDown(mToolbar);
        mDebitDatas.clear();
        mDebitDatas.addAll(mDebitDataResult);
        undoList_adapter.notifyDataSetChanged();
    }

    @Override
    public void getDebitDataFailed(String mMessage) {

    }

    @Override
    public void onScanSuccess(String barcode) {
        switch (state) {
            case 1:
                BackupMaterialCar car;
                try {
                    car = ((BackupMaterialCar) barCodeImp.getEntity(barcode, BarCodeType.BACKUP_MATERIAL_CAR));
                    //mTextView2.setText(car.getSource());
                    Map<String, String> maps = new HashMap<>();
                    maps.put("work_order", work_order);
                    maps.put("part", part);
                    maps.put("side", side);
                    maps.put("pre_car", car.getSource());
                    getPresenter().bindBoundPrepCar(GsonTools.createGsonListString(maps));
                } catch (Exception e) {
                    e.printStackTrace();
                    state = 1;
                    ToastUtils.showMessage(this, "扫描有误，请扫描备料车");
                    tv_hint.setText("扫描有误，请扫描备料车");
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    textToSpeechManager.readMessage("扫描有误，请扫描备料车");
                }
                break;
            case 2:
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
                    issureToWarehBody.setTc(materialblockbarcode.getBusinessCode());
                    issureToWarehBody.setPo(materialblockbarcode.getPO());
                    issureToWarehBody.setQty(materialblockbarcode.getCount());
                    issureToWarehBody.setWork_order(work_order);
                    issureToWarehBody.setSide(side);
                    issureToWarehBody.setPart(part);
                    String code = btnSwitch.isChecked() ? "1" : "0";
                    issureToWarehBody.setCode(code);
                    //currentDeltaMaterialNumber = materialblockbarcode.getDeltaMaterialNumber();
                    getPresenter().issureToWareh(GsonTools.createGsonListString(issureToWarehBody));

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showMessage(this, "请扫描对应架位的料盘");
                    tv_hint.setText("请扫描对应架位的料盘");
                    textToSpeechManager.readMessage("请扫描对应架位的料盘");
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //mToolbarHeight = mToolbar.getHeight();
        super.onWindowFocusChanged(hasFocus);
    }


    @OnClick(R.id.button2)
    public void onClick() {
        if (!isHaveIssureOver) {
            ToastUtils.showMessage(this, getString(R.string.unfinished_station));
            return;
        }
        if (mCustomPopWindow == null) {
            createCustomPopWindow();

        }
        if (SingleClick.isSingle(1000)) {
            getPresenter().getDebitDataList(mS);
        }
    }

    private void createCustomPopWindow() {
        mCustomPopWindow = CustomPopWindow.builder().with(this).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).setAnimationStyle(R.style.popupAnimalStyle).setView(R.layout.dialog_bottom_sheet).build();
        View mContentView = mCustomPopWindow.getContentView();
        RecyclerView rv_debit = ViewUtils.findView(mContentView, R.id.rv_sheet);
        Button bt_cancel = ViewUtils.findView(mContentView, R.id.bt_sheet_back);
        Button bt_confirm = ViewUtils.findView(mContentView, R.id.bt_sheet_confirm);
        Button bt_select_all = ViewUtils.findView(mContentView, R.id.bt_sheet_select_all);
        ViewUtils.findView(mContentView, R.id.bt_sheet_select_cancel).setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
        bt_confirm.setOnClickListener(this);
        bt_select_all.setOnClickListener(this);

        undoList_adapter = new CommonBaseAdapter<DebitData>(getContext(), mDebitDatas) {
            @Override
            protected void convert(CommonViewHolder holder, final DebitData item, int position) {
                holder.setText(R.id.tv_material_id, "料号：" + item.getMaterial_no());
                holder.setText(R.id.tv_slot, "架位：" + item.getSlot());
                holder.setText(R.id.tv_amount, "需求量：" + String.valueOf(item.getAmount()));
                holder.setText(R.id.tv_issue, "已发料量：" + String.valueOf(item.getIssue_amount()));
                final CheckBox mCheckBox = holder.getView(R.id.cb_debit);
                mCheckBox.setChecked(item.isChecked());
                holder.getView(R.id.al).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCheckBox.setChecked(!item.isChecked());
                        item.setChecked(!item.isChecked());
                    }
                });

            }

            @Override
            protected int getItemViewLayoutId(int position, DebitData item) {
                return R.layout.item_debit_list;
            }

        };
        rv_debit.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        rv_debit.setLayoutManager(linearLayoutManager);
        rv_debit.setAdapter(undoList_adapter);
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
                List<DebitParameters.ListBean> mDebitCheckedData = new ArrayList<>();

                for (DebitData mDebitData : mDebitDatas) {
                    if (mDebitData.isChecked()) {
                        DebitParameters.ListBean mListBean = new DebitParameters.ListBean();
                        mListBean.setSlot(mDebitData.getSlot());
                        mListBean.setTotal_qty(mDebitData.getIssue_amount());
                        mListBean.setMaterial_no(mDebitData.getMaterial_no());
                        mListBean.setDemand_qty(mDebitData.getAmount());
                        mDebitCheckedData.add(mListBean);
                    }
                }
                if (mDebitCheckedData.size() == 0) {
                    ToastUtils.showMessage(this, "还未选择口账列表！");
                    return;
                }
                DebitParameters mDebitParameters = new DebitParameters();
                mDebitParameters.setAction(Constant.ACTION);
                mDebitParameters.setRfname("124133");
                mDebitParameters.setWork_order(work_order);
                mDebitParameters.setSide(side);
                mDebitParameters.setPart(part);
                mDebitParameters.setList(mDebitCheckedData);
                getPresenter().deduction(GsonTools.createGsonListString(mDebitParameters));
                break;
            case R.id.bt_sheet_select_all:
                if (mCustomPopWindow != null && mCustomPopWindow.isShowing()) {
                    if (mDebitDatas != null && mDebitDatas.size() != 0) {
                        for (DebitData mDebitData : mDebitDatas) {
                            mDebitData.setChecked(true);
                        }
                        undoList_adapter.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.bt_sheet_select_cancel:
                if (mCustomPopWindow != null && mCustomPopWindow.isShowing()) {
                    if (mDebitDatas != null && mDebitDatas.size() != 0) {
                        for (DebitData mDebitData : mDebitDatas) {
                            mDebitData.setChecked(false);
                        }
                        undoList_adapter.notifyDataSetChanged();
                    }
                }
                break;
            default:
                break;
        }
    }


}
