package com.delta.smt.ui.production_warning.mvp.produce_line;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.production_warning.item.ItemProduceLine;

import java.util.List;

import rx.Observable;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/16 9:41
 */


public interface ProduceLineContract {
    interface View extends IView {
        void getDataLineDatas(List<ItemProduceLine> itemProduceLine);

        void getFailed(String message);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();
    }

    interface Model extends IModel {

        Observable<Result<ItemProduceLine>> getProductionLineDatas();


    }

}
