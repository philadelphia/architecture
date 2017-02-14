package com.delta.smt.ui.fault_processing.processing;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.ToastUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.libs.adapter.ItemCountViewAdapter;
import com.delta.libs.adapter.ItemOnclick;
import com.delta.libs.adapter.ItemTimeViewHolder;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActivity;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FaultMessage;
import com.delta.smt.entity.FaultMessage.RowsBean;
import com.delta.smt.entity.SolutionMessage;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.fault_processing.fault_add.FaultProcessingAddActivity;
import com.delta.smt.ui.fault_processing.fault_solution.FaultSolutionDetailActivity;
import com.delta.smt.ui.fault_processing.processing.di.DaggerFaultProcessingComponent;
import com.delta.smt.ui.fault_processing.processing.di.FaultProcessingModule;
import com.delta.smt.ui.fault_processing.processing.mvp.FalutProcessingContract;
import com.delta.smt.ui.fault_processing.processing.mvp.FaultProcessingPresenter;
import com.delta.smt.utils.ViewUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:02
 */


public class FalutProcessingActivity extends BaseActivity<FaultProcessingPresenter> implements FalutProcessingContract.View, WarningManger.OnWarning, ItemOnclick<RowsBean> {
    @BindView(R.id.rv_faultProcessing)
    FamiliarRecyclerView rvFaultProcessing;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @Inject
    WarningManger warningManger;
    private List<RowsBean> datas = new ArrayList<>();
    List<SolutionMessage.RowsBean> solutionDatas = new ArrayList<>();
    // private CommonBaseAdapter<RowsBean> mMyAdapter;
    CommonBaseAdapter<SolutionMessage.RowsBean> dialog_adapter;
    private String lines;
    private LinearLayoutManager manager;
    private ItemCountViewAdapter<RowsBean> mMyAdapter;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerFaultProcessingComponent.builder().appComponent(appComponent).faultProcessingModule(new FaultProcessingModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        lines = getIntent().getExtras().getString(Constant.PRODUCTIONLINE);
        warningManger.addWarning(Constant.ENGINEER_FAULT_ALARM_FLAG, this.getClass());
        warningManger.setRecieve(true);
        warningManger.setOnWarning(this);
        getPresenter().getFaultProcessingMessages(lines);
    }

    @Override
    protected void initView() {

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("故障处理预警");
//        mMyAdapter = new CommonBaseAdapter<RowsBean>(this, datas) {
//            @Override
//            protected void convert(CommonViewHolder holder, RowsBean falutMesage, int position) {
//                holder.setText(R.id.tv_line, "产线：" + falutMesage.getLine());
//                holder.setText(R.id.tv_name, falutMesage.getProcess() + "-" + falutMesage.getFaultMessage());
//                holder.setText(R.id.tv_processing, "制程：" + falutMesage.getProcess());
//                holder.setText(R.id.tv_faultMessage, "故障信息：" + falutMesage.getFaultMessage());
//                holder.setText(R.id.tv_code, "故障代码：" + falutMesage.getFaultCode());
//                Chronometer chronometer = holder.getView(R.id.chronometer);
//                chronometer.setBase(SystemClock.elapsedRealtime() - 1000 * 60);
//                chronometer.start();
//            }
//
//            @Override
//            protected int getItemViewLayoutId(int position, RowsBean item) {
//                return R.layout.item_processing;
//            }
//
//        };
        mMyAdapter = new ItemCountViewAdapter<RowsBean>(this, datas) {
            @Override
            protected int getCountViewId() {
                return R.id.cv_countView;
            }

            @Override
            protected int getLayoutId() {
                return R.layout.item_processing;
            }

            @Override
            protected void convert(ItemTimeViewHolder holder, RowsBean falutMesage, int position) {
                holder.setText(R.id.tv_line, "产线：" + falutMesage.getLine());
                holder.setText(R.id.tv_name, falutMesage.getProcess() + "-" + falutMesage.getFaultMessage());
                holder.setText(R.id.tv_processing, "制程：" + falutMesage.getProcess());
                holder.setText(R.id.tv_faultMessage, "故障信息：" + falutMesage.getFaultMessage());
                holder.setText(R.id.tv_code, "故障代码：" + falutMesage.getFaultCode());
            }
        };
        manager = new LinearLayoutManager(this);
        rvFaultProcessing.setLayoutManager(manager);
        rvFaultProcessing.setAdapter(mMyAdapter);
        mMyAdapter.setOnItemTimeOnclick(this);


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
    protected int getContentViewId() {
        return R.layout.activity_fault_processing;
    }


    @Override
    public void getFalutMessgeSucess(FaultMessage falutMesage) {

        datas.clear();
        List<RowsBean> rows = falutMesage.getRows();
        for (int i = 0; i < rows.size(); i++) {
            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            try {
                Date parse = format.parse(rows.get(i).getCreateTime());
                rows.get(i).setCreat_time(parse.getTime());
                rows.get(i).setEntityId(i);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        datas.addAll(rows);
        mMyAdapter.notifyDataSetChanged();
    }

    @Override
    public void getFalutMessageFailed(String message) {

        ToastUtils.showMessage(this, message);
    }

    @Override
    public void getSolutionMessageSucess(List<SolutionMessage.RowsBean> rowsBeen) {


        solutionDatas.clear();

        solutionDatas.addAll(rowsBeen);
        dialog_adapter.notifyDataSetChanged();

    }

    @OnClick({R.id.tv_setting})
    public void onClick() {
    }

    @Override
    public void warningComing(String warningMessage) {


    }


    @Override
    public void onItemClick(final View Itemview, final RowsBean item, int position) {

        getPresenter().getSolution(item.getFaultCode());
        solutionDatas.clear();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.mystyle);
        View view = LayoutInflater.from(this).inflate(R.layout.dialogview_fault_processing, null);
        builder.setView(view);
        TextView textView = ViewUtils.findView(view, R.id.tv_title);
        RecyclerView rv_ll = ViewUtils.findView(view, R.id.rv_processing_dialog);
        TextView tv_add = ViewUtils.findView(view, R.id.tv_add);
        tv_add.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_add.getPaint().setAntiAlias(true);//抗锯齿
        textView.setText(item.getFaultType() + " " + item.getFaultCode());
        final AlertDialog dialog = builder.show();
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString(Constant.FAULTCODE, item.getFaultCode());
                IntentUtils.showIntent(FalutProcessingActivity.this, FaultProcessingAddActivity.class, bundle);
                dialog.dismiss();
            }
        });
        Log.e(TAG, "onItemClick: " + rv_ll.toString());
        dialog_adapter = new CommonBaseAdapter<SolutionMessage.RowsBean>(this, solutionDatas) {
            @Override
            protected void convert(CommonViewHolder holder, SolutionMessage.RowsBean item, int position) {

                TextView textView = holder.getView(R.id.tv_content);
                textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                textView.getPaint().setAntiAlias(true);//抗锯齿
                textView.setText(Html.fromHtml(item.getName()));
                //holder.setText(R.id.tv_content, item.getName());
            }

            @Override
            protected int getItemViewLayoutId(int position, SolutionMessage.RowsBean item) {
                return R.layout.item_fault_processing_dialog;
            }
        };

        rv_ll.setLayoutManager(new LinearLayoutManager(this));
        rv_ll.setAdapter(dialog_adapter);
        rv_ll.setVerticalScrollBarEnabled(true);
        dialog_adapter.setOnItemClickListener(new CommonBaseAdapter.OnItemClickListener<SolutionMessage.RowsBean>() {
            @Override
            public void onItemClick(View view, SolutionMessage.RowsBean rowsBean, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.FAULTID, String.valueOf(item.getId()));
                bundle.putString(Constant.FAULTCODE, rowsBean.getFaultCode());
                bundle.putString(Constant.FAULTSOLUTIONID, String.valueOf(rowsBean.getId()));
                bundle.putString(Constant.FAULTSOLUTIONNAME, rowsBean.getName());
                IntentUtils.showIntent(FalutProcessingActivity.this, FaultSolutionDetailActivity.class, bundle);
                dialog.dismiss();

            }
        });


    }
}
