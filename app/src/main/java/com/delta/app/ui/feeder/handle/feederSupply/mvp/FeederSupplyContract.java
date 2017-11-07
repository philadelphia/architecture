package com.delta.app.ui.feeder.handle.feederSupply.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.app.entity.BaseEntity;
import com.delta.app.entity.DebitData;
import com.delta.app.entity.FeederSupplyItem;
import com.delta.app.entity.Result;

import java.util.List;

import rx.Observable;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/26.
 */

public interface FeederSupplyContract {
    interface View extends IView {
        void onGetFeederListSuccess(List<FeederSupplyItem> data);

        void onFeederSupplySuccess(List<FeederSupplyItem> data);


        void showUnDebitedItemList(List<DebitData> data);

        void showUnUpLoadToMESItemList(UpLoadEntity mT );

        void onGetFeederListFailed(String message);

        void onAllSupplyComplete();


        void onUpLoadFailed(String message);


        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();

        void upLoading();

        void upLoadFailed(String mMessage);
    }

    interface Model extends IModel {
        Observable<Result<FeederSupplyItem>> getFeederList(String workID);

        Observable<Result<FeederSupplyItem>> getFeederInsertionToSlotTimeStamp(String condition);

        Observable<Result> resetFeederSupplyStatus(String condition);

        Observable<Result> jumpMES(String condition);


        Observable<Result<DebitData>> deductionAutomatically(String value);

        Observable<Result<DebitData>> getUnDebitedItemList(String condition);



        //获取没有上传到MES的列表
        Observable<BaseEntity<UpLoadEntity>> getUnUpLoadToMESList(String condition);

        //上传Feeder发料到MES
        Observable<Result> upLoadFeederSupplyToMES(String value);

        //light off
        Observable <Result> lightOff(String argument);
    }
}
