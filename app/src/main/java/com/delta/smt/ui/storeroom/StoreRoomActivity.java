package com.delta.smt.ui.storeroom;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
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
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.delta.buletoothio.barcode.parse.BarCodeType.PCB_FRAME_LOCATION;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class StoreRoomActivity extends BaseActivity<StoreRoomPresenter> implements StoreRoomContract.View {

    @BindView(R.id.storage_pcbed)
    EditText storagePcbed;
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
        storagePcbed.setFocusable(true);
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
                holder.setText(R.id.shortList_statistics, item.getDeltaMaterialNumber());
                holder.setText(R.id.shortList_pcbcode, item.getDeltaMaterialNumber().substring(0, 2));
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
            Log.e("barcode", mBarCode.getDeltaMaterialNumber());
            storagePcbed.setText(mBarCode.getDeltaMaterialNumber());
            storageVendored.setText(mBarCode.getDeltaMaterialNumber().substring(0, 2));
            storageDatacodeed.setText(mBarCode.getDC());
            storageCounted.setText(mBarCode.getCount());
            if (materialBlockBarCodes.size() < 3) {
                materialBlockBarCodes.add(mBarCode);
                setTextView();
            }


        } catch (EntityNotFountException e) {

            e.printStackTrace();
            try {
                PcbFrameLocation frameCode = (PcbFrameLocation) barCodeParseIpml.getEntity(barcode, PCB_FRAME_LOCATION);
                storageIded.setText(frameCode.getSource());
                Log.e("info", frameCode.getSource());
                if (materialBlockBarCodes.size() < 3) {
                    if (!TextUtils.isEmpty(storageIded.getText()))
                        getPresenter().fatchPutInStorage(materialBlockBarCodes, storageIded.getText().toString());
                }
            } catch (EntityNotFountException e1) {

                e1.printStackTrace();
            }

        }
    }

    private void setTextView() {
        if (mBarCode != null) {
            if (materialsList.size() < 4) {
                ThreeOfMaterial threeOfMaterial = new ThreeOfMaterial(mBarCode.getDeltaMaterialNumber(), mBarCode.getDeltaMaterialNumber().substring(0, 2), mBarCode.getDC(),mBarCode.getCount());
                materialsList.add(threeOfMaterial);
                mShortLisrAdapter.notifyDataSetChanged();
            }else {
                ToastUtils.showMessage(this,"请确实是否有扫错的条码或者确认箱子上有几个条码!");
            }

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
    public void lightSuccsee() {
        ToastUtils.showMessage(this, "请放到固定架位");
        storageSubmit.setBackgroundColor(this.getResources().getColor(R.color.background));
        storageSubmit.setEnabled(false);
    }

    @Override
    public void lightfaild() {
        ToastUtils.showMessage(this, "点灯操作失败");
    }

    @Override
    public void storageSuccsee() {
        ToastUtils.showMessage(this, "入料成功");
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
        storageSubmit.setBackgroundColor(this.getResources().getColor(R.color.background));
        storageSubmit.setEnabled(true);
    }

    @Override
    public void storagefaild() {
        ToastUtils.showMessage(this, "入料失败");
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
                    storageClear.setBackgroundColor(Color.GRAY);
                    storageClear.setEnabled(false);
                    getPresenter().fatchOnLight(materialBlockBarCodes);
                } else {
                    ToastUtils.showMessage(StoreRoomActivity.this, "请扫码后在点击确认");
                }
                break;
        }
    }

}
