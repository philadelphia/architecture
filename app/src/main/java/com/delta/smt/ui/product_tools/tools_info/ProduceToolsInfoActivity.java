package com.delta.smt.ui.product_tools.tools_info;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ProductToolsInfo;
import com.delta.smt.ui.product_tools.mtools_info.Produce_mToolsActivity;
import com.delta.smt.ui.product_tools.tools_info.di.DaggerProduceToolsInfoCompoent;
import com.delta.smt.ui.product_tools.tools_info.di.ProduceToolsInfoModule;
import com.delta.smt.ui.product_tools.tools_info.mvp.ProduceToolsInfoContract;
import com.delta.smt.ui.product_tools.tools_info.mvp.ProduceToolsInfoPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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

    List<ProductToolsInfo> data = new ArrayList<>();
    CommonBaseAdapter<ProductToolsInfo> adapter;
    String workNumber;

    String sourceActivity = "ProduceToolsBorrowActivity";
    String TAG = "ProduceToolsInfoActivity";

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerProduceToolsInfoCompoent.builder().appComponent(appComponent).produceToolsInfoModule(new ProduceToolsInfoModule(this)).build().Inject(this);
    }

    @Override
    protected void initData() {


        workNumber = this.getIntent().getExtras().getString(sourceActivity);

        getPresenter().getToolsInfo("{\"workOrderID\":" + workNumber + "}");


    }

    @Override
    protected void initView() {

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("治具信息");

        mProductToolsWorkItemTextView.setText(workNumber);
        //productInfoBarCodeEditText.setText(workNumber);

        data.add(0, new ProductToolsInfo("序号", "治具二维码", "治具类型", "所在架位", "重新选择", "状态",""));

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
                        Bundle bundle=new Bundle();
                        bundle.putString("workNumber",workNumber);
                        bundle.putString("jigTypeID",item.getJigTypeID());
                        intent.putExtras(bundle);
                        intent.setClass(ProduceToolsInfoActivity.this, Produce_mToolsActivity.class);
                        startActivity(intent);
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
        data.addAll(ProductToolsItem);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getFail() {

    }

    @Override
    public void onScanSuccess(String barcode) {
        super.onScanSuccess(barcode);

    }
}
