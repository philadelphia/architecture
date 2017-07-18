package com.delta.smt.ui.mantissa_warehouse.detail;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.delta.buletoothio.barcode.parse.entity.LastMaterialCar;
import com.delta.buletoothio.barcode.parse.entity.LastMaterialLocation;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
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
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.DebitData;
import com.delta.smt.entity.DebitParameters;
import com.delta.smt.entity.IssureToWarehFinishResult;
import com.delta.smt.entity.MantissaBingingCar;
import com.delta.smt.entity.MantissaBingingCarBean;
import com.delta.smt.entity.MantissaCarBean;
import com.delta.smt.entity.MantissaWarehouseDetailsResult;
import com.delta.smt.entity.MantissaWarehousePutBean;
import com.delta.smt.entity.MantissaWarehouseReady;
import com.delta.smt.entity.MaterialCar;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.WarehouseDetailBean;
import com.delta.smt.ui.mantissa_warehouse.detail.di.DaggerMantissaWarehouseDetailsComponent;
import com.delta.smt.ui.mantissa_warehouse.detail.di.MantissaWarehouseDetailsModule;
import com.delta.smt.ui.mantissa_warehouse.detail.mvp.MantissaWarehouseDetailsContract;
import com.delta.smt.ui.mantissa_warehouse.detail.mvp.MantissaWarehouseDetailsPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;
import com.delta.ttsmanager.TextToSpeechManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.buletoothio.barcode.parse.BarCodeType.MATERIAL_BLOCK_BARCODE;
import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */

public class MantissaWarehouseDetailsActivity extends BaseActivity<MantissaWarehouseDetailsPresenter> implements MantissaWarehouseDetailsContract.View, View.OnClickListener {

    @BindView(R.id.recy_title)
    RecyclerView mRecyTitle;
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
    TextView mCar;
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
    @Inject
    TextToSpeechManager textToSpeechManager;
    boolean isOver = true;
    boolean isHaveIsSureOver;
    BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
    @BindView(R.id.textView)
    TextView textView;
    private List<MantissaWarehouseDetailsResult.RowsBean> dataList = new ArrayList();
    private List<MantissaWarehouseDetailsResult.RowsBean> dataList2 = new ArrayList();
    private CommonBaseAdapter<MantissaWarehouseDetailsResult.RowsBean> title_adapter;
    private CommonBaseAdapter<MantissaWarehouseDetailsResult.RowsBean> content_adapter;
    private CommonBaseAdapter<DebitData> undoList_adapter;
    private MantissaWarehouseReady.RowsBean mMantissaWarehouse;
    private String work_order;
    private String lastCar;
    private int flag = 1;
    private String side;
    private boolean isChecked = true;
    private String line_name;
    private String material_num;
    private String serial_num;
    private int index = 0;
    private LinearLayoutManager content_LinerLayoutManager;
    private String mS;
    private List<DebitData> mDebitDatas = new ArrayList<>();
    private CustomPopWindow mCustomPopWindow;
    private boolean isScanMaterialBlockBarCode;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerMantissaWarehouseDetailsComponent.builder().appComponent(appComponent).mantissaWarehouseDetailsModule(new MantissaWarehouseDetailsModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {

        Intent intent = this.getIntent();
        mMantissaWarehouse = (MantissaWarehouseReady.RowsBean) intent.getSerializableExtra("item");
        work_order = mMantissaWarehouse.getWork_order();
        side = mMantissaWarehouse.getSide();
        line_name = mMantissaWarehouse.getLine_name();
        WarehouseDetailBean bindBean = new WarehouseDetailBean(side, "Mantissa", work_order);
        mS = GsonTools.createGsonListString(bindBean);
        getPresenter().getMantissaWarehouseDetails(mS);
        //备料车
        MantissaCarBean car = new MantissaCarBean(work_order, "Mantissa", side);
        getPresenter().getFindCar(GsonTools.createGsonListString(car));
        isChecked = SpUtil.getBooleanSF(this, "Mantissa" + "checked");
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText(R.string.mantissa＿warehouse＿stock);
        tvWorkOrder.setText("工单：" + work_order);
        tvLineName.setText("线别：" + line_name);
        tvLineNum.setText("面别：" + side);
        btnSwitch.setChecked(isChecked);
        textView.setText("余料车：");

        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SpUtil.SetBooleanSF(MantissaWarehouseDetailsActivity.this, "Mantissa" + "checked", b);
            }
        });
        dataList.add(new MantissaWarehouseDetailsResult.RowsBean(1, "", "", 0));
        title_adapter = new CommonBaseAdapter<MantissaWarehouseDetailsResult.RowsBean>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehouseDetailsResult.RowsBean item, int position) {
                holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehouseDetailsResult.RowsBean item) {
                return R.layout.details_item;
            }
        };
        mRecyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyTitle.setAdapter(title_adapter);


        content_adapter = new CommonBaseAdapter<MantissaWarehouseDetailsResult.RowsBean>(getContext(), dataList2) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehouseDetailsResult.RowsBean item, int position) {
                holder.setText(R.id.tv_number, item.getMaterial_no());
                holder.setText(R.id.tv_location, item.getShelf_no());
                holder.setText(R.id.tv_needNumber, String.valueOf(item.getAmount()));
                holder.setText(R.id.tv_shipments, String.valueOf(item.getIssue_amount()));


                if (item.getMaterial_no().equals(serial_num)) {
                    index = position;
                }
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
            protected int getItemViewLayoutId(int position, MantissaWarehouseDetailsResult.RowsBean item) {
                return R.layout.details_item;
            }

        };
        content_LinerLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        mRecycleContent.setLayoutManager(content_LinerLayoutManager);
        mRecycleContent.setAdapter(content_adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_storage_details;
    }


    @Override
    public void getFailed(String message) {
        ToastUtils.showMessage(this, message);
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }


    @Override
    public void getBingingCarSuccess(Result<MaterialCar> mMaterialCarResult) {
        flag=2;
        List<MaterialCar> mMaterialCars = mMaterialCarResult.getRows();
        if (mMaterialCars.size() != 0) {
            tv_hint.setText(getString(R.string.bindMatericalcar) + mMaterialCars.get(0).getCar_name());
            textToSpeechManager.readMessage(getString(R.string.bindMatericalcar) + mMaterialCars.get(0).getCar_name());
        }
        StringBuffer mStringBuffer = new StringBuffer();
        if (mMaterialCars.size() != 0) {
            for (int mI = 0; mI < mMaterialCars.size(); mI++) {
                if (mI == mMaterialCars.size() - 1) {
                    mStringBuffer.append(mMaterialCars.get(mI).getCar_name());
                } else {
                    mStringBuffer.append(mMaterialCars.get(mI).getCar_name() + ",");
                }
            }
            mCar.setText(mStringBuffer.toString());
            //  tv_hint.setText(rows.get(0).getCar_name());
        }
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
    }


    @Override
    public void getBingingCarFailed(String msg) {
        // 保证没有备料车成功的时候继续绑定，有备料车绑定的时候继续绑定
        if (flag != 2) {

            flag = 1;
        }
        tv_hint.setText(msg);
        // tv_hint.setText(msg);
        ToastUtils.showMessage(this, msg);
        textToSpeechManager.readMessage(msg);
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }


    @Override
    public void getMantissaWarehouseputSuccess(MantissaWarehouseDetailsResult rows) {

        issureToWareh(rows);
        tv_hint.setText(rows.getMsg());
        if (isOver) {
            getPresenter().getMantissaWareOver(mS);
        }
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
    }


    // 获取列表成功后的初始化
    private void issureToWareh(MantissaWarehouseDetailsResult rows) {
        isOver = true;
        isHaveIsSureOver = false;
        dataList2.clear();
        dataList2.addAll(rows.getRows());
        boolean isFirstUndo = true;
        for (int i = 0; i < dataList2.size(); i++) {
            if (dataList2.get(i).getStatus() == 1) {
                if (isFirstUndo) {
                    isFirstUndo = false;
                }
            }
            if (dataList2.get(i).getStatus() == 2) {
                isHaveIsSureOver = true;
            } else {
                isOver = false;
            }
        }
        content_adapter.notifyDataSetChanged();
        RecycleViewUtils.scrollToMiddle(content_LinerLayoutManager, index, mRecycleContent);
    }

    @Override
    public void getMantissaWarehouseputFailed(final String message) {
//        DialogUtils.showCommonDialog(this, message, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                getPresenter().getMantissaWareOver();
//            }
//        });
        ToastUtils.showMessage(this, message);
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void getMantissaWareOverSuccess(IssureToWarehFinishResult issureToWarehFinishResult) {
        Toast.makeText(this, issureToWarehFinishResult.getMsg(), Toast.LENGTH_SHORT).show();
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
    }


    @Override
    public void getMantissaWareOverFailed(String message) {
        ToastUtils.showMessage(this, message);
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);

    }

    @Override
    public void getFindCarSucess(Result<MaterialCar> mMaterialCarResult) {
        StringBuffer mStringBuffer = new StringBuffer();
        if (mMaterialCarResult.getRows().size() != 0) {
            for (int mI = 0; mI < mMaterialCarResult.getRows().size(); mI++) {
                if (mI == mMaterialCarResult.getRows().size() - 1) {
                    mStringBuffer.append(mMaterialCarResult.getRows().get(mI).getCar_name());
                } else {
                    mStringBuffer.append(mMaterialCarResult.getRows().get(mI).getCar_name() + ",");
                }
            }
            mCar.setText(mStringBuffer.toString());
            //  tv_hint.setText(rows.get(0).getCar_name());
        }
        flag = 2;
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void getFindCarFailed(String message) {
        flag = 1;
        tv_hint.setText(message);
        textToSpeechManager.readMessage(message);
        //ToastUtils.showMessage(this, message);
    }

    @Override
    public void getMantissaWarehouseDetailsSucess(MantissaWarehouseDetailsResult mantissaWarehouseDetails) {
        issureToWareh(mantissaWarehouseDetails);
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
    }

    @Override
    public void getMantissaWarehouseDetailsFailed(String msg) {
        ToastUtils.showMessage(this, msg);
        textToSpeechManager.readMessage(msg);
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
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
    public void onScanSuccess(String barcode) {
        super.onScanSuccess(barcode);

        switch (flag) {
            case 1:
                try {
                    LastMaterialCar LastMaterialCar = (LastMaterialCar) barCodeParseIpml.getEntity(barcode, BarCodeType.LAST_MATERIAL_CAR);
                    lastCar = LastMaterialCar.getSource();
                    MantissaBingingCarBean bindBean = new MantissaBingingCarBean(work_order, "Mantissa", lastCar, side);
                    getPresenter().getbingingCar(GsonTools.createGsonListString(bindBean));
                } catch (EntityNotFountException e) {
                    ToastUtils.showMessage(this, getString(R.string.scan_remain_car_message));
                    textToSpeechManager.readMessage(getString(R.string.scan_remain_car_message));
                    tv_hint.setText(getString(R.string.scan_remain_car_message));
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    e.printStackTrace();
                }
                break;
            case 2://料盘 料盘
                try {
                    MaterialBlockBarCode mBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, MATERIAL_BLOCK_BARCODE);
                    serial_num = mBlockBarCode.getStreamNumber();
                    material_num = mBlockBarCode.getDeltaMaterialNumber();
                    String unit = mBlockBarCode.getUnit();
                    String vendor = mBlockBarCode.getVendor();
                    String dc = mBlockBarCode.getDC();
                    String lc = mBlockBarCode.getLC();
                    String mBusinessCode = mBlockBarCode.getBusinessCode();
                    String po = mBlockBarCode.getPO();
                    String quantity = mBlockBarCode.getCount();
                    MantissaWarehousePutBean bindBean = new MantissaWarehousePutBean(serial_num, material_num, unit, vendor, dc, lc, mBusinessCode, po, quantity);
                    bindBean.setSide(side);
                    bindBean.setWork_order(work_order);
                    String code = btnSwitch.isChecked() ? "1" : "0";
                    bindBean.setCode(code);
                    bindBean.setPart("Mantissa");
                    getPresenter().getMantissaWarehouseput(GsonTools.createGsonListString(bindBean));
                } catch (EntityNotFountException e) {
                    //1.因为是发料完成后才扫描标签，android端不可以保存状态，此状态可能会和server不同步，
                    // 这会造成这样的情况，当app重新进入的时候不知道上次发料的是哪个，因为数据存在内存中，而用户又不能在此扫描这个料盘。所以说后台会做判断。
                    try {
                        LastMaterialCar LastMaterialCar = (LastMaterialCar) barCodeParseIpml.getEntity(barcode, BarCodeType.LAST_MATERIAL_CAR);
                        lastCar = LastMaterialCar.getSource();
                        MantissaBingingCarBean bindBean = new MantissaBingingCarBean(work_order, "Mantissa", lastCar, side);
                        getPresenter().getbingingCar(GsonTools.createGsonListString(bindBean));
                    } catch (EntityNotFountException notFoundException) {
                        notFoundException.printStackTrace();
                        try {
                            LastMaterialLocation mLastMaterialLocation = (LastMaterialLocation) barCodeParseIpml.getEntity(barcode, BarCodeType.LAST_MATERIAL_LOCATION);
                            MantissaBingingCar mMantissaBingingCar = new MantissaBingingCar(work_order, side, "",mLastMaterialLocation.getSource());
                            getPresenter().changecarshelf(GsonTools.createGsonListString(mMantissaBingingCar));
                        } catch (EntityNotFountException mE) {
                            mE.printStackTrace();
                            VibratorAndVoiceUtils.wrongVibrator(this);
                            VibratorAndVoiceUtils.wrongVoice(this);
                        }
                    }
                }
                break;



        }
    }

    @Override
    public void deductionFailed(String message) {
        ToastUtils.showMessage(this, message);

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

        ToastUtils.showMessage(this, mMessage);

    }

    @Override
    public void changecarshelfSuccess(String mMessage) {
        ToastUtils.showMessage(this,mMessage);
        tv_hint.setText(mMessage);
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
    }

    @Override
    public void changecarshelfFailed(String mMessage) {
        ToastUtils.showMessage(this, mMessage);
        tv_hint.setText(mMessage);
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void deductionSuccess(List<DebitData> mRows) {
        if (mRows != null && mRows.size() == 0 && isOver) {
            getPresenter().getMantissaWareOver(mS);// 确保扣账完成;
        }
        mDebitDatas.clear();
        mDebitDatas.addAll(mRows);
        undoList_adapter.notifyDataSetChanged();

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
        if (!isHaveIsSureOver) {
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
                mDebitParameters.setPart("Mantissa");
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