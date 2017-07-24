package com.delta.smt.ui.warehouse.mvp;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.bindmaterial.BindCarBean;
import com.delta.smt.entity.bindmaterial.BindLabelBean;
import com.delta.smt.entity.bindmaterial.FinishPda;
import com.delta.smt.entity.bindmaterial.ScanMaterialPanBean;
import com.delta.smt.entity.bindmaterial.StartStoreBean;
import com.delta.smt.entity.bindmaterial.WheatherBindStart;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * @copyright : 台达电子企业管理(上海)有限公司 (c) 2017
 * @creator : YangS
 * @create-time : 2017/7/11 11:29
 * @description :
 */

public class BindMaterialPresenter extends BasePresenter<BindMaterialContract.Model,BindMaterialContract.View> {

    @Inject
    public BindMaterialPresenter(BindMaterialContract.Model model, BindMaterialContract.View mView) {
        super(model, mView);
    }

    /**
     *  进入入库页面时读取已经绑定标签的列表(判断是否已开始入库)
     */
    public void judegStart(){
        getModel().wheatherStart().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<WheatherBindStart>() {
            @Override
            public void call(WheatherBindStart wheatherBindStart) {
                if(wheatherBindStart.getCode() == 0){
                    getView().showContentView();
                    getView().havedStart(wheatherBindStart);
                }else if(wheatherBindStart.getCode() == -1){
                    getView().showContentView();
                    getView().noStart(wheatherBindStart);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().showErrorView();
                getView().showMesage("网络异常");
            }
        });
    }

    /**
     * 开始新物料入库
     */
    public void startStore(){
        getModel().startStore().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<StartStoreBean>() {
            @Override
            public void call(StartStoreBean startStoreBean) {
                if(startStoreBean.getCode() == 0){
                    getView().showContentView();
                    getView().startSucceed(startStoreBean);
                }else if(startStoreBean.getCode() == -1){
                    getView().showContentView();
                    getView().startFailed(startStoreBean);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().showContentView();
                getView().showMesage("网络异常");
            }
        });
    }

    /**
     * 绑定料车
     * @param carName
     */
    public void bindCar(String carName){
        getModel().bindCar(carName).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<BindCarBean>() {
            @Override
            public void call(BindCarBean bindCarBean) {
                if(bindCarBean.getCode() == 0){
                    getView().showContentView();
                    getView().bindCarSucceed(bindCarBean);
                }else if(bindCarBean.getCode() == -1){
                    getView().showContentView();
                    getView().showMesage(bindCarBean.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().showContentView();
                getView().showMesage("网络异常");
            }
        });
    }

    /**
     * 扫描料盘
     * @param materialPan
     */
    public void scanMaterialPan(String materialPan){
        getModel().scanMate(materialPan).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<ScanMaterialPanBean>() {
            @Override
            public void call(ScanMaterialPanBean scanMaterialPanBean) {
                if(scanMaterialPanBean.getCode() == 0){
                    getView().showContentView();
                    getView().scanMaterialSucceed(scanMaterialPanBean);
                }else if(scanMaterialPanBean.getCode() == -1){
                    getView().showContentView();
                    getView().scanMaterialFailed(scanMaterialPanBean);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("werw",throwable.getMessage());
                getView().showContentView();
                getView().showMesage("网络异常");
            }
        });
    }

    /**
     * 绑定可移动标签
     * @param moveLabel
     */
    public void bindMoveLabel(String moveLabel){
        getModel().bindLabel(moveLabel).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<BindLabelBean>() {
            @Override
            public void call(BindLabelBean bindLabelBean) {
                if(bindLabelBean.getCode() == 0){
                    getView().showContentView();
                    getView().bindLabelSucceed(bindLabelBean);
                }else if(bindLabelBean.getCode() == -1){
                    getView().showContentView();
                    getView().bindLabelFailed(bindLabelBean);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().showContentView();
                getView().showMesage("网络异常");
            }
        });
    }

    /**
     * 开立入库单
     */
    public void finishedPda(){
        getModel().finisdedPda().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<FinishPda>() {
            @Override
            public void call(FinishPda finishPda) {
                if(finishPda.getCode() == 0){
                    getView().showContentView();
                    getView().finishedPdaSucceed(finishPda);
                }else if(finishPda.getCode() == -1){
                    getView().showContentView();
                    getView().finishedPdaFailded(finishPda);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().showContentView();
                getView().showMesage("网络异常");
            }
        });
    }
}
