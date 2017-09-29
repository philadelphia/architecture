package com.delta.smt.ui.production_warning.produce_warning_fragment.mvp;


import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ProduceWarning;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.production_warining_item.ItemWarningInfo;

import java.util.List;

import rx.Observable;

/**
 * Author Fuxiang.Zhang
 * Date   2016/12/23
 */

public interface ProduceWarningFragmentContract{
    interface View extends IView {
        void onGetWarningItemSuccess(List<ItemWarningInfo> itemWarningInfo);
        void onGetWarningItemFailed(String message);

        void getItemWarningConfirmSuccess();

        void showLoadingView();

        void showContentView();

        //void showErrorView();

        void showEmptyView();
    }

    interface Model extends IModel {
        Observable<ProduceWarning> getWarningItemList(String condition);
        Observable<Result> getItemWarningConfirm(String condition);
        Observable<Result> getBarcodeInfo(String condition);
    }
}
