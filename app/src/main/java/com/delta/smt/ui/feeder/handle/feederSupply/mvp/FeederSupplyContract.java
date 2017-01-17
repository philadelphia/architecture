package com.delta.smt.ui.feeder.handle.feederSupply.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.Result;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/26.
 */

public interface FeederSupplyContract {
    interface View extends IView{
         void onSuccess(List<FeederSupplyItem> data);

         void onFailed();
    }

    interface Model extends IModel{
         Observable<Result<FeederSupplyItem>> getAllToBeSuppliedFeeders(String workID);

         Observable<Result> upLoadFeederSupplyResult();
    }
}
