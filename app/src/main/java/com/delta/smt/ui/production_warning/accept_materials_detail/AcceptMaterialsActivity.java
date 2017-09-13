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
import com.delta.smt.ui.production_warning.accept_materials_detail.di.AcceptMaterialsModule;
import com.delta.smt.entity.production_warining_item.ItemAcceptMaterialDetail;
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
 * Created by Fuxiang.Zhang on 2017/2/13.
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


    private CommonBaseAdapter<ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean> adapter;
    private CommonBaseAdapter<ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean> adapter1;
    private List<ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean> dataList = new ArrayList();
    private List<ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean> dataList1 = new ArrayList();

    private final String TAG = "AcceptMaterialsActivity";

    private String lines,work,face,material_number;
    private String materialNumber, oldSerialNumber, newSerialNumber,newBarcode;
    private String slot,feeder,serialNumber,barcode1;
    private String streamNumber;
    private int tag = 0;
    private int flag= 0;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerAcceptMaterialsCompnent.builder().appComponent(appComponent)
                .acceptMaterialsModule(new AcceptMaterialsModule(this)).build().inject(this);
    }

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
        getPresenter().getItemDatas(lines);

    }

    @Override
    protected void initView() {

        mTvLine.setText("线别："+lines);
//        mTvMaterialStationNum.setText("待接料料站："+material_number);
        mTvFace.setText("面别："+face);
        mTvWorkOrder.setText("工单号："+work);

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

                holder.itemView.setBackgroundColor(Color.YELLOW);

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

        /*扫描料盘，feedID，料站的方式接料*/
        /*if (flag==0) {

            try {
                MaterialBlockBarCode mMaterialBlockBarCode =
                        (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                materialNumber = mMaterialBlockBarCode.getDeltaMaterialNumber();
                streamNumber = mMaterialBlockBarCode.getStreamNumber();
                Log.e(TAG, "onScanSuccess: " + "料号：" + materialNumber);
                Log.e(TAG, "onScanSuccess: " + "流水号：" + streamNumber);

                if (materialNumber != null && streamNumber != null&&dataList1.get(0).getPartNumber()!=null) {

*//*                    if (dataList1.get(0).getPartNumber().equals(materialNumber)
                            ) {
                        flag++;
                        oldSerialNumber = streamNumber;

                        //扫码 正确时调用的声音和震动
                        VibratorAndVoiceUtils.correctVibrator(this);
                        VibratorAndVoiceUtils.correctVoice(this);
                        Snackbar.make(getCurrentFocus(), "旧料盘匹配正确，请扫新料盘！", Snackbar.LENGTH_SHORT).show();

                    } else {*//*
                    if (dataList1.get(0).getPartNumber().equals(materialNumber)){
                        serialNumber=streamNumber;
                        barcode1=barcode;
                        flag=flag+2;
                        //扫码正确时调用的声音和震动
                        VibratorAndVoiceUtils.correctVibrator(this);
                        VibratorAndVoiceUtils.correctVoice(this);
//                        Snackbar.make(getCurrentFocus(), "新料盘扫描成功，请扫Feeder。", Snackbar.LENGTH_SHORT).show();
                        SnackbarUtil.showRead(getRootView(this), "新料盘扫描成功，请扫Feeder。",textToSpeechManager);

                    }else {
//                        Snackbar.make(getCurrentFocus(), "与第一条的料号匹配错误！", Snackbar.LENGTH_SHORT).show();
//                        textToSpeechManager.readMessage( "与第一条的料号匹配错误！");
                        SnackbarUtil.showRead(getRootView(this), "与第一条的料号匹配错误！",textToSpeechManager);
                        flag=0;
                    }


                }

            } catch (EntityNotFountException e) {

                //扫码错误时调用的声音和震动
                VibratorAndVoiceUtils.wrongVibrator(this);
                VibratorAndVoiceUtils.wrongVoice(this);

*//*                Snackbar.make(getCurrentFocus(), "请扫描正确的料盘！", Snackbar.LENGTH_SHORT).show();
                textToSpeechManager.readMessage( "请扫描正确的料盘！");*//*
                SnackbarUtil.showRead(getRootView(this), "请扫描正确的料盘！",textToSpeechManager);
                this.materialNumber = null;
                this.streamNumber = null;
                e.printStackTrace();
            }
        }

*//*        if (flag==1){

            try {
                MaterialBlockBarCode mMaterialBlockBarCode =
                        (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                materialNumber = mMaterialBlockBarCode.getDeltaMaterialNumber();
                streamNumber = mMaterialBlockBarCode.getStreamNumber();

                if (dataList1.get(0).getPartNumber().equals(materialNumber)
                        ) {
                    flag = 0;
                    newSerialNumber = streamNumber;

                    //扫码正确时调用的声音和震动
                    VibratorAndVoiceUtils.correctVibrator(this);
                    VibratorAndVoiceUtils.correctVoice(this);

                    getPresenter().commitSerialNumber(oldSerialNumber, newSerialNumber);

                } else {

                    //扫码错误时调用的声音和震动
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    Snackbar.make(getCurrentFocus(), "新料盘匹配错误,请扫描正确的新料盘！", Snackbar.LENGTH_SHORT).show();
                }
            } catch (EntityNotFountException e) {

                //扫码错误时调用的声音和震动
                VibratorAndVoiceUtils.wrongVibrator(this);
                VibratorAndVoiceUtils.wrongVoice(this);

                Snackbar.make(getCurrentFocus(), "请扫描正确的料盘！", Snackbar.LENGTH_SHORT).show();
                materialNumber = null;
                streamNumber = null;
                e.printStackTrace();
            }



        }*//*

        else if (flag==2){
            //feeder号
            try {
                Feeder mFeeder= (Feeder) barCodeParseIpml.getEntity(barcode,BarCodeType.FEEDER);
                feeder=barcode;

                flag++;

                //扫码错误时调用的声音和震动
                VibratorAndVoiceUtils.correctVibrator(this);
                VibratorAndVoiceUtils.correctVoice(this);
*//*                Snackbar.make(getCurrentFocus(), "扫描feeder正确，请扫描料站", Snackbar.LENGTH_SHORT).show();
                textToSpeechManager.readMessage( "扫描feeder正确，请扫描料站");*//*

                SnackbarUtil.showRead(getRootView(this), "扫描feeder正确，请扫描料站",textToSpeechManager);
            } catch (EntityNotFountException e) {
                //扫码错误时调用的声音和震动
                VibratorAndVoiceUtils.wrongVibrator(this);
                VibratorAndVoiceUtils.wrongVoice(this);

*//*                Snackbar.make(getCurrentFocus(), "请扫描正确的feeder号！", Snackbar.LENGTH_SHORT).show();
                textToSpeechManager.readMessage( "请扫描正确的feeder号！");*//*

                SnackbarUtil.showRead(getRootView(this), "请扫描正确的feeder号！",textToSpeechManager);
                e.printStackTrace();

            }

        }

        else if (flag==3){

*//*            slot=barcode;
            flag=0;
            getPresenter().commitarcoderDate(dataList1.get(0).getPartNumber(),slot,feeder,lines,serialNumber,barcode1);*//*

            //料站
            try {

                MaterialStation mMaterialStation= (MaterialStation) barCodeParseIpml.getEntity(barcode,BarCodeType.MATERIAL_STATION);
                slot=barcode;
                flag=0;
                getPresenter().commitarcoderDate(dataList1.get(0).getPartNumber(),slot,feeder,lines,serialNumber,barcode1);

            } catch (EntityNotFountException e) {
                //扫码错误时调用的声音和震动
                VibratorAndVoiceUtils.wrongVibrator(this);
                VibratorAndVoiceUtils.wrongVoice(this);

*//*                Snackbar.make(getCurrentFocus(), "请扫描正确的料站！", Snackbar.LENGTH_SHORT).show();
                textToSpeechManager.readMessage( "请扫描正确的料站！");*//*

                SnackbarUtil.showRead(getRootView(this), "请扫描正确的料站！",textToSpeechManager);
                e.printStackTrace();
            }

        }*/







        /*扫描新旧料盘方式接料*/
        try {
            MaterialBlockBarCode mMaterialBlockBarCode =
                    (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
            materialNumber = mMaterialBlockBarCode.getDeltaMaterialNumber();
            streamNumber = mMaterialBlockBarCode.getStreamNumber();
            Log.e(TAG, "onScanSuccess: " + "料号：" + materialNumber);
            Log.e(TAG, "onScanSuccess: " + "流水号：" + streamNumber);
            Log.e(TAG, "onScanSuccess: " + dataList1.get(0).getId());
            Log.e(TAG, "onScanSuccess: "+"第一条料号"+dataList1.get(0).getPartNumber() );
            Log.e(TAG, "onScanSuccess: "+"第一条流水号"+dataList1.get(0).getSerialNumber() );
            if (materialNumber != null && streamNumber != null) {
                if (tag == 0) {
                    if (materialNumber.equals(dataList1.get(0).getPartNumber())
                            && streamNumber.equals(dataList1.get(0).getSerialNumber())
                            ) {
                        tag++;
                        oldSerialNumber = streamNumber;

                        //扫码正确时调用的声音和震动
                        VibratorAndVoiceUtils.correctVibrator(this);
                        VibratorAndVoiceUtils.correctVoice(this);
//                        ToastUtils.showMessage(getContext(), "旧料盘匹配正确，请扫新料盘！");
//                        Snackbar.make(getCurrentFocus(), "旧料盘匹配正确，请扫新料盘！", Snackbar.LENGTH_SHORT).show();
                        SnackbarUtil.showRead(getRootView(this), "旧料盘匹配正确，请扫新料盘！",textToSpeechManager);
                    } else {


                        //扫码错误时调用的声音和震动
                        VibratorAndVoiceUtils.wrongVibrator(this);
                        VibratorAndVoiceUtils.wrongVoice(this);
//                        ToastUtils.showMessage(getContext(), "旧料盘匹配错误！");
//                        Snackbar.make(getCurrentFocus(), "旧料盘匹配错误", Snackbar.LENGTH_SHORT).show();
                        SnackbarUtil.showRead(getRootView(this), "旧料盘匹配错误",textToSpeechManager);

                    }
                } else {
                    if (materialNumber.equals(dataList1.get(0).getPartNumber())
                            && !streamNumber.equals(dataList1.get(0).getSerialNumber())) {
                        tag = 0;
                        newSerialNumber = streamNumber;
                        newBarcode=mMaterialBlockBarCode.getSource();
                        //扫码正确时调用的声音和震动
                        VibratorAndVoiceUtils.correctVibrator(this);
                        VibratorAndVoiceUtils.correctVoice(this);
                        if (newBarcode!=null) {
                            getPresenter().commitSerialNumber(lines,materialNumber,oldSerialNumber, newSerialNumber,newBarcode);
                        }


                    } else {

                        //扫码错误时调用的声音和震动
                        VibratorAndVoiceUtils.wrongVibrator(this);
                        VibratorAndVoiceUtils.wrongVoice(this);
//                        ToastUtils.showMessage(getContext(), "新料盘匹配错误！");
//                        Snackbar.make(getCurrentFocus(), "新料盘匹配错误！", Snackbar.LENGTH_SHORT).show();
                        SnackbarUtil.showRead(getRootView(this), "新料盘匹配错误！",textToSpeechManager);
                    }
                }


            }

        }catch (DCTimeFormatException e){
            ToastUtils.showMessage(getContext(),e.getMessage());
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

//            ToastUtils.showMessage(getContext(), "请扫描正确的料盘！");
//            Snackbar.make(getCurrentFocus(), "请扫描正确的料盘！", Snackbar.LENGTH_SHORT).show();
            SnackbarUtil.showRead(getRootView(this), "请扫描正确的料盘！",textToSpeechManager);
            materialNumber = null;
            streamNumber = null;
            e.printStackTrace();
        }
    }

    //请求item列表数据
    @Override
    public void getAcceptMaterialsItemDatas(ItemAcceptMaterialDetail itemAcceptMaterialDetail) {


        if (itemAcceptMaterialDetail.getRows().getConnectMaterialCount()==0){
            getPresenter().requestCloseLight(String.valueOf(mTvLine.getText()));
            finish();
        }else {
            mTvMaterialStationNum.setText("待接料料站："+String.valueOf(itemAcceptMaterialDetail.getRows().getConnectMaterialCount()));
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
            Snackbar.make(getCurrentFocus(), message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showMessage(String message) {
        ToastUtils.showMessage(this, message);
    }


    //扫码数据提交成功的操作
    @Override
    public void commitSerialNumberSucess() {
//        ToastUtils.showMessage(getContext(), "新料盘匹配正确，接料完成！");
/*        Snackbar.make(getCurrentFocus(), "扫描正确，接料完成！", Snackbar.LENGTH_SHORT).show();
        textToSpeechManager.readMessage( "扫描正确，接料完成！");*/
        SnackbarUtil.showRead(getRootView(this),"扫描正确，接料完成！",textToSpeechManager);
        getPresenter().getItemDatas(lines);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
