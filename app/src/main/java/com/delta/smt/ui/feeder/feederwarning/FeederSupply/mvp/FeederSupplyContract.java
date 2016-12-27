package com.delta.smt.ui.feeder.feederWarning.FeederSupply.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.FeederSupplyWorkItem;

import java.util.List;

import rx.Observable;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

public interface FeederSupplyContract {
    interface View extends IView{
        public void onSuccess(List<FeederSupplyWorkItem> data);
        public void onFalied();
    }

    interface Model extends IModel{
        public Observable<List<FeederSupplyWorkItem>> getAllSupplyWorkItems();
    }
}
