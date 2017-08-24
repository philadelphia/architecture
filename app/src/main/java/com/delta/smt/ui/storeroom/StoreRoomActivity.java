package com.delta.smt.ui.storeroom;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
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
import com.delta.smt.entity.ThreeOfMaterial;
import com.delta.smt.ui.storeroom.di.DaggerStoreRoomComponent;
import com.delta.smt.ui.storeroom.di.StoreRoomModule;
import com.delta.smt.ui.storeroom.mvp.StoreRoomContract;
import com.delta.smt.ui.storeroom.mvp.StoreRoomPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.commonlibs.utils.GsonTools.createGsonListString;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class StoreRoomActivity extends BaseActivity<StoreRoomPresenter> implements StoreRoomContract.View {

    @BindView(R.id.storage_pcbed)
    EditText storagePcbed;
    @BindView(R.id.activity_warning_main)
    LinearLayout warningActivityMain;
    @BindView(R.id.storage_vendored)
    EditText storageVendored;
    @BindView(R.id.storage_datacodeed)
    EditText storageDatacodeed;
    @BindView(R.id.storage_ided)
    EditText storageIded;
    @BindView(R.id.storage_show)
    RecyclerView storageShow;
    @BindView(R.id.storage_submit)
    Button storageSubmit;
    @BindView(R.id.storage_clear)
    Button storageClear;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.storage_counted)
    EditText storageCounted;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;


    private int status=0;

    private boolean isButtonOnclick = false;
    private StringBuffer stringBuffer = new StringBuffer();

    private List<MaterialBlockBarCode> materialBlockBarCodes = new ArrayList<>();
    private List<ThreeOfMaterial> materialsList = new ArrayList<>();
    private MaterialBlockBarCode mBarCode;
    private CommonBaseAdapter<ThreeOfMaterial> mShortLisrAdapter;

    public static String myResEx= "^[0-9A-Z]{5}$";
    private String mBarcode;


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerStoreRoomComponent.builder().appComponent(appComponent).storeRoomModule(new StoreRoomModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        storagePcbed.setFocusable(false);
        storageVendored.setFocusable(false);
        storageDatacodeed.setFocusable(false);
        storageCounted.setFocusable(false);
        storageIded.setFocusable(false);
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText(getResources().getString(R.string.pcbku));
//        ThreeOfMaterial threeOfMaterial = new ThreeOfMaterial("料号", "PCB Code", "Data Code","数量");
//        materialsList.add(threeOfMaterial);
        mShortLisrAdapter = new CommonBaseAdapter<ThreeOfMaterial>(getApplicationContext(), materialsList) {
            @Override
            protected void convert(CommonViewHolder holder, ThreeOfMaterial item, int position) {

                holder.itemView.setBackgroundColor(Color.WHITE);
                holder.setText(R.id.shortList_statistics, item.getDeltaMaterialNumber());
                holder.setText(R.id.shortList_pcbcode, item.getPcbCode());
                holder.setText(R.id.shortList_datacode, item.getDataCode());
                holder.setText(R.id.shortList_count, item.getCount());

            }

            @Override
            protected int getItemViewLayoutId(int position, ThreeOfMaterial item) {
                return R.layout.item_shortlist;
            }
        };
        mShortLisrAdapter.addHeaderView(View.inflate(this,R.layout.item_shortlist,null));
        mShortLisrAdapter.setOnItemLongClickListener(new CommonBaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, Object item, int position) {
                if (materialsList.size()!=0){
                    materialsList.remove(position);
                    materialBlockBarCodes.remove(position);
                    mShortLisrAdapter.notifyDataSetChanged();
                }
            }
        });
        storageShow.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        storageShow.setAdapter(mShortLisrAdapter);


    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_warning_details;
    }


    @Override
    public void onScanSuccess(String barcode) {
        Log.i(TAG, "onScanSuccess: "+barcode);
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (status){
            case 0:
                try {
                    mBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                    Log.i(TAG, "onScanSuccess: "+mBarCode.getDeltaMaterialNumber());
                    Log.e("barcode", mBarCode.getDeltaMaterialNumber());
                    if(materialBlockBarCodes.size() ==0) {
                        Map<String,String>map=new HashMap<>();
                        map.put("boxSerial",mBarCode.getStreamNumber());
                        String jsonarrat=createGsonListString(map);
                        getPresenter().isBoxSerialExist(jsonarrat);

                    }else{
                        int i=0;
                        int  materialBlockBarCodesize=materialBlockBarCodes.size();
                        for (;i<materialBlockBarCodesize;i++){
                            if (mBarCode.getStreamNumber().equals(materialBlockBarCodes.get(i).getStreamNumber())){
                                break;
                            }


                        }
                        if (i<materialBlockBarCodesize){
                            VibratorAndVoiceUtils.wrongVibrator(this);
                            VibratorAndVoiceUtils.wrongVoice(this);
                            ToastUtils.showMessage(getApplication(),"请确认是否扫描的同一个标签!!");
                        }else {
                            VibratorAndVoiceUtils.correctVibrator (this);
                            VibratorAndVoiceUtils.correctVoice(this);
                            Map<String,String>map=new HashMap<>();
                            map.put("boxSerial",mBarCode.getStreamNumber());
                            String jsonarrat=createGsonListString(map);
                            getPresenter().isBoxSerialExist(jsonarrat);
//                            materialBlockBarCodes.add(mBarCode);
//                            ThreeOfMaterial threeOfMaterial = new ThreeOfMaterial(mBarCode.getDeltaMaterialNumber(), mBarCode.getStreamNumber().substring(0, 2), mBarCode.getDC(),mBarCode.getCount());
//                            materialsList.add(threeOfMaterial);
//                            mShortLisrAdapter.notifyDataSetChanged();
//                            storagePcbed.setText(mBarCode.getDeltaMaterialNumber());
//                            storageVendored.setText(mBarCode.getStreamNumber().substring(0, 2));
//                            storageDatacodeed.setText(mBarCode.getDC());
//                            storageCounted.setText(mBarCode.getCount());
                        }

                    }

                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                    VibratorAndVoiceUtils.wrongVibrator(this);
                    VibratorAndVoiceUtils.wrongVoice(this);
                    status=1;
                }

                break;
            case 1:
//                try {
                   // PcbFrameLocation frameCode = (PcbFrameLocation) barCodeParseIpml.getEntity(barcode, PCB_FRAME_LOCATION);
//                Pattern pattern = Pattern.compile(myResEx);
//                Matcher matcher = pattern.matcher(barcode);
//                boolean isMatcher = matcher.matches();
//                if (isMatcher) {

                mBarcode=barcode;

                    if (materialBlockBarCodes.size() != 0) {
                        if (!TextUtils.isEmpty(barcode)){
                            VibratorAndVoiceUtils.correctVibrator(this);
                            VibratorAndVoiceUtils.correctVoice(this);
                            Map<String,String>map=new HashMap<>();
                            map.put("labelCode",barcode);
                            String jsonarrat=createGsonListString(map);
                            getPresenter().isLabelExist(jsonarrat);

                        }
                    } else {
                        VibratorAndVoiceUtils.wrongVibrator(this);
                        VibratorAndVoiceUtils.wrongVoice(this);
                        storageIded.setText(null);
                        status=0;
                        SnackbarUtil.showMassage(warningActivityMain, "请先扫描外箱条码，再扫描架位");
                    }


//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                    VibratorAndVoiceUtils.wrongVibrator(this);
//                    VibratorAndVoiceUtils.wrongVoice(this);
//                }
                break;
        }


    }

    @Override
    public void storeSuccess(String s) {
        storagePcbed.setText(null);
        storageVendored.setText(null);
        storageDatacodeed.setText(null);
        storageCounted.setText(null);
        storageIded.setText(null);
        materialBlockBarCodes.clear();
        materialsList.clear();
        mShortLisrAdapter.notifyDataSetChanged();
//        ThreeOfMaterial threeOfMaterial = new ThreeOfMaterial("料号", "PCB Code", "Data Code","数量");
//        materialsList.add(threeOfMaterial);

    }

    @Override
    public void storeFaild(String s) {
        ToastUtils.showMessage(this, s);

    }

    @Override
    public void lightSuccsee(String log,String s) {
        SnackbarUtil.showMassage(warningActivityMain,log);
        for (int i=0;i<materialsList.size();i++){
        ThreeOfMaterial threeOfMaterial = materialsList.get(i);
        threeOfMaterial.setPrice(s);
        materialsList.set(materialsList.size()-1,threeOfMaterial);
        }
        mShortLisrAdapter.notifyDataSetChanged();
    }

    @Override
    public void lightfaild() {
        SnackbarUtil.showMassage(warningActivityMain,"点灯操作失败!");
       // ToastUtils.showMessage(this, "点灯操作失败");
    }

    @Override
    public void storageSuccsee() {
        SnackbarUtil.showMassage(warningActivityMain,"入料成功!");
        //ToastUtils.showMessage(this, "入料成功");
        materialBlockBarCodes.clear();
        materialsList.clear();
//        ThreeOfMaterial threeOfMaterial = new ThreeOfMaterial("料号", "PCB Code", "Data Code","数量");
//        materialsList.add(threeOfMaterial);
        mShortLisrAdapter.notifyDataSetChanged();
        stringBuffer = null;
        stringBuffer = new StringBuffer();
        storagePcbed.setText(null);
        storageVendored.setText(null);
        storageDatacodeed.setText(null);
        storageIded.setText(null);
        storageCounted.setText(null);
        storageSubmit.setBackgroundColor(this.getResources().getColor(R.color.background));
        storageSubmit.setEnabled(true);
        storageClear.setBackgroundColor(this.getResources().getColor(R.color.background));
        storageClear.setEnabled(true);
        status=0;

    }

    @Override
    public void storagefaild(String s) {
        SnackbarUtil.showMassage(warningActivityMain,s);
       // ToastUtils.showMessage(this, "入料失败");
        materialBlockBarCodes.clear();
        materialsList.clear();
//        ThreeOfMaterial threeOfMaterial = new ThreeOfMaterial("料号", "PCB Code", "Data Code","数量");
//        materialsList.add(threeOfMaterial);
        mShortLisrAdapter.notifyDataSetChanged();
        stringBuffer = null;
        stringBuffer = new StringBuffer();
        storagePcbed.setText(null);
        storageVendored.setText(null);
        storageDatacodeed.setText(null);
        storageCounted.setText(null);
        storageIded.setText(null);
        storageSubmit.setBackgroundColor(this.getResources().getColor(R.color.background));
        storageSubmit.setEnabled(true);
        storageClear.setBackgroundColor(this.getResources().getColor(R.color.background));
        storageClear.setEnabled(true);
        status=0;
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

    @OnClick({R.id.storage_clear, R.id.storage_submit})
    public void onHeaderClick(View v) {

        switch (v.getId()) {
            case R.id.storage_clear:
                storagePcbed.setText(null);
                storageVendored.setText(null);
                storageDatacodeed.setText(null);
                storageCounted.setText(null);
                materialBlockBarCodes.clear();
                materialsList.clear();
//                ThreeOfMaterial threeOfMaterial = new ThreeOfMaterial("料号", "PCB Code", "Data Code","数量");
//                materialsList.add(threeOfMaterial);
                mShortLisrAdapter.notifyDataSetChanged();

                break;
            case R.id.storage_submit:
                if (materialBlockBarCodes.size() != 0) {
                    storageSubmit.setBackgroundColor(Color.GRAY);
                    storageSubmit.setEnabled(false);
                    storageClear.setBackgroundColor(Color.GRAY);
                    storageClear.setEnabled(false);
                    if (materialBlockBarCodes!=null&&storageIded.getText()!=null){
                    getPresenter().fatchPutInStorage(materialBlockBarCodes, storageIded.getText().toString());
                    }
//                    getPresenter().fatchOnLight(materialBlockBarCodes);
                } else {
                    ToastUtils.showMessage(StoreRoomActivity.this, "请扫码后在点击确认");
                }
                break;
        }
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
    public void isBoxSerialExistSuccess() {
        if(mBarCode!=null){
            materialBlockBarCodes.add(mBarCode);
            ThreeOfMaterial threeOfMaterial = new ThreeOfMaterial(mBarCode.getDeltaMaterialNumber(), mBarCode.getStreamNumber().substring(0, 2), mBarCode.getDC(),mBarCode.getCount());
            materialsList.add(threeOfMaterial);
            mShortLisrAdapter.notifyDataSetChanged();
            storagePcbed.setText(mBarCode.getDeltaMaterialNumber());
            storageVendored.setText(mBarCode.getStreamNumber().substring(0, 2));
            storageDatacodeed.setText(mBarCode.getDC());
            storageCounted.setText(mBarCode.getCount());
        }
    }

    @Override
    public void isLabelExistSuccess() {
        storageIded.setText(mBarcode);

    }

    @Override
    public void onFaild(String s) {
        SnackbarUtil.showMassage(warningActivityMain,s);
    }
}
