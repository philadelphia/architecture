package com.delta.smt.ui.storeroom;

import android.app.AlertDialog;
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
import com.delta.buletoothio.barcode.parse.entity.PcbFrameLocation;
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
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.R.attr.id;
import static com.delta.buletoothio.barcode.parse.BarCodeType.PCB_FRAME_LOCATION;

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

    AlertDialog.Builder builder;

    private boolean isButtonOnclick = false;
    private StringBuffer stringBuffer = new StringBuffer();

    private List<MaterialBlockBarCode> materialBlockBarCodes = new ArrayList<>();
    private List<ThreeOfMaterial> materialsList = new ArrayList<>();
    private MaterialBlockBarCode mBarCode;
    private CommonBaseAdapter<ThreeOfMaterial> mShortLisrAdapter;


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
        builder = new AlertDialog.Builder(this);
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
        storageShow.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        storageShow.setAdapter(mShortLisrAdapter);


    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_warning_details;
    }


    @Override
    public void onScanSuccess(String barcode) {
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        try {
            mBarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
            VibratorAndVoiceUtils.correctVibrator (this);
            VibratorAndVoiceUtils.correctVoice(this);
            Log.e("barcode", mBarCode.getDeltaMaterialNumber());
            storagePcbed.setText(mBarCode.getDeltaMaterialNumber());
            storageVendored.setText(mBarCode.getStreamNumber().substring(0, 2));
            storageDatacodeed.setText(mBarCode.getDC());
            storageCounted.setText(mBarCode.getCount());
            if (materialBlockBarCodes.size() ==0){
                materialBlockBarCodes.add(mBarCode);
                setTextView();
            }else{
//                for (int i=0;i<materialBlockBarCodes.size();i++){
//                if (!mBarCode.getStreamNumber().equals(materialBlockBarCodes.get(i).getStreamNumber())){
                materialBlockBarCodes.add(mBarCode);
//                }
//                }
                setTextView();
            }


        } catch (EntityNotFountException e) {

            e.printStackTrace();
            try {
                PcbFrameLocation frameCode = (PcbFrameLocation) barCodeParseIpml.getEntity(barcode, PCB_FRAME_LOCATION);

                storageIded.setText(frameCode.getSource());
                if (materialBlockBarCodes.size() < 11&&materialBlockBarCodes.size()!=0) {
                    if (!TextUtils.isEmpty(storageIded.getText())){

                        getPresenter().fatchPutInStorage(materialBlockBarCodes, storageIded.getText().toString());
                }
                }else {
                    storageIded.setText(null);
                    SnackbarUtil.showMassage(warningActivityMain,"请先扫描外箱条码，再扫描架位");
                }
                VibratorAndVoiceUtils.correctVibrator (this);
                VibratorAndVoiceUtils.correctVoice(this);
            } catch (Exception e1) {
                e1.printStackTrace();
                VibratorAndVoiceUtils. wrongVibrator (this);
                VibratorAndVoiceUtils. wrongVoice (this);
            }

        }
    }

    private void setTextView() {
        if (mBarCode != null) {
            if (materialsList.size() ==0){
                ThreeOfMaterial threeOfMaterial = new ThreeOfMaterial(mBarCode.getDeltaMaterialNumber(), mBarCode.getStreamNumber().substring(0, 2), mBarCode.getDC(),mBarCode.getCount());
                materialsList.add(threeOfMaterial);
                mShortLisrAdapter.notifyDataSetChanged();
            }else if (materialsList.size() < 10) {
                ThreeOfMaterial threeOfMaterial = new ThreeOfMaterial(mBarCode.getDeltaMaterialNumber(), mBarCode.getStreamNumber().substring(0, 2), mBarCode.getDC(),mBarCode.getCount());
                materialsList.add(threeOfMaterial);
                mShortLisrAdapter.notifyDataSetChanged();

                }
            }else {
                SnackbarUtil.showMassage(warningActivityMain,"请确实是否有扫错的条码或者确认箱子上有几个条码!");
                //ToastUtils.showMessage(this,"请确实是否有扫错的条码或者确认箱子上有几个条码!");
            }

        }

//    }


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
    public void lightSuccsee(String s) {
        SnackbarUtil.showMassage(warningActivityMain,s);
        //ToastUtils.showMessage(this, "请放到固定架位");
//        storageSubmit.setBackgroundColor(this.getResources().getColor(R.color.background));
//        storageSubmit.setEnabled(false);
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
                    getPresenter().fatchOnLight(materialBlockBarCodes);
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
}
