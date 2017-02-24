package com.delta.smt.ui.production_warning.mvp.accept_materials_detail;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.production_warning.item.ItemAcceptMaterialDetail;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Fuxiang.Zhang on 2017/2/13.
 */
@ActivityScope
public class AcceptMaterialsPresenter extends BasePresenter<AcceptMaterialsContract.Model,AcceptMaterialsContract.View>{

    @Inject
    public AcceptMaterialsPresenter(AcceptMaterialsContract.Model model, AcceptMaterialsContract.View mView) {
        super(model, mView);
    }

    //请求item数据
    public void getItemDatas(String line){

        Map<String,String> mMap=new HashMap<>();
        mMap.put("line",line);
        line=new Gson().toJson(mMap);
        Log.e("aaa", "getItemDatas: "+line);
        getModel().getAcceptMaterialsItemDatas(line).subscribe(new Action1<ItemAcceptMaterialDetail>() {
            @Override
            public void call(ItemAcceptMaterialDetail itemAcceptMaterialDetail) {
                if (itemAcceptMaterialDetail.getCode().equals("0")) {
                    getView().getAcceptMaterialsItemDatas(itemAcceptMaterialDetail);
                }else{
                    getView().getItemDatasFailed(itemAcceptMaterialDetail.getMsg());

                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getItemDatasFailed(throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //提交扫码成功数据
    public void commitSerialNumber(String oldSerialNumber, String newSerialNumber){
        Log.e("aaa", "commitSerialNumber:old: "+oldSerialNumber );
        Log.e("aaa", "commitSerialNumber:new: "+newSerialNumber );

        Map<String, String> map = new HashMap<>();
        map.put("oldSerialNumber", oldSerialNumber);
        map.put("newSerialNumber", newSerialNumber);
        String argu = new Gson().toJson(map);


        getModel().commitSerialNumber(argu).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if ("0".equals(result.getCode())) {
                    getView().commitSerialNumberSucess();
                }else{
                    getView().getItemDatasFailed(result.getMessage());
                    Log.e("aaa", "请求错误 "+result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getItemDatasFailed(throwable.getMessage());
                    Log.e("aaa", "请求异常 "+throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //返回关灯请求
    public void requestCloseLight(String line){
        Log.e(TAG, "requestCloseLight: "+line );
        Map<String,String> map=new HashMap<>();
        map.put("line",line);
        line=new Gson().toJson(map);

        getModel().requestCloseLight(line).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if ("0".equals(result.getCode())) {
                    getView().getItemDatasFailed("已关灯");
                }else{
                    getView().getItemDatasFailed(result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getItemDatasFailed(throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
