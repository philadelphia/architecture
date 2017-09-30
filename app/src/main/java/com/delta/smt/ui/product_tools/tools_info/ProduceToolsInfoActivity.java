package com.delta.smt.ui.product_tools.tools_info;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.buletoothio.barcode.parse.BarCodeParseIpml;
import com.delta.buletoothio.barcode.parse.BarCodeType;
import com.delta.buletoothio.barcode.parse.entity.ProductToolsBarcode;
import com.delta.buletoothio.barcode.parse.exception.EntityNotFountException;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.SnackbarUtil;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
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
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsInfoActivity extends BaseActivity<ProduceToolsInfoPresenter> implements ProduceToolsInfoContract.View, CommonBaseAdapter.OnItemClickListener<ProductToolsInfo> {

    public static final int MORE_REQUEST_OK = 1;

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
    String parm = "";
    List<ProductToolsInfo> data = new ArrayList<>();
    List<ProductToolsInfo> data_cache = new ArrayList<>();
    CommonBaseAdapter<ProductToolsInfo> adapter;
    String workNumber;
    String sourceActivity = "ProduceToolsBorrowActivity";
    String TAG = "ProduceToolsInfoActivity";
    Product_mToolsInfo selectItem;
    String barcode;
    String ID = "admin";
    BarCodeParseIpml barCodeParseIpml = new BarCodeParseIpml();
    private StringBuffer mStringBuffer;

    @OnClick(R.id.confirm)
    public void confirmData() {

        mStringBuffer = new StringBuffer();
        if (data.size() != 0) {
            for (int mI = 1; mI < data.size(); mI++) {
                if (mI == data.size() - 1) {
                    mStringBuffer.append("\\\"").append(data.get(mI).getProductToolsBarCode()).append("\\\"");
                } else {
                    mStringBuffer.append("\\\"").append(data.get(mI).getProductToolsBarCode()).append("\\\"").append(",");
                }
            }

        }
        List<String> datas = new ArrayList<>();
        for (ProductToolsInfo productToolsInfo : data) {
            if (!productToolsInfo.getProductToolsBarCode().equals("治具二维码"))
                datas.add(productToolsInfo.getProductToolsBarCode());
        }
        parm = GsonTools.createGsonString(new String[]{"workOrderId", "jig", "user"}, new Object[]{Integer.parseInt(workNumber), datas, ID});
        getPresenter().getToolsVerfy(parm);

    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerProduceToolsInfoCompoent.builder().appComponent(appComponent).produceToolsInfoModule(new ProduceToolsInfoModule(this)).build().Inject(this);
    }

    @Override
    protected void initData() {

        workNumber = this.getIntent().getExtras().getString(sourceActivity);
        String gsonString = GsonTools.createGsonString(new String[]{"workOrderId"}, new Object[]{Integer.parseInt(workNumber)});
        getPresenter().getToolsInfoInit(gsonString);
    }

    @Override
    protected void initView() {

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("治具信息");

        mProductToolsWorkItemTextView.setText(this.getIntent().getExtras().getString("OrderName"));

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

                    holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.c_efefef));
                    holder.itemView.setVisibility(View.VISIBLE);

                } else {

                    holder.setBackgroundColor(R.id.ReSelect, 0xFF428bca);
                    holder.setTextColor(R.id.ReSelect, 0xFFffffff);
                    holder.setText(R.id.TurnNumber, item.getTurnNumber());
                    holder.setText(R.id.ProductToolsBarCode, item.getProductToolsBarCode());
                    holder.setText(R.id.ProductToolsType, item.getProduceToolsType());
                    holder.setText(R.id.ProductToolsLocation, item.getProductToolsLocation());
                    holder.setText(R.id.ReSelect, item.getReSelect());
                    holder.setText(R.id.Status, item.getStatus());


                }
                Log.e("jigTypeID", item.getJigTypeId());
                holder.setOnClickListener(R.id.ReSelect, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("workNumber", workNumber);
                        bundle.putString("jigTypeID", item.getJigTypeId());
                        intent.putExtras(bundle);
                        intent.setClass(ProduceToolsInfoActivity.this, Produce_mToolsActivity.class);
                        startActivityForResult(intent, MORE_REQUEST_OK);
                        //finish();
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
                    p.setJigId(selectItem.getJigID());
                }

            }

            data.addAll(ProductToolsItem);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getToolsInfoAndChangeTool(List<ProductToolsInfo> ProductToolsItem) {
        data.clear();
        data.add(0, new ProductToolsInfo("序号", "治具二维码", "治具类型", "所在架位", "重新选择", "状态", "", ""));
        data.addAll(ProductToolsItem);
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data_cache.size(); j++) {
                if (data.get(i).getProduceToolsType().equals(data_cache.get(j).getProduceToolsType())) {
                    data.get(i).setProductToolsBarCode(data_cache.get(j).getProductToolsBarCode());
                    data.get(i).setProductToolsLocation(data_cache.get(j).getProductToolsLocation());
                    data.get(i).setJigId(data_cache.get(j).getJigId());
                    break;
                }
            }
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void getToolsInfoInit(List<ProductToolsInfo> ProductToolsItem) {
        //data.clear();
        data_cache.clear();
        data.addAll(ProductToolsItem);
        data_cache.addAll(ProductToolsItem);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getToolsVerfy(List<ProductToolsInfo> ProductToolsItem) {
        if (ProductToolsItem == null) {
            SnackbarUtil.showMassage(ProduceToolsInfoActivity.this.getWindow().getCurrentFocus(), "请求的数据不存在");
        }

        data.clear();
        data.add(0, new ProductToolsInfo("序号", "治具二维码", "治具类型", "所在架位", "重新选择", "状态", "", ""));
        data.addAll(ProductToolsItem);
        data_cache.clear();
        data_cache.addAll(ProductToolsItem);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getToolsBorrowSubmit(List<ProductToolsInfo> ProductToolsItem) {
        if (ProductToolsItem == null) {
            SnackbarUtil.showMassage(ProduceToolsInfoActivity.this.getWindow().getCurrentFocus(), "请求的数据不存在");
        }

        data.clear();
        data.add(0, new ProductToolsInfo("序号", "治具二维码", "治具类型", "所在架位", "重新选择", "状态", "", ""));
        data.addAll(ProductToolsItem);
        data_cache.clear();
        data_cache.addAll(ProductToolsItem);
        adapter.notifyDataSetChanged();

        /*if (jsonProductRequestToolsRoot.getCode() == 0) {

            for (int i = 1; i < data.size(); i++) {

                if (data.get(i).getProductToolsBarCode().equals(this.barcode)) {

                    data.get(i).setStatus("已借出");

                    //data_cache中缓存状态
                    for (int j = 0; j < data_cache.size(); j++) {
                        if (data_cache.get(j).getProductToolsBarCode().equals(this.barcode)) {
                            data_cache.get(j).setStatus("已借出");
                            break;
                        }
                    }
                    adapter.notifyDataSetChanged();
                    SnackbarUtil.showMassage(getCurrentFocus(), "借出成功");
                    VibratorAndVoiceUtils.correctVibrator(ProduceToolsInfoActivity.this);
                    VibratorAndVoiceUtils.correctVoice(ProduceToolsInfoActivity.this);

                }
            }
        } else {

            SnackbarUtil.showMassage(ProduceToolsInfoActivity.this.getWindow().getCurrentFocus(), jsonProductToolsSubmitRoot.getMessage());
            VibratorAndVoiceUtils.wrongVibrator(ProduceToolsInfoActivity.this);
            VibratorAndVoiceUtils.wrongVoice(ProduceToolsInfoActivity.this);

        }*/

    }

    @Override
    public void getFail(String message) {
        if (!TextUtils.isEmpty(message)) {
            SnackbarUtil.showMassage(this.getCurrentFocus(), message);
            VibratorAndVoiceUtils.wrongVibrator(ProduceToolsInfoActivity.this);
            VibratorAndVoiceUtils.wrongVoice(ProduceToolsInfoActivity.this);
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
        statusLayout.setErrorClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getToolsVerfy(parm);
            }
        });
    }

    @Override
    public void onScanSuccess(String barcode) {
        super.onScanSuccess(barcode);
        productInfoBarCodeEditText.setText(barcode);
        /**
         * 判断二维码是否是治具二维码
         */
        try {
            ProductToolsBarcode productToolsBarcode = (ProductToolsBarcode) barCodeParseIpml.getEntity(barcode, BarCodeType.PRODUCT_TOOLS);
        } catch (EntityNotFountException e) {
            SnackbarUtil.showMassage(this.getCurrentFocus(), "请扫描正确的治具二维码!");
            VibratorAndVoiceUtils.wrongVibrator(ProduceToolsInfoActivity.this);
            VibratorAndVoiceUtils.wrongVoice(ProduceToolsInfoActivity.this);
            return;
        }
        /**
         * 判断二维码在列表中是否存在
         */
        if (!existInList(barcode, data)) {
            SnackbarUtil.showMassage(this.getCurrentFocus(), "此二维码在列表中不存在!");
            VibratorAndVoiceUtils.wrongVibrator(ProduceToolsInfoActivity.this);
            VibratorAndVoiceUtils.wrongVoice(ProduceToolsInfoActivity.this);
            return;
        }

        String param = GsonTools.createGsonString(new String[]{"workOrderId", "jigCode", "user"}, new Object[]{Integer.parseInt(workNumber), barcode, ID});
        getPresenter().getToolsBorrowSubmit(param);
        this.barcode = barcode;


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == MORE_REQUEST_OK && resultCode == Produce_mToolsActivity.MORE_RESULT_OK) {
            this.selectItem = (Product_mToolsInfo) intent.getExtras().getSerializable("Produce_mToolsActivity");
            String workNumber = intent.getExtras().getString("workNumber");
            this.workNumber = workNumber;
            HashMap<String, String> parm = new HashMap<>();
            parm.put("value", "{\"workOrderId\":" + workNumber + "}");
            String gsonListString = GsonTools.createGsonString(new String[]{"workOrderId"}, new Object[]{Integer.parseInt(workNumber)});
            //将更改后的结果写入data_cache
            changeToolCache(selectItem);
            getPresenter().getToolsInfoAndChangeTool(gsonListString);
        }
    }

    private void changeToolCache(Product_mToolsInfo p) {
        for (int i = 0; i < data_cache.size(); i++) {
            if (p.getProductToolsType().equals(data_cache.get(i).getProduceToolsType())) {
                data_cache.get(i).setProductToolsBarCode(p.getProductToolsBarCode());
                data_cache.get(i).setProductToolsLocation(p.getProductToolsLocation());
                data_cache.get(i).setJigId(p.getJigID());
            }

        }
    }

    /**
     * 判断所扫描治具条码在列表中是否存在
     *
     * @param code 所扫描治具条码
     * @param data 列表对应List
     * @return true存在，false不存在
     */
    public boolean existInList(String code, List<ProductToolsInfo> data) {
        for (ProductToolsInfo d : data) {
            if (code.equals(d.getProductToolsBarCode())) {
                return true;
            }
        }
        return false;
    }


}


