package com.delta.smt.ui.fault_processing.processing;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.common.adapter.ItemCountdownViewAdapter;
import com.delta.smt.common.adapter.ItemTimeViewHolder;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FalutMesage;
import com.delta.smt.ui.fault_processing.processing.di.DaggerFaultProcessingComponent;
import com.delta.smt.ui.fault_processing.processing.di.FaultProcessingModule;
import com.delta.smt.ui.fault_processing.processing.mvp.FalutProcessingContract;
import com.delta.smt.ui.fault_processing.processing.mvp.FaultProcessingPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:02
 */


public class FalutProcessingActivity extends BaseActiviy<FaultProcessingPresenter> implements FalutProcessingContract.View {
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.rv_faultProcessing)
    FamiliarRecyclerView rvFaultProcessing;
    private List<FalutMesage> datas = new ArrayList<>();
    private ItemCountdownViewAdapter<FalutMesage> mMyAdapter;

    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerFaultProcessingComponent.builder().appComponent(appComponent).faultProcessingModule(new FaultProcessingModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {


        getPresenter().getFaultProcessingMessages();
    }

    @Override
    protected void initView() {

        mMyAdapter = new ItemCountdownViewAdapter<FalutMesage>(this, datas) {
            @Override
            protected int getLayoutId() {
                return R.layout.item_processing;
            }

            @Override
            protected void convert(ItemTimeViewHolder holder, FalutMesage falutMesage, int position) {

                holder.setText(R.id.tv_line, "产线："+falutMesage.getProduceline());
                holder.setText(R.id.tv_name, falutMesage.getProcessing() + "-" + falutMesage.getFaultMessage());
                holder.setText(R.id.tv_processing, "制程：" + falutMesage.getProcessing());
                holder.setText(R.id.tv_faultMessage, "故障信息：" + falutMesage.getFaultMessage());
                holder.setText(R.id.tv_code, "故障代码：" + falutMesage.getFaultCode());

            }
        };
        rvFaultProcessing.setLayoutManager(new LinearLayoutManager(this));
        rvFaultProcessing.setAdapter(mMyAdapter);

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (null != mMyAdapter) {
            mMyAdapter.startRefreshTime();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mMyAdapter) {
            mMyAdapter.cancelRefreshTime();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mMyAdapter) {
            mMyAdapter.cancelRefreshTime();
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fault_processing;
    }

    @Override
    public void getFalutMessgeSucess(List<FalutMesage> falutMesages) {

        Log.e(TAG, "getFalutMessgeSucess: "+falutMesages+falutMesages.size());
        datas.clear();
        datas.addAll(falutMesages);
        mMyAdapter.notifyDataSetChanged();
    }

    @Override
    public void getFalutMessageFailed() {

    }

}
