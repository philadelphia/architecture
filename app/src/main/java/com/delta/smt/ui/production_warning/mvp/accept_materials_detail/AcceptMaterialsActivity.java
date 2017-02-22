package com.delta.smt.ui.production_warning.mvp.accept_materials_detail;

import android.graphics.Color;
import android.net.http.LoggingEventHandler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.demacia.barcode.BarCodeIpml;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.production_warning.di.accept_materials_detail.AcceptMaterialsModule;
import com.delta.smt.ui.production_warning.di.accept_materials_detail.DaggerAcceptMaterialsCompnent;
import com.delta.smt.ui.production_warning.item.ItemAcceptMaterialDetail;
import com.delta.smt.utils.VibratorAndVoiceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Fuxiang.Zhang on 2017/2/13.
 */

public class AcceptMaterialsActivity extends BaseActivity<AcceptMaterialsPresenter>
        implements AcceptMaterialsContract.View{

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar mToolbar;
    @BindView(R.id.tv_line_type)
    TextView mTvLineType;
    @BindView(R.id.tv_material_station_num)
    TextView mTvMaterialStationNum;
    @BindView(R.id.recy_title)
    RecyclerView mRecyTitle;
    @BindView(R.id.recy_content)
    RecyclerView mRecyContent;
    @BindView(R.id.hr_scrow)
    HorizontalScrollView mHrScrow;

    private CommonBaseAdapter<ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean> adapter;
    private CommonBaseAdapter<ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean> adapter1;
    private List<ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean> dataList=new ArrayList();
    private List<ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean> dataList1=new ArrayList();

    private final String TAG="AcceptMaterialsActivity";

    private String lines;
    private String materialNumber,oldSerialNumber,newSerialNumber;
    private String streamNumber;
    private int tag=0;
    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerAcceptMaterialsCompnent.builder().appComponent(appComponent)
                .acceptMaterialsModule(new AcceptMaterialsModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        lines=getIntent().getExtras().getString(Constant.ACCEPTMATERIALSLINES);
        Log.e(TAG, "initData: "+lines );
        getPresenter().getItemDatas(lines);

    }

    @Override
    protected void initView() {

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

/*        //初始化item的适配器
        dataList1.add(new ItemAcceptMaterialDetail("250000501","06T021","0","pcs","RP1201"));
        dataList1.add(new ItemAcceptMaterialDetail("250000501","03T002","200","pcs","RP1205"));*/
        adapter1=new CommonBaseAdapter<ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean>(getContext(),dataList1) {
            @Override
            protected void convert(CommonViewHolder holder, ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean item, int position) {
                holder.setText(R.id.tv_material_id,item.getPartNumber());
                holder.setText(R.id.tv_material_station,item.getSlot());
                holder.setText(R.id.tv_remain_num,String.valueOf(item.getQuantity()));
                holder.setText(R.id.tv_unit,item.getUnit());
                holder.setText(R.id.tv_location,item.getLocation());

                holder.itemView.setBackgroundColor(Color.YELLOW);

            }

            @Override
            protected int getItemViewLayoutId(int position, ItemAcceptMaterialDetail.RowsBean.LineMaterialEntitiesBean item) {
                return R.layout.accept_material_detail_item;
            }
        };
        mRecyContent.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mRecyContent.setAdapter(adapter1);


    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_produce_warning_details;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getPresenter().requestCloseLight(String.valueOf(mTvLineType.getText()));
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
        if (keyCode==KeyEvent.KEYCODE_BACK)
        {
            getPresenter().requestCloseLight(String.valueOf(mTvLineType.getText()));
        }
        return super.onKeyDown(keyCode, event);
    }

    //扫码成功处理
    @Override
    public void onScanSuccess(String barcode) {
        //二维码识别和解析
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        Log.e(TAG, "onScanSuccess: "+barcode);

        try {
            MaterialBlockBarCode mMaterialBlockBarCode =
                    (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);

            materialNumber =mMaterialBlockBarCode.getDeltaMaterialNumber();
            streamNumber=mMaterialBlockBarCode.getStreamNumber();
            Log.e(TAG, "onScanSuccess: "+"料号：" + materialNumber);
            Log.e(TAG, "onScanSuccess: "+"流水号："+streamNumber);
            Log.e(TAG, "onScanSuccess: "+dataList1.get(0).getId());

            if(materialNumber!=null&&streamNumber!=null){
                if (tag==0){
                    if (dataList1.get(0).getPartNumber().equals(materialNumber)
                            &&dataList1.get(0).getSerialNumber().equals(streamNumber)
                            ){
                        tag++;
                        oldSerialNumber=streamNumber;

                        //扫码正确时调用的声音和震动
                        VibratorAndVoiceUtils.correctVibrator(this);
                        VibratorAndVoiceUtils. correctVoice (this);
//                        ToastUtils.showMessage(getContext(), "旧料盘匹配正确，请扫新料盘！");
                        Snackbar.make(getCurrentFocus(), "旧料盘匹配正确，请扫新料盘！", Snackbar.LENGTH_SHORT).show();
                    }else{

                        //扫码错误时调用的声音和震动
                        VibratorAndVoiceUtils. wrongVibrator (this);
                        VibratorAndVoiceUtils. wrongVibrator (this);
//                        ToastUtils.showMessage(getContext(), "旧料盘匹配错误！");
                        Snackbar.make(getCurrentFocus(), "旧料盘匹配错误", Snackbar.LENGTH_SHORT).show();
                    }
                }else{
                    if(dataList1.get(0).getPartNumber().equals(materialNumber)
                            &&!dataList1.get(0).getSerialNumber().equals(streamNumber)){
                        tag=0;
                        newSerialNumber=streamNumber;

                        //扫码正确时调用的声音和震动
                        VibratorAndVoiceUtils.correctVibrator(this);
                        VibratorAndVoiceUtils. correctVoice (this);

                        getPresenter().commitSerialNumber(oldSerialNumber,newSerialNumber);

                    }else {

                        //扫码错误时调用的声音和震动
                        VibratorAndVoiceUtils. wrongVibrator (this);
                        VibratorAndVoiceUtils. wrongVibrator (this);
//                        ToastUtils.showMessage(getContext(), "新料盘匹配错误！");
                        Snackbar.make(getCurrentFocus(), "新料盘匹配错误！", Snackbar.LENGTH_SHORT).show();
                    }
                }


            }

        } catch (EntityNotFountException e) {

            //扫码错误时调用的声音和震动
            VibratorAndVoiceUtils. wrongVibrator (this);
            VibratorAndVoiceUtils. wrongVibrator (this);

//            ToastUtils.showMessage(getContext(), "请扫描正确的料盘！");
            Snackbar.make(getCurrentFocus(), "请扫描正确的料盘！", Snackbar.LENGTH_SHORT).show();
            materialNumber = null;
            streamNumber=null;
            e.printStackTrace();
        }
    }

    //请求item列表数据
    @Override
    public void getAcceptMaterialsItemDatas(ItemAcceptMaterialDetail itemAcceptMaterialDetail) {

        mTvLineType.setText(itemAcceptMaterialDetail.getRows().getLine());
        mTvMaterialStationNum.setText(String.valueOf(itemAcceptMaterialDetail.getRows().getConnectMaterialCount()));

        dataList1.clear();
        dataList1.addAll(itemAcceptMaterialDetail.getRows().getLineMaterialEntities());
        //对adapter刷新改变
        adapter1.notifyDataSetChanged();
    }

    //网络请求失败
    @Override
    public void getItemDatasFailed(String message) {
        ToastUtils.showMessage(this,message);
    }

    //扫码数据提交成功的操作
    @Override
    public void commitSerialNumberSucess() {
//        ToastUtils.showMessage(getContext(), "新料盘匹配正确，接料完成！");
        Snackbar.make(getCurrentFocus(), "新料盘匹配正确，接料完成！", Snackbar.LENGTH_SHORT).show();
        getPresenter().getItemDatas(lines);
    }


}
