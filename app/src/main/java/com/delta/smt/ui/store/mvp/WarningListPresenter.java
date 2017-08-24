package com.delta.smt.ui.store.mvp;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.OutBound;
import com.delta.smt.entity.PcbNumber;
import com.delta.smt.entity.Success;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

import static com.delta.commonlibs.utils.GsonTools.createGsonListString;

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
        Map<String,String>map=new HashMap<>();
        map.put("id",sapWorkOrderId);
        map.put("type",""+alarmId);
        String jsonArray=createGsonListString(map);
        getModel().getAlarmSuccessfulState(jsonArray).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Success>() {
            @Override
            public void call(Success s) {
                getView().showContentView();
                if ("0".equals(s.getCode())){

                    getView().onSucessStates(s.getMessage());
                    }else {
                    getView().onFailed(s.getMessage());
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
        Map<String,Integer>map=new HashMap<>();
        map.put("scheduleId ",scheduleId );
        String jsonArray=createGsonListString(map);
        getModel().getScheduleSuccessState(jsonArray).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Success>() {
            @Override
            public void call(Success s) {
                getView().showContentView();
                if ("0".equals(s.getCode())){
                    getView().onSucessStates(s.getMessage());}else {
                    getView().onFailed(s.getMessage());
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


    public void fetchAlarminfoOutBound(String id, String sapWorkOrderId, String partNum, int amount){
        Map<String,String>map=new HashMap<>();
        map.put("id",id);
        map.put("sapWorkOrderId",sapWorkOrderId);
        map.put("partNum",partNum);
        map.put("amount",""+amount);
        String s= createGsonListString(map);
        Log.i("info",s);
        getModel().getOutbound(s).doOnSubscribe(new Action0() {
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
                getView().onFailed(outBound.getMessage());
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
    public void fetchScheduleOutBound(String id,String sapWorkOrderId,String partNum,int amount){
        Map<String,String>map=new HashMap<>();
        map.put("id",""+id);
        map.put("sapWorkOrderId",sapWorkOrderId);
        map.put("partNum",partNum);
        map.put("amount",""+amount);
        String s= createGsonListString(map);
        Log.i("info",s);
        getModel().getScheduleDetailed(s).doOnSubscribe(new Action0() {
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
                    getView().onFailed(outBound.getMessage());
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
                getView().onFailed(pcbNumber.getMessage());
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

    public void fetchPcbSuccess(String mAlarminfoId,int amout, int id,int type){
        Map<String,String>map=new HashMap<>();
        map.put("billId",mAlarminfoId);
        map.put("id",""+id);
        map.put("amount",""+amout);
        map.put("type",""+type);
       String s= createGsonListString(map);
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
                getView().onSucessState(success.getMessage());
                }else {
                    getView().onFailedSate(success.getMessage());
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
            Map<String,String>map=new HashMap<>();
            map.put("labelCode",s);
            String json= createGsonListString(map);
            getModel().Closelighting(json).doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    getView().showContentView();
                }
            }).subscribe(new Action1<Success>() {
                @Override
                public void call(Success success) {
                    getView().showContentView();
                    if ("0".equals(success.getCode())) {
                        getView().onCloseLightSucces(success.getMessage());
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
    public void getOutSumbit(String scheduleId){
        Map<String,String>map=new HashMap<>();
        map.put("scheduleId",scheduleId);
        String jsonArray=createGsonListString(map);
        getModel().getOutSubmit(jsonArray).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showContentView();
            }
        }).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
                getView().showContentView();
                if ("0".equals(success.getCode())) {
                    getView().onOutSubmit(success.getMessage());
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
    public void getAlarmOutSumbit(String scheduleId){
        Map<String,String>map=new HashMap<>();
        map.put("alarmId",scheduleId);
        String jsonArray=createGsonListString(map);
        Log.i("info",jsonArray);
        getModel().getAlarmOutSubmit(jsonArray).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showContentView();
            }
        }).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
                getView().showContentView();
                if ("0".equals(success.getCode())) {
                    getView().onOutSubmit(success.getMessage());
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

    public void getRefresh(String id, String partNum, int offset, int type){
        Map<String,String>map=new HashMap<>();
        map.put("id",id);
        map.put("partNum",partNum);
        map.put("offset",""+offset);
        map.put("type",""+type);
        String jsonArray=createGsonListString(map);
        Log.i("info",jsonArray);
        getModel().getRefresh(jsonArray).subscribe(new Action1<OutBound>() {
            @Override
            public void call(OutBound outBound) {
                if ("0".equals(outBound.getCode())) {
                    getView().onOutSuccess(outBound.getRows());
                } else {
                    getView().onFailed(outBound.getMessage());
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
