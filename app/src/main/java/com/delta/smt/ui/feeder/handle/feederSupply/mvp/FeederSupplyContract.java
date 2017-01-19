package com.delta.smt.ui.feeder.handle.feederSupply.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.ResultFeeder;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/26.
 */

public interface FeederSupplyContract {
    interface View extends IView{
         void onSuccess(List<FeederSupplyItem> data);

         void onFailed(String message);

        void onUpLoadFailed(String message);
    }

    interface Model extends IModel{
         Observable<Result<FeederSupplyItem>> getAllToBeSuppliedFeeders(String workID);

        Observable<Result<FeederSupplyItem>> getFeederInsertionToSlotTimeStamp(String condition);

         Observable<ResultFeeder> upLoadFeederSupplyResult();
    }
}
