package com.delta.smt.ui.mantissa_warehouse.ready;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.delta.commonlibs.utils.SnackbarUtil;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
import com.delta.libs.adapter.ItemCountViewAdapter;
import com.delta.libs.adapter.ItemOnclick;
import com.delta.libs.adapter.ItemTimeViewHolder;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.MantissaWarehouseReady;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.mantissa_warehouse.detail.MantissaWarehouseDetailsActivity;
import com.delta.smt.ui.mantissa_warehouse.ready.di.DaggerMantissaWarehouseReadyComponent;
import com.delta.smt.ui.mantissa_warehouse.ready.di.MantissaWarehouseReadyModule;
import com.delta.smt.ui.mantissa_warehouse.ready.mvp.MantissaWarehouseReadyContract;
import com.delta.smt.ui.mantissa_warehouse.ready.mvp.MantissaWarehouseReadyPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */

public class MantissaWarehouseReadyActivity extends BaseActivity<MantissaWarehouseReadyPresenter>
        implements MantissaWarehouseReadyContract.View,
        WarningManger.OnWarning, ItemOnclick<MantissaWarehouseReady.RowsBean> {

    @Inject
    WarningManger warningManger;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.toolbar)
    AutoToolbar mToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.statusLayout)
    StatusLayout statusLayout;
    private List<MantissaWarehouseReady.RowsBean> dataList = new ArrayList();

    private ItemCountViewAdapter<MantissaWarehouseReady.RowsBean> adapter;
    private boolean isSending;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerMantissaWarehouseReadyComponent.builder().appComponent(appComponent).mantissaWarehouseReadyModule(new MantissaWarehouseReadyModule(this)).build().inject(this);

    }

    @Override
    protected void initData() {

        //接收那种预警，没有的话自己定义常量
        WarningManger.getInstance().addWarning(Constant.STORAGEREAD, getClass());
        //是否接收预警 可以控制预警时机
        WarningManger.getInstance().setRecieve(true);
        //关键 初始化预警接口
        WarningManger.getInstance().setOnWarning(this);

    }


    @Override
    protected void initView() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText("尾数仓备料");
        adapter = new ItemCountViewAdapter<MantissaWarehouseReady.RowsBean>(this, dataList) {
            @Override
            protected int getCountViewId() {
                return R.id.cv_countView;
            }

            @Override
            protected int getLayoutId() {
                return R.layout.fragment_mantissa_ready;
            }

            @Override
            protected void convert(ItemTimeViewHolder holder, MantissaWarehouseReady.RowsBean item, int position) {
                holder.setText(R.id.tv_line_name, "线别: " + item.getLine_name());
                holder.setText(R.id.tv_order, "工单号: " + item.getWork_order());
                holder.setText(R.id.tv_sides, "面别: " + item.getSide());
                if (1 == item.getStatus()) {
                    holder.setText(R.id.tv_states, "状态: " + "未开始发料");
                    holder.itemView.setBackgroundColor(Color.WHITE);
                } else {
                    holder.setText(R.id.tv_states, "状态：正在发料");
                    holder.itemView.setBackgroundColor(Color.YELLOW);
                }
            }
        };
        adapter.setOnItemTimeOnclick(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mantissa;
    }


    @Override
    public void getSucess(List<MantissaWarehouseReady.RowsBean> mantissaWarehouseReadies) {

        dataList.clear();
        for (int i = 0; i < mantissaWarehouseReadies.size(); i++) {
            mantissaWarehouseReadies.get(i).setEnd_time(System.currentTimeMillis() + mantissaWarehouseReadies.get(i).getRemain_time() * 1000);
            mantissaWarehouseReadies.get(i).setEntityId(i);
            if (mantissaWarehouseReadies.get(i).getStatus() == 0) {
                Collections.swap(mantissaWarehouseReadies, 0, i);
                isSending = true;
            }
        }

        dataList.addAll(mantissaWarehouseReadies);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void getFailed(String message) {
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
    }

    @Override
    public void showEmptyView() {

        statusLayout.showEmptyView();
    }

    @Override
    public void warningComing(String warningMessage) {
        DialogRelativelayout dialogRelativelayout = new DialogRelativelayout(this);
        //2.传入的是红色字体的标题
        dialogRelativelayout.setStrTitle("测试标题");
        //3.传入的是黑色字体的二级标题
        dialogRelativelayout.setStrSecondTitle("预警异常");
        //4.传入的是一个ArrayList<String>
        ArrayList<String> datas = new ArrayList<>();
        datas.add("dsfdsf");
        datas.add("sdfsdf1");
        datas.add("dsfsdf2");
        dialogRelativelayout.setStrContent(datas);
        //5.构建Dialog，setView的时候把这个View set进去。
        new AlertDialog.Builder(this).setView(dialogRelativelayout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    @Override
    protected void onResume() {
        WarningManger.getInstance().registerWReceiver(this);
        getPresenter().getMantissaWarehouseReadies();
        super.onResume();
    }

    @Override
    protected void onStop() {
        WarningManger.getInstance().unregisterWReceriver(this);
        super.onStop();
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
    public void onItemClick(View item, MantissaWarehouseReady.RowsBean rowsBean, int position) {
        if (rowsBean.getStatus() == 1 && isSending == true) {
            SnackbarUtil.showMassage(mRecyclerView, Constant.FAILURE_START_ISSUE_STRING);
            return;
        }
        Intent intent = new Intent(this, MantissaWarehouseDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", rowsBean);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

}
