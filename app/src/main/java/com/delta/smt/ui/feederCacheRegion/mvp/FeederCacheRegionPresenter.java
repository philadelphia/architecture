package com.delta.smt.ui.feederCacheRegion.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Tao.ZT.Zhang on 2016/12/21.
 */

public class FeederCacheRegionPresenter extends BasePresenter<FeederCacheRegionContract.Model, FeederCacheRegionContract.View> {
    @Inject
    public FeederCacheRegionPresenter(FeederCacheRegionContract.Model model, FeederCacheRegionContract.View mView) {
        super(model, mView);
    }
}
