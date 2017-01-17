package com.delta.smt.ui.store.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.AlarmInfoDetailed;
import com.delta.smt.entity.OutBound;
import com.delta.smt.entity.ParameterOutBound;
import com.delta.smt.entity.PcbNumber;
import com.delta.smt.entity.Success;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

import static android.R.attr.id;
import static android.R.id.list;

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
        getModel().getAlarmSuccessfulState(sapWorkOrderId,alarmId).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                getView().onSucessState(s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed();
            }
        });
    }
    public void getScheduleSuccessState(String sapWorkOrderId){
        getModel().getScheduleSuccessState(sapWorkOrderId).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                getView().onSucessState(s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed();
            }
        });
    }


    public void fetchAlarminfoOutBound(int id, String sapWorkOrderId, String partNum, int amount){
        getModel().getOutbound(id,sapWorkOrderId,partNum,amount).subscribe(new Action1<OutBound>() {
            @Override
            public void call(OutBound outBound) {
                if ("0".equals(outBound.getCode())){
            List<OutBound.DataBean> datalist = outBound.getData();
            getView().onOutSuccess(datalist);
            }else {
                getView().onFailed();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed();
            }
        });
    }
    public void fetchScheduleOutBound(String sapWorkOrderId,String partNum,int amount){
        getModel().getScheduleDetailed(sapWorkOrderId,partNum,amount).subscribe(new Action1<OutBound>() {
            @Override
            public void call(OutBound outBound) {
                if ("0".equals(outBound.getCode())){
                    List<OutBound.DataBean> datalist = outBound.getData();
                    getView().onOutSuccess(datalist);
                }else {
                    getView().onFailed();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed();
            }
        });
    }
    public  void fetchPcbNumber(String streamNumber){

        getModel().getPcbNumber(streamNumber).subscribe(new Action1<PcbNumber>() {
            @Override
            public void call(PcbNumber pcbNumber) {
            if ("0".equals(pcbNumber.getCode())){
                getView().getNumberSucces(pcbNumber.getData());
            }else {
                getView().onFailed();
            }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed();
            }
        });
    }

    public void fetchPcbSuccess(int amout, int id){
//        Gson gson=new Gson();
//        List<ParameterOutBound> list=new ArrayList<>();
//        ParameterOutBound parameterOutBound=new ParameterOutBound(id,amout);
//        list.add(parameterOutBound);
        JSONObject json=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.putOpt("id",id);
            jsonObject.putOpt("amount",amout);
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
                getView().onSucessState(success.getMsg());}else {
                    getView().onFailed();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed();
            }
        });
    }



}
