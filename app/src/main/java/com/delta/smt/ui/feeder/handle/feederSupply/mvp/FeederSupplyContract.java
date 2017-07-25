package com.delta.smt.ui.feeder.handle.feederSupply.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.entity.BaseEntity;
import com.delta.smt.entity.DebitData;
import com.delta.smt.entity.FeederMESItem;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.ResultFeeder;
import com.delta.smt.entity.UpLoadEntity;

import java.util.List;

import rx.Observable;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/26.
 */

public interface FeederSupplyContract {
    interface View extends IView {
        void onSuccess(List<FeederSupplyItem> data);

        void onFeederSupplySuccess(List<FeederSupplyItem> data);


        void showUnDebitedItemList(List<DebitData> data);

        void showUnUpLoadToMESItemList(UpLoadEntity mT );

        void onFailed(String message);

        void onAllSupplyComplete();


        void onUpLoadFailed(String message);


        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();
    }

    interface Model extends IModel {
        Observable<Result<FeederSupplyItem>> getAllToBeSuppliedFeeders(String workID);

        Observable<Result<FeederSupplyItem>> getFeederInsertionToSlotTimeStamp(String condition);

        Observable<Result> resetFeederSupplyStatus(String condition);

        Observable<Result> jumpMES(String condition);


        Observable<Result<DebitData>> deductionAutomatically(String value);

        Observable<Result<DebitData>> getUnDebitedItemList(String condition);



        //获取没有上传到MES的列表
        Observable<BaseEntity<UpLoadEntity>> getUnUpLoadToMESList(String condition);

        //上传Feeder发料到MES
        Observable<Result> upLoadFeederSupplyToMES(String value);
    }
}
