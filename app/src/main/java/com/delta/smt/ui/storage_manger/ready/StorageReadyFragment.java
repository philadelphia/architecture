package com.delta.smt.ui.storage_manger.ready;

import com.delta.smt.R;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.storage_manger.ready.mvp.StorageReadyContract;
import com.delta.smt.ui.storage_manger.ready.mvp.StorageReadyPresenter;

/**
 * Created by Zhenyu.Liu on 2016/12/21.
 */

public class StorageReadyFragment extends BaseFragment<StorageReadyPresenter> implements StorageReadyContract.View{
    @Override
    protected void initView() {

    }

    @Override
    protected void componentInject(AppComponent appComponent) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_storage_ready;
    }

    @Override
    public void getStorageReadySucess() {

    }

    @Override
    public void getStorageReadyFailed() {

    }
}
