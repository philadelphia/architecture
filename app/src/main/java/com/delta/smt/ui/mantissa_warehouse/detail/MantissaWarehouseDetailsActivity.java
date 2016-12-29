package com.delta.smt.ui.mantissa_warehouse.detail;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.MantissaWarehouseDetails;
import com.delta.smt.ui.mantissa_warehouse.detail.mvp.MantissaWarehouseDetailsContract;
import com.delta.smt.ui.mantissa_warehouse.detail.mvp.MantissaWarehouseDetailsPresenter;

import java.util.List;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */

public class MantissaWarehouseDetailsActivity extends BaseActiviy<MantissaWarehouseDetailsPresenter> implements  MantissaWarehouseDetailsContract.View  {
    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mantissa_details;
    }

    @Override
    public void getSucess(List<MantissaWarehouseDetails> mantissaWarehouseDetailses) {

    }

    @Override
    public void getFailed() {

    }
}
