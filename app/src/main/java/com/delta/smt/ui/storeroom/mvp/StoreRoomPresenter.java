package com.delta.smt.ui.storeroom.mvp;

import android.util.Log;

import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.Light;
import com.delta.smt.entity.ParameterLight;
import com.delta.smt.entity.Success;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        for (int i=0;i<materialBlockBarCodes.size();i++){
        JSONObject jsonObject1=new JSONObject();
            try {
                jsonObject1.putOpt("partNum",materialBlockBarCodes.get(i).getDeltaMaterialNumber());
                jsonObject1.putOpt("pcbCode",materialBlockBarCodes.get(i).getBusinessCode());
                jsonObject1.putOpt("dateCode",materialBlockBarCodes.get(i).getDC());
                jsonObject1.putOpt("serial",materialBlockBarCodes.get(i).getStreamNumber());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject1);
        }
        try {
            jsonObject.putOpt("data",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Gson gson=new Gson();
//        ParameterLight pa=new ParameterLight();
//        List<ParameterLight.DataBean> listData=new ArrayList<>();
//        for (int i=0;i<materialBlockBarCodes.size();i++){
//            ParameterLight.DataBean data=new ParameterLight.DataBean();
//            data.setPartNum(materialBlockBarCodes.get(i).getDeltaMaterialNumber());
//            data.setPcbCode(materialBlockBarCodes.get(i).getBusinessCode());
//            data.setDateCode(materialBlockBarCodes.get(i).getDC());
//            data.setSerial(materialBlockBarCodes.get(i).getStreamNumber());
//            listData.add(data);
//        }
//        pa.setData(listData);
//        String jsonString="[\'{\"data:\""+gson.toJson(listData).toString()+"}\']";
//                //.replace("\"","\\\"`");
        String jsonString="[\'"+jsonObject.toString()+"\']";
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
