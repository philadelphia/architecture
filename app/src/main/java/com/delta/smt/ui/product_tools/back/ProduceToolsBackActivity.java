package com.delta.smt.ui.product_tools.back;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ProductToolsBack;
import com.delta.smt.ui.product_tools.back.di.DaggerProduceToolsBackComponent;
import com.delta.smt.ui.product_tools.back.di.ProduceToolsBackModule;
import com.delta.smt.ui.product_tools.back.mvp.ProduceToolsBackContract;
import com.delta.smt.ui.product_tools.back.mvp.ProduceToolsBackPresenter;

import java.util.List;

import butterknife.BindView;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public class ProduceToolsBackActivity extends BaseActiviy<ProduceToolsBackPresenter> implements ProduceToolsBackContract.View,CommonBaseAdapter.OnItemClickListener<ProductToolsBack>{

    @BindView(R.id.ProductBorrowRecyclerView)
    RecyclerView mProductBorrowRecyclerView;

    @BindView(R.id.toolbar)
    AutoToolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    List<ProductToolsBack> data;
    CommonBaseAdapter<ProductToolsBack> adapter;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerProduceToolsBackComponent.builder().appComponent(appComponent).produceToolsBackModule(new ProduceToolsBackModule(this)).build().Inject(this);

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
        toolbarTitle.setText("治具归还");


        data.add(0,new ProductToolsBack("序号","治具二维码","工单号","治具类型","状态"));

        adapter=new CommonBaseAdapter<ProductToolsBack>(getContext(),data) {
            @Override
            protected void convert(CommonViewHolder holder, ProductToolsBack item, int position) {

                if(position==0){

                    holder.setBackgroundColor(R.id.TurnNumber,0xFFf2f2f2);
                    holder.setBackgroundColor(R.id.ProductToolsBarCode,0xFFf2f2f2);
                    holder.setBackgroundColor(R.id.WorkNumber,0xFFf2f2f2);
                    holder.setBackgroundColor(R.id.ProductToolsType,0xFFf2f2f2);
                    holder.setBackgroundColor(R.id.Status,0xFFf2f2f2);

                }else {

                    holder.setText(R.id.TurnNumber,item.getTurnNumber());
                    holder.setText(R.id.ProductToolsBarCode,item.getProductToolsBarCode());
                    holder.setText(R.id.WorkNumber,item.getWorkNumber());
                    holder.setText(R.id.ProductToolsType,item.getProductToolsType());
                    holder.setText(R.id.Status,item.getStatus());

                }

            }

            @Override
            protected int getItemViewLayoutId(int position, ProductToolsBack item) {
                return R.layout.item_product_back;
            }
        };

        adapter.setOnItemClickListener(this);
        mProductBorrowRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mProductBorrowRecyclerView.setAdapter(adapter);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_product_back;
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
    public void onItemClick(View view, ProductToolsBack item, int position) {

    }

    @Override
    public void getData(List<ProductToolsBack> data) {
        this.data=data;
    }

    @Override
    public void getFail() {

    }
}
