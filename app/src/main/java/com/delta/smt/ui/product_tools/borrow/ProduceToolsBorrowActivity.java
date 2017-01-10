package com.delta.smt.ui.product_tools.borrow;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ProductWorkItem;
import com.delta.smt.ui.product_tools.borrow.di.DaggerProduceToolsBorrowComponent;
import com.delta.smt.ui.product_tools.borrow.di.ProduceToolsBorrowModule;
import com.delta.smt.ui.product_tools.borrow.mvp.ProduceToolsBorrowContract;
import com.delta.smt.ui.product_tools.borrow.mvp.ProduceToolsBorrowPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsBorrowActivity extends BaseActiviy<ProduceToolsBorrowPresenter> implements ProduceToolsBorrowContract.View, CommonBaseAdapter.OnItemClickListener<ProductWorkItem> {

    @BindView(R.id.toolbar_title)
    TextView mTitleTextView;

    @BindView(R.id.navigation)
    TextView mBackTextView;

    @BindView(R.id.ProductBorrowRecyclerView)
    RecyclerView mProductBorrowRecyclerView;

    List<ProductWorkItem> data;
    CommonBaseAdapter<ProductWorkItem> adapter;


    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerProduceToolsBorrowComponent.builder().appComponent(appComponent).produceToolsBorrowModule(new ProduceToolsBorrowModule(this)).build().Inject(this
        );

    }

    @Override
    protected void initData() {
        getPresenter().getData();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mTitleTextView.setText("治具借出");

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
            intent.setClass(this,Produce_mToolsActivity.class);
            startActivity(intent);
        }
    }
}
