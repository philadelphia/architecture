package com.delta.smt.ui.checkstock.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.CheckStock;
import com.delta.smt.entity.ExceptionsBean;
import com.delta.smt.entity.Success;

import java.util.List;

import javax.inject.Inject;

import rx.exceptions.Exceptions;
import rx.functions.Action0;
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
        getModel().getCheckStock(s).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<CheckStock>() {
                @Override
                public void call(CheckStock rowsBeen) {
                if ("0".equals(rowsBeen.getCode())) {
                    getView().showContentView();
                    if (rowsBeen.getMsg().contains("Success")){
                    List<CheckStock.RowsBean> rows = rowsBeen.getRows();
                    getView().onSucess(rows);
                } else {
                    getView().onFailed(rowsBeen.getMsg());
                }}
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().showErrorView();
                getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
            }
        });
    }

    public void fetchCheckStockSuccessNumber(int id,int realCount){
        getModel().getCheckStockNumber(id,realCount).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
                if ("0".equals(success.getCode())){
                    if (success.getMsg().contains("Success")){
                    getView().onCheckStockNumberSucess(success.getMsg());
                }else {
                    getView().onFailed(success.getMsg());
                }}
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
               // 無法連接到服務器，請確認是否處於聯網狀態，服務器是否開啟，如果一直有問題請聯繫管理員
                getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
            }
        });
    }

    public void fetchError(String boxSerial, String subShelfSerial){
        getModel().getError(boxSerial,subShelfSerial).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
            if ("0".equals(success.getCode())){
                if (success.getMsg().contains("Success")){
                getView().onErrorsSucess(success.getMsg());
            }else {
                getView().onFailed(success.getMsg());
            }}
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
            }
        });
    }
    public void fetchException(String boxSerial){
        getModel().getException(boxSerial).subscribe(new Action1<ExceptionsBean>() {
            @Override
            public void call(ExceptionsBean success) {
                StringBuffer errorBuffer=new StringBuffer();
                errorBuffer.append("误差 \n");
                StringBuffer fewBuffer=new StringBuffer();

                StringBuffer moreBuffer=new StringBuffer();
                StringBuffer changeBuffer=new StringBuffer();
                changeBuffer.append("变更 \n");
                StringBuffer notBuffer=new StringBuffer();
                notBuffer.append("未盘点 \n");
                if ("0".equals(success.getCode())){
                    if (success.getMsg().contains("Success")){
                        for (int i=0;i<success.getRows().size();i++){
                           switch (Integer.valueOf(success.getRows().get(i).getStatus())){
                               case 0:
                               case 1:
                               case 2:
                                   errorBuffer.append("\n "+success.getRows().get(i).getPartNum()+"误差"+(success.getRows().get(i).getBoundCount()-success.getRows().get(i).getBoundCount()));
                                   break;
                               case 3:
                                   errorBuffer.append("\n "+success.getRows().get(i).getPartNum()+"误差"+(success.getRows().get(i).getBoundCount()-success.getRows().get(i).getBoundCount()));
                                   break;
                               case 4:
                                   errorBuffer.append("\n "+success.getRows().get(i).getPartNum()+"误差"+(success.getRows().get(i).getBoundCount()-success.getRows().get(i).getBoundCount()));
                                   break;
                               case 5:
                                   changeBuffer.append("\n "+success.getRows().get(i).getPartNum()+(success.getRows().get(i).getBoundCount()-success.getRows().get(i).getBoundCount())+"片");
                                   break;
                               case 6:
                                   notBuffer.append("\n "+success.getRows().get(i).getPartNum()+"未盘点");
                                   break;

                           }
                        }
                        fewBuffer.append(errorBuffer.toString()+changeBuffer.toString()+notBuffer.toString());
                        getView().onErrorSucess(fewBuffer.toString());
                }else {
                    getView().onFailed(success.getMsg());
                }}
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
            }
        });
    }
    public void fetchSubmit(String boxSerial){
        getModel().getSubmit(boxSerial).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
                if ("0".equals(success.getCode())){
                    if (success.getMsg().contains("Success")){

                    getView().onErrorsSucess(success.getMsg());
                }else {
                    getView().onFailed(success.getMsg());
                }}
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
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
                getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
            }
        });
    }
}
