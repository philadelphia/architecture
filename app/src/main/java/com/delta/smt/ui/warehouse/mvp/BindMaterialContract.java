package com.delta.smt.ui.warehouse.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.bindmaterial.BindCarBean;
import com.delta.smt.entity.bindmaterial.BindLabelBean;
import com.delta.smt.entity.bindmaterial.FinishPda;
import com.delta.smt.entity.bindmaterial.ScanMaterialPanBean;
import com.delta.smt.entity.bindmaterial.StartStoreBean;
import com.delta.smt.entity.bindmaterial.WheatherBindStart;

import rx.Observable;

/**
 * @copyright : 台达电子企业管理(上海)有限公司 (c) 2017
 * @creator : YangS
 * @create-time : 2017/7/11 11:25
 * @description :
 */

public interface BindMaterialContract {

    interface Model extends IModel{
        Observable<WheatherBindStart> wheatherStart();
        Observable<StartStoreBean> startStore();
        Observable<BindCarBean> bindCar(String carName);
        Observable<ScanMaterialPanBean> scanMate(String materialPan);
        Observable<BindLabelBean> bindLabel(String moveLabel);
        Observable<FinishPda> finisdedPda();
    }

    interface View extends IView{
        void showMesage(String message);
        void havedStart(WheatherBindStart wheatherBindStart);
        void noStart(WheatherBindStart wheatherBindStart);
        void startSucceed(StartStoreBean startStoreBean);
        void startFailed(StartStoreBean startStoreBean);
        void bindCarSucceed(BindCarBean bindCarBean);
        void bindCarFailed(BindCarBean bindCarBean);
        void scanMaterialSucceed(ScanMaterialPanBean scanMaterialPanBean);
        void scanMaterialFailed(ScanMaterialPanBean scanMaterialPanBean);
        void bindLabelSucceed(BindLabelBean bindLabelBean);
        void bindLabelFailed(BindLabelBean bindLabelBean);
        void finishedPdaSucceed(FinishPda finishPda);
        void finishedPdaFailded(FinishPda finishPda);

        void showLoadingView();
        void showContentView();
        void showErrorView();
        void showEmptyView();
    }
}
