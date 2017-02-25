package com.delta.smt.ui.fault_processing.processing;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.delta.commonlibs.utils.GsonTools;
import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.utils.SnackbarUtil;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.commonlibs.widget.statusLayout.StatusLayout;
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
import com.delta.smt.entity.FaultParameter;
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

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:02
 */

public class FalutProcessingActivity extends BaseActivity<FaultProcessingPresenter> implements FalutProcessingContract.View, WarningManger.OnWarning, ItemOnclick<RowsBean>, Toolbar.OnMenuItemClickListener {
    @BindView(R.id.rv_faultProcessing)
    RecyclerView rvFaultProcessing;
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.statusLayouts)
    StatusLayout statusLayout;
    @Inject
    WarningManger warningManger;
    private List<RowsBean> datas = new ArrayList<>();
    List<SolutionMessage.RowsBean> solutionDatas = new ArrayList<>();
    // private CommonBaseAdapter<RowsBean> mMyAdapter;
    CommonBaseAdapter<SolutionMessage.RowsBean> dialog_adapter;
    private String lines;
    private LinearLayoutManager manager;
    private ItemCountViewAdapter<RowsBean> mMyAdapter;
    private FaultParameter faultParameter;
    private String paramter;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerFaultProcessingComponent.builder().appComponent(appComponent).faultProcessingModule(new FaultProcessingModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {
        lines = getIntent().getExtras().getString(Constant.PRODUCTIONLINE);
        faultParameter = new FaultParameter();
        faultParameter.setLines(lines);
        paramter = GsonTools.createGsonString(faultParameter);
        warningManger.addWarning(Constant.ENGINEER_FAULT_ALARM_FLAG, this.getClass());
        warningManger.setRecieve(true);
        warningManger.setOnWarning(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getFaultProcessingMessages(paramter);
    }

    @Override
    protected void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("故障处理预警");
        toolbar.setOnMenuItemClickListener(this);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fault_processing, menu);
        return true;
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

        SnackbarUtil.show(statusLayout, getString(R.string.server_error_message));
    }

    @Override
    public void getSolutionMessageSucess(List<SolutionMessage.RowsBean> rowsBeen) {


        solutionDatas.clear();

        solutionDatas.addAll(rowsBeen);
        dialog_adapter.notifyDataSetChanged();

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

    @OnClick({R.id.tv_setting})
    public void onClick() {
    }

    @Override
    public void warningComing(String warningMessage) {


    }

    @Override
    protected void handError() {
        super.handError();
        statusLayout.showErrorView();
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_all:
                faultParameter.setProcesses("");
                break;
            case R.id.action_chip:
                faultParameter.setProcesses("贴片机");
                break;
            case R.id.action_reflow:
                faultParameter.setProcesses("回焊炉");
                break;
            case R.id.action_aoi:
                faultParameter.setProcesses("AOI");
                break;
            case R.id.action_ict:
                faultParameter.setProcesses("ICT");
                break;
            default:
                break;
        }
        paramter = GsonTools.createGsonString(faultParameter);
        getPresenter().getFaultProcessingMessages(paramter);
        return true;
    }
}
