package com.delta.smt.ui.warehouse;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.ActivityLED;
import com.delta.buletoothio.barcode.parse.entity.AddMaterialCar;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.DCTimeFormatException;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.SnackbarUtil;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.bindmaterial.BindCarBean;
import com.delta.smt.entity.bindmaterial.BindLabelBean;
import com.delta.smt.entity.bindmaterial.FinishPda;
import com.delta.smt.entity.bindmaterial.ScanMaterialPanBean;
import com.delta.smt.entity.bindmaterial.StartStoreBean;
import com.delta.smt.entity.bindmaterial.StorageBindBean;
import com.delta.smt.entity.bindmaterial.WheatherBindStart;
import com.delta.smt.entity.bindrequest.BindCarBeanRequest;
import com.delta.smt.entity.bindrequest.BindLabel;
import com.delta.smt.entity.bindrequest.BindMaterialBean;
import com.delta.smt.ui.warehouse.di.BindMaterialModule;
import com.delta.smt.ui.warehouse.di.DaggerBindMaterialComponent;
import com.delta.smt.ui.warehouse.mvp.BindMaterialContract;
import com.delta.smt.ui.warehouse.mvp.BindMaterialPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @copyright : 台达电子企业管理(上海)有限公司 (c) 2017
 * @creator : YangS
 * @create-time : 2017/7/11 11:22
 * @description :绑定料车
 */

public class BindMaterialCarActivity extends BaseActivity<BindMaterialPresenter> implements BindMaterialContract.View, BaseActivity.OnBarCodeSuccess {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.tv_store_singlenum)
    TextView tvStoreSinglenum;
    @BindView(R.id.tv_store_car)
    TextView tvStoreCar;
    @BindView(R.id.ry_car)
    RecyclerView ryCar;
    int state = 1;
    Gson gson;
    @BindView(R.id.btn_store)
    Button btnStore;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    @BindView(R.id.ll_activity)
    LinearLayout llActivity;
    private CommonBaseAdapter<StorageBindBean> storageBindBeanCommonBaseAdapter;
    private View mInflate;
    private ArrayList<StorageBindBean> beanArrayList;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerBindMaterialComponent.builder().appComponent(appComponent).bindMaterialModule(new BindMaterialModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("入库绑定");
        gson = new Gson();
        ryCar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        beanArrayList = new ArrayList<>();
        mInflate = LayoutInflater.from(this).inflate(R.layout.recy_bindmaterial, null);
    }

    @Override
    protected void initView() {

        storageBindBeanCommonBaseAdapter = new CommonBaseAdapter<StorageBindBean>(BindMaterialCarActivity.this, beanArrayList) {
            @Override
            protected void convert(CommonViewHolder holder, StorageBindBean item, int position) {
                holder.setText(R.id.tv_material_num, beanArrayList.get(position).getMaterialNo());
                holder.setText(R.id.tv_material_sum, beanArrayList.get(position).getMaterialTotal() + "");
                holder.setText(R.id.tv_material_label, beanArrayList.get(position).getMoveLabel());
                holder.setText(R.id.tv_material_shelf, beanArrayList.get(position).getShelf());

            }

            @Override
            protected int getItemViewLayoutId(int position, StorageBindBean item) {
                return R.layout.recy_bindmaterial;
            }

        };
        storageBindBeanCommonBaseAdapter.addHeaderView(mInflate);
        ryCar.setAdapter(storageBindBeanCommonBaseAdapter);

        getPresenter().judegStart();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bindmaterial;
    }

    /**
     * Toast异常信息
     *
     * @param message
     */
    @Override
    public void showMesage(String message) {
        try {
            SnackbarUtil.show(llActivity,message);
           // Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void havedStart(WheatherBindStart wheatherBindStart) {
        tvStoreSinglenum.setText(wheatherBindStart.getRows().getStorageNum());
        tvStoreCar.setText(wheatherBindStart.getRows().getCarName());
        beanArrayList.clear();
        beanArrayList.addAll(wheatherBindStart.getRows().getStorageBind());
        storageBindBeanCommonBaseAdapter.notifyDataSetChanged();
        if (wheatherBindStart.getRows().getCarName() == null) {
            showMesage("已经开始入库");
            showMesage("请扫描料车");
        } else {
            for (int i = 0; i < wheatherBindStart.getRows().getStorageBind().size(); i++) {
                if(wheatherBindStart.getRows().getStorageBind().get(i).getMoveLabel().equals("N/A")){
                    state = 3;
                    showMesage("还有料盘未绑定标签，请扫描标签");
                }
            }
            if( state != 3){
                state = 2;
                showMesage("已经开始入库");
                showMesage("请开始扫描料盘");
            }

        }


    }

    @Override
    public void noStart(WheatherBindStart wheatherBindStart) {
        getPresenter().startStore();
    }

    @Override
    public void startSucceed(StartStoreBean startStoreBean) {
        tvStoreSinglenum.setText(startStoreBean.getRows().getStorageNum());
        showMesage("开始入库");
        showMesage("请扫描料车");
    }

    @Override
    public void startFailed(StartStoreBean startStoreBean) {
        showMesage(startStoreBean.getMessage());
    }

    @Override
    public void bindCarSucceed(BindCarBean bindCarBean) {
        showMesage("料车绑定成功，请开始扫描料盘");
        tvStoreSinglenum.setText(bindCarBean.getRows().getStorageNum());
        tvStoreCar.setText(bindCarBean.getRows().getCarName());
    }

    @Override
    public void bindCarFailed(BindCarBean bindCarBean) {
        state = 1;
    }

    @Override
    public void scanMaterialSucceed(ScanMaterialPanBean scanMaterialPanBean) {
        tvStoreCar.setText(scanMaterialPanBean.getRows().getCarName());
        beanArrayList.clear();
        beanArrayList.addAll(scanMaterialPanBean.getRows().getStorageBind());
        storageBindBeanCommonBaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void scanMaterialFailed(ScanMaterialPanBean scanMaterialPanBean) {
        showMesage(scanMaterialPanBean.getMessage());
    }

    @Override
    public void bindLabelSucceed(BindLabelBean bindLabelBean) {
        beanArrayList.clear();
        beanArrayList.addAll(bindLabelBean.getRows().getStorageBind());
        storageBindBeanCommonBaseAdapter.notifyDataSetChanged();
        state = 2;
        showMesage("请继续扫描料盘");
    }

    @Override
    public void bindLabelFailed(BindLabelBean bindLabelBean) {
        showMesage(bindLabelBean.getMessage());
    }

    @Override
    public void finishedPdaSucceed(FinishPda finishPda) {
        btnStore.setVisibility(View.GONE);
        showMesage("已经成功提交本次记录");
    }

    @Override
    public void finishedPdaFailded(FinishPda finishPda) {
        showMesage(finishPda.getMessage());
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
                getPresenter().judegStart();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showEmptyView() {
        statusLayout.showEmptyView();
    }

    @Override
    public void onScanSuccess(String barcode) {
        Log.e(TAG, "onScanSuccess: " + barcode +"::;"+state);
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (state) {
            case 1:
                try {
                    AddMaterialCar addMaterialCar = (AddMaterialCar) barCodeParseIpml.getEntity(barcode, BarCodeType.ADD_MATERIAL_CAR);
                    BindCarBeanRequest bindCar = new BindCarBeanRequest(addMaterialCar.getSource());
                    BindCarBeanRequest[] bindBean = new BindCarBeanRequest[]{bindCar};
                    String bind = gson.toJson(bindBean);
                    Log.e("请扫描料车", state + "");
                    getPresenter().bindCar(bind);
                    state = 2;
                } catch (Exception e) {
                    showMesage("请扫描正确的料车");
                }
                break;
            case 2:
                if (barcode.length() > 20) {
                    try {
                        btnStore.setVisibility(View.GONE);
                        MaterialBlockBarCode materialBlockBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                        BindMaterialBean bindMaterialBean = new BindMaterialBean();
                        bindMaterialBean.setSerial_no(materialBlockBarCode.getStreamNumber());
                        bindMaterialBean.setMaterial_no(materialBlockBarCode.getDeltaMaterialNumber());
                        bindMaterialBean.setDc(materialBlockBarCode.getDC());
                        bindMaterialBean.setLc(materialBlockBarCode.getLC());
                        bindMaterialBean.setPo(materialBlockBarCode.getPO());
                        bindMaterialBean.setQty(materialBlockBarCode.getCount());
                        bindMaterialBean.setUnit(materialBlockBarCode.getUnit());
                        bindMaterialBean.setVendor(materialBlockBarCode.getVendor());
                        bindMaterialBean.setTc(materialBlockBarCode.getBusinessCode());
                        bindMaterialBean.setInv_no(materialBlockBarCode.getInvNo());
                        BindMaterialBean[] bindMaterialList = new BindMaterialBean[]{bindMaterialBean};
                        String bind = gson.toJson(bindMaterialList);
                        getPresenter().scanMaterialPan(bind);
                    }catch (DCTimeFormatException mDCException){
                        ToastUtils.showMessage(this, mDCException.getMessage());
                        VibratorAndVoiceUtils.wrongVibrator(this);
                        VibratorAndVoiceUtils.wrongVoice(this);
                    }
                    catch (EntityNotFountException e) {
                        showMesage("请扫描正确的料盘");
                    }
                } else {
                    try {
                        ActivityLED activityLED = (ActivityLED) barCodeParseIpml.getEntity(barcode, BarCodeType.ACTIVITY_LED);
                        String moveLabel = activityLED.getSource();
                        BindLabel bindLabel = new BindLabel(moveLabel);
                        BindLabel[] bindList = new BindLabel[]{bindLabel};
                        String bind = gson.toJson(bindList);
                        getPresenter().bindMoveLabel(bind);
                        btnStore.setVisibility(View.VISIBLE);
                    } catch (EntityNotFountException e) {
                        e.printStackTrace();
                        showMesage("请扫描正确的标签");
                    }

                }
                break;
            case 3:
                try {
                    ActivityLED activityLED = (ActivityLED) barCodeParseIpml.getEntity(barcode, BarCodeType.ACTIVITY_LED);
                    String moveLabel = activityLED.getSource();
                    BindLabel bindLabel = new BindLabel(moveLabel);
                    BindLabel[] bindList = new BindLabel[]{bindLabel};
                    String bind = gson.toJson(bindList);
                    getPresenter().bindMoveLabel(bind);
                    btnStore.setVisibility(View.VISIBLE);

                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                    showMesage("请扫描正确的标签");
                }
                break;
        }
    }

    @OnClick(R.id.btn_store)
    public void onViewClicked() {
        getPresenter().finishedPda();
    }


}
