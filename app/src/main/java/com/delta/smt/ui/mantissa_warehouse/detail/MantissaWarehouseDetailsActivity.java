package com.delta.smt.ui.mantissa_warehouse.detail;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.delta.buletoothio.barcode.parse.entity.LastMaterialCar;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.SingleClick;
import com.delta.commonlibs.utils.SpUtil;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.IssureToWarehFinishResult;
import com.delta.smt.entity.MantissaBingingCarBean;
import com.delta.smt.entity.MantissaCarBean;
import com.delta.smt.entity.MantissaWarehouseDetailsResult;
import com.delta.smt.entity.MantissaWarehouseReady;
import com.delta.smt.entity.MantissaWarehouseputBean;
import com.delta.smt.entity.MaterialCar;
import com.delta.smt.entity.WarehouseDetailBean;
import com.delta.smt.ui.mantissa_warehouse.detail.di.DaggerMantissaWarehouseDetailsComponent;
import com.delta.smt.ui.mantissa_warehouse.detail.di.MantissaWarehouseDetailsModule;
import com.delta.smt.ui.mantissa_warehouse.detail.mvp.MantissaWarehouseDetailsContract;
import com.delta.smt.ui.mantissa_warehouse.detail.mvp.MantissaWarehouseDetailsPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;
import com.delta.smt.utils.ViewUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

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
    boolean isOver = true;
    boolean isHaveIssureOver;
    BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
    private List<MantissaWarehouseDetailsResult.RowsBean> dataList = new ArrayList();
    private List<MantissaWarehouseDetailsResult.RowsBean> dataList2 = new ArrayList();
    private CommonBaseAdapter<MantissaWarehouseDetailsResult.RowsBean> title_adapter;
    private CommonBaseAdapter<MantissaWarehouseDetailsResult.RowsBean> content_adapter;
    private List<MantissaWarehouseDetailsResult.RowsBean> undebitDataList = new ArrayList<>();
    private CommonBaseAdapter<MantissaWarehouseDetailsResult.RowsBean> undoList_adapter;
    private MantissaWarehouseReady.RowsBean mMantissaWarehouse;
    private BottomSheetDialog bottomSheetDialog;
    private String work_order;
    private String lastCar;
    private int flag = 1;
    private String side;
    private boolean ischecked = true;
    private String s;
    private String line_name;
    private String material_num = "";
    private String serial_num = "";
    private int index = 0;

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
        WarehouseDetailBean bindBean = new WarehouseDetailBean(side, work_order);
        Gson gson = new Gson();
        s = gson.toJson(bindBean);
        getPresenter().getMantissaWarehouseDetails(s);
        //备料车
        MantissaCarBean car = new MantissaCarBean(work_order, "Mantissa", side);
        String carbean = gson.toJson(car);
        getPresenter().getFindCar(carbean);
        ischecked = SpUtil.getBooleanSF(this, "Mantissa" + "checked");
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText(R.string.ｍantissa＿warehouse＿stock);
        tvWorkOrder.setText("工单：" + work_order);
        tvLineName.setText("线别：" + line_name);
        tvLineNum.setText("面别：" + side);
        btnSwitch.setChecked(ischecked);

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
        mRecyContetn.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mRecyContetn.setAdapter(content_adapter);
        createBottomSheetDialog();
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
    public void getBingingCarSucess(MaterialCar car) {
        mCar.setText(car.getRows().get(0).getCar_name());
        flag = 2;
        tv_hint.setText(car.getRows().get(0).getCar_name());
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
    }


    @Override
    public void getBingingCarFailed(String message) {
        flag = 1;
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }


    @Override
    public void getMantissaWarehouseputSucess(MantissaWarehouseDetailsResult rows) {

        issureToWareh(rows);
        tv_hint.setText(rows.getMsg());
        if (btnSwitch.isChecked()) {
            getPresenter().debit();
        }
        if (isOver) {
            getPresenter().getMantissaWareOver(s);
        }
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
    }


    private void createBottomSheetDialog() {

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_sheet, null);
        RecyclerView mRecyTitle = ViewUtils.findView(view, R.id.rv_sheet_title);
        RecyclerView mRecycleView = ViewUtils.findView(view, R.id.rv_sheet);
        Button bt_cancel = ViewUtils.findView(view, R.id.bt_sheet_cancel);
        Button bt_confim = ViewUtils.findView(view, R.id.bt_sheet_confirm);
        bt_cancel.setOnClickListener(this);
        bt_confim.setOnClickListener(this);
        undoList_adapter = new CommonBaseAdapter<MantissaWarehouseDetailsResult.RowsBean>(getContext(), dataList2) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehouseDetailsResult.RowsBean item, int position) {
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
            protected int getItemViewLayoutId(int position, MantissaWarehouseDetailsResult.RowsBean item) {
                return R.layout.details_item;
            }

        };
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);

        mRecyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyTitle.setAdapter(title_adapter);
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

    // 获取列表成功后的初始化
    private void issureToWareh(MantissaWarehouseDetailsResult rows) {
        undebitDataList.clear();
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
                undebitDataList.add(dataList2.get(i));
                isOver = false;
            }
        }


        content_adapter.notifyDataSetChanged();
        mRecyContetn.scrollToPosition(index);
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
    public void getMantissaWareOverSucess(IssureToWarehFinishResult issureToWarehFinishResult) {
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
    public void getFindCarSucess(MaterialCar car) {
        String rows = car.getRows().get(0).getCar_name();
        tv_hint.setText(car.getMsg());
        mCar.setText(rows);
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
        ToastUtils.showMessage(this, message);
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
    public void debitSuccess() {
        ToastUtils.showMessage(this, getString(R.string.debit_success));

    }

    @Override
    public void debitFailed(String message) {
        ToastUtils.showMessage(this, getString(R.string.debit_failed));

    }

    @Override
    public void onScanSuccess(String barcode) {
        super.onScanSuccess(barcode);

        switch (flag) {
            case 1:
                try {
                    LastMaterialCar LastMaterialCar = (LastMaterialCar) barCodeParseIpml.getEntity(barcode, BarCodeType.LAST_MATERIAL_CAR);
                    lastCar = LastMaterialCar.getSource();
                    Toast.makeText(this, lastCar, Toast.LENGTH_SHORT).show();
                    MantissaBingingCarBean bindBean = new MantissaBingingCarBean(work_order, "Mantissa", lastCar, side);
                    Gson gson = new Gson();
                    String s = gson.toJson(bindBean);
                    getPresenter().getbingingCar(s);
                } catch (EntityNotFountException e) {
                    ToastUtils.showMessage(this, getString(R.string.scan_remain_car_message));
                    tv_hint.setText(getString(R.string.scan_remain_car_message));
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    e.printStackTrace();
                }
                break;

            case 2:
                try {
                    MaterialBlockBarCode materiaBar = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, MATERIAL_BLOCK_BARCODE);
                    serial_num = materiaBar.getStreamNumber();
                    material_num = materiaBar.getDeltaMaterialNumber();
                    String unit = materiaBar.getUnit();
                    String vendor = materiaBar.getVendor();
                    String dc = materiaBar.getDC();
                    String lc = materiaBar.getLC();
                    String trasaction_code = materiaBar.getBusinessCode();
                    String po = materiaBar.getPO();
                    String quantity = materiaBar.getCount();
                    MantissaWarehouseputBean bindBean = new MantissaWarehouseputBean(serial_num, material_num, unit, vendor, dc, lc, trasaction_code, po, quantity);
                    bindBean.setSide(side);
                    bindBean.setWork_order(work_order);
                    Gson gson = new Gson();
                    String s = gson.toJson(bindBean);
                    getPresenter().getMantissaWarehouseput(s);

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showMessage(this, getString(R.string.scan_corresponding_materials_plate));
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                }

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
            return;
        }
        if (SingleClick.isSingle(1000)) {

            if (isOver == false) {

                if (isOver == false) {

                    if (bottomSheetDialog.isShowing()) {
                        bottomSheetDialog.dismiss();
                    } else {
                        undoList_adapter.notifyDataSetChanged();
                        bottomSheetDialog.show();
                    }
                } else {
                    getPresenter().debit();
                }

            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_sheet_cancel:
                bottomSheetDialog.dismiss();
                break;
            case R.id.bt_sheet_confirm:
                getPresenter().debit();
                bottomSheetDialog.dismiss();
                break;
            default:
                break;
        }
    }
}