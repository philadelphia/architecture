package com.delta.smt.ui.fault_processing.fault_add.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.BaseEntity;

import rx.Observable;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:24
 */


public interface FaultProcessingAddContract {

    interface Model extends IModel {
        Observable<BaseEntity> addSolution(String contents);

    }

    interface View extends IView {

        void addSucess(String message);

        void addFailed(String message);

    }
}
