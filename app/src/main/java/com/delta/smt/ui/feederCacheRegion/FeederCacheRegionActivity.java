package com.delta.smt.ui.feederCacheRegion;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.delta.smt.R;
import com.delta.smt.base.BaseActiviy;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.feederCacheRegion.di.DaggerFeederCacheRegionComponent;
import com.delta.smt.ui.feederCacheRegion.di.FeederCacheRegionModule;
import com.delta.smt.ui.feederCacheRegion.mvp.FeederCacheRegionContract;
import com.delta.smt.ui.feederCacheRegion.mvp.FeederCacheRegionPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeederCacheRegionActivity extends BaseActiviy<FeederCacheRegionPresenter> implements FeederCacheRegionContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_selectWareHouse)
    Button btnSelectWareHouse;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_feeder_cache_region;
    }

    @Override
    protected void componentInject(AppComponent appComponent) {
        DaggerFeederCacheRegionComponent.builder().appComponent(appComponent).feederCacheRegionModule(new FeederCacheRegionModule(this)).build().inject(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onSucess() {

    }

    @Override
    public void onFailed() {

    }


    @OnClick(R.id.btn_selectWareHouse)
    public void onClick() {
    }
}
