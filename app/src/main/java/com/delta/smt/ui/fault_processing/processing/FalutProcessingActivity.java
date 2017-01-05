package com.delta.smt.ui.fault_processing.processing;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.FalutMesage;
import com.delta.smt.ui.fault_processing.processing.di.DaggerFaultProcessingComponent;
import com.delta.smt.ui.fault_processing.processing.di.FaultProcessingModule;
import com.delta.smt.ui.fault_processing.processing.mvp.FalutProcessingContract;
import com.delta.smt.ui.fault_processing.processing.mvp.FaultProcessingPresenter;

import java.util.List;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:02
 */


public class FalutProcessingActivity extends BaseActiviy<FaultProcessingPresenter> implements FalutProcessingContract.View {
    @Override
    protected void componentInject(AppComponent appComponent) {

        DaggerFaultProcessingComponent.builder().appComponent(appComponent).faultProcessingModule(new FaultProcessingModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

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
