package com.delta.smt.ui.storeroom;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.storeroom.mvp.StoreRoomPresenter;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class StoreRoomActivity extends BaseActiviy<StoreRoomPresenter>{
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
        return R.layout.activity_warning_details;
    }
}
