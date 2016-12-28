package com.delta.smt.ui.storage_manger.details;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.StorageDetails;
import com.delta.smt.ui.storage_manger.details.mvp.StorageDetailsContract;
import com.delta.smt.ui.storage_manger.details.mvp.StorageDetailsPresenter;

import java.util.List;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */

public class StorageDetailsActivity extends BaseActiviy<StorageDetailsPresenter> implements StorageDetailsContract.View{


    @Override
    protected void componentInject(AppComponent appComponent) {
       // DaggerStorageDetailsComponent.builder().appComponent(appComponent).storageDetailsModule(new StorageDetailsModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_storage_details;
    }

    @Override
    public void getSucess(List<StorageDetails> storageDetailses) {

    }

    @Override
    public void getFailed() {

    }
}
