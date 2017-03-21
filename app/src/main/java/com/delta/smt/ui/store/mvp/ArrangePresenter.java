package com.delta.smt.ui.store.mvp;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.AllQuery;
import com.delta.smt.entity.ItemInfo;
import com.delta.smt.entity.Success;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@FragmentScope
public class ArrangePresenter extends BasePresenter<ArrangeContract.Model,ArrangeContract.View> {
        @Inject
    public ArrangePresenter(ArrangeContract.Model model, ArrangeContract.View mView) {
        super(model, mView);
    }
    public void fatchArrange(){
        getModel().getArrange().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<AllQuery>() {
            @Override
            public void call(AllQuery itemInfos) {
                getView().showContentView();
                if ("0".equals(itemInfos.getCode())){
                    if (itemInfos.getMsg().contains("Success")){
                    List<ItemInfo> itemInfoList=new ArrayList<>();

                        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                    for (int i=0;i<itemInfos.getRows().size();i++){
                        Log.e("infows1",""+itemInfos.getRows().size());
                        ItemInfo itemInfo=new ItemInfo();
                        itemInfo.setEntityId(i);
                        itemInfo.setEnd_time(System.currentTimeMillis()+itemInfos.getRows().get(i).getLeftTime());
//                        try {
//                            Date parse = format.parse(itemInfos.getRows().get(i).getEndTime());
//                            Date parse = format.parse("18-02-22 14:23:52");
//                        itemInfo.setEndTime(10000l);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }

                        itemInfo.setText("线别:" +itemInfos.getRows().get(i).getProductLine() + "\n" + "工单号:" + itemInfos.getRows().get(i).getSapWorkOrderId() + "\n" + "PCB料号:" + itemInfos.getRows().get(i).getPartNum() + "\n" + "主板:" + itemInfos.getRows().get(i).getMainBoard() +  "\n" + "小板："+itemInfos.getRows().get(i).getSubBoard()+ "\n" + "需求量：" + itemInfos.getRows().get(i).getAmount() + "\n" + "状态:" + itemInfos.getRows().get(i).getStatus()+ "\n" + "计划上线时间:"+itemInfos.getRows().get(i).getEndTime());
                        //itemInfo.setEndTime(Long.valueOf(itemInfos.getRows().get(i).getEndTime()));
                        itemInfo.setMainBoard(itemInfos.getRows().get(i).getMainBoard());
                        itemInfo.setSubBoard(itemInfos.getRows().get(i).getSubBoard());
                        itemInfo.setMachine(itemInfos.getRows().get(i).getPartNum());
                        itemInfo.setWorkNumber(itemInfos.getRows().get(i).getSapWorkOrderId());
                        itemInfo.setAmount(itemInfos.getRows().get(i).getAmount());
                        itemInfo.setAlarminfoId(itemInfos.getRows().get(i).getId());
                        itemInfo.setAlarminfo(false);//设置标志位，排程选项进入是false
                        itemInfoList.add(itemInfo);
                    }

                    getView().onSucess(itemInfoList);
                }else {
                    getView().onFailed(itemInfos.getMsg());
                }}

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                    getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
                }catch (Exception e){

                }    }
        });
    }

    public void closeLights(int s,int type){
        getModel().getArrangeCloneLight(s,type).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }}).subscribe(new Action1<Success>() {
            @Override
            public void call(Success success) {
                getView().showContentView();
                if ("0".equals(success.getCode())){
                    getView().onColenSucess(success.getMsg());
                }else {
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

                }    }
        });
            }


}
