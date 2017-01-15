package com.delta.smt.ui.over_receive;

import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.common.ItemOnclick;
import com.delta.smt.common.adapter.ItemCountdownViewAdapter;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.ModuleUpWarningItem;
import com.delta.smt.entity.OverReceiveItem;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.over_receive.di.DaggerOverReceiveComponent;
import com.delta.smt.ui.over_receive.di.OverReceiveModule;
import com.delta.smt.ui.over_receive.mvp.OverReceiveContract;
import com.delta.smt.ui.over_receive.mvp.OverReceivePresenter;
import com.delta.smt.ui.smt_module.module_up.di.DaggerModuleUpComponent;
import com.delta.smt.ui.smt_module.module_up.di.ModuleUpModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.delta.smt.base.BaseApplication.getContext;

/**
 * Created by Shufeng.Wu on 2017/1/15.
 */

public class OverReceiveActivity extends BaseActivity<OverReceivePresenter> implements OverReceiveContract.View, ItemOnclick, WarningManger.OnWarning{

    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.recy_title)
    RecyclerView recyTitle;
    @BindView(R.id.recy_content)
    RecyclerView recyContent;
    @BindView(R.id.manual_debit)
    AppCompatButton manualDebit;

    private CommonBaseAdapter<OverReceiveItem> adapterTitle;
    private CommonBaseAdapter<OverReceiveItem> adapter;
    private List<OverReceiveItem> dataList = new ArrayList<>();
    private List<OverReceiveItem> dataSource = new ArrayList<>();

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerOverReceiveComponent.builder().appComponent(appComponent).overReceiveModule(new OverReceiveModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        getPresenter().getAllOverReceiveItems();
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("仓库房超领");

        recyTitle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        dataList.add(new OverReceiveItem("线别", "料号", "架位", "需求量", "剩余料使用时间","状态"));
        adapterTitle = new CommonBaseAdapter<OverReceiveItem>(this, dataList) {
            @Override
            protected void convert(CommonViewHolder holder, OverReceiveItem item, int position) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.c_efefef));
                holder.setText(R.id.tv_lineID,item.getLineID());
                holder.setText(R.id.tv_materialID,item.getMaterialID());
                holder.setText(R.id.tv_shelfPositionID,item.getShelfPositionID());
                holder.setText(R.id.tv_demandAmount,item.getDemandAmount());
                holder.setText(R.id.tv_materialRemainingUsageTime,item.getMaterialRemainingUsageTime());
                holder.setText(R.id.tv_state,item.getState());
            }

            @Override
            protected int getItemViewLayoutId(int position, OverReceiveItem item) {
                return R.layout.item_over_receive_list;
            }
        };

        recyTitle.setAdapter(adapterTitle);

        adapter = new CommonBaseAdapter<OverReceiveItem>(this, dataSource) {
            @Override
            protected void convert(CommonViewHolder holder, OverReceiveItem item, int position) {
                holder.itemView.setBackgroundColor(Color.WHITE);
                holder.setText(R.id.tv_lineID,item.getLineID());
                holder.setText(R.id.tv_materialID,item.getMaterialID());
                holder.setText(R.id.tv_shelfPositionID,item.getShelfPositionID());
                holder.setText(R.id.tv_demandAmount,item.getDemandAmount());
                holder.setText(R.id.tv_materialRemainingUsageTime,item.getMaterialRemainingUsageTime());
                holder.setText(R.id.tv_state,item.getState());
            }

            @Override
            protected int getItemViewLayoutId(int position, OverReceiveItem item) {
                return R.layout.item_over_receive_list;
            }

        };

        recyContent.setAdapter(adapter);
        //adapter.setOnItemClickListener(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_over_receive;
    }

    @Override
    public void onSuccess(List<OverReceiveItem> data) {
        dataSource.clear();
        dataSource.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFalied() {

    }

    @OnClick({R.id.manual_debit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.manual_debit:
                getPresenter().manualDebit();
                break;
        }
    }

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
    public void onItemClick(View item, int position) {

    }

    @Override
    public void warningComing(String warningMessage) {

    }
}
