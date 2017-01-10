package com.delta.smt.ui.fault_processing.processing;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.delta.commonlibs.utils.IntentUtils;
import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.Constant;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.CommonBaseAdapter;
import com.delta.smt.common.CommonViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FalutMesage;
import com.delta.smt.manager.WarningManger;
import com.delta.smt.ui.fault_processing.fault_add.FaultProcessingAddActivity;
import com.delta.smt.ui.fault_processing.fault_solution.FaultSolutionActivity;
import com.delta.smt.ui.fault_processing.processing.di.DaggerFaultProcessingComponent;
import com.delta.smt.ui.fault_processing.processing.di.FaultProcessingModule;
import com.delta.smt.ui.fault_processing.processing.mvp.FalutProcessingContract;
import com.delta.smt.ui.fault_processing.processing.mvp.FaultProcessingPresenter;
import com.delta.smt.utils.ViewUtils;

import java.util.ArrayList;
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


public class FalutProcessingActivity extends BaseActiviy<FaultProcessingPresenter> implements FalutProcessingContract.View, CommonBaseAdapter.OnItemClickListener, WarningManger.OnWarning {
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
    private List<FalutMesage> datas = new ArrayList<>();
    private CommonBaseAdapter<FalutMesage> mMyAdapter;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerFaultProcessingComponent.builder().appComponent(appComponent).faultProcessingModule(new FaultProcessingModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {

        warningManger.addWarning(Constant.FAULTWARNING, this.getClass());
        warningManger.setRecieve(true);
        warningManger.setOnWarning(this);
        getPresenter().getFaultProcessingMessages();
    }

    @Override
    protected void initView() {

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("故障处理预警");
        mMyAdapter = new CommonBaseAdapter<FalutMesage>(this, datas) {
            @Override
            protected void convert(CommonViewHolder holder, FalutMesage falutMesage, int position) {
                holder.setText(R.id.tv_line, "产线：" + falutMesage.getProduceline());
                holder.setText(R.id.tv_name, falutMesage.getProcessing() + "-" + falutMesage.getFaultMessage());
                holder.setText(R.id.tv_processing, "制程：" + falutMesage.getProcessing());
                holder.setText(R.id.tv_faultMessage, "故障信息：" + falutMesage.getFaultMessage());
                holder.setText(R.id.tv_code, "故障代码：" + falutMesage.getFaultCode());

            }

            @Override
            protected int getItemViewLayoutId(int position, FalutMesage item) {
                return R.layout.item_processing;
            }

        };
        rvFaultProcessing.setLayoutManager(new LinearLayoutManager(this));
        rvFaultProcessing.setAdapter(mMyAdapter);
        mMyAdapter.setOnItemClickListener(this);
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
    public void getFalutMessgeSucess(List<FalutMesage> falutMesages) {

        Log.e(TAG, "getFalutMessgeSucess: " + falutMesages + falutMesages.size());
        datas.clear();
        datas.addAll(falutMesages);
        mMyAdapter.notifyDataSetChanged();
    }

    @Override
    public void getFalutMessageFailed() {

    }


    @OnClick({R.id.tv_setting})
    public void onClick() {
    }

    @Override
    public void onItemClick(View Itemview, Object item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.mystyle);
        View view = LayoutInflater.from(this).inflate(R.layout.dialogview_fault_processing, null);
        builder.setView(view);
        TextView textView = ViewUtils.findView(view, R.id.tv_title);
        RecyclerView rv_ll = ViewUtils.findView(view, R.id.rv_processing_dialog);
        Button button = ViewUtils.findView(view, R.id.bt_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.showIntent(FalutProcessingActivity.this, FaultProcessingAddActivity.class);
            }
        });
//        ImageView iv_add = ViewUtils.findView(view, R.id.iv_add);
//        iv_add.setOnClickListener(this);
        Log.e(TAG, "onItemClick: " + rv_ll.toString());
        List<String> datas = new ArrayList<>();
        datas.add("修改AOI检测参数");
        datas.add("AOI机台发生故障，联系厂商修理");
        // datas.add("新增解决方案");
        CommonBaseAdapter<String> adapter = new CommonBaseAdapter<String>(this, datas) {
            @Override
            protected void convert(CommonViewHolder holder, String item, int position) {

                holder.setText(R.id.tv_content, item);
            }

            @Override
            protected int getItemViewLayoutId(int position, String item) {
                return R.layout.item_fault_processing_dialog;
            }
        };

        rv_ll.setLayoutManager(new LinearLayoutManager(this));
        rv_ll.setAdapter(adapter);
        adapter.setOnItemClickListener(new CommonBaseAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(View view, String item, int position) {
                IntentUtils.showIntent(FalutProcessingActivity.this, FaultSolutionActivity.class);
            }
        });
        textView.setText("故障代码：AOI-00001");
        builder.show();
    }

    @Override
    public void warningComming(String warningMessage) {

    }

//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()) {
//            case R.id.iv_add:
//                IntentUtils.showIntent(this, FaultProcessingAddActivity.class);
//                Log.e(TAG, "onClick: ");
//                break;
//        }
//    }
}
