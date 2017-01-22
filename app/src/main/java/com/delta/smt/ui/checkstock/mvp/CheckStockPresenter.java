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
                getView().onFailed("请确认后台服务正常启动");
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
                getView().onFailed("请确认后台服务正常启动");
            }
        });
    }

    public void fetchError(String boxSerial, String subShelfSerial){
        getModel().getError(boxSerial,subShelfSerial).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
            if ("0".equals(success.getCode())){
                if (success.getMsg().contains("Success")){
                getView().onErrorSucess(success.getMsg());
            }else {
                getView().onFailed(success.getMsg());
            }}
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed("请确认后台服务正常启动");
            }
        });
    }
    public void fetchException(String boxSerial){
        getModel().getCheckStock(boxSerial).subscribe(new Action1<CheckStock>() {
            @Override
            public void call(CheckStock success) {
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
                                   errorBuffer.append("\n "+success.getRows().get(i).getPartNum()+"误差"+(success.getRows().get(i).getBoundCount()-success.getRows().get(i).getRealCount()));
                                   break;
                               case 3:
                                   errorBuffer.append("\n "+success.getRows().get(i).getPartNum()+"误差"+(success.getRows().get(i).getBoundCount()-success.getRows().get(i).getRealCount()));
                                   break;
                               case 4:
                                   errorBuffer.append("\n "+success.getRows().get(i).getPartNum()+"误差"+(success.getRows().get(i).getBoundCount()-success.getRows().get(i).getRealCount()));
                                   break;
                               case 5:
                                   changeBuffer.append("\n 新增"+success.getRows().get(i).getPartNum()+(success.getRows().get(i).getBoundCount()-success.getRows().get(i).getRealCount())+"片");
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
                getView().onFailed("请确认后台服务正常启动");
            }
        });
    }
    public void fetchSubmit(String boxSerial){
        getModel().getSubmit(boxSerial).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
                if ("0".equals(success.getCode())){
                    if (success.getMsg().contains("Success")){

                    getView().onErrorSucess(success.getMsg());
                }else {
                    getView().onFailed(success.getMsg());
                }}
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
