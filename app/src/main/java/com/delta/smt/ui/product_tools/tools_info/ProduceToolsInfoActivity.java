package com.delta.smt.ui.product_tools.tools_info;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.commonlibs.utils.SnackbarUtil;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.JsonProductToolsLocationRoot;
import com.delta.smt.entity.JsonProductToolsSubmitRoot;
import com.delta.smt.entity.ProductToolsInfo;
import com.delta.smt.entity.Product_mToolsInfo;
import com.delta.smt.ui.product_tools.SharedPreferencesUtils;
import com.delta.smt.ui.product_tools.mtools_info.Produce_mToolsActivity;
import com.delta.smt.ui.product_tools.tools_info.di.DaggerProduceToolsInfoCompoent;
import com.delta.smt.ui.product_tools.tools_info.di.ProduceToolsInfoModule;
import com.delta.smt.ui.product_tools.tools_info.mvp.ProduceToolsInfoContract;
import com.delta.smt.ui.product_tools.tools_info.mvp.ProduceToolsInfoPresenter;
import com.delta.smt.utils.VibratorAndVoiceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsInfoActivity extends BaseActivity<ProduceToolsInfoPresenter> implements ProduceToolsInfoContract.View, CommonBaseAdapter.OnItemClickListener<ProductToolsInfo> {

    @BindView(R.id.ProductInfoRecyclerView)
    RecyclerView mProductBorrowRecyclerView;

    @BindView(R.id.toolbar)
    AutoToolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.ProductInfoBarCode)
    EditText productInfoBarCodeEditText;

    @BindView(R.id.ProductToolsWorkItem)
    TextView mProductToolsWorkItemTextView;

    @BindView(R.id.MainBroad)
    TextView mainBroadTextView;

    @BindView(R.id.LittleBroad)
    TextView littleBroadTextView;

    @BindView(R.id.Cover)
    TextView coverTextView;

    @BindView(R.id.Line)
    TextView lineTextView;

    @BindView(R.id.PWB_Code)
    TextView mPWB_CodeTextView;

    @BindView(R.id.PCBCODE)
    TextView mPCBCODETextView;

    @BindView(R.id.ProductToolStatusLayout)
    StatusLayout statusLayout;

    private StringBuffer mStringBuffer;
    private String mString;
    List<ProductToolsInfo> data = new ArrayList<>();
    CommonBaseAdapter<ProductToolsInfo> adapter;
    String workNumber;

    String sourceActivity = "ProduceToolsBorrowActivity";
    String TAG = "ProduceToolsInfoActivity";
    Product_mToolsInfo selectItem;
    String barcode;
    int ID = 1001;

    @OnClick(R.id.confirm)
    public void confirmData() {

        mStringBuffer= new StringBuffer();
        mString=new String();

        if (data.size()!=0){
            for (int mI = 1; mI < data.size(); mI++) {
                if (mI==data.size()-1){
                    mStringBuffer.append("\\\""+data.get(mI).getProductToolsBarCode()+"\\\"");
                }else {
                    mStringBuffer.append("\\\""+data.get(mI).getProductToolsBarCode()+"\\\"" + ",");
                }
            }

        }
        Log.i(TAG, String.valueOf(mStringBuffer));
        mString="[\"{\\\"workOrderID\\\":"+workNumber+", \\\"jig\\\":["+mStringBuffer.toString()+"]}\"]";
        Log.i(TAG, "confirmData: "+mString);
        getPresenter().getToolsVerfy(mString);

    }



    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerProduceToolsInfoCompoent.builder().appComponent(appComponent).produceToolsInfoModule(new ProduceToolsInfoModule(this)).build().Inject(this);
    }

    @Override
    protected void initData() {

        workNumber = this.getIntent().getExtras().getString(sourceActivity);

        if (workNumber == null) {

            this.selectItem = (Product_mToolsInfo) this.getIntent().getExtras().getSerializable("Produce_mToolsActivity");
            String workNumber = this.getIntent().getExtras().getString("workNumber");
            this.workNumber = workNumber;
            getPresenter().getToolsInfo("{\"workOrderID\":" + workNumber + "}");

        } else {
            getPresenter().getToolsInfo("{\"workOrderID\":" + workNumber + "}");
        }
    }

    @Override
    protected void initView() {

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("治具信息");

        mProductToolsWorkItemTextView.setText(this.getIntent().getExtras().getString("OrderName") );

        if (this.getIntent().getExtras().getString("MainBroad") != null) {
            mainBroadTextView.setText(this.getIntent().getExtras().getString("MainBroad"));
            littleBroadTextView.setText(this.getIntent().getExtras().getString("LittleBroad"));
            coverTextView.setText(this.getIntent().getExtras().getString("Cover"));
            lineTextView.setText(this.getIntent().getExtras().getString("Line"));
            mPWB_CodeTextView.setText(this.getIntent().getExtras().getString("PWB"));
            mPCBCODETextView.setText(this.getIntent().getExtras().getString("PCB"));

            SharedPreferencesUtils.putData(this, "MainBroad", this.getIntent().getExtras().getString("MainBroad"));
            SharedPreferencesUtils.putData(this, "LittleBroad", this.getIntent().getExtras().getString("LittleBroad"));
            SharedPreferencesUtils.putData(this, "Cover", this.getIntent().getExtras().getString("Cover"));
            SharedPreferencesUtils.putData(this, "Line", this.getIntent().getExtras().getString("Line"));
            SharedPreferencesUtils.putData(this, "PWB", this.getIntent().getExtras().getString("PWB"));
            SharedPreferencesUtils.putData(this, "PCB", this.getIntent().getExtras().getString("PCB"));
        } else {
            mainBroadTextView.setText(SharedPreferencesUtils.getData(this, "MainBroad"));
            littleBroadTextView.setText(SharedPreferencesUtils.getData(this, "LittleBroad"));
            coverTextView.setText(SharedPreferencesUtils.getData(this, "Cover"));
            lineTextView.setText(SharedPreferencesUtils.getData(this, "Line"));
            mPWB_CodeTextView.setText(SharedPreferencesUtils.getData(this, "PWB"));
            mPCBCODETextView.setText(SharedPreferencesUtils.getData(this, "PCB"));
        }

        data.add(0, new ProductToolsInfo("序号", "治具二维码", "治具类型", "所在架位", "重新选择", "状态", "", ""));


        adapter = new CommonBaseAdapter<ProductToolsInfo>(getContext(), data) {
            @Override
            protected void convert(CommonViewHolder holder, final ProductToolsInfo item, int position) {

                if (position == 0) {

                    holder.setBackgroundColor(R.id.TurnNumber, 0xFFf2f2f2);
                    holder.setBackgroundColor(R.id.ProductToolsBarCode, 0xFFf2f2f2);
                    holder.setBackgroundColor(R.id.ProductToolsType, 0xFFf2f2f2);
                    holder.setBackgroundColor(R.id.ProductToolsLocation, 0xFFf2f2f2);
                    holder.setBackgroundColor(R.id.ReSelect, 0xFFf2f2f2);
                    holder.setBackgroundColor(R.id.Status, 0xFFf2f2f2);
                    holder.itemView.setVisibility(View.VISIBLE);

                } else {

                    holder.setBackgroundColor(R.id.ReSelect, 0xFF428bca);
                    holder.setText(R.id.TurnNumber, item.getTurnNumber());
                    holder.setText(R.id.ProductToolsBarCode, item.getProductToolsBarCode());
                    holder.setText(R.id.ProductToolsType, item.getProduceToolsType());
                    holder.setText(R.id.ProductToolsLocation, item.getProductToolsLocation());
                    holder.setText(R.id.ReSelect, item.getReSelect());
                    holder.setText(R.id.Status, item.getStatus());


                }

                holder.setOnClickListener(R.id.ReSelect, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("workNumber", workNumber);
                        bundle.putString("jigTypeID", item.getJigTypeID());
                        intent.putExtras(bundle);
                        intent.setClass(ProduceToolsInfoActivity.this, Produce_mToolsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                TextView more = holder.getView(R.id.ReSelect);
                if (more.getText().equals("更多")) {
                    more.setScaleY(0.9f);
                    more.setScaleX(0.9f);
                }
            }

            @Override
            protected int getItemViewLayoutId(int position, ProductToolsInfo item) {
                return R.layout.item_product_tools_info;
            }
        };

        adapter.setOnItemClickListener(this);
        mProductBorrowRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mProductBorrowRecyclerView.setAdapter(adapter);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_product_tools_info;
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

    @Override
    public void onItemClick(View view, ProductToolsInfo item, int position) {

    }

    @Override
    public void getToolsInfo(List<ProductToolsInfo> ProductToolsItem) {
        if (ProductToolsItem == null) {
        }
        if (selectItem == null) {
            data.addAll(ProductToolsItem);
            adapter.notifyDataSetChanged();
        } else {

            for (ProductToolsInfo p : ProductToolsItem) {

                Log.e("selectItem", selectItem.getProductToolsType() + 666 + selectItem.getProductToolsType());

                if (selectItem.getProductToolsType().equals(p.getProduceToolsType())) {
                    p.setProductToolsBarCode(selectItem.getProductToolsBarCode());
                    p.setProductToolsLocation(selectItem.getProductToolsLocation());
                    p.setJigID(selectItem.getJigID());
                }

            }

            data.addAll(ProductToolsItem);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getToolsVerfy(List<ProductToolsInfo> ProductToolsItem) {
        if (ProductToolsItem == null) {
            SnackbarUtil.showMassage(ProduceToolsInfoActivity.this.getWindow().getCurrentFocus(), "请求的数据不存在");
        }
        int i = 0;
        for (ProductToolsInfo p : ProductToolsItem) {
            i++;
            data.get(i).setTurnNumber(p.getTurnNumber());
            data.get(i).setProductToolsBarCode(p.getProductToolsBarCode());
            data.get(i).setJigTypeID(p.getJigTypeID());
            data.get(i).setStatus(p.getStatus());
            data.get(i).setProduceToolsType(p.getProduceToolsType());

        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getToolsBorrowSubmit(JsonProductToolsSubmitRoot j) {
        if (j == null) {
            SnackbarUtil.showMassage(ProduceToolsInfoActivity.this.getWindow().getCurrentFocus(), "请求的数据不存在");
        }
        Log.e("getToolsBorrowSubmit", j.toString());
        if (j.getCode() == 0) {

            int i = 0;
            for (ProductToolsInfo p : data) {

                i++;
                if (data.get(i).getProductToolsBarCode().equals(this.barcode)) {

                    data.get(i).setStatus("已完成");
                    productInfoBarCodeEditText.setText(this.barcode);
                    adapter.notifyDataSetChanged();
                    SnackbarUtil.showMassage(getCurrentFocus(), "借出成功");
                    VibratorAndVoiceUtils.correctVibrator(ProduceToolsInfoActivity.this);
                    VibratorAndVoiceUtils.correctVoice(ProduceToolsInfoActivity.this);

                }
            }
        } else {

            SnackbarUtil.showMassage(ProduceToolsInfoActivity.this.getWindow().getCurrentFocus(), j.getMessage());
            VibratorAndVoiceUtils.wrongVibrator(ProduceToolsInfoActivity.this);
            VibratorAndVoiceUtils.wrongVoice(ProduceToolsInfoActivity.this);

        }

    }

    @Override
    public void getFail(String message) {
        SnackbarUtil.showMassage(getCurrentFocus(),message);

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
                getPresenter().getToolsVerfy(mString);
            }
        });
    }

    @Override
    public void onScanSuccess(String barcode) {
        super.onScanSuccess(barcode);

        getPresenter().getToolsBorrowSubmit("[\"{\\\"workOrderID\\\":" + workNumber + ",\\\"barcode\\\":\\\"" + barcode + "\\\",\\\"userID\\\":" + ID + "}\"]");

        this.barcode = barcode;

    }

}
