package com.delta.smt.ui.product_tools.borrow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ProductWorkItem;
import com.delta.smt.ui.product_tools.MyCompare;
import com.delta.smt.ui.product_tools.TimeSortUnit;
import com.delta.smt.ui.product_tools.borrow.di.DaggerProduceToolsBorrowComponent;
import com.delta.smt.ui.product_tools.borrow.di.ProduceToolsBorrowModule;
import com.delta.smt.ui.product_tools.borrow.mvp.ProduceToolsBorrowContract;
import com.delta.smt.ui.product_tools.borrow.mvp.ProduceToolsBorrowPresenter;
import com.delta.smt.ui.product_tools.tools_info.ProduceToolsInfoActivity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsBorrowActivity extends BaseActivity<ProduceToolsBorrowPresenter> implements ProduceToolsBorrowContract.View, CommonBaseAdapter.OnItemClickListener<ProductWorkItem> {

    String TAG="ProduceToolsBorrowActivity";

    @BindView(R.id.ProductBorrowRecyclerView)
    RecyclerView mProductBorrowRecyclerView;

    @BindView(R.id.toolbar)
    AutoToolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    List<ProductWorkItem> data;
    CommonBaseAdapter<ProductWorkItem> adapter;


    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerProduceToolsBorrowComponent.builder().appComponent(appComponent).produceToolsBorrowModule(new ProduceToolsBorrowModule(this)).build().Inject(this);

    }

    @Override
    protected void initData() {
        getPresenter().getData();
    }

    @Override
    protected void initView() {

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("治具借出");

        //TODO data按时间排序

        MyCompare myCompare=new MyCompare();
        Collections.sort(data,myCompare);

        data.add(0,new ProductWorkItem("工单号","工单类型","机种","PCB code","组合料号","线别","PWB料号","面别","计划上线时间","治具状态"));

        adapter = new CommonBaseAdapter<ProductWorkItem>(getContext(), data) {
            @Override
            protected void convert(CommonViewHolder holder, ProductWorkItem item, int position) {

                if (position == 0) {

                    holder.setBackgroundColor(R.id.WorkNumber, 0xFFf2f2f2);

                    holder.setBackgroundColor(R.id.WorkType, 0xFFf2f2f2);

                    holder.setBackgroundColor(R.id.MachineType, 0xFFf2f2f2);

                    holder.setBackgroundColor(R.id.PCB_Code, 0xFFf2f2f2);

                    holder.setBackgroundColor(R.id.FormMaterialNumber, 0xFFf2f2f2);

                    holder.setBackgroundColor(R.id.Line, 0xFFf2f2f2);

                    holder.setBackgroundColor(R.id.PWB_MaterialNumber, 0xFFf2f2f2);

                    holder.setBackgroundColor(R.id.Cover, 0xFFf2f2f2);

                    holder.setBackgroundColor(R.id.PlanOnLineTime, 0xFFf2f2f2);

                    holder.setBackgroundColor(R.id.ProductToolsStatus, 0xFFf2f2f2);

                } else {

                    holder.setText(R.id.WorkNumber, item.getWorkNumber());

                    holder.setText(R.id.WorkType, item.getWorkItemType());

                    holder.setText(R.id.MachineType, item.getMachineType());

                    holder.setText(R.id.PCB_Code, item.getPCB_Code());

                    holder.setText(R.id.FormMaterialNumber, item.getFormMaterialNumber());

                    holder.setText(R.id.Line, item.getLine());

                    holder.setText(R.id.PWB_MaterialNumber, item.getPWB_Number());

                    holder.setText(R.id.Cover, item.getCover());

                    holder.setText(R.id.PlanOnLineTime, item.getPlayOnLineTime());

                    holder.setText(R.id.ProductToolsStatus, item.getProductStatus());

                    if (item.getProductStatus().equals(getString(R.string.AreReady))) {

                        holder.setBackgroundColor(R.id.WorkNumber, 0xFF3B9D43);

                        holder.setBackgroundColor(R.id.WorkType, 0xFF3B9D43);

                        holder.setBackgroundColor(R.id.MachineType, 0xFF3B9D43);

                        holder.setBackgroundColor(R.id.PCB_Code, 0xFF3B9D43);

                        holder.setBackgroundColor(R.id.FormMaterialNumber, 0xFF3B9D43);

                        holder.setBackgroundColor(R.id.Line, 0xFF3B9D43);

                        holder.setBackgroundColor(R.id.PWB_MaterialNumber, 0xFF3B9D43);

                        holder.setBackgroundColor(R.id.Cover, 0xFF3B9D43);

                        holder.setBackgroundColor(R.id.PlanOnLineTime, 0xFF3B9D43);

                        holder.setBackgroundColor(R.id.ProductToolsStatus, 0xFF3B9D43);
                    }
                }

            }

            @Override
            protected int getItemViewLayoutId(int position, ProductWorkItem item) {
                return R.layout.item_product_borrow;
            }
        };
        adapter.setOnItemClickListener(this);
        mProductBorrowRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mProductBorrowRecyclerView.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_product_tools_borrow;
    }

    @Override
    public void getFormData(List<ProductWorkItem> ProductWorkItemList) {

        this.data = ProductWorkItemList;

    }

    @Override
    public void getFail() {

    }

    @Override
    public void onItemClick(View view, ProductWorkItem item, int position) {
        if(item.getProductStatus().equals(getString(R.string.AreReady))){
            Intent intent=new Intent();
            Bundle bundle=new Bundle();
            bundle.putString(TAG,item.getWorkNumber());
            intent.putExtras(bundle);
            intent.setClass(this,ProduceToolsInfoActivity.class);
            startActivity(intent);
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
