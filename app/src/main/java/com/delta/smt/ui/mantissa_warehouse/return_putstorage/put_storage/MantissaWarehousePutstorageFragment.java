package com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage;

import android.view.KeyEvent;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.base.BaseFragment;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.storage_manger.ready.mvp.StorageReadyPresenter;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

public class MantissaWarehousePutstorageFragment extends BaseFragment<StorageReadyPresenter> implements BaseActiviy.OnDispathchKeyEvent {
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
        return R.layout.fragment_mantissa_putstorage;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return false;
    }
}
