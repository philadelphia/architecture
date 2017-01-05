package com.delta.smt.ui.fault_processing.processing.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.FalutMesage;

import java.util.List;

import rx.Observable;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:24
 */


public interface FalutProcessingContract {

    interface Model extends IModel {
        Observable<List<FalutMesage>> getFalutMessages();
    }

    interface View extends IView {

        void getFalutMessgeSucess(List<FalutMesage> falutMesages);

        void getFalutMessageFailed();
    }
}
