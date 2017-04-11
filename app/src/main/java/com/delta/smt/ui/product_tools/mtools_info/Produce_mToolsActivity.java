package com.delta.smt.ui.product_tools.mtools_info;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.Product_mToolsInfo;
import com.delta.smt.ui.product_tools.mtools_info.di.DaggerProduct_mToolsComponent;
import com.delta.smt.ui.product_tools.mtools_info.di.Product_mToolsModule;
import com.delta.smt.ui.product_tools.mtools_info.mvp.Produce_mToolsContract;
import com.delta.smt.ui.product_tools.mtools_info.mvp.Produce_mToolsPresenter;
import com.delta.smt.ui.product_tools.tools_info.ProduceToolsInfoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class Produce_mToolsActivity extends BaseActivity<Produce_mToolsPresenter> implements Produce_mToolsContract.View, CommonBaseAdapter.OnItemClickListener<Product_mToolsInfo> {

    private final String TAG="Produce_mToolsActivity";

    @BindView(R.id.ProductInfoRecyclerView)
    RecyclerView mProductBorrowRecyclerView;

    @BindView(R.id.toolbar)
    AutoToolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.mToolsConfirm)
    Button confirm;

    @OnClick(R.id.mToolsConfirm)
    public void confirmData(){
        if(selectItem!=null) {
            Intent i = new Intent();
            Bundle b = new Bundle();
            b.putSerializable(TAG, selectItem);
            b.putString("workNumber",workNumber);
            i.putExtras(b);
            i.setClass(this, ProduceToolsInfoActivity.class);
            startActivity(i);
            finish();
        }
    }

    private String workNumber;
    private String jigTypeID;

    View selectView;

    List<Product_mToolsInfo> data=new ArrayList<>();
    CommonBaseAdapter<Product_mToolsInfo> adapter;

    Product_mToolsInfo selectItem;

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerProduct_mToolsComponent.builder().appComponent(appComponent).product_mToolsModule(new Product_mToolsModule(this)).build().Inject(this);
    }

    @Override
    protected void initData() {

        workNumber=getIntent().getExtras().getString("workNumber");
        jigTypeID=getIntent().getExtras().getString("jigTypeID");

        getPresenter().getData("{\"workOrderID\":"+workNumber+",\"jigTypeID\":"+jigTypeID+"}");

    }

    @Override
    protected void initView() {

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("治具借出");

        data.add(0, new Product_mToolsInfo("序号", "治具二维码", "治具类型", "所在架位",""));

        adapter = new CommonBaseAdapter<Product_mToolsInfo>(getContext(), data) {
            @Override
            protected void convert(CommonViewHolder holder, Product_mToolsInfo item, int position) {

                if (position == 0) {

                    holder.itemView.setBackgroundColor(getContext().getResources().getColor(R.color.c_efefef));

                } else {

                    holder.setText(R.id.TurnNumber, item.getTurnNumber());
                    holder.setText(R.id.ProductToolsBarCode, item.getProductToolsBarCode());
                    holder.setText(R.id.ProductToolsType, item.getProductToolsType());
                    holder.setText(R.id.ProductToolsLocation, item.getProductToolsLocation());

                }

            }

            @Override
            protected int getItemViewLayoutId(int position, Product_mToolsInfo item) {
                return R.layout.item_product_mtools_info;
            }
        };

        adapter.setOnItemClickListener(this);
        mProductBorrowRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mProductBorrowRecyclerView.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_product_mtools_info;
    }

    @Override
    public void get_mToolsData(List<Product_mToolsInfo> product_mToolsInfo) {

        data.addAll(product_mToolsInfo);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void getFail() {
        Toast.makeText(this, "请求的数据不存在!", Toast.LENGTH_SHORT).show();
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
    public void onItemClick(View view, Product_mToolsInfo item, int position) {
        if (selectView != null) {
            selectView.setBackgroundColor(0xFFffffff);
        }
        view.setBackgroundColor(0xFF9FDEFF);
        selectView = view;
        selectItem=item;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
