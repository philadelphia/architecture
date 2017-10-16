package com.delta.smt.ui.storeroom.mvp;

import android.util.Log;

import com.delta.buletoothio.barcode.parse.entity.MaterialBlockBarCode;
import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.commonlibs.utils.GsonTools;
import com.delta.smt.entity.AddSuccess;
import com.delta.smt.entity.Light;
import com.delta.smt.entity.MaterialBlockBarCodeList;
import com.delta.smt.entity.Success;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Action0;
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
        getModel().getStoreRoomSuccess().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                getView().showContentView();
                getView().storeSuccess(s);
                Log.e("info","test成功");
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("info","test失败");
                try {
                    getView().showErrorView();
                    getView().storeFaild("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
                }catch (Exception e){

                }
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
                if("0".equals(materialBlockBarCodes.get(i).getStreamNumber().substring(0,1))) {
                    jsonObject1.putOpt("pcbCode", materialBlockBarCodes.get(i).getStreamNumber().substring(0, 2));
                }else{
                    jsonObject1.putOpt("pcbCode", materialBlockBarCodes.get(i).getDeltaMaterialNumber().substring(materialBlockBarCodes.get(i).getDeltaMaterialNumber().length()-2, materialBlockBarCodes.get(i).getDeltaMaterialNumber().length()));
                }
                jsonObject1.putOpt("dateCode",materialBlockBarCodes.get(i).getDC());
                jsonObject1.putOpt("serial",materialBlockBarCodes.get(i).getStreamNumber());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject1);
        }
        String jsonString=jsonArray.toString();
        Log.e("info",jsonString);
        getModel().OnLight(jsonString).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Light>() {
            @Override
            public void call(Light light) {
                getView().showContentView();
            if ("0".equals(light.getCode())){
                if ("当前库存中没有目标相同的料，请放置到一个空位即可!".equals(light.getMessage())){
                    getView().lightSuccsee("当前库存中没有目标相同的料，请放置到一个空位即可!","N/A");
                    }else {
                    StringBuffer buffer = new StringBuffer();
                    for (int i = 0; i < light.getRows().size(); i++) {
                        buffer.append(light.getRows().get(i).getSubShelfSerial());
                    }
                    getView().lightSuccsee(light.getMessage(),buffer.toString());
                }
                }else {
             getView().storeFaild(light.getMessage());


            }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try{
                getView().showErrorView();
                getView().storeFaild("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
            }catch (Exception e){
                }

            }
        });
    }

    public  void fatchPutInStorage(List<MaterialBlockBarCode> materialBlockBarCodes,String s){
        MaterialBlockBarCodeList materialBlockBarCodeList=new MaterialBlockBarCodeList();
        MaterialBlockBarCodeList.MaterialBlockBarCodefo codefo = null;
        List<MaterialBlockBarCodeList.MaterialBlockBarCodefo> list=new ArrayList<>();
        for(int i=0;i<materialBlockBarCodes.size();i++){
            codefo=new MaterialBlockBarCodeList.MaterialBlockBarCodefo();
            codefo.setSerial(materialBlockBarCodes.get(i).getStreamNumber());
            codefo.setPartNum(materialBlockBarCodes.get(i).getDeltaMaterialNumber());
            codefo.setPcbCode(materialBlockBarCodes.get(i).getStreamNumber().substring(0,2));
            codefo.setDateCode(materialBlockBarCodes.get(i).getDC());
            codefo.setCount(materialBlockBarCodes.get(i).getCount());
            codefo.setVender(materialBlockBarCodes.get(i).getVendor());
            codefo.setUnit(materialBlockBarCodes.get(i).getUnit());
            codefo.setPurchaseOrder(materialBlockBarCodes.get(i).getPO());
            codefo.setTradingNum(materialBlockBarCodes.get(i).getBusinessCode());
            codefo.setInvoiceNum(materialBlockBarCodes.get(i).getInvNo());
            codefo.setSubShelfCode(s);
            list.add(codefo);
        }
        String jsonString=GsonTools.createGsonString(list);
        Log.e("info",jsonString);
        getModel().PutInStorage(jsonString).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Success>() {
            @Override
            public void call(Success storageSuccess) {
                getView().showContentView();
                if (storageSuccess.getCode().equals("0")) {

                    getView().storageSuccsee();
                }else {
                    getView().storagefaild(storageSuccess.getMessage());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try{
                    getView().showErrorView();
                    getView().storeFaild("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
                }catch (Exception e){}
            }
        });
    }

    public void isBoxSerialExist(String boxseral){
        getModel().isBoxSerialExist(boxseral).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<AddSuccess>() {
            @Override
            public void call(AddSuccess success) {
            getView().showContentView();
                if (success.getCode()==0){
                    getView().isBoxSerialExistSuccess();
                }else{
                    getView().onFaild(success.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i("info", throwable.getMessage());
                try{
                    getView().showErrorView();
                    getView().onFaild("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
                }catch (Exception e){}
            }
        });
    }

    public void isLabelExist(String lable){
        getModel().isLabelExist(lable).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<AddSuccess>() {
            @Override
            public void call(AddSuccess success) {
                getView().showContentView();
                if (success.getCode()==0) {
                   getView().isLabelExistSuccess();
                }else{
                    getView().onFaild(success.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try{
                    getView().showErrorView();
                    getView().onFaild("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
                }catch (Exception e){}
            }
        });
    }

}
