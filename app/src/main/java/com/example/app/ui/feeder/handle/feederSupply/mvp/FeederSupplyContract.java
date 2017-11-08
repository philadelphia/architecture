package com.example.app.ui.feeder.handle.feederSupply.mvp;

import com.example.commonlibs.mvp.IModel;
import com.example.commonlibs.mvp.IView;
import com.example.app.entity.DebitData;
import com.example.app.entity.FeederSupplyItem;
import com.example.app.entity.Result;

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


        //上传Feeder发料到MES
        Observable<Result> upLoadFeederSupplyToMES(String value);

        //light off
        Observable <Result> lightOff(String argument);
    }
}
