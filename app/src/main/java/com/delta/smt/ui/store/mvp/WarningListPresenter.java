package com.delta.smt.ui.store.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.OutBound;
import com.delta.smt.entity.PcbNumber;
import com.delta.smt.entity.Success;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Lin.Hou on 2016-12-27.
 */
@ActivityScope
public class WarningListPresenter extends BasePresenter<WarningListContract.Model,WarningListContract.View>{
    @Inject
    public WarningListPresenter(WarningListContract.Model model, WarningListContract.View mView) {
        super(model, mView);
    }


    public void getAlarmSuccessfulState(String sapWorkOrderId, int alarmId){
        getModel().getAlarmSuccessfulState(sapWorkOrderId,alarmId).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Success>() {
            @Override
            public void call(Success s) {
                getView().showContentView();
                if ("0".equals(s.getCode())){

                    getView().onSucessStates(s.getMsg());
                    }else {
                    getView().onFailed(s.getMsg());
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
    public void getScheduleSuccessState( int scheduleId){
        getModel().getScheduleSuccessState(scheduleId).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Success>() {
            @Override
            public void call(Success s) {
                getView().showContentView();
                if ("0".equals(s.getCode())){
                    getView().onSucessStates(s.getMsg());}else {
                    getView().onFailed(s.getMsg());
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


    public void fetchAlarminfoOutBound(int id, String sapWorkOrderId, String partNum, int amount){
        getModel().getOutbound(id,sapWorkOrderId,partNum,amount).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<OutBound>() {
            @Override
            public void call(OutBound outBound) {
                getView().showContentView();
                if ("0".equals(outBound.getCode())){
                    List<OutBound.DataBean> datalist = outBound.getRows();
            getView().onOutSuccess(datalist);
            }else {
                getView().onFailed(outBound.getMsg());
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
    public void fetchScheduleOutBound(int id,String sapWorkOrderId,String partNum,int amount){
        getModel().getScheduleDetailed(id,sapWorkOrderId,partNum,amount).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<OutBound>() {
            @Override
            public void call(OutBound outBound) {
                getView().showContentView();
                if ("0".equals(outBound.getCode())){
                    List<OutBound.DataBean> datalist = outBound.getRows();
                    getView().onOutSuccess(datalist);
                }else {
                    getView().onFailed(outBound.getMsg());
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
    public  void fetchPcbNumber(String streamNumber){

        getModel().getPcbNumber(streamNumber).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<PcbNumber>() {
            @Override
            public void call(PcbNumber pcbNumber) {
                getView().showContentView();
            if ("0".equals(pcbNumber.getCode())){
                getView().getNumberSucces(pcbNumber.getRows());
            }else {
                getView().onFailed(pcbNumber.getMsg());
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

    public void fetchPcbSuccess(int mAlarminfoId,int amout, int id,int type){
//        Gson gson=new Gson();
//        List<ParameterOutBound> list=new ArrayList<>();
//        ParameterOutBound parameterOutBound=new ParameterOutBound(id,amout);
//        list.add(parameterOutBound);
        JSONObject json=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.putOpt("billId",mAlarminfoId);
            jsonObject.putOpt("id",id);
//            jsonObject.putOpt("id",mAlarminfoId);
//            jsonObject.putOpt("billId",id);
            jsonObject.putOpt("amount",amout);
            jsonObject.putOpt("type",type);
            jsonArray.put(jsonObject);
            json.putOpt("data",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String s="[\'"+json.toString()+"\']";
        getModel().getPcbSuccess(s).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
                getView().showContentView();
                if("0".equals(success.getCode())){
                getView().onSucessState(success.getMsg());
                }else {
                    getView().onFailedSate(success.getMsg());
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
        public void closeLight(String s){
            getModel().Closelighting(s).doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    getView().showContentView();
                }
            }).subscribe(new Action1<Success>() {
                @Override
                public void call(Success success) {
                    getView().showContentView();
                    if ("0".equals(success.getCode())) {
                        getView().onCloseLightSucces(success.getMsg());
                    }else{
                        getView().onFailed(success.getMsg());
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
    public void getOutSumbit(int scheduleId){
        getModel().getOutSubmit(scheduleId).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showContentView();
            }
        }).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
                getView().showContentView();
                if ("0".equals(success.getCode())) {
                    getView().onOutSubmit(success.getMsg());
                }else{
                    getView().onFailed(success.getMsg());
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
    public void getAlarmOutSumbit(int scheduleId){
        getModel().getAlarmOutSubmit(scheduleId).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showContentView();
            }
        }).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
                getView().showContentView();
                if ("0".equals(success.getCode())) {
                    getView().onOutSubmit(success.getMsg());
                }else{
                    getView().onFailed(success.getMsg());
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

    public void getRefresh(int id, String partNum, int offset, int type){
        getModel().getRefresh(id,partNum,offset,type).subscribe(new Action1<OutBound>() {
            @Override
            public void call(OutBound outBound) {
                if ("0".equals(outBound.getCode())) {
                    getView().onOutSuccess(outBound.getRows());
                } else {
                    getView().onFailed(outBound.getMsg());
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
