package com.delta.smt.ui.fault_processing.fault_solution.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.BaseEntity;
import com.delta.smt.entity.FaultSolutionMessage;
import com.delta.smt.entity.ResultFault;
import com.delta.smt.entity.ResultString;

import java.util.List;

import rx.Observable;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:24
 */


public interface FaultSolutionContract {

    interface Model extends IModel {
        Observable<BaseEntity<String>> getDetailSolutionMessage(String productLines);


        Observable<BaseEntity> resolveFault(String content);

    }

    interface View extends IView {

        void getDetailSolutionMessage(List<FaultSolutionMessage.RowsBean> faultMessage);

        void getMessageFailed(String message);

        void resolveFaultSuccess(String message);

        void onSuccess(String message);

        void onFailed(String message);




    }
}
