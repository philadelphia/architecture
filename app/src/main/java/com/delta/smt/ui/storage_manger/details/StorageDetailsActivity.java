package com.delta.smt.ui.storage_manger.details;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import com.delta.buletoothio.barcode.parse.exception.DCTimeFormatException;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.DialogUtils;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.SingleClick;
import com.delta.commonlibs.utils.SpUtil;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.CustomPopWindow;
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
import com.delta.smt.utils.BarCodeDialogUtils;
import com.delta.smt.utils.VibratorAndVoiceUtils;
import com.delta.ttsmanager.TextToSpeechManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Zhenu.Liu on 2016/12/28.
 */

public class StorageDetailsActivity extends BaseActivity<StorageDetailsPresenter> implements StorageDetailsContract.View, View.OnClickListener, DialogInterface.OnClickListener {

    private static final int JUMPMATERIAL = 3;
    private static final int UNWAREHMATERIAL = 1;
    private final List<StorageDetails> dataList = new ArrayList<>();
    private final List<StorageDetails> mDetailses = new ArrayList<>();
    private final List<DebitData> mDebitDatas = new ArrayList<>();
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
    @BindView(R.id.btn_debitManually)
    Button btn_debitManually;
    @BindView(R.id.tv_car_name)
    TextView mCarName;
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
    @Inject
    TextToSpeechManager textToSpeechManager;
    private CommonBaseAdapter<StorageDetails> content_adapter;
    private BarCodeParseIpml mBarCodeParseIpml;
    private String work_order;
    private String part;
    private String side;
    private boolean ischeck = true;
    private boolean isOver;
    private CommonBaseAdapter<DebitData> undoList_adapter;
    private String mS;
    private String line_name;
    private CustomPopWindow mCustomPopWindow;
    private int state = 1;
    private Dialog mConfirmDialog;
    private IssureToWarehBody mIssureToWarehBody;
    private Dialog mProgressDialog;
    private Dialog mSendDialolg;


    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerStorageDetailsComponent.builder().appComponent(appComponent).storageDetailsModule(new StorageDetailsModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {
        //textToSpeechManager = new RawTextToSpeech(App.getmContext());
        mBarCodeParseIpml = new BarCodeParseIpml();
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
        mToolbarTitle.setText(getString(R.string.storage) + part);
        // JumpOver();
        tvWorkOrder.setText(getString(R.string.workOrder) + work_order);
        tvLineName.setText(getString(R.string.line) + line_name);
        tvLineNum.setText(getString(R.string.side) + side);
        dataList.add(new StorageDetails("", "", 0, 0));
        btnSwitch.setChecked(ischeck);
        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SpUtil.SetBooleanSF(StorageDetailsActivity.this, part + "checked", b);
            }
        });
        CommonBaseAdapter<StorageDetails> mTitle_adapter = new CommonBaseAdapter<StorageDetails>(getContext(), dataList) {
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
        mRecycleTitle.setAdapter(mTitle_adapter);


        content_adapter = new CommonBaseAdapter<StorageDetails>(getContext(), mDetailses) {
            @Override
            protected void convert(CommonViewHolder holder, StorageDetails item, int position) {
                holder.setText(R.id.tv_number, item.getMaterial_no());
                holder.setText(R.id.tv_location, item.getShelf_no());
                holder.setText(R.id.tv_needNumber, String.valueOf(item.getAmount()));
                holder.setText(R.id.tv_shipments, String.valueOf(item.getIssue_amount()));
                holder.setText(R.id.tv_slot, item.getSlot());
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

//    private void JumpOver() {
//        mTvSetting.setText("跳过");
//        mTvSetting.setVisibility(View.VISIBLE);
//        mTvSetting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Map<String, String> mMap = new HashMap<>();
//                mMap.put("part", part);
//                mMap.put("work_order", work_order);
//                mMap.put("side", side);
//                mMap.put("code", "A");
//                getPresenter().issureToWarehFinish(GsonTools.createGsonListString(mMap));
//            }
//        });
//    }

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
            tv_hint.setText(getString(R.string.bindMatericalcar) + data.get(0).getCar_name());
            textToSpeechManager.readMessage(getString(R.string.bindMatericalcar) + data.get(0).getCar_name());
        }
        StringBuilder mStringBuffer = new StringBuilder();
        if (data.size() != 0) {
            for (int mI = 0; mI < data.size(); mI++) {
                if (mI == data.size() - 1) {
                    mStringBuffer.append(data.get(mI).getCar_name());
                } else {
                    mStringBuffer.append(data.get(mI).getCar_name()).append(",");
                }
            }
            mCarName.setText(mStringBuffer.toString());
            //  tv_hint.setText(rows.get(0).getCar_name());
        }
        VibratorAndVoiceUtils.correctVoice(this);
        VibratorAndVoiceUtils.correctVibrator(this);
    }

    @Override
    public void issureToWarehSuccess(Result<StorageDetails> rows) {

        if (Constant.DIVIDE_MATERIAL_AND_ISSUE.equals(rows.getMessage())) {
            VibratorAndVoiceUtils.wrongVibrator(this);
            VibratorAndVoiceUtils.wrongVoice(this);
        }

        issureToWareh(rows);
        tv_hint.setText(rows.getMessage());
        textToSpeechManager.stop();
        textToSpeechManager.readMessage(rows.getMessage());
        if (isOver) {
            getPresenter().issureToWarehFinish(mS);
        }
    }


    private void issureToWareh(Result<StorageDetails> rows) {
        //红色置顶
        mDetailses.clear();
        isToWarehOver(rows.getRows());
        removeJumpMaterialFromRows(rows);
        mDetailses.addAll(rows.getRows());
        content_adapter.notifyDataSetChanged();
        mRecycleContent.scrollToPosition(0);
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
    }

    private void removeJumpMaterialFromRows(Result<StorageDetails> rows) {
        //只有状态是发完料和跳料的状态才能removeFromRows
        Iterator<StorageDetails> mIterator = rows.getRows().iterator();
        while (mIterator.hasNext()) {

            StorageDetails mNext = mIterator.next();
            //因为后台排序是 未发料，发料，跳料所以说可以写成下面的
            if (mNext.getStatus() == UNWAREHMATERIAL) {
                break;
            }
            if (mNext.getStatus() == JUMPMATERIAL) {
                mDetailses.add(mNext);
                mIterator.remove();
            }
        }
    }

    private void isToWarehOver(List<StorageDetails> mRows) {
        isOver = true;
        //boolean mIsHaveIssureOver = false;
        boolean isFirstUndo = true;
        for (int i = 0; i < mRows.size(); i++) {
            if (mRows.get(i).getStatus() == 1) {
                if (isFirstUndo) {
                    isFirstUndo = false;
                }
            }
            if (mRows.get(i).getStatus() == 2) {
                //  mIsHaveIssureOver = true;
            } else {
                isOver = false;
            }
        }
    }

    @Override
    public void issureToWarehFinishSuccess(String msg) {

        ToastUtils.showMessage(this, "发料完成");
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
    }

    @Override
    public void queryMaterailCar(List<MaterialCar> rows) {
        StringBuilder mStringBuffer = new StringBuilder();
        if (rows.size() != 0) {
            for (int mI = 0; mI < rows.size(); mI++) {
                if (mI == rows.size() - 1) {
                    mStringBuffer.append(rows.get(mI).getCar_name());
                } else {
                    mStringBuffer.append(rows.get(mI).getCar_name()).append(",");
                }
            }
            mCarName.setText(mStringBuffer.toString());
            //  tv_hint.setText(rows.get(0).getCar_name());
        }
        state = 2;

    }

    @Override
    public void queryMaterailCarFailed(String msg) {
        ToastUtils.showMessage(this, msg);
        tv_hint.setText(msg);
        mCarName.setText("无");
        textToSpeechManager.readMessage(msg);
        state = 1;

    }

    @Override
    public void bindMaterialCarFailed(String msg) {
        if (state != 2) {
            state = 1;
        }
        tv_hint.setText(msg);
        ToastUtils.showMessage(this, msg);
        textToSpeechManager.readMessage(msg);
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void jumpMaterialsSuccess(Result<StorageDetails> result) {
//        mDetailses.clear();
//        isToWarehOver(result.getRows());
//        removeJumpMaterialFromRows(result);
//        mDetailses.addAll(result.getRows());
//        content_adapter.notifyDataSetChanged();
//        mRecycleContent.scrollToPosition(0);
        issureToWareh(result);
        getPresenter().issureToWareh(GsonTools.createGsonListString(mIssureToWarehBody));
        // issureToWareh(result);
        tv_hint.setText(result.getMessage());
//        if (isOver) {
//            getPresenter().isSureToWarehFinish(mS);
//        }
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

        if (mConfirmDialog == null) {

            mConfirmDialog = BarCodeDialogUtils.showCommonDialog(this, message, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    getPresenter().jumpMaterials(mS);
                }
            }, getBarCodeIpml());
        }
        if (!mConfirmDialog.isShowing()) {
            mConfirmDialog.show();
        }

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
    public void sureCompleteIssueSuccess(String message) {
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
    public void isSureToWarehFinish() {
        getPresenter().issureToWarehFinish(mS);
    }

    @Override
    public void showDialogLoadingView() {
        if (mProgressDialog == null) {
            mProgressDialog = DialogUtils.createProgressDialog(this, "正在发送到备料区。。。", false);
            mProgressDialog.show();
        }
    }

    @Override
    public void showBacAreaMessageSuccess(Result mResult) {
        //刷新列表
        getPresenter().getStorageDetails(mS);
        getPresenter().queryMaterailCar(mS);
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
        tv_hint.setText(mResult.getMessage());
        ToastUtils.showMessage(this, mResult.getMessage());
    }

    @Override
    public void showBacAreaMessageFailed(String mMessage) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        tv_hint.setText(mMessage);
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
        ToastUtils.showMessage(this, mMessage);
    }

    @Override
    protected void handError(String contents) {
        super.handError(contents);
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onScanSuccess(String barcode) {
        Log.e(TAG, "onScanSuccess: " + barcode);
        switch (state) {
            case 1:
                BackupMaterialCar car;
                try {
                    car = ((BackupMaterialCar) mBarCodeParseIpml.getEntity(barcode, BarCodeType.BACKUP_MATERIAL_CAR));
                    //mCarName.setText(car.getSource());
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
                    MaterialBlockBarCode mMaterialblockbarcode = (MaterialBlockBarCode) mBarCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                    if (mIssureToWarehBody == null) {
                        mIssureToWarehBody = new IssureToWarehBody();
                    }
                    mIssureToWarehBody.setMaterial_no(mMaterialblockbarcode.getDeltaMaterialNumber());
                    mIssureToWarehBody.setSerial_no(mMaterialblockbarcode.getStreamNumber());
                    mIssureToWarehBody.setUnit(mMaterialblockbarcode.getUnit());
                    mIssureToWarehBody.setDc(mMaterialblockbarcode.getDC());
                    mIssureToWarehBody.setLc(mMaterialblockbarcode.getDC());
                    mIssureToWarehBody.setVendor(mMaterialblockbarcode.getDC());
                    mIssureToWarehBody.setVendor(mMaterialblockbarcode.getVendor());
                    mIssureToWarehBody.setTc(mMaterialblockbarcode.getBusinessCode());
                    mIssureToWarehBody.setPo(mMaterialblockbarcode.getPO());
                    mIssureToWarehBody.setQty(mMaterialblockbarcode.getCount());
                    mIssureToWarehBody.setWork_order(work_order);
                    mIssureToWarehBody.setSide(side);
                    mIssureToWarehBody.setPart(part);
                    String code = btnSwitch.isChecked() ? "1" : "0";
                    mIssureToWarehBody.setCode(code);
                    //currentDeltaMaterialNumber = materialblockbarcode.getDeltaMaterialNumber();
                    getPresenter().issureToWareh(GsonTools.createGsonListString(mIssureToWarehBody));

                } catch (DCTimeFormatException mDCException) {
                    ToastUtils.showMessage(this, mDCException.getMessage());
                    tv_hint.setText(mDCException.getMessage());
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                    BackupMaterialCar otherCar;
                    try {
                        otherCar = ((BackupMaterialCar) mBarCodeParseIpml.getEntity(barcode, BarCodeType.BACKUP_MATERIAL_CAR));
                        //mCarName.setText(car.getSource());
                        Map<String, String> maps = new HashMap<>();
                        maps.put("work_order", work_order);
                        maps.put("part", part);
                        maps.put("side", side);
                        maps.put("pre_car", otherCar.getSource());
                        getPresenter().bindBoundPrepCar(GsonTools.createGsonListString(maps));
                    } catch (EntityNotFountException notFountCarException) {

                        ToastUtils.showMessage(this, "条码格式不正确！不能识别此码！");
                        tv_hint.setText("条码格式不正确！不能识别此码！");
                        VibratorAndVoiceUtils.wrongVibrator(this);
                        VibratorAndVoiceUtils.wrongVoice(this);
                        notFountCarException.printStackTrace();
                        state = 2;
//                        ToastUtils.showMessage(this, "扫描有误，请扫描备料车");
//                        tv_hint.setText("扫描有误，请扫描备料车");
//                        VibratorAndVoiceUtils.wrongVibrator(this);
//                        VibratorAndVoiceUtils.wrongVoice(this);
//                        textToSpeechManager.readMessage("扫描有误，请扫描备料车");
                    }

//                    e.printStackTrace();
//                    ToastUtils.showMessage(this, "请扫描对应架位的料盘");
//                    tv_hint.setText("请扫描对应架位的料盘");
//                    textToSpeechManager.readMessage("请扫描对应架位的料盘");
//                    VibratorAndVoiceUtils.wrongVibrator(this);
//                    VibratorAndVoiceUtils.wrongVoice(this);
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


//    private void createCustomPopWindow() {
//        mCustomPopWindow = CustomPopWindow.builder().with(this).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).setAnimationStyle(R.style.popupAnimalStyle).setView(R.layout.dialog_bottom_sheet).build();
//        View mContentView = mCustomPopWindow.getContentView();
//        RecyclerView rv_debit = ViewUtils.findView(mContentView, R.id.rv_sheet);
//        Button bt_cancel = ViewUtils.findView(mContentView, R.id.bt_sheet_back);
//        Button bt_confirm = ViewUtils.findView(mContentView, R.id.bt_sheet_confirm);
//        Button bt_select_all = ViewUtils.findView(mContentView, R.id.bt_sheet_select_all);
//        ViewUtils.findView(mContentView, R.id.bt_sheet_select_cancel).setOnClickListener(this);
//        bt_cancel.setOnClickListener(this);
//        bt_confirm.setOnClickListener(this);
//        bt_select_all.setOnClickListener(this);
//
//        undoList_adapter = new CommonBaseAdapter<DebitData>(getContext(), mDebitDatas) {
//            @Override
//            protected void convert(CommonViewHolder holder, final DebitData item, int position) {
//                holder.setText(R.id.tv_material_id, "料号：" + item.getMaterial_no());
//                holder.setText(R.id.tv_slot, "架位：" + item.getSlot());
//                holder.setText(R.id.tv_amount, "需求量：" + String.valueOf(item.getAmount()));
//                holder.setText(R.id.tv_issue, "已发料量：" + String.valueOf(item.getIssue_amount()));
//                final CheckBox mCheckBox = holder.getView(R.id.cb_debit);
//                mCheckBox.setChecked(item.isChecked());
//                holder.getView(R.id.al).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mCheckBox.setChecked(!item.isChecked());
//                        item.setChecked(!item.isChecked());
//                    }
//                });
//
//            }
//
//            @Override
//            protected int getItemViewLayoutId(int position, DebitData item) {
//                return R.layout.item_debit_list;
//            }
//
//        };
//        rv_debit.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setSmoothScrollbarEnabled(true);
//        rv_debit.setLayoutManager(linearLayoutManager);
//        rv_debit.setAdapter(undoList_adapter);
//    }


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
                    if (mDebitDatas.size() != 0) {
                        for (DebitData mDebitData : mDebitDatas) {
                            mDebitData.setChecked(true);
                        }
                        undoList_adapter.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.bt_sheet_select_cancel:
                if (mCustomPopWindow != null && mCustomPopWindow.isShowing()) {
                    if (mDebitDatas.size() != 0) {
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


//    @OnClick({R.id.btn_debitManually})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.bt_send_back_area:
//
//                break;
//            case R.id.btn_debitManually:
//                if (!isHaveIssureOver) {
//                    ToastUtils.showMessage(this, getString(R.string.unfinished_station));
//                    return;
//                }
//                if (mCustomPopWindow == null) {
//                    createCustomPopWindow();
//
//                }
//                if (SingleClick.isSingle(1000)) {
//                    getPresenter().getDebitDataList(mS);
//                }
//                break;
//        }
//    }


    @OnClick(R.id.bt_send_back_area)
    public void onViewClicked() {
        Log.d(TAG, "onViewClicked() called");
        if (state == 2) {
            if (mSendDialolg == null) {
                mSendDialolg = BarCodeDialogUtils.showCommonDialog(this, "是否将料发送到备料区？", this, getBarCodeIpml());
            }
            mSendDialolg.show();
        } else {
            ToastUtils.showMessage(StorageDetailsActivity.this, "请先绑定备料车！");
        }

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

                if (mS != null && SingleClick.isSingle(1000)) {
                    getPresenter().sendBackArea(mS);
                }
        dialog.dismiss();
    }
}
