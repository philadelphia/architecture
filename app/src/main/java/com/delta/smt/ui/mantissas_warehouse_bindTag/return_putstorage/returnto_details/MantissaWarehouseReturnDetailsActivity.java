package com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.returnto_details;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.entity.Warehouse;
import com.delta.buletoothio.barcode.parse.exception.DCTimeFormatException;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.SnackbarUtil;
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
import com.delta.smt.entity.BacKBarCode;
import com.delta.smt.entity.MantissaWarehouseReturnBean;
import com.delta.smt.entity.MantissaWarehouseReturnResult;
import com.delta.smt.entity.ManualDebitBean;
import com.delta.smt.entity.ManualmaticDebitBean;
import com.delta.smt.entity.WarehousePutinStorageBean;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.returnto_details.di.DaggerMantissaWarehouseReturnDetailsComponent;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.returnto_details.di.MantissaWarehouseReturnDetailsModule;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.returnto_details.mvp.MantissaWarehouseReturnDetailsContract;
import com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.returnto_details.mvp.MantissaWarehouseReturnDetailsPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.delta.buletoothio.barcode.parse.BarCodeType.MATERIAL_BLOCK_BARCODE;
import static com.delta.smt.R.id.bt_sheet_confirm;

/**
 * Created by Zhenyu.Liu on 2017/9/29.
 */

public class MantissaWarehouseReturnDetailsActivity extends BaseActivity<MantissaWarehouseReturnDetailsPresenter>
        implements MantissaWarehouseReturnDetailsContract.View, View.OnClickListener {

    @BindView(R.id.recy_title)
    RecyclerView mRecyTitle;
    @BindView(R.id.recy_contetn)
    RecyclerView mRecyContetn;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar mToolbar;
    private List<MantissaWarehouseReturnResult.MantissaWarehouseReturn> dataList = new ArrayList();
    private List<MantissaWarehouseReturnResult.MantissaWarehouseReturn> dataList2 = new ArrayList();
    private CommonBaseAdapter<MantissaWarehouseReturnResult.MantissaWarehouseReturn> adapter;
    private CommonBaseAdapter<MantissaWarehouseReturnResult.MantissaWarehouseReturn> adapter2;

    private int flag = 1;

    private String materialNumber;
    private String lastCar;
    private String serialNum;

    private String automaticDebit;
    private String manualDebit;
    private boolean ischeck = false;

    private String work_order;
    private String side;
    private String mS;

    private CustomPopWindow mCustomPopWindow;
    private CommonBaseAdapter<ManualDebitBean.ManualDebit> undoList_adapter;
    private List<ManualDebitBean.ManualDebit> mDebitDatas = new ArrayList<>();


    private int scan_position = -1;


    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerMantissaWarehouseReturnDetailsComponent.builder().appComponent(appComponent).mantissaWarehouseReturnDetailsModule(new MantissaWarehouseReturnDetailsModule(this)).build().inject(this);

    }


    @Override
    protected void initData() {

        work_order = getIntent().getStringExtra(Constant.WORK_ORDER);
        side = getIntent().getStringExtra(Constant.SIDE);
        Map<String, String> mMap = new HashMap<>();
        mMap.put("side", side);
        mMap.put("work_order", work_order);
        mS = GsonTools.createGsonListString(mMap);
        System.out.println(mS);
        getPresenter().getMantissaWarehouseReturn(mS);

        ischeck = SpUtil.getBooleanSF(this, "autochecked");
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        mToolbarTitle.setText("尾数仓入库");

        dataList.add(new MantissaWarehouseReturnResult.MantissaWarehouseReturn("", "", ""));
        adapter = new CommonBaseAdapter<MantissaWarehouseReturnResult.MantissaWarehouseReturn>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehouseReturnResult.MantissaWarehouseReturn item, int position) {
                holder.itemView.setBackgroundColor(getApplication().getResources().getColor(R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehouseReturnResult.MantissaWarehouseReturn item) {
                return R.layout.mantissa_return_item;
            }
        };
        mRecyTitle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyTitle.setAdapter(adapter);


        adapter2 = new CommonBaseAdapter<MantissaWarehouseReturnResult.MantissaWarehouseReturn>(this, dataList2) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehouseReturnResult.MantissaWarehouseReturn item, int position) {
                if (scan_position == -1) {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                } else if (scan_position == position) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }

                holder.setText(R.id.tv_number, item.getMaterial_no());
                holder.setText(R.id.tv_location, item.getShelf_no());
            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehouseReturnResult.MantissaWarehouseReturn item) {
                return R.layout.mantissa_return_item;
            }

        };
        mRecyContetn.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        mRecyContetn.setAdapter(adapter2);


    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mantissa_return;
    }

    @Override
    public void getSucess(List<MantissaWarehouseReturnResult.MantissaWarehouseReturn> mantissaWarehouseReturnes) {

        dataList2.clear();
        dataList2.addAll(mantissaWarehouseReturnes);
        adapter2.notifyDataSetChanged();

    }

    @Override
    public void getFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getMaterialLocationSucess() {
        flag = 2;
        //扫描成功震动并发声
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
        setItemHighLightBasedOnMID(materialNumber);
        mRecyContetn.scrollToPosition(scan_position);
        Toast.makeText(this, "已扫描料盘", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getMaterialLocationFailed(String message) {
        SnackbarUtil.showMassage(mRecyContetn, message);
        //扫描失败震动并发声
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }


    @Override
    public void getputinstrageSucess(List<MantissaWarehouseReturnResult.MantissaWarehouseReturn> mantissaWarehouseReturns) {

        dataList2.clear();
        dataList2.addAll(mantissaWarehouseReturns);
        adapter2.notifyDataSetChanged();
        flag = 1;
        scan_position = -1;
        //扫描成功震动并发声
        VibratorAndVoiceUtils.correctVibrator(this);
        VibratorAndVoiceUtils.correctVoice(this);
    }

    @Override
    public void getputinstrageFailed(String message) {
        SnackbarUtil.showMassage(mRecyContetn, message);
        //扫描失败震动并发声
        VibratorAndVoiceUtils.wrongVibrator(this);
        VibratorAndVoiceUtils.wrongVoice(this);
    }

    @Override
    public void getManualmaticDebitSucess(List<ManualDebitBean.ManualDebit> manualDebits) {
        if (manualDebits.size() == 0 || manualDebits == null) {

            ToastUtils.showMessage(this, "已完成所有扣账,暂无扣账列表！");

        } else {

            // mCustomPopWindow.showAsDropDown(mantissaWarehouseReturnAndPutStorageActivity.getToolbar());
            mDebitDatas.clear();
            mDebitDatas.addAll(manualDebits);
            undoList_adapter.notifyDataSetChanged();


        }
    }

    @Override
    public void getManualmaticDebitFailed(String message) {
        ToastUtils.showMessage(this, message);
    }

    @Override
    public void getdeductionSucess(List<ManualDebitBean.ManualDebit> manualDebits) {

        if (manualDebits.size() == 0) {
            mCustomPopWindow.dissmiss();
            ToastUtils.showMessage(this, "已完成所有扣账！");
        }
        mDebitDatas.clear();
        mDebitDatas.addAll(manualDebits);
        undoList_adapter.notifyDataSetChanged();
    }

    @Override
    public void getdeductionFailed(String message) {
        ToastUtils.showMessage(this, message);
    }

    @Override
    public void offLightsSucess() {
    }

    @Override
    public void offLightsFailed(String message) {
        ToastUtils.showMessage(this, message);
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
                //   getPresenter().getMantissaWarehouseReturn();
            }
        });

    }

    @Override
    public void showEmptyView() {

        statusLayout.showEmptyView();
        statusLayout.setEmptyClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  getPresenter().getMantissaWarehouseReturn();
            }
        });

    }


    @Subscribe
    public void scanSucceses(BacKBarCode bacKBarCode) {

        String barcode = bacKBarCode.getBarCode();

        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();

        switch (flag) {
            case 1:
                try {
                    MaterialBlockBarCode materiaBar = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, MATERIAL_BLOCK_BARCODE);
                    materialNumber = materiaBar.getDeltaMaterialNumber();
                    serialNum = materiaBar.getStreamNumber();


                    MantissaWarehouseReturnBean bindBean = new MantissaWarehouseReturnBean(materialNumber, serialNum);
                    String s = GsonTools.createGsonListString(bindBean);

                    getPresenter().getMaterialLocation(s);
                } catch (DCTimeFormatException mDCException) {
                    ToastUtils.showMessage(this, mDCException.getMessage());
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                } catch (EntityNotFountException e) {
                    SnackbarUtil.showMassage(mRecyContetn, "扫描有误，请扫描料盘！");
                }

                break;
            case 2:
                try {
                    Warehouse lastMaterialCar = (Warehouse) barCodeParseIpml.getEntity(barcode, BarCodeType.WAREHOUSE);
                    lastCar = lastMaterialCar.getSource();
                    automaticDebit = "1";
                    manualDebit = "0";

                    if (ischeck) {
                        WarehousePutinStorageBean bindBean = new WarehousePutinStorageBean(materialNumber, serialNum, lastCar, automaticDebit);
                        String s = GsonTools.createGsonListString(bindBean);
                        getPresenter().getputinstrage(s);
                    } else {
                        WarehousePutinStorageBean bindBean = new WarehousePutinStorageBean(materialNumber, serialNum, lastCar, manualDebit);
                        String s = GsonTools.createGsonListString(bindBean);
                        getPresenter().getputinstrage(s);
                    }

                } catch (EntityNotFountException e) {
                    // SnackbarUtil.showMassage(mRecyContetn, "扫描有误，请扫描架位！");

                    try {
                        MaterialBlockBarCode materiaBar = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, MATERIAL_BLOCK_BARCODE);
                        materialNumber = materiaBar.getDeltaMaterialNumber();
                        serialNum = materiaBar.getStreamNumber();

                        MantissaWarehouseReturnBean bindBean = new MantissaWarehouseReturnBean(materialNumber, serialNum);
                        String s = GsonTools.createGsonListString(bindBean);
                        getPresenter().getMaterialLocation(s);
                    } catch (DCTimeFormatException mDCException) {
                        ToastUtils.showMessage(this, mDCException.getMessage());
                        VibratorAndVoiceUtils.wrongVibrator(this);
                        VibratorAndVoiceUtils.wrongVoice(this);
                    } catch (EntityNotFountException ee) {
                        SnackbarUtil.showMassage(mRecyContetn, "此处不能识别此码！");
                    }


                }
                break;

        }


    }


    public void setItemHighLightBasedOnMID(String materialID) {
        for (int i = 0; i < dataList2.size(); i++) {
            if (dataList2.get(i).getMaterial_no().equals(materialID)) {
                scan_position = i;
                break;
            }
        }
        adapter2.notifyDataSetChanged();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_sheet_back:
                if (mCustomPopWindow != null && mCustomPopWindow.isShowing()) {
                    mCustomPopWindow.dissmiss();
                }
                break;
            case bt_sheet_confirm:
                List<ManualmaticDebitBean> mDebitCheckedData = new ArrayList<>();
                Log.e("bt_sheet_confirm", "ddddddddddddd");
                for (ManualDebitBean.ManualDebit mDebitData : mDebitDatas) {
                    if (mDebitData.isChecked()) {
                        ManualmaticDebitBean mListBean = new ManualmaticDebitBean();
                        mListBean.setMaterial_no(mDebitData.getMaterial_no());
                        mListBean.setSerial_no(mDebitData.getSerial_no());
                        mDebitCheckedData.add(mListBean);
                    }
                }
                getPresenter().deduction(GsonTools.createGsonString(mDebitCheckedData));
                break;
            case R.id.bt_sheet_select_all:
                if (mCustomPopWindow != null && mCustomPopWindow.isShowing()) {
                    if (mDebitDatas != null && mDebitDatas.size() != 0) {
                        for (ManualDebitBean.ManualDebit mDebitData : mDebitDatas) {
                            mDebitData.setChecked(true);
                        }
                        undoList_adapter.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.bt_sheet_select_cancel:
                if (mCustomPopWindow != null && mCustomPopWindow.isShowing()) {
                    if (mDebitDatas != null && mDebitDatas.size() != 0) {
                        for (ManualDebitBean.ManualDebit mDebitData : mDebitDatas) {
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
