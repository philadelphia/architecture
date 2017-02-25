package com.delta.smt.ui.mantissa_warehouse.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
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
import com.delta.commonlibs.utils.DialogUtils;
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

public class MantissaWarehouseDetailsActivity extends BaseActivity<MantissaWarehouseDetailsPresenter> implements MantissaWarehouseDetailsContract.View {

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
    private List<MantissaWarehouseDetailsResult.RowsBean> dataList = new ArrayList();
    private List<MantissaWarehouseDetailsResult.RowsBean> dataList2 = new ArrayList();
    private CommonBaseAdapter<MantissaWarehouseDetailsResult.RowsBean> adapter;
    private CommonBaseAdapter<MantissaWarehouseDetailsResult.RowsBean> adapter2;
    private MantissaWarehouseReady.RowsBean mMantissaWarehouse;
    private String workorder;
    private String name;
    private String lastCar;
    private int flag = 1;
    private String side;
    private boolean ischecked = true;

    private String unSendingMessage;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerMantissaWarehouseDetailsComponent.builder().appComponent(appComponent).mantissaWarehouseDetailsModule(new MantissaWarehouseDetailsModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {

        Intent intent = this.getIntent();
        mMantissaWarehouse = (MantissaWarehouseReady.RowsBean) intent.getSerializableExtra("item");
        workorder = mMantissaWarehouse.getWork_order();
        side = mMantissaWarehouse.getSide();
        WarehouseDetailBean bindBean = new WarehouseDetailBean(side, workorder);
        Gson gson = new Gson();
        String s = gson.toJson(bindBean);
        getPresenter().getMantissaWarehouseDetails(s);
        mCar.setText("");
        //备料车
        MantissaCarBean car = new MantissaCarBean(workorder, "Mantissa", side);
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
        mToolbarTitle.setText("尾数仓备料");
        btnSwitch.setChecked(ischecked);
        btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SpUtil.SetBooleanSF(MantissaWarehouseDetailsActivity.this, "Mantissa" + "checked", b);
            }
        });
        dataList.add(new MantissaWarehouseDetailsResult.RowsBean(1, "", "", 0));
        adapter = new CommonBaseAdapter<MantissaWarehouseDetailsResult.RowsBean>(getContext(), dataList) {
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
        mRecyTitle.setAdapter(adapter);


        adapter2 = new CommonBaseAdapter<MantissaWarehouseDetailsResult.RowsBean>(getContext(), dataList2) {
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
        mRecyContetn.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mRecyContetn.setAdapter(adapter2);

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
        mCar.setText("");
        mCar.setText(car.getRows().get(0).getCar_name());
        flag = 2;
        tv_hint.setText(car.getRows().get(0).getCar_name());
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
    }


    @Override
    public void getBingingCarFailed(String message) {
        flag = 1;
        tv_hint.setText(message);
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }


    @Override
    public void getMantissaWarehouseputSucess(MantissaWarehouseDetailsResult rows) {

        issureToWareh(rows);
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
    }

    boolean isOver = true;
    boolean isHaveIssureOver;

    private void issureToWareh(MantissaWarehouseDetailsResult rows) {
        isOver = true;
        isHaveIssureOver = false;
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("还有未发完的料站，是否还要继续扣账？\n\n");
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
                stringbuffer.append("料号："+dataList2.get(i).getMaterial_no() +"--架位："+dataList2.get(i).getShelf_no()+"--料站："+dataList2.get(i).getSlot()+"\n");

                isOver = false;
            }
        }
        if (btnSwitch.isChecked()) {
            getPresenter().debit();
        }
        if (isOver) {
            getPresenter().getMantissaWareOver();
        }
        if (!"Success".equalsIgnoreCase(rows.getMsg())) {

            tv_hint.setText(rows.getMsg());
        }
        unSendingMessage = stringbuffer.toString();
        mRecyContetn.scrollToPosition(position);
        adapter2.notifyDataSetChanged();
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
        Toast.makeText(this, "扣账成功", Toast.LENGTH_SHORT).show();
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
        ToastUtils.showMessage(this, message);
    }

    @Override
    public void getMantissaWarehouseDetailsSucess(MantissaWarehouseDetailsResult mantissaWarehouseDetails) {
        dataList2.clear();
        dataList2.addAll(mantissaWarehouseDetails.getRows());
        adapter2.notifyDataSetChanged();
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
        ToastUtils.showMessage(this, "扣账成功");

    }

    @Override
    public void debitFailed(String message) {
        ToastUtils.showMessage(this, "扣账失败");

    }

    @Override
    public void onScanSuccess(String barcode) {
        Log.e(TAG, "onScanSuccess: ");
        super.onScanSuccess(barcode);
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (flag) {
            case 1:
                try {
                    LastMaterialCar LastMaterialCar = (LastMaterialCar) barCodeParseIpml.getEntity(barcode, BarCodeType.LAST_MATERIAL_CAR);
                    lastCar = LastMaterialCar.getSource();
                    Toast.makeText(this, lastCar, Toast.LENGTH_SHORT).show();
                    MantissaBingingCarBean bindBean = new MantissaBingingCarBean(workorder, "Mantissa", lastCar, side);
                    Gson gson = new Gson();
                    String s = gson.toJson(bindBean);
                    getPresenter().getbingingCar(s);
                } catch (EntityNotFountException e) {
                    ToastUtils.showMessage(this, "请扫描对应料车");
                    tv_hint.setText("请扫描对应料车");
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    e.printStackTrace();
                }
                break;

            case 2:
                try {
                    MaterialBlockBarCode materiaBar = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, MATERIAL_BLOCK_BARCODE);
                    String serial_num = materiaBar.getStreamNumber();
                    String material_num = materiaBar.getDeltaMaterialNumber();
                    String unit = materiaBar.getUnit();
                    String vendor = materiaBar.getVendor();
                    String dc = materiaBar.getDC();
                    String lc = materiaBar.getLC();
                    String trasaction_code = materiaBar.getBusinessCode();
                    String po = materiaBar.getPO();
                    String quantity = materiaBar.getCount();
                    MantissaWarehouseputBean bindBean = new MantissaWarehouseputBean(serial_num, material_num, unit, vendor, dc, lc, trasaction_code, po, quantity);
                    name = material_num;
                    Gson gson = new Gson();
                    String s = gson.toJson(bindBean);
                    getPresenter().getMantissaWarehouseput(s);

                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                    ToastUtils.showMessage(this, "请扫描对应料盘！");
                    tv_hint.setText("请扫描对应料盘");
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
            tv_hint.setText(getString(R.string.unfinished_station));
            return;
        }
        if (SingleClick.isSingle(1000)) {

            if (isOver == false) {

                DialogUtils.showCommonDialog(this, unSendingMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        getPresenter().debit();

                    }
                });
            }
        }

    }
}
