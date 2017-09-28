package com.delta.smt.ui.production_warning.accept_materials_detail;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.DCTimeFormatException;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.SnackbarUtil;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.production_warining_item.ItemAcceptMaterialDetail;
import com.delta.smt.ui.production_warning.accept_materials_detail.di.AcceptMaterialsModule;
import com.delta.smt.ui.production_warning.accept_materials_detail.di.DaggerAcceptMaterialsCompnent;
import com.delta.smt.ui.production_warning.accept_materials_detail.mvp.AcceptMaterialsContract;
import com.delta.smt.ui.production_warning.accept_materials_detail.mvp.AcceptMaterialsPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;
import com.delta.ttsmanager.TextToSpeechManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.delta.smt.base.BaseApplication.getContext;


/**
 * @author : Fuxiang.Zhang
 * @description :接料详情页面，有扫码操作
 * @date : 2017/9/18 16:01
 */

public class AcceptMaterialsActivity extends BaseActivity<AcceptMaterialsPresenter>
        implements AcceptMaterialsContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar mToolbar;
    @BindView(R.id.tv_material_station_num)
    TextView mTvMaterialStationNum;
    @BindView(R.id.recy_title)
    RecyclerView mRecyTitle;
    @BindView(R.id.recy_content)
    RecyclerView mRecyContent;
    @BindView(R.id.hr_scrow)
    HorizontalScrollView mHrScrow;
    @BindView(R.id.tv_line)
    TextView mTvLine;
    @BindView(R.id.tv_face)
    TextView mTvFace;
    @BindView(R.id.tv_work_order)
    TextView mTvWorkOrder;

    @Inject
    TextToSpeechManager textToSpeechManager;
    @BindView(R.id.edt_materialNumber)
    EditText edtMaterialNumber;
    @BindView(R.id.edt_serialNumber)
    EditText edtSerialNumber;


    private CommonBaseAdapter<ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean> adapter;
    private CommonBaseAdapter<ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean> adapter1;
    private List<ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean> dataList = new ArrayList();
    private List<ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean> dataList1 = new ArrayList();

    private final String TAG = "AcceptMaterialsActivity";

    private String lines, work, face, material_number;
    private String materialNumber, oldSerialNumber, newSerialNumber, newBarcode, newMaterialNumber;
    private String oldMaterialNumber;
    private String slot, feeder, serialNumber, barcode1;
    private String streamNumber;
    private int tag = 0;
    private int flag = 0;
    private int index = -1;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerAcceptMaterialsCompnent.builder().appComponent(appComponent)
                .acceptMaterialsModule(new AcceptMaterialsModule(this)).build().inject(this);
    }

    //获得跟view
    private View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    @Override
    protected void initData() {
        lines = getIntent().getExtras().getString(Constant.ACCEPT_MATERIALS_LINES);
        work = getIntent().getExtras().getString(Constant.ACCEPT_MATERIALS_WORK);
        face = getIntent().getExtras().getString(Constant.ACCEPT_MATERIALS_FACE);
//        material_number = getIntent().getExtras().getString(Constant.ACCEPT_MATERIALS_NUM);
        Log.e(TAG, "initData: " + lines);
        getPresenter().getAllItems(lines);

    }

    @Override
    protected void initView() {

        mTvLine.setText("线别：" + lines);
//        mTvMaterialStationNum.setText("待接料料站："+material_number);
        mTvFace.setText("面别：" + face);
        mTvWorkOrder.setText("工单号：" + work);

        //初始化titile
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText("贴片机接料");

        //初始化item标题栏的适配器
        dataList.add(new ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean());
        adapter = new CommonBaseAdapter<ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean item, int position) {
                holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.c_efefef));
            }

            @Override
            protected int getItemViewLayoutId(int position, ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean item) {
                return R.layout.accept_material_detail_item;
            }
        };
        mRecyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyTitle.setAdapter(adapter);

        //初始化item的适配器
        adapter1 = new CommonBaseAdapter<ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean>(getContext(), dataList1) {
            @Override
            protected void convert(CommonViewHolder holder, ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean item, int position) {
                holder.setText(R.id.tv_material_id, item.getPartNumber());
                holder.setText(R.id.tv_material_station, item.getSlot());
                holder.setText(R.id.tv_remain_num, String.valueOf(item.getQuantity()));
//                holder.setText(R.id.tv_unit,item.getUnit());
                holder.setText(R.id.tv_location, item.getLocation());
                if (index == position) {
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                }

            }

            @Override
            protected int getItemViewLayoutId(int position, ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean item) {
                return R.layout.accept_material_detail_item;
            }
        };
        mRecyContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyContent.setAdapter(adapter1);


    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_produce_warning_details;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getPresenter().requestCloseLight(String.valueOf(mTvLine.getText()));
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //返回键监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //关灯操作
            getPresenter().requestCloseLight(String.valueOf(mTvLine.getText()));
        }
        return super.onKeyDown(keyCode, event);
    }

    //扫码成功处理
    @Override
    public void onScanSuccess(String barcode) {
        textToSpeechManager.stop();
        //二维码识别和解析
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        Log.e(TAG, "onScanSuccess: " + barcode);

        /**
         * 扫描新旧料盘方式的接料
         */
        try {
            MaterialBlockBarCode mMaterialBlockBarCode =
                    (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
            materialNumber = mMaterialBlockBarCode.getDeltaMaterialNumber();
            streamNumber = mMaterialBlockBarCode.getStreamNumber();
            Log.e(TAG, "onScanSuccess: " + "料号：" + materialNumber);
            Log.e(TAG, "onScanSuccess: " + "流水号：" + streamNumber);
            edtMaterialNumber.setText(materialNumber);
            edtSerialNumber.setText(streamNumber);
            if (tag == 0) {
                if (isOldMaterial(materialNumber, streamNumber)) {
                    Log.i(TAG, "onScanSuccess: 旧料盘");
                    tag = 1;
                    oldSerialNumber = streamNumber;
                    oldMaterialNumber = materialNumber;
                    //扫码正确时调用的声音和震动
                    VibratorAndVoiceUtils.correctVibrator(this);
                    VibratorAndVoiceUtils.correctVoice(this);
                    adapter1.notifyDataSetChanged();
                    if (isNeedReplaceMaterial()){
                        SnackbarUtil.showRead(getRootView(this), "料站需要换料，请继续扫描新料盘！", textToSpeechManager);
                    } else {
                        SnackbarUtil.showRead(getRootView(this), "料站需要接料，请继续扫描新料盘！", textToSpeechManager);
                    }

                } else {
                    //扫码错误时调用的声音和震动
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    SnackbarUtil.showRead(getRootView(this), "模组上不存在该料盘或不需要接料，请重新扫描或刷新页面！", textToSpeechManager);
                }
            } else {
                //扫描新料盘
                if (isOldMaterial(materialNumber, streamNumber)) {
                    tag = 1;
                    oldSerialNumber = streamNumber;
                    oldMaterialNumber = materialNumber;
                    //扫码正确时调用的声音和震动
                    VibratorAndVoiceUtils.correctVibrator(this);
                    VibratorAndVoiceUtils.correctVoice(this);
                    adapter1.notifyDataSetChanged();
                    if (isNeedReplaceMaterial()){
                        SnackbarUtil.showRead(getRootView(this), "旧料盘匹配正确，请继续扫描新料盘进行换料！", textToSpeechManager);
                    } else {
                        SnackbarUtil.showRead(getRootView(this), "旧料盘匹配正确，请继续扫描新料盘进行接料！", textToSpeechManager);
                    }
                } else {
                    newMaterialNumber = materialNumber;
                    newSerialNumber = streamNumber;
                    newBarcode = mMaterialBlockBarCode.getSource();
                    //扫码正确时调用的声音和震动
                    VibratorAndVoiceUtils.correctVibrator(this);
                    VibratorAndVoiceUtils.correctVoice(this);
                    Log.i(TAG, "onScanSuccess: 是新料盘，开始接料");
                    if (newBarcode != null) {
                        Log.i(TAG, "onScanSuccess:  开始发送请求");
                        Log.i(TAG, "onScanSuccess: newBarcode ==" + newBarcode);
                        Log.i(TAG, "onScanSuccess:lines=  " + lines);
                        Log.i(TAG, "onScanSuccess:oldMaterialNumber=  " + oldMaterialNumber);
                        Log.i(TAG, "onScanSuccess:oldStreamNumber=  " + oldSerialNumber);

                        Log.i(TAG, "onScanSuccess:newMaterialNumber=  " + newMaterialNumber);
                        Log.i(TAG, "onScanSuccess:newSerialNumber=  " + newSerialNumber);
                        getPresenter().commitSerialNumber(lines, oldMaterialNumber, oldSerialNumber, newMaterialNumber, newSerialNumber, newBarcode);
                    }
                    tag = 0;

                }
            }

        } catch (DCTimeFormatException e) {
            ToastUtils.showMessage(getContext(), e.getMessage());
            materialNumber = null;
            streamNumber = null;
            e.printStackTrace();

            //扫码错误时调用的声音和震动
            VibratorAndVoiceUtils.wrongVibrator(getContext());
            VibratorAndVoiceUtils.wrongVoice(getContext());

        } catch (EntityNotFountException e) {

            //扫码错误时调用的声音和震动
            VibratorAndVoiceUtils.wrongVibrator(this);
            VibratorAndVoiceUtils.wrongVoice(this);

            SnackbarUtil.showRead(getRootView(this), "请扫描正确的料盘！", textToSpeechManager);
            materialNumber = null;
            streamNumber = null;
            e.printStackTrace();
        }
    }

    private boolean isOldMaterial(String materialNumber, String streamNumber) {
        Log.i(TAG, "isOldMaterial: materialNumber==" + materialNumber + "streamNumber==" + streamNumber);
        ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean lineMaterialEntitiesBean = null;
        for (int i = 0; i < dataList1.size(); i++) {
            lineMaterialEntitiesBean = dataList1.get(i);
            if (lineMaterialEntitiesBean.getSerialNumber().equalsIgnoreCase(streamNumber) && lineMaterialEntitiesBean.getPartNumber().equalsIgnoreCase(materialNumber)) {
                index = i;
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return true--换料
     *
     */
    private boolean isNeedReplaceMaterial() {
        Log.i(TAG, "isOldMaterial: materialNumber==" + materialNumber + "streamNumber==" + streamNumber);
        ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean lineMaterialEntitiesBean = dataList1.get(index);
        return lineMaterialEntitiesBean.getMode() == 1 ? true : false;
    }

    /**
     * @param materialNumber 料号
     * @param streamNumber  流水码
     * @return true--扫描的是新料盘，false--不是新料盘
     */
    private boolean isNewMaterial(String materialNumber, String streamNumber) {
        for (ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean lineMaterialEntitiesBean : dataList1) {
            if (!lineMaterialEntitiesBean.getSerialNumber().equalsIgnoreCase(streamNumber) && lineMaterialEntitiesBean.getPartNumber().equalsIgnoreCase(materialNumber)) {
                return true;
            }

        }
        return false;
    }


    //请求item列表数据
    @Override
    public void getAcceptMaterialsItemDatas(ItemAcceptMaterialDetail itemAcceptMaterialDetail) {
        index = -1;
        if (itemAcceptMaterialDetail.getRows().getConnectMaterialCount() == 0) {
            getPresenter().requestCloseLight(String.valueOf(mTvLine.getText()));
            finish();
        } else {
            mTvMaterialStationNum.setText("待接料料站数：" + String.valueOf(itemAcceptMaterialDetail.getRows().getConnectMaterialCount()));
            dataList1.clear();
            dataList1.addAll(itemAcceptMaterialDetail.getRows().getLineMaterialEntities());
            //对adapter刷新改变
            adapter1.notifyDataSetChanged();

        }

    }

    //网络请求失败
    @Override
    public void getItemDatasFailed(String message) {
//        ToastUtils.showMessage(this,message);
        if ("Error".equals(message)) {
            Snackbar.make(getCurrentFocus(), this.getString(R.string.server_error_message), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(getCurrentFocus(), "料站接料失败，" + message + "，请重新扫描!", Snackbar.LENGTH_LONG).show();
        }
    }


    @Override
    public void showMessage(String message) {
        ToastUtils.showMessage(this, message);
    }


    //扫码数据提交成功的操作
    /**
     *
     * @param rows  剩余的待换料或接料的数量
     *
     */

    @Override
    public void commitSerialNumberSuccess(int rows) {
        SnackbarUtil.showRead(getRootView(this), "料站接料完成，请继续扫描旧料盘进行接料！", textToSpeechManager);
        index = -1;
//        该工单接料或换料未完成。
        if (rows >0){
            getPresenter().getAllItems(lines);
        }else if (rows ==0){    //        该工单接料或换料完成。
            finish();
        }


    }

    @Override
    public void onNewMaterialNotExists(String message) {
        tag = 1;
        SnackbarUtil.showRead(getRootView(this), message, textToSpeechManager);
    }

    @Override
    public void onOldMaterialNotExists(String message) {
        refresh();
    }

    private void refresh() {
        getPresenter().getAllItems(lines);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
