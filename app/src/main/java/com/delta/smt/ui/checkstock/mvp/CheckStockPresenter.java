package com.delta.smt.ui.checkstock.mvp;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.smt.entity.CheckStock;
import com.delta.smt.entity.ExceptionsBean;
import com.delta.smt.entity.Success;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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
        Map<String,String>map=new HashMap<>();
        map.put("labelCode",s);
        String json = GsonTools.createGsonListString(map);
        Log.i("info",json);
        getModel().getCheckStock(json).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<CheckStock>() {
                @Override
                public void call(CheckStock rowsBeen) {
                if ("0".equals(rowsBeen.getCode())) {
                    getView().showContentView();
                    if (rowsBeen.getMessage().contains("success")){
                    List<CheckStock.RowsBean> rows = rowsBeen.getRows();
                    getView().onSucess(rows);
                } else {
                        getView().showContentView();
                    getView().onFailed(rowsBeen.getMessage());
                }}
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                    getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
                }catch (Exception e){

                }
            }
        });
    }

    public void fetchCheckStockSuccessNumber(int id,int realCount){
        String []key={"id","realCount"};
        int []values={id,realCount};
        Map<String,String>map=new HashMap<>();
        map.put("id",""+id);
        map.put("realCount",""+realCount);

        String JsonArray=GsonTools.createGsonListString(map);
        Log.i("info",JsonArray);
        getModel().getCheckStockNumber(JsonArray).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
                getView().showContentView();
                if ("0".equals(success.getCode())){
                    if (success.getMessage().contains("success")){
                    getView().onCheckStockNumberSucess(success.getMessage());
                }else {
                        getView().showContentView();
                    getView().onFailed(success.getMessage());
                }}
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                    getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
                }catch (Exception e){

                }
            }
        });
    }

    public void fetchError(String boxSerial, String subShelfSerial){
        Map<String,String>map=new HashMap<>();
        map.put("boxSerial",boxSerial);
        map.put("labelCode",""+subShelfSerial);
        String JsonArray=GsonTools.createGsonListString(map);
        Log.i("info",JsonArray);
        getModel().getError(JsonArray).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
            if ("0".equals(success.getCode())){
                getView().showContentView();
                if (success.getMessage().contains("success")){
                getView().onErrorsSucess(success.getMessage());
            }else {
                    getView().showContentView();
                getView().onFailed(success.getMessage());
            }}
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                    getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
                }catch (Exception e){

                }
            }
        });
    }
    public void fetchException(String boxSerial){
        Map<String,String>map=new HashMap<>();
        map.put("labelCode",boxSerial);
        String json = GsonTools.createGsonListString(map);
        Log.i("info",json);
        getModel().getException(json).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<ExceptionsBean>() {
            @Override
            public void call(ExceptionsBean success) {
                getView().showContentView();
                StringBuffer errorBuffer=new StringBuffer();
                errorBuffer.append("误差 \n");
                StringBuffer fewBuffer=new StringBuffer();
                StringBuffer moreBuffer=new StringBuffer();
                StringBuffer changeBuffer=new StringBuffer();
                changeBuffer.append("变更 \n");
                StringBuffer notBuffer=new StringBuffer();
                notBuffer.append("未盘点 \n");
                if ("0".equals(success.getCode())){
                    Log.e("info",""+success.getRows().size());
                    if (success.getRows().size()!=0){
                        for (int i=0;i<success.getRows().size();i++){
                           switch (success.getRows().get(i).getStatus()){
                               case 0:
                               case 1:
                               case 2:
                                   errorBuffer.append("\n ").append(success.getRows().get(i).getPartNum()).append("误差").append(success.getRows().get(i).getBoundCount() - success.getRows().get(i).getBoundCount());
                                   break;
                               case 3:
                                   errorBuffer.append("\n ").append(success.getRows().get(i).getPartNum()).append("误差").append(success.getRows().get(i).getBoundCount() - success.getRows().get(i).getBoundCount());
                                   break;
                               case 4:
                                   errorBuffer.append("\n ").append(success.getRows().get(i).getPartNum()).append("误差").append(success.getRows().get(i).getBoundCount() - success.getRows().get(i).getBoundCount());
                                   break;
                               case 5:
                                   changeBuffer.append("\n ").append(success.getRows().get(i).getPartNum()).append(success.getRows().get(i).getBoundCount() - success.getRows().get(i).getBoundCount()).append("片");
                                   break;
                               case 6:
                                   notBuffer.append("\n ").append(success.getRows().get(i).getPartNum()).append("未盘点");
                                   break;

                           }
                        }
                        fewBuffer.append(errorBuffer.toString()).append(changeBuffer.toString()).append(notBuffer.toString());
                        getView().onErrorSucess(fewBuffer.toString());
                    }else {
                        getView().onErrorSucess("本架位盘点正常");
                    }
                }else {
                    getView().showContentView();
                    getView().onFailed(success.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                    getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
                }catch (Exception e){

                }
            }
        });
    }
    public void fetchSubmit(String boxSerial){
        getModel().getSubmit(boxSerial).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
                getView().showContentView();
                if ("0".equals(success.getCode())){
                    if (success.getMessage().contains("Success")){
                        getView().onErrorsSucess(success.getMessage());
                }else {
                    getView().onFailed(success.getMessage());
                }}
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                    getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
                }catch (Exception e){

                }
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
                getView().showErrorView();
                getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
            }
        });
    }



    public void fetchJudgeSuceess(String s) {
        Map<String,String>map=new HashMap<>();
        map.put("boxSerial",s);
        String json = GsonTools.createGsonListString(map);
        Log.i("info",json);
    getModel().getJudgeSuceess(json).doOnSubscribe(new Action0() {
        @Override
        public void call() {
            getView().showLoadingView();
        }
    }).subscribe(new Action1<Success>() {
        @Override
        public void call(Success success) {
            getView().showContentView();
            if ("0".equals(success.getCode())){
                getView().JudgeSuceess(success.getMessage());
            }else{
                getView().onFailed(success.getMessage());
            }
        }
    }, new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            try {
                getView().showErrorView();
                getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
            }catch (Exception e){

            }
        }
    });
    }
}
