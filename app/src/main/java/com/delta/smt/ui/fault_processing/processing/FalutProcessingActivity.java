package com.delta.smt.ui.fault_processing.processing;

import android.support.v7.widget.RecyclerView;

import com.delta.commonlibs.widget.autolayout.AutoToolbar;
import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FalutMesage;
import com.delta.smt.ui.fault_processing.processing.di.DaggerFaultProcessingComponent;
import com.delta.smt.ui.fault_processing.processing.di.FaultProcessingModule;
import com.delta.smt.ui.fault_processing.processing.mvp.FalutProcessingContract;
import com.delta.smt.ui.fault_processing.processing.mvp.FaultProcessingPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:02
 */


public class FalutProcessingActivity extends BaseActiviy<FaultProcessingPresenter> implements FalutProcessingContract.View {
    @BindView(R.id.toolbar)
    AutoToolbar toolbar;
    @BindView(R.id.rv_faultProcessing)
    RecyclerView rvFaultProcessing;

    private List<FalutMesage> datas = new ArrayList<>();
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

//        CommonBaseAdapter<FalutMesage> adapter = new CommonBaseAdapter<FalutMesage>(this,datas) {
//            @Override
//            protected void convert(CommonViewHolder holder, FalutMesage item, int position) {
//
//
//            }
//
//
//            @Override
//            protected int getItemViewLayoutId(int position, FalutMesage item) {
//                return 0;
//            }
//        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fault_processing;
    }

    @Override
    public void getFalutMessgeSucess(List<FalutMesage> falutMesages) {

    }

    @Override
    public void getFalutMessageFailed() {

    }

}
