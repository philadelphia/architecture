package com.delta.smt.ui.store;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.FrameLocation;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.demacia.barcode.BarCodeIpml;
import com.delta.demacia.barcode.exception.DevicePairedNotFoundException;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ListWarning;
import com.delta.smt.ui.store.di.DaggerWarningListComponent;
import com.delta.smt.ui.store.di.WarningListModule;
import com.delta.smt.ui.store.mvp.WarningListContract;
import com.delta.smt.ui.store.mvp.WarningListPresenter;
import com.delta.smt.utils.BarCodeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by Lin.Hou on 2016-12-27.
 */

public class WarningListActivity extends BaseActiviy<WarningListPresenter> implements WarningListContract.View {
    @BindView(R.id.header_back)
    TextView headerBack;
    @BindView(R.id.header_title)
    TextView headerTitle;
    @BindView(R.id.header_setting)
    TextView headerSetting;
    @BindView(R.id.ed_work)
    TextView edWork;
    @BindView(R.id.ed_pcb)
    TextView edPcb;
    @BindView(R.id.ed_machine)
    TextView edMachine;
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(R.id.recy_contetn)
    RecyclerView recyContetn;
    @BindView(R.id.activity_mianview)
    LinearLayout activityMianview;


    List<ListWarning> mList = new ArrayList<>();
    private CommonBaseAdapter<ListWarning> mAdapter;
    private int position = 0;
    private String mWorkNumberString;
    private String mMachineString;
    private String mMaterialNumberString;
    private MaterialBlockBarCode mMaterbarCode;
    private FrameLocation mFramebarCode;


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerWarningListComponent.builder().appComponent(appComponent).warningListModule(new WarningListModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        getPresenter().fetchListWarning();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mWorkNumberString = bundle.getString("workNumber");
            mMachineString = bundle.getString("machine");
            mMaterialNumberString = bundle.getString("materialNumber");
            Log.i("info-->", mWorkNumberString);
            Log.i("info-->", mMachineString);
            Log.i("info-->", mMaterialNumberString);
        }
    }

    @Override
    protected void initView() {
        headerTitle.setText(this.getResources().getString(R.string.storetitle));
        edWork.setText(mWorkNumberString);
        edPcb.setText(mMachineString);
        edMachine.setText(mMaterialNumberString);
        List<ListWarning> list = new ArrayList<>();
        list.add(new ListWarning("", "", "", "", "", ""));

        CommonBaseAdapter<ListWarning> AdapterTitle = new CommonBaseAdapter<ListWarning>(this, list) {
            @Override
            protected void convert(CommonViewHolder holder, ListWarning item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.waring_editext));
            }

            @Override
            protected int getItemViewLayoutId(int position, ListWarning item) {
                return R.layout.item_warning;
            }
        };
        recyTitle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyTitle.setAdapter(AdapterTitle);

        mAdapter = new CommonBaseAdapter<ListWarning>(this, mList) {
            @Override
            protected void convert(CommonViewHolder holder, ListWarning item, int position) {

                holder.setText(R.id.pcb_number, item.getPcb());
                holder.setText(R.id.pcb_price, item.getJia());
                if (TextUtils.isEmpty(item.getDangqaian())) {
                    holder.setText(R.id.pcb_thenumber, "");
                }
                holder.setText(R.id.pcb_demand, item.getXuqiu());
                holder.setText(R.id.pcb_code, item.getPcbCode());
                holder.setText(R.id.pcb_time, item.getPcbCode());
                if (mMaterbarCode != null) {
                    if (mList.get(0).getPcb().equals(mMaterbarCode.getDeltaMaterialNumber())) {
                        getPresenter().fetchWarningNumber();
                    }

                }
                if (item.getDangqaian() != null) {
                    if (mFramebarCode != null) {
                        if (mFramebarCode.getSource().equals(mList.get(0).getJia())) {
                            getPresenter().fetchSuccessState();
                        }
                    }
                }
                if (item.isColor()) {
                    holder.setBackgroundColor(R.id.pcb_number, Color.YELLOW);
                    holder.setBackgroundColor(R.id.pcb_price, Color.YELLOW);
                    holder.setBackgroundColor(R.id.pcb_thenumber, Color.YELLOW);
                    holder.setBackgroundColor(R.id.pcb_demand, Color.YELLOW);
                    holder.setBackgroundColor(R.id.pcb_code, Color.YELLOW);
                    holder.setBackgroundColor(R.id.pcb_time, Color.YELLOW);
                }

            }

            @Override
            protected int getItemViewLayoutId(int position, ListWarning item) {
                return R.layout.item_warning;
            }
        };
        recyContetn.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyContetn.setAdapter(mAdapter);


    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_warning;
    }



    @Override
    public void onSucess(List<ListWarning> wareHouses) {
        mList.clear();
        mList.addAll(wareHouses);
        mList.get(0).setColor(true);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onWarningNumberSucess(List<ListWarning> wareHouses) {
        mList.clear();
        mList.addAll(wareHouses);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onWarningNumberFailed() {

    }

    @Override
    public void onSucessState(String s) {
        Snackbar.make(activityMianview,"发料成功",Snackbar.LENGTH_INDEFINITE).show();
        recyContetn.scrollToPosition(position + 1);//请求+1
        mList.get(position).setColor(false);
        mList.get(position + 1).setColor(true);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailedState(String s) {

    }

    @Override
    public void onScanSuccess(String barcode) {
        ToastUtils.showMessage(this,"--->"+barcode);
        Log.i("BARCODE","---->"+barcode);
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        try {
            if (BarCodeUtils.barCodeType(barcode) != null) {
                switch (BarCodeUtils.barCodeType(barcode)) {
                    case MATERIAL_BLOCK_BARCODE:
                        mMaterbarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);
                        break;
                    case FRAME_LOCATION:
                        mFramebarCode = (FrameLocation) barCodeParseIpml.getEntity(barcode, BarCodeType.FRAME_LOCATION);
                        break;
                }

            }

        } catch (EntityNotFountException e) {

            e.printStackTrace();
        }
    }



}
