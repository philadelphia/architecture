package com.delta.smt.ui.feeder.handle.feederSupply.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.Result;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

public interface FeederSupplyContract {
    interface View extends IView{
        public void onSuccess(List<FeederSupplyItem> data);

        public void onFalied();
    }

    interface Model extends IModel{
        public Observable<List<FeederSupplyItem>> getAllToBeSuppliedFeeders();

        public Observable<Result> upLoadFeederSupplyResult();
    }
}
