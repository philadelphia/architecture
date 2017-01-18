package com.delta.smt.ui.checkstock.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.CheckStock;
import com.delta.smt.entity.Success;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@ActivityScope
public class CheckStockPresenter extends BasePresenter<CheckStockContract.Model,CheckStockContract.View> {
    @Inject
    public CheckStockPresenter(CheckStockContract.Model model, CheckStockContract.View mView) {
        super(model, mView);
    }
    public void fetchCheckStock(String s){
        getModel().getCheckStock(s).subscribe(new Action1<CheckStock>() {
            @Override
            public void call(CheckStock rowsBeen) {
                if ("0".equals(rowsBeen.getCode())) {
                    List<CheckStock.RowsBean> rows = rowsBeen.getRows();
                    getView().onSucess(rows);
                } else {
                    getView().onFailed(rowsBeen.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed("请确认后台服务正常启动");
            }
        });
    }

    public void fetchCheckStockSuccessNumber(int id,int realCount){
        getModel().getCheckStockNumber(id,realCount).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
                if ("0".equals(success.getCode())){
                    getView().onCheckStockNumberSucess(success.getMsg());
                }else {
                    getView().onFailed(success.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed("请确认后台服务正常启动");
            }
        });
    }

    public void fetchError(String boxSerial, String subShelfSerial){
        getModel().getError(boxSerial,subShelfSerial).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
            if ("0".equals(success.getCode())){
                getView().onErrorSucess(success.getMsg());
            }else {
                getView().onFailed(success.getMsg());
            }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed("请确认后台服务正常启动");
            }
        });
    }
    public void fetchException(String boxSerial){
        getModel().getException(boxSerial).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
                if ("0".equals(success.getCode())){
                    getView().onErrorSucess(success.getMsg());
                }else {
                    getView().onFailed(success.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed("请确认后台服务正常启动");
            }
        });
    }
    public void fetchSubmit(String boxSerial){
        getModel().getSubmit(boxSerial).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
                if ("0".equals(success.getCode())){
                    getView().onErrorSucess(success.getMsg());
                }else {
                    getView().onFailed(success.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed("请确认后台服务正常启动");
            }
        });
    }



    public void fetchCheckStockSuccess(){
        getModel().getCheckStockSuccess().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

                getView().onCheckStockSucess(s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed("请确认后台服务正常启动");
            }
        });
    }
}
