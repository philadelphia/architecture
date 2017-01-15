package com.delta.smt.ui.storeroom.mvp;

import android.util.Log;

import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.Light;
import com.delta.smt.entity.ParameterLight;
import com.delta.smt.entity.Success;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@ActivityScope
public class StoreRoomPresenter extends BasePresenter<StoreRoomContract.Model,StoreRoomContract.View>{
    @Inject
    public StoreRoomPresenter(StoreRoomContract.Model model, StoreRoomContract.View mView) {
        super(model, mView);
    }
    public void fatchStoreRoomSuccess(){
        getModel().getStoreRoomSuccess().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                getView().storeSuccess(s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().storeFaild(throwable.getMessage().toString());
            }
        });
    }
    public void fatchOnLight(List<MaterialBlockBarCode> materialBlockBarCodes){
        Gson gson=new Gson();
        ParameterLight pa=new ParameterLight();
        List<ParameterLight.DataBean> listData=new ArrayList<>();
        for (int i=0;i<materialBlockBarCodes.size();i++){
            ParameterLight.DataBean data=new ParameterLight.DataBean();
            data.setPartNum(materialBlockBarCodes.get(i).getDeltaMaterialNumber());
            data.setPcbCode(materialBlockBarCodes.get(i).getBusinessCode());
            data.setDateCode(materialBlockBarCodes.get(i).getDC());
            data.setSerial(materialBlockBarCodes.get(i).getStreamNumber());
            listData.add(data);
        }
        pa.setData(listData);
        String jsonString=gson.toJson(pa).toString();
        Log.e("info",jsonString);
        getModel().OnLight(jsonString).subscribe(new Action1<Light>() {
            @Override
            public void call(Light light) {
            if (light.getMsg().equals("Success")){
                getView().lightSuccsee();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    public  void fatchPutInStorage(List<MaterialBlockBarCode> materialBlockBarCodes,String s){
        Gson gson=new Gson();
        ParameterLight pa=new ParameterLight();
        List<ParameterLight.DataBean> listData=new ArrayList<>();
        for (int i=0;i<materialBlockBarCodes.size();i++){
            ParameterLight.DataBean data=new ParameterLight.DataBean();
            data.setSerial(materialBlockBarCodes.get(i).getStreamNumber());
            data.setPartNum(materialBlockBarCodes.get(i).getDeltaMaterialNumber());
            data.setPcbCode(materialBlockBarCodes.get(i).getBusinessCode());
            data.setDateCode(materialBlockBarCodes.get(i).getDC());
            data.setCount(materialBlockBarCodes.get(i).getCount());
            data.setSubShelfCode(s);
            listData.add(data);
        }
        pa.setData(listData);
        String jsonString=gson.toJson(pa).toString();
        Log.e("info",jsonString);
        getModel().PutInStorage(jsonString).subscribe(new Action1<Success>() {
            @Override
            public void call(Success storageSuccess) {
                if (storageSuccess.getCode().equals("0")) {
                    getView().storageSuccsee();
                }else {
                    getView().storagefaild();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().storagefaild();
            }
        });
    }
}
