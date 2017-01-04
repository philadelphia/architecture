package com.delta.smt.ui.checkstock;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.CheckStock;
import com.delta.smt.ui.checkstock.di.CheckStockModule;
import com.delta.smt.ui.checkstock.di.DaggerCheckStockComponent;
import com.delta.smt.ui.checkstock.mvp.CheckStockContract;
import com.delta.smt.ui.checkstock.mvp.CheckStockPresenter;
import com.delta.smt.utils.BarCodeUtils;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.widget.Toast.makeText;
import static com.delta.smt.R.id.recy_contetn;
import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class CheckStockActivity extends BaseActiviy<CheckStockPresenter> implements CheckStockContract.View, View.OnClickListener {
    @BindView(R.id.header_back)
    TextView headerBack;
    @BindView(R.id.header_title)
    TextView headerTitle;
    @BindView(R.id.header_setting)
    TextView headerSetting;
    @BindView(R.id.cargoned)
    EditText cargoned;
    @BindView(R.id.cargon_affirm)
    Button cargonAffirm;
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(recy_contetn)
    RecyclerView recyContetn;
    private List<CheckStock> dataList= new ArrayList<>();
    private CommonBaseAdapter<CheckStock> mAdapter;
    private TextView mErrorContent;
    private AlertDialog.Builder builder=new AlertDialog.Builder(this);
    private AlertDialog mErrorDialog;
    private AlertDialog mResultDialog;
    private TextView mResultContent;
    private MaterialBlockBarCode mMaterbarCode;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerCheckStockComponent.builder().appComponent(appComponent).checkStockModule(new CheckStockModule(this)) .build().inject(this);
    }

    @Override
    protected void initData() {
    getPresenter().fetchCheckStock();


    }

    @Override
    protected void initView() {
        List<CheckStock>list=new ArrayList<>();
        list.add(new CheckStock("","","","",""));
        CommonBaseAdapter<CheckStock> mAdapterTitle = new CommonBaseAdapter<CheckStock>(getContext(), list) {
            @Override
            protected void convert(CommonViewHolder holder, CheckStock item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.waring_editext));
            }

            @Override
            protected int getItemViewLayoutId(int position, CheckStock item) {
                return R.layout.item_check;
            }
        };
        recyTitle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyTitle.setAdapter(mAdapterTitle);

        mAdapter= new CommonBaseAdapter<CheckStock>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, CheckStock item, int position) {
                holder.setText(R.id.statistics,item.getPcb());
                holder.setText(R.id.statistics_id,item.getLiu());
                holder.setText(R.id.statistics_pcbnumber,item.getNumber());
                if (TextUtils.isEmpty(item.getCheck())){
                holder.setText(R.id.statistics_number,item.getCheck());
                }
                holder.setText(R.id.statistics_storenumber,item.getZhuangtai());

                if (mMaterbarCode!=null){
                    for (int i=0;i<dataList.size();i++){
                    if (mMaterbarCode.getDeltaMaterialNumber().equals(dataList.get(i).getPcb())){
                        getPresenter().fetchCheckStockSuccessNumber();
                    }
                }}

            }

            @Override
            protected int getItemViewLayoutId(int position, CheckStock item) {
                return R.layout.item_check;
            }
        };
        recyContetn.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyContetn.setAdapter(mAdapter);


    }

    @Override
    public void onScanSuccess(String barcode) {
        super.onScanSuccess(barcode);
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        try {
        switch (BarCodeUtils.barCodeType(barcode)){
            case MATERIAL_BLOCK_BARCODE:
              mMaterbarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, BarCodeType.MATERIAL_BLOCK_BARCODE);  }
                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                }

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_check;
    }

    @OnClick(R.id.cargon_affirm)
    public void onClick(){
        if (mMaterbarCode!=null){
            if (TextUtils.isEmpty(cargoned.getText())){
                getPresenter().fetchCheckStockSuccessNumber();
            }else {
                Toast toast=Toast.makeText(this,"请输入数量",Toast.LENGTH_SHORT);
                View view=toast.getView();
                view.setBackgroundColor(Color.WHITE);
                toast.setView(view);
                toast.show();
            }
        }else {
            Toast toast=Toast.makeText(this,"请先扫描外箱条码",Toast.LENGTH_SHORT);
            View view=toast.getView();
            view.setBackgroundColor(Color.WHITE);
            toast.setView(view);
            toast.show();
        }
    }
    @Override
    public void onSucess(List<CheckStock> wareHouses) {
        dataList.clear();
        dataList.addAll(wareHouses);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onCheckStockNumberSucess(List<CheckStock> wareHouses) {

    }

    @Override
    public void onCheckStockNumberFailed() {

    }

    @Override
    public void onCheckStockSucess(String wareHouses) {
        mErrorDialog= builder.create();
        mErrorDialog.setContentView(R.layout.dialog_error);
        mErrorContent= (TextView) mErrorDialog.findViewById(R.id.error_content);
        mErrorDialog.findViewById(R.id.error_cancel).setOnClickListener(this);
        mErrorDialog.findViewById(R.id.error_alteration).setOnClickListener(this);
        mErrorDialog.show();
    }

    @Override
    public void onCheckStockFailed() {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.error_cancel:
                if (mErrorDialog.isShowing()){
                    mErrorDialog.dismiss();}
                break;
            case R.id.error_alteration:
                mResultDialog= builder.create();
                mResultDialog.setContentView(R.layout.dialog_result);
                mResultContent= (TextView) mResultDialog.findViewById(R.id.result_content);
                mResultDialog.findViewById(R.id.result_cancel).setOnClickListener(this);
                mResultDialog.findViewById(R.id.result_alteration).setOnClickListener(this);
                mResultDialog.show();
                break;
            case R.id.result_cancel:

                break;
            case R.id.result_alteration:
                if (mResultDialog.isShowing()){
                    mResultDialog.dismiss();
                }
                break;
        }
    }

}
