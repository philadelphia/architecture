package com.delta.smt.ui.fault_processing.processing.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.FaultMessage;
import com.delta.smt.entity.SolutionMessage;

import java.util.List;

import rx.Observable;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:24
 */


public interface FalutProcessingContract {

    interface Model extends IModel {
        Observable<FaultMessage> getFalutMessages(String productLines);


        Observable<SolutionMessage> getSolutionMessage(String faultCode);
    }

    interface View extends IView {

        void getFaultMessageSuccess(FaultMessage falutMesage);

        void getFaultMessageFailed(String message);

        void getSolutionMessageSuccess(List<SolutionMessage.RowsBean> rowsBeen);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();
    }
}
