package com.delta.smt.ui.maintain.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.LedLight;
import com.delta.smt.entity.Success;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Lin.Hou on 2017-03-13.
 */
@ActivityScope
public class PCBMaintainPresenter extends BasePresenter<PCBMaintainContract.Model,PCBMaintainContract.View> {
    @Inject
    public PCBMaintainPresenter(PCBMaintainContract.Model model, PCBMaintainContract.View mView) {
        super(model, mView);
    }
    public void getSubshelf(String s) {
        getModel().getSubshelf(s).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<LedLight>() {
            @Override
            public void call(LedLight dataBean) {
                if ("0".equals(dataBean.getCode())) {
                    getView().showContentView();
                    getView().getSubshelf(dataBean.getRows());
                } else {
                    getView().showContentView();
                    getView().onFailed(dataBean.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                    getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
                } catch (Exception e) {

                }
            }
        });
    }
    public void getUpdate(String id,String lightSerial){
        getModel().getUpdate(id,lightSerial).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
                if ("0".equals(success.getCode())) {
                    getView().showContentView();
                    getView().getUpdate(success.getMessage());
                } else if("-1".equals(success.getCode())){
                    getView().showContentView();
                    getView().onFailed(success.getMessage());
                }else {
                    getView().showContentView();
                    getView().UnboundDialog(success.getCode());
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
    public void getUnbound(String id){
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject1=new JSONObject();
        try {
            jsonObject1.putOpt("id",id);
            jsonArray.put(jsonObject1);
            jsonObject.putOpt("data",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsonString="[\'"+jsonObject.toString()+"\']";
        getModel().getUnbound(jsonString).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
                if ("0".equals(success.getCode())) {
                    getView().showContentView();
                    getView().Unbound(success.getMessage());
                } else {
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
}
