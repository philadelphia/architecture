package com.delta.smt.ui.mantissa_warehouse.ready;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.DialogRelativelayout;
import com.delta.smt.common.ItemOnclick;
import com.delta.smt.common.adapter.ItemCountdownViewAdapter;
import com.delta.smt.common.adapter.ItemTimeViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.MantissaWarehouseReady;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.mantissa_warehouse.detail.MantissaWarehouseDetailsActivity;
import com.delta.smt.ui.mantissa_warehouse.ready.di.DaggerMantissaWarehouseReadyComponent;
import com.delta.smt.ui.mantissa_warehouse.ready.di.MantissaWarehouseReadyModule;
import com.delta.smt.ui.mantissa_warehouse.ready.mvp.MantissaWarehouseReadyContract;
import com.delta.smt.ui.mantissa_warehouse.ready.mvp.MantissaWarehouseReadyPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */

public class MantissaWarehouseReadyActivity extends BaseActivity<MantissaWarehouseReadyPresenter>
        implements MantissaWarehouseReadyContract.View,
        ItemOnclick,
        WarningManger.OnWarning {

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


    private List<MantissaWarehouseReady.MantissaWarehouse> dataList = new ArrayList();

    private ItemCountdownViewAdapter<MantissaWarehouseReady.MantissaWarehouse> adapter;

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

        getPresenter().getMantissaWarehouseReadies();
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbarTitle.setText("尾数仓备料");


        adapter = new ItemCountdownViewAdapter<MantissaWarehouseReady.MantissaWarehouse>(this, dataList) {
            @Override
            protected void convert(ItemTimeViewHolder holder, MantissaWarehouseReady.MantissaWarehouse item, int position) {
                holder.setText(R.id.tv_linee, "线别: " + item.getLine());
                holder.setText(R.id.tv_number, "工单号: " + item.getWork_order());
                holder.setText(R.id.tv_face, "面别: " + item.getFace());
                if("1".equals(item.getStatus())){
                    holder.setText(R.id.tv_type, "状态: " + "等待备料");
                }
            }

            @Override
            protected int getLayoutId() {
                return R.layout.fragment_mantissa_ready;
            }
        };

        adapter.setOnItemTimeOnclck(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mantissa;
    }


    @Override
    public void getSucess(List<MantissaWarehouseReady.MantissaWarehouse> mantissaWarehouseReadies) {

        dataList.clear();
        dataList.addAll(mantissaWarehouseReadies);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void getFailed(String message) {

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
    public void onItemClick(View item, int position) {
        Intent intent = new Intent(this, MantissaWarehouseDetailsActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("item", dataList.get(position));
        intent.putExtras(bundle);
        this.startActivity(intent);
    }
}
