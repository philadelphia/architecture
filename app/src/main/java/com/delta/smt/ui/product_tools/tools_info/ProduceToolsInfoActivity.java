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
import android.widget.Toast;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.JsonProductToolsLocation;
import com.delta.smt.entity.ProductToolsInfo;
import com.delta.smt.entity.Product_mToolsInfo;
import com.delta.smt.ui.product_tools.mtools_info.Produce_mToolsActivity;
import com.delta.smt.ui.product_tools.tools_info.di.DaggerProduceToolsInfoCompoent;
import com.delta.smt.ui.product_tools.tools_info.di.ProduceToolsInfoModule;
import com.delta.smt.ui.product_tools.tools_info.mvp.ProduceToolsInfoContract;
import com.delta.smt.ui.product_tools.tools_info.mvp.ProduceToolsInfoPresenter;

import org.json.JSONException;
import org.json.JSONObject;

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

    @OnClick(R.id.confirm)
    public void confirmData() {

        if (data.size() > 1) {
            String d1;
            String d2;
            String d3;
            String d4;
            try {
                d1 = data.get(1).getJigID();
            }catch (Exception e){
                d1 = "0";
            }
            try {
                d2 = data.get(2).getJigID();
            }catch (Exception e){
                d2 = "0";
            }
            try {
                d3 = data.get(3).getJigID();
            }catch (Exception e){
                d3 = "0";
            }
            try {
                d4 = data.get(4).getJigID();
            }catch (Exception e){
                d4 = "0";
            }
                getPresenter().getToolsVerfy("[\"{\\\"workOrderID\\\":" + workNumber + ",\\\"stencil\\\":" + d1 + ",\\\"scraper\\\":" + d2 + ",\\\"plate\\\":" + d3 + ",\\\"ict\\\":" + d4 + "}\"]");
        }
    }

    List<ProductToolsInfo> data = new ArrayList<>();
    CommonBaseAdapter<ProductToolsInfo> adapter;
    String workNumber;

    String sourceActivity = "ProduceToolsBorrowActivity";
    String TAG = "ProduceToolsInfoActivity";
    Product_mToolsInfo selectItem;
    String barcode;
    int ID = 1001;

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

        mProductToolsWorkItemTextView.setText(workNumber);

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
            Toast.makeText(this, "请求的数据不存在!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "请求的数据不存在!", Toast.LENGTH_SHORT).show();
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
    public void getToolsBorrowSubmit(JsonProductToolsLocation j) {
        if (j == null) {
            Toast.makeText(this, "请求的数据不存在!", Toast.LENGTH_SHORT).show();
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

                }
            }
        } else {

            Toast.makeText(this, j.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void getFail() {

        Toast.makeText(this, "请求的数据不存在!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onScanSuccess(String barcode) {
        super.onScanSuccess(barcode);

        getPresenter().getToolsBorrowSubmit("[\"{\\\"workOrderID\\\":" + workNumber + ",\\\"barcode\\\":\\\"" + barcode + "\\\",\\\"userID\\\":" + ID + "}\"]");

        this.barcode = barcode;

    }
}
