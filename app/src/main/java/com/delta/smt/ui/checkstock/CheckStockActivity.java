package com.delta.smt.ui.checkstock;

import android.app.AlertDialog;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.entity.FrameLocation;
import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.CheckStock;
import com.delta.smt.entity.CheckStockDemo;
import com.delta.smt.ui.checkstock.di.CheckStockModule;
import com.delta.smt.ui.checkstock.di.DaggerCheckStockComponent;
import com.delta.smt.ui.checkstock.mvp.CheckStockContract;
import com.delta.smt.ui.checkstock.mvp.CheckStockPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.buletoothio.barcode.parse.BarCodeType.FRAME_LOCATION;
import static com.delta.buletoothio.barcode.parse.BarCodeType.MATERIAL_BLOCK_BARCODE;
import static com.delta.smt.R.id.recy_contetn;
import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class CheckStockActivity extends BaseActivity<CheckStockPresenter> implements CheckStockContract.View, View.OnClickListener {

    @BindView(R.id.cargoned)
    EditText cargoned;
    @BindView(R.id.cargon_affirm)
    Button cargonAffirm;
    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(recy_contetn)
    RecyclerView recyContetn;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    private List<CheckStock.RowsBean> dataList = new ArrayList<>();
    private CommonBaseAdapter<CheckStock.RowsBean> mAdapter;
    private TextView mErrorContent;
    private AlertDialog.Builder builder;
    private AlertDialog mErrorDialog;
    private AlertDialog mResultDialog;
    private TextView mResultContent;
    private MaterialBlockBarCode mMaterbarCode;
    private  int status=1;
    private FrameLocation mFrameLocation;
    private int mId;
    private FrameLocation mFrameLocationSuccess;


    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerCheckStockComponent.builder().appComponent(appComponent).checkStockModule(new CheckStockModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {



    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText(this.getResources().getString(R.string.pcbcheck));
        builder = new AlertDialog.Builder(this);

        List<CheckStockDemo> list = new ArrayList<>();
        list.add(new CheckStockDemo("", "", "", "", ""));
        CommonBaseAdapter<CheckStockDemo> mAdapterTitle = new CommonBaseAdapter<CheckStockDemo>(getContext(), list) {
            @Override
            protected void convert(CommonViewHolder holder, CheckStockDemo item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.waring_editext));
            }

            @Override
            protected int getItemViewLayoutId(int position, CheckStockDemo item) {
                return R.layout.item_check;
            }

        };
        recyTitle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyTitle.setAdapter(mAdapterTitle);

        mAdapter = new CommonBaseAdapter<CheckStock.RowsBean>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, CheckStock.RowsBean item, int position) {
                holder.setText(R.id.statistics, item.getPartNum());
                holder.setText(R.id.statistics_id, item.getBoxSerial());
                holder.setText(R.id.statistics_pcbnumber, ""+item.getBoundCount());
                if (item.getRealCount()==0) {
                    holder.setText(R.id.statistics_number, "");
                }else {
                    holder.setText(R.id.statistics_number, ""+item.getRealCount());
                }
                holder.setText(R.id.statistics_storenumber, item.getStatus());

                if (mMaterbarCode != null) {
                    for (int i = 0; i < dataList.size(); i++) {
                        if (mMaterbarCode.getDeltaMaterialNumber().equals(dataList.get(i).getPartNum())) {
                            if (Integer.valueOf(mMaterbarCode.getCount())==dataList.get(i).getBoundCount()){
                            mId=dataList.get(i).getId();
                            getPresenter().fetchCheckStockSuccessNumber(dataList.get(i).getId(),Integer.valueOf(mMaterbarCode.getCount()));}else {
                                cargoned.requestFocus();
                            }
                        }else{
                            mErrorDialog = builder.create();
                            mErrorDialog.setContentView(R.layout.dialog_error);
                            mErrorContent = (TextView) mErrorDialog.findViewById(R.id.error_content);
                            mErrorContent.setText(mMaterbarCode.getDeltaMaterialNumber()+"-"+mMaterbarCode.getCount()+"片\n不是本架位的物料，是否变更架位");
                            mErrorDialog.findViewById(R.id.error_cancel).setOnClickListener(CheckStockActivity.this);
                            mErrorDialog.findViewById(R.id.error_alteration).setOnClickListener(CheckStockActivity.this);
                            mErrorDialog.show();
                        }
                    }
                }

            }

            @Override
            protected int getItemViewLayoutId(int position, CheckStock.RowsBean item) {
                return R.layout.item_check;
            }

        };
        recyContetn.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyContetn.setAdapter(mAdapter);


    }

    @Override
    public void onScanSuccess(String barcode) {
        super.onScanSuccess(barcode);
        BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
        switch (status) {
            case 1:
                try {
                    mFrameLocation = (FrameLocation) barCodeParseIpml.getEntity(barcode, FRAME_LOCATION);
                    getPresenter().fetchCheckStock(mFrameLocation.getSource());
                    status=2;
                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                    ToastUtils.showMessage(this,"扫描码有问题");
                    status=1;
                }
                break;
            case  2:
                try {
                    mMaterbarCode = (MaterialBlockBarCode) barCodeParseIpml.getEntity(barcode, MATERIAL_BLOCK_BARCODE);
                    status=2;
                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                    ToastUtils.showMessage(this,"请重新扫描架位");
                    status=3;
                }
                break;
            case 3:
                try {
                    mFrameLocationSuccess = (FrameLocation) barCodeParseIpml.getEntity(barcode, FRAME_LOCATION);
                    if(mFrameLocationSuccess.getSource().equals(mFrameLocation.getSource())){
                        getPresenter().fetchException(mFrameLocationSuccess.getSource());
                    }else {
                        ToastUtils.showMessage(this,"两次扫描架位不一致");
                    }

                } catch (EntityNotFountException e) {
                    e.printStackTrace();
                    ToastUtils.showMessage(this,"扫描码有问题");
                }

                break;
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_check;
    }

    @OnClick(R.id.cargon_affirm)
    public void onClick() {
        if (mMaterbarCode != null) {
            if (!TextUtils.isEmpty(cargoned.getText())) {
                if (mId!=0){
                String ss=cargoned.getText().toString();
                getPresenter().fetchCheckStockSuccessNumber(mId,Integer.valueOf(ss));}
            } else {
                Toast toast = Toast.makeText(this, "请输入数量", Toast.LENGTH_SHORT);
                View view = toast.getView();
                view.setBackgroundColor(Color.WHITE);
                toast.setView(view);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(this, "请先扫描外箱条码", Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundColor(Color.WHITE);
            toast.setView(view);
            toast.show();
        }
    }

    @Override
    public void onSucess(List<CheckStock.RowsBean> wareHouses) {
        dataList.clear();
        dataList.addAll(wareHouses);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(String s) {
        ToastUtils.showMessage(this,s);
    }

    @Override
    public void onCheckStockNumberSucess(String wareHouses) {

    }

    @Override
    public void onErrorSucess(String wareHouses) {

    }

    @Override
    public void onExceptionSucess(String wareHouses) {
        getPresenter().fetchSubmit(mFrameLocationSuccess.getSource());
    }

    @Override
    public void onSubmitSucess(String wareHouses) {
        ToastUtils.showMessage(this,"盘点成功");
    }


    @Override
    public void onCheckStockSucess(String wareHouses) {
//        mErrorDialog = builder.create();
//        mErrorDialog.setContentView(R.layout.dialog_error);
//        mErrorContent = (TextView) mErrorDialog.findViewById(R.id.error_content);
//        mErrorDialog.findViewById(R.id.error_cancel).setOnClickListener(this);
//        mErrorDialog.findViewById(R.id.error_alteration).setOnClickListener(this);
//        mErrorDialog.show();
        mResultDialog = builder.create();
        mResultDialog.setContentView(R.layout.dialog_result);
        mResultContent = (TextView) mResultDialog.findViewById(R.id.result_content);
        mResultDialog.findViewById(R.id.result_cancel).setOnClickListener(this);
        mResultDialog.findViewById(R.id.result_alteration).setOnClickListener(this);
        mResultDialog.show();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.error_cancel:
                if (mErrorDialog.isShowing()) {
                    mErrorDialog.dismiss();
                }
                break;
            case R.id.error_alteration:
                getPresenter().fetchError(mMaterbarCode.getDeltaMaterialNumber(),mFrameLocation.getSource());
                break;
            case R.id.result_cancel:
                if (mErrorDialog.isShowing()) {
                    mErrorDialog.dismiss();
                }
                break;
            case R.id.result_alteration:
                if (mResultDialog.isShowing()) {
                    mResultDialog.dismiss();
                }
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
