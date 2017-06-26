package com.delta.smt.ui.checkstock;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
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

public class CheckStockActivity extends BaseActivity<CheckStockPresenter> implements CheckStockContract.View {

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


    private TextView mResultContent;
    private MaterialBlockBarCode mMaterbarCode;
    private int status = 1;
    private PcbFrameLocation mFrameLocation;
    private int mId;
    private int position;
    private PcbFrameLocation mFrameLocationSuccess;
    private boolean isShowDialog = true;
    private int isChexNumber = 0;
    private boolean isChexs = true;
    private String FrameLocation=null;
    private boolean isJudge=false;


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerCheckStockComponent.builder().appComponent(appComponent).checkStockModule(new CheckStockModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle!=null){
        FrameLocation=bundle.getString("FrameLocation");}

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
                if (item.isColor()) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                }else{
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }



            }

            @Override
            protected int getItemViewLayoutId(int position, CheckStock.RowsBean item) {
                return R.layout.item_check;
            }

        };
        recyContetn.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyContetn.setAdapter(mAdapter);
        if (!"".equals(FrameLocation)&&FrameLocation!=null){
            isJudge=true;
            cargonTv.setText(FrameLocation);
            getPresenter().fetchCheckStock(FrameLocation);
            status = 2;
        }


    }

    @Override
    public void onScanSuccess(String barcode) {
        super.onScanSuccess(barcode);
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (status) {
            case 1:
                try {
                    mFrameLocation = (PcbFrameLocation) barCodeParseIpml.getEntity(barcode, BarCodeType.PCB_FRAME_LOCATION);
                    VibratorAndVoiceUtils.correctVibrator(this);
                    VibratorAndVoiceUtils.correctVoice(this);
                    if (mFrameLocation != null) {
                        cargonTv.setText(mFrameLocation.getSource());
                        getPresenter().fetchCheckStock(mFrameLocation.getSource());
                        status = 2;
                    }
                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    SnackbarUtil.showMassage(mianCheckStockActivityView, "扫描的架位二维码错误，请重新扫描");
                    //ToastUtils.showMessage(this, "扫描的架位二维码错误，请重新扫描");
                    status = 1;
                } catch (Exception e) {

                }
                break;
            case 2:
                try {
                    mMaterbarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);

                    if (mMaterbarCode != null) {
                        if (dataList.size() != 0) {
                            for (int i = 0; i < dataList.size(); i++) {
                                if (!dataList.get(i).isCheck()) {
                                    isChexNumber++;
                                    if (mMaterbarCode.getStreamNumber().equals(dataList.get(i).getBoxSerial())) {
                                        if (Integer.valueOf(mMaterbarCode.getCount()) <= dataList.get(i).getBoundCount()) {
                                            VibratorAndVoiceUtils.correctVibrator(this);
                                            VibratorAndVoiceUtils.correctVoice(this);
                                            position = i;
                                            mId = dataList.get(i).getId();
                                            dataList.get(i).setColor(true);
                                            dataList.get(i).setCheck(true);
                                            mAdapter.notifyDataSetChanged();
                                            getPresenter().fetchCheckStockSuccessNumber(dataList.get(i).getId(), Integer.valueOf(mMaterbarCode.getCount()));
                                            break;
                                        } else {
                                            VibratorAndVoiceUtils.wrongVibrator(this);
                                            VibratorAndVoiceUtils.wrongVoice(this);
                                            mId = dataList.get(i).getId();
                                            cargoned.setFocusable(true);
                                            cargoned.setFocusableInTouchMode(true);
                                            cargoned.requestFocus();
                                            cargoned.findFocus();
                                            position = i;
                                            dataList.get(i).setColor(true);
                                            dataList.get(i).setCheck(true);
                                            mAdapter.notifyDataSetChanged();
                                            SnackbarUtil.showMassage(mianCheckStockActivityView, "请查数后输入数量!");
                                            //ToastUtils.showMessage(CheckStockActivity.this,"请查数后输入数量!");
                                            break;
                                        }
                                    } else {
                                        if (isChexNumber == dataList.size()) {
                                            if (isShowDialog) {
//                                            isShowDialog = false;
                                                getPresenter().fetchJudgeSuceess(mMaterbarCode.getStreamNumber());
                                            }

                                        }
                                    }
                                }
                            }
                        } else {
                            VibratorAndVoiceUtils.correctVibrator(this);
                            VibratorAndVoiceUtils.correctVoice(this);
                            getPresenter().fetchJudgeSuceess(mMaterbarCode.getStreamNumber());
                        }
                        ToastUtils.showMessage(this, "料号：" + mMaterbarCode.getDeltaMaterialNumber() + "\n数量：" + mMaterbarCode.getCount() + "\n单位：" + mMaterbarCode.getUnit() + "\nVendor：" + mMaterbarCode.getVendor() + "\n Data Code：" + mMaterbarCode.getDC() + "\n PCB Code：" + mMaterbarCode.getStreamNumber().substring(0, 2) + "\n 流水号" + mMaterbarCode.getStreamNumber());
                    }
                    isChexNumber = 0;
                    status = 2;
                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    SnackbarUtil.showMassage(mianCheckStockActivityView, "请重新扫描架位");
                    //ToastUtils.showMessage(this, "请重新扫描架位");
                    status = 3;
                } catch (Exception e) {

                }
                break;
            case 3:
                try {
                    mFrameLocationSuccess = (PcbFrameLocation) barCodeParseIpml.getEntity(barcode, BarCodeType.PCB_FRAME_LOCATION);

                        if (mFrameLocationSuccess.getSource().equals(cargonTv.getText())) {
                            VibratorAndVoiceUtils.correctVibrator(this);
                            VibratorAndVoiceUtils.correctVoice(this);
                            getPresenter().fetchException(mFrameLocationSuccess.getSource());
                        } else {
                            cargoned.setFocusable(true);
                            if (isChexs) {
                                VibratorAndVoiceUtils.wrongVibrator(this);
                                VibratorAndVoiceUtils.wrongVoice(this);
                                ToastUtils.showMessage(this, "两次扫描架位不一致");
                                isChexs = false;
                            } else {
                                status = 1;
                            }
                        }

                    } catch(EntityNotFountException e){
                        e.printStackTrace();
                        VibratorAndVoiceUtils.wrongVibrator(this);
                        VibratorAndVoiceUtils.wrongVoice(this);
                        //SnackbarUtil.showMassage(mianCheckStockActivityView,"请输入数量");
                        status = 2;
                        ToastUtils.showMessageLong(this, "扫描的架位二维码错误，请重新扫描");
                    }catch(Exception e){

                    }

                    break;

        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_check;
    }


    @Override
    public void onSucess(List<CheckStock.RowsBean> wareHouses) {
        position = -1;
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
        dataList.get(position).setColor(false);
        getPresenter().fetchCheckStock(cargonTv.getText().toString());
//        if (isJudge) {
//            cargonTv.setText(FrameLocation);
//            getPresenter().fetchCheckStock(FrameLocation);
//        }else {
//            cargonTv.setText(mFrameLocation.getSource());
//            getPresenter().fetchCheckStock(mFrameLocation.getSource());
//        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorSucess(String wareHouses) {
        //ToastUtils.showMessage(this, wareHouses);
       AlertDialog mResultDialog = new AlertDialog.Builder(this).setTitle("盘点结果").setMessage(wareHouses).setPositiveButton("继续盘点本架位", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                    status=2;
            }
        }).setNegativeButton("完成本架位的盘点", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dataList.clear();
                mAdapter.notifyDataSetChanged();
                cargonTv.setText("");
                status=1;
            }
        }).create();
        mResultDialog.show();


    }

    @Override
    public void onErrorsSucess(String wareHouses) {
        SnackbarUtil.showMassage(mianCheckStockActivityView, wareHouses);
        //ToastUtils.showMessage(this, wareHouses);
        if (isJudge){
            getPresenter().fetchCheckStock(FrameLocation);}else {
            getPresenter().fetchCheckStock(mFrameLocation.getSource());
        }

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
    public void JudgeSuceess(String s) {
//        isShowDialog = true;
       AlertDialog mErrorDialog =new AlertDialog.Builder(this).setTitle("盘点异常").setMessage(mMaterbarCode.getDeltaMaterialNumber() + "-" + mMaterbarCode.getCount() + "片\n不是本架位的物料，是否变更架位").setPositiveButton("取消", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               isShowDialog = true;
               dialog.dismiss();
           }
       }).setNegativeButton("变更架位", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               isShowDialog = true;
               dialog.dismiss();
               if (isJudge){
                   getPresenter().fetchError(mMaterbarCode.getStreamNumber(), FrameLocation);
                   getPresenter().fetchCheckStock(FrameLocation);
               }else {
                   getPresenter().fetchError(mMaterbarCode.getStreamNumber(), mFrameLocation.getSource());
                   getPresenter().fetchCheckStock(mFrameLocation.getSource());
               }
           }
       }).create();
        mErrorDialog.show();

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            AlertDialog mRollbackDialog = new AlertDialog.Builder(this).setTitle("提示").setMessage("请确认是否回退到上一个界面?").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    IntentUtils.showIntent(CheckStockActivity.this, StartWorkAndStopWorkActivity.class);



                }
            }).create();
            Button positive=mRollbackDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positive.setBackgroundColor(ContextCompat.getColor(this,R.color.background));
            Button negative=mRollbackDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            negative.setBackgroundColor(ContextCompat.getColor(this,R.color.background));
            mRollbackDialog.show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @OnClick({R.id.cargon_affirm})
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.cargon_affirm:
                if (mMaterbarCode != null) {
                    if (!TextUtils.isEmpty(cargoned.getText())) {
                        if (mId != 0) {
                            String ss = cargoned.getText().toString();
                            getPresenter().fetchCheckStockSuccessNumber(mId, Integer.valueOf(ss));
                            cargoned.setText(null);
                            cargoned.clearFocus();
                            cargoned.setFocusable(false);

                        }
                    } else {
                        SnackbarUtil.showMassage(mianCheckStockActivityView, "请输入数量");
                        // ToastUtils.showMessage(this,"请输入数量");
                    }
                } else {
                    SnackbarUtil.showMassage(mianCheckStockActivityView, "请先扫描外箱条码");
                    //ToastUtils.showMessage(this,"请先扫描外箱条码");
                }
                break;

        }
    }
}
