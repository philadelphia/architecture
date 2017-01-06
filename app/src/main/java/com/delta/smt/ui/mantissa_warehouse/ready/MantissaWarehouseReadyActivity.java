package com.delta.smt.ui.mantissa_warehouse.ready;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.delta.smt.MainActivity;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.MantissaWarehouseReady;
import com.delta.smt.ui.mantissa_warehouse.detail.MantissaWarehouseDetailsActivity;
import com.delta.smt.ui.mantissa_warehouse.ready.di.DaggerMantissaWarehouseReadyComponent;
import com.delta.smt.ui.mantissa_warehouse.ready.di.MantissaWarehouseReadyModule;
import com.delta.smt.ui.mantissa_warehouse.ready.mvp.MantissaWarehouseReadyContract;
import com.delta.smt.ui.mantissa_warehouse.ready.mvp.MantissaWarehouseReadyPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.Module;

import static com.delta.smt.R.id.recyclerView;
import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */
@Module
public class MantissaWarehouseReadyActivity extends BaseActiviy<MantissaWarehouseReadyPresenter> implements MantissaWarehouseReadyContract.View, CommonBaseAdapter.OnItemClickListener<MantissaWarehouseReady> {

    @BindView(recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.header_back)
    RelativeLayout mHeaderBack;
    @BindView(R.id.header_title)
    TextView mHeaderTitle;
    @BindView(R.id.header_setting)
    TextView mHeaderSetting;

    private List<MantissaWarehouseReady> dataList = new ArrayList();
    private CommonBaseAdapter<MantissaWarehouseReady> adapter;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerMantissaWarehouseReadyComponent.builder().appComponent(appComponent).mantissaWarehouseReadyModule(new MantissaWarehouseReadyModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {

        getPresenter().getMantissaWarehouseReadies();

    }

    @Override
    protected void initView() {
        mHeaderTitle.setText("尾数仓备料");
        adapter = new CommonBaseAdapter<MantissaWarehouseReady>(getContext(), dataList) {
            @Override
            protected void convert(CommonViewHolder holder, MantissaWarehouseReady item, int position) {
                holder.setText(R.id.tv_title, "线别: " + item.getLine());
                holder.setText(R.id.tv_line, "工单号: " + item.getNumber());
                holder.setText(R.id.tv_material_station, "面别: " + item.getFace());
                holder.setText(R.id.tv_add_count, "状态: " + item.getType());
            }

            @Override
            protected int getItemViewLayoutId(int position, MantissaWarehouseReady item) {
                return R.layout.fragment_storage_ready;
            }

        };

        adapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mantissa;
    }

    @Override
    public void onItemClick(View view, MantissaWarehouseReady item, int position) {
        Intent intent = new Intent(this, MantissaWarehouseDetailsActivity.class);

        startActivity(intent);
    }

    @Override
    public void getSucess(List<MantissaWarehouseReady> mantissaWarehouseReadies) {
        dataList.clear();
        dataList.addAll(mantissaWarehouseReadies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getFailed() {

    }

    @OnClick({R.id.header_back, R.id.header_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.header_setting:
                break;
        }
    }
}
