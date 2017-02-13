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
        getModel().getAlarmSuccessfulState(sapWorkOrderId,alarmId).subscribe(new Action1<Success>() {
            @Override
            public void call(Success s) {
                if ("0".equals(s.getCode())){
                    if (s.getMsg().contains("Success")){
                    getView().onSucessStates(s.getMsg());
                    }else {
                    getView().onFailed(s.getMsg());
                }}
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
            }
        });
    }
    public void getScheduleSuccessState( int scheduleId){
        getModel().getScheduleSuccessState(scheduleId).subscribe(new Action1<Success>() {
            @Override
            public void call(Success s) {
                if ("0".equals(s.getCode())){
                getView().onSucessStates(s.getMsg());}else {
                    getView().onFailed(s.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
            }
        });
    }


    public void fetchAlarminfoOutBound(int id, String sapWorkOrderId, String partNum, int amount){
        getModel().getOutbound(id,sapWorkOrderId,partNum,amount).subscribe(new Action1<OutBound>() {
            @Override
            public void call(OutBound outBound) {
                if ("0".equals(outBound.getCode())){
                    if (outBound.getMsg().contains("Success")){
            List<OutBound.DataBean> datalist = outBound.getRows();
            getView().onOutSuccess(datalist);
            }else {
                getView().onFailed(outBound.getMsg());
                }}
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
            }
        });
    }
    public void fetchScheduleOutBound(int id,String sapWorkOrderId,String partNum,int amount){
        getModel().getScheduleDetailed(id,sapWorkOrderId,partNum,amount).subscribe(new Action1<OutBound>() {
            @Override
            public void call(OutBound outBound) {
                if ("0".equals(outBound.getCode())){
                    if (outBound.getMsg().contains("Success")){
                    List<OutBound.DataBean> datalist = outBound.getRows();
                    getView().onOutSuccess(datalist);
                }else {
                    getView().onFailed(outBound.getMsg());
                }}
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
            }
        });
    }
    public  void fetchPcbNumber(String streamNumber){

        getModel().getPcbNumber(streamNumber).subscribe(new Action1<PcbNumber>() {
            @Override
            public void call(PcbNumber pcbNumber) {
            if ("0".equals(pcbNumber.getCode())){

                getView().getNumberSucces(pcbNumber.getRows());
            }else {
                getView().onFailed(pcbNumber.getMsg());
            }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
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
            jsonObject.putOpt("amount",amout);
            jsonObject.putOpt("type",type);
            jsonArray.put(jsonObject);
            json.putOpt("data",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String s="[\'"+json.toString()+"\']";
        getModel().getPcbSuccess(s).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
                if("0".equals(success.getCode())){
                getView().onSucessState(success.getMsg());
                }else {
                    getView().onFailed(success.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
            }
        });
    }



}
