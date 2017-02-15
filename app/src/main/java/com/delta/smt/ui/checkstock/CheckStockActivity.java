package com.delta.smt.ui.checkstock;

import android.app.AlertDialog;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.entity.PcbFrameLocation;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.SnackbarUtil;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.MainActivity;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.CheckStock;
import com.delta.smt.entity.CheckStockDemo;
import com.delta.smt.ui.checkstock.di.CheckStockModule;
import com.delta.smt.ui.checkstock.di.DaggerCheckStockComponent;
import com.delta.smt.ui.checkstock.mvp.CheckStockContract;
import com.delta.smt.ui.checkstock.mvp.CheckStockPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.R.id.recy_contetn;
import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class CheckStockActivity extends BaseActivity<CheckStockPresenter> implements CheckStockContract.View, View.OnClickListener {

    @BindView(R.id.cargoned)
    EditText cargoned;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    @BindView(R.id.activity_check_main)
    LinearLayout mianCheckStockActivityView;
    @BindView(R.id.cargon_affirm)
    Button cargonAffirm;
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(recy_contetn)
    RecyclerView recyContetn;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.cargon_tv)
    TextView cargonTv;
    private List<CheckStock.RowsBean> dataList = new ArrayList<>();
    private CommonBaseAdapter<CheckStock.RowsBean> mAdapter;
    private TextView mErrorContent;
    private AlertDialog.Builder builder;
    private AlertDialog mErrorDialog;
    private AlertDialog mResultDialog;
    private TextView mResultContent;
    private MaterialBlockBarCode mMaterbarCode;
    private int status = 1;
    private PcbFrameLocation mFrameLocation;
    private int mId;
    private int position;
    private PcbFrameLocation mFrameLocationSuccess;
    private boolean isShowDialog=true;
    private int isChexNumber=0;
    private boolean isChexs=true;


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerCheckStockComponent.builder().appComponent(appComponent).checkStockModule(new CheckStockModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText(this.getResources().getString(R.string.pcbcheck));
        cargonTv.setFocusable(true);
        cargoned.clearFocus();
        cargoned.setFocusable(false);
        builder = new AlertDialog.Builder(this);
        List<CheckStockDemo> list = new ArrayList<>();
        list.add(new CheckStockDemo("", "", "", "", ""));
        CommonBaseAdapter<CheckStockDemo> mAdapterTitle = new CommonBaseAdapter<CheckStockDemo>(getContext(), list) {
            @Override
            protected void convert(CommonViewHolder holder, CheckStockDemo item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, CheckStockDemo item) {
                return R.layout.item_check;
            }

        };
        recyTitle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyTitle.setAdapter(mAdapterTitle);

        mAdapter = new CommonBaseAdapter<CheckStock.RowsBean>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, CheckStock.RowsBean item, int position) {
                holder.itemView.setBackgroundColor(Color.WHITE);
                holder.setText(R.id.statistics, item.getPartNum());
                holder.setText(R.id.statistics_pcbnumber, "" + item.getBoundCount());
                if (item.getRealCount() == 0) {
                    holder.setText(R.id.statistics_number, "");
                } else {
                    holder.setText(R.id.statistics_number, "" + item.getRealCount());
                }
                holder.setText(R.id.statistics_storenumber, item.getStatus());
                if (item.isColor()){
                   holder.setBackgroundColor(R.id.statistics, Color.YELLOW);
                   holder.setBackgroundColor(R.id.statistics_pcbnumber, Color.YELLOW);
                   holder.setBackgroundColor(R.id.statistics_number, Color.YELLOW);
                   holder.setBackgroundColor(R.id.statistics_storenumber, Color.YELLOW);
                }


            }

            @Override
            protected int getItemViewLayoutId(int position, CheckStock.RowsBean item) {
                return R.layout.item_check;
            }

        };
        recyContetn.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyContetn.setAdapter(mAdapter);


    }

    @Override
    public void onScanSuccess(String barcode) {
        super.onScanSuccess(barcode);
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (status) {
            case 1:
                try {
                    mFrameLocation = (PcbFrameLocation) barCodeParseIpml.getEntity(barcode, BarCodeType.PCB_FRAME_LOCATION);
                    VibratorAndVoiceUtils.correctVibrator (this);
                    VibratorAndVoiceUtils.correctVoice(this);
                    if (mFrameLocation!=null){
                    cargonTv.setText(mFrameLocation.getSource());
                    getPresenter().fetchCheckStock(mFrameLocation.getSource());
                    status = 2;}
                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                    VibratorAndVoiceUtils. wrongVibrator (this);
                    VibratorAndVoiceUtils. wrongVoice (this);
                    SnackbarUtil.showMassage(mianCheckStockActivityView,"扫描的架位二维码错误，请重新扫描");
                    //ToastUtils.showMessage(this, "扫描的架位二维码错误，请重新扫描");
                    status = 1;
                }
                break;
            case 2:
                try {
                    mMaterbarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                    VibratorAndVoiceUtils.correctVibrator (this);
                    VibratorAndVoiceUtils.correctVoice(this);
                    if (mMaterbarCode != null) {
                        for (int i = 0; i < dataList.size(); i++) {
                           if (!dataList.get(i).isCheck()){
                           isChexNumber++;
                            if (mMaterbarCode.getStreamNumber().equals(dataList.get(i).getBoxSerial() )) {
                                if (Integer.valueOf(mMaterbarCode.getCount()) <=dataList.get(i).getBoundCount()) {
                                    position=i;
                                    mId = dataList.get(i).getId();
                                    dataList.get(i).setColor(true);
                                    dataList.get(i).setCheck(true);
                                    mAdapter.notifyDataSetChanged();
                                    getPresenter().fetchCheckStockSuccessNumber(dataList.get(i).getId(), Integer.valueOf(mMaterbarCode.getCount()));
                                    break;
                                } else {
                                    mId = dataList.get(i).getId();
                                    cargoned.setFocusable(true);
                                    cargoned.setFocusableInTouchMode(true);
                                    cargoned.requestFocus();
                                    cargoned.findFocus();
                                    position=i;
                                    dataList.get(i).setColor(true);
                                    dataList.get(i).setCheck(true);
                                    mAdapter.notifyDataSetChanged();
                                    SnackbarUtil.showMassage(mianCheckStockActivityView,"请查数后输入数量!");
                                    //ToastUtils.showMessage(CheckStockActivity.this,"请查数后输入数量!");
                                    break;
                                }
                            } else {
                                if (isChexNumber==dataList.size()){
                                if (isShowDialog) {
                                    isShowDialog=false;
                                    mErrorDialog = builder.create();
                                    mErrorDialog.show();
                                    mErrorDialog.setContentView(R.layout.dialog_error);
                                    mErrorContent = (TextView) mErrorDialog.findViewById(R.id.error_content);
                                    mErrorContent.setText(mMaterbarCode.getDeltaMaterialNumber() + "-" + mMaterbarCode.getCount() + "片\n不是本架位的物料，是否变更架位");
                                    mErrorDialog.findViewById(R.id.error_cancel).setOnClickListener(CheckStockActivity.this);
                                    mErrorDialog.findViewById(R.id.error_alteration).setOnClickListener(CheckStockActivity.this);
                                }}
                            }}
                        }
                        ToastUtils.showMessage(this,"料号："+mMaterbarCode.getDeltaMaterialNumber()+"\n数量："+mMaterbarCode.getCount()+"\n单位："+mMaterbarCode.getUnit()+"\nVendor："+mMaterbarCode.getVendor()+"\n Data Code："+mMaterbarCode.getDC()+"\n PCB Code："+mMaterbarCode.getStreamNumber().substring(0,2)+"\n 流水号"+mMaterbarCode.getStreamNumber());
                    }
                    isChexNumber=0;
                    status = 2;
                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                    VibratorAndVoiceUtils. wrongVibrator (this);
                    VibratorAndVoiceUtils. wrongVoice (this);
                    SnackbarUtil.showMassage(mianCheckStockActivityView,"请重新扫描架位");
                    //ToastUtils.showMessage(this, "请重新扫描架位");
                    status = 3;
                }
                break;
            case 3:
                try {
                    mFrameLocationSuccess = (PcbFrameLocation) barCodeParseIpml.getEntity(barcode, BarCodeType.PCB_FRAME_LOCATION);
                    VibratorAndVoiceUtils.correctVibrator (this);
                    VibratorAndVoiceUtils.correctVoice(this);
                    if (mFrameLocationSuccess.getSource().equals(mFrameLocation.getSource())) {
                        getPresenter().fetchException(mFrameLocationSuccess.getSource());
                    } else {
                        cargoned.setFocusable(true);
                        if (isChexs){
                            ToastUtils.showMessage(this, "两次扫描架位不一致");
                            isChexs=false;
                        }else {
                            status = 1;
                        }
                    }

                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                    VibratorAndVoiceUtils. wrongVibrator (this);
                    VibratorAndVoiceUtils. wrongVoice (this);
                    //SnackbarUtil.showMassage(mianCheckStockActivityView,"请输入数量");
                    ToastUtils.showMessageLong(this, "扫描的架位二维码错误，请重新扫描");
                }

                break;
        }

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_check;
    }

    @OnClick(R.id.cargon_affirm)
    public void onClick() {
        if (mMaterbarCode != null) {
            if (!TextUtils.isEmpty(cargoned.getText())) {
                if (mId != 0) {
                    String ss = cargoned.getText().toString();
                    getPresenter().fetchCheckStockSuccessNumber(mId, Integer.valueOf(ss));
                    cargoned.clearFocus();
                    cargoned.setFocusable(false);

                }
            } else {
                SnackbarUtil.showMassage(mianCheckStockActivityView,"请输入数量");
               // ToastUtils.showMessage(this,"请输入数量");
            }
        } else {
            SnackbarUtil.showMassage(mianCheckStockActivityView,"请先扫描外箱条码");
            //ToastUtils.showMessage(this,"请先扫描外箱条码");
        }
    }

    @Override
    public void onSucess(List<CheckStock.RowsBean> wareHouses) {
        position=-1;
        dataList.clear();
        dataList.addAll(wareHouses);
        mAdapter.notifyDataSetChanged();


    }

    @Override
    public void onFailed(String s) {
        ToastUtils.showMessage(this, s);
    }

    @Override
    public void onCheckStockNumberSucess(String wareHouses) {
        ToastUtils.showMessage(this, wareHouses);
        dataList.get(position).setColor(false);
        if (mFrameLocation!=null){
            cargonTv.setText(mFrameLocation.getSource());
            getPresenter().fetchCheckStock(mFrameLocation.getSource());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorSucess(String wareHouses) {
        ToastUtils.showMessage(this, wareHouses);
        mResultDialog = builder.create();
        mResultDialog.show();
        mResultDialog.setContentView(R.layout.dialog_result);
        mResultContent = (TextView) mResultDialog.findViewById(R.id.result_content);
        mResultContent.setText(wareHouses);
        mResultDialog.findViewById(R.id.result_cancel).setOnClickListener(this);
        mResultDialog.findViewById(R.id.result_alteration).setOnClickListener(this);

    }

    @Override
    public void onErrorsSucess(String wareHouses) {
        SnackbarUtil.showMassage(mianCheckStockActivityView,wareHouses);
        //ToastUtils.showMessage(this, wareHouses);
        getPresenter().fetchCheckStock(mFrameLocation.getSource());
    }

    @Override
    public void onExceptionSucess(String wareHouses) {
        getPresenter().fetchSubmit(mFrameLocationSuccess.getSource());
    }

    @Override
    public void onSubmitSucess(String wareHouses) {
        cargoned.setText("");
        ToastUtils.showMessageLong(this, "盘点成功");
    }


    @Override
    public void onCheckStockSucess(String wareHouses) {
//        mErrorDialog = builder.create();
//        mErrorDialog.setContentView(R.layout.dialog_error);
//        mErrorContent = (TextView) mErrorDialog.findViewById(R.id.error_content);
//        mErrorDialog.findViewById(R.id.error_cancel).setOnClickListener(this);
//        mErrorDialog.findViewById(R.id.error_alteration).setOnClickListener(this);
//        mErrorDialog.show();


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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.error_cancel:
                if (mErrorDialog.isShowing()) {
                    isShowDialog=true;
                    mErrorDialog.dismiss();
                }
                break;
            case R.id.error_alteration:
                if (mErrorDialog.isShowing()) {
                    isShowDialog=true;
                    mErrorDialog.dismiss();
                getPresenter().fetchError(mMaterbarCode.getStreamNumber(), mFrameLocation.getSource());
                getPresenter().fetchCheckStock(mFrameLocation.getSource());}
                break;
            case R.id.result_cancel:
                if (mErrorDialog.isShowing()) {
                    mErrorDialog.dismiss();
                }
                break;
            case R.id.result_alteration:
                if (mResultDialog.isShowing()) {
                    mResultDialog.dismiss();
                    getPresenter().fetchSubmit(mFrameLocationSuccess.getSource());
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                IntentUtils.showIntent(this, MainActivity.class);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
