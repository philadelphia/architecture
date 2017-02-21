package com.delta.smt.ui.store.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.AllQuery;
import com.delta.smt.entity.ItemInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@FragmentScope
public class WarningPresenter extends BasePresenter<WarningContract.Model,WarningContract.View> {
    @Inject
    public WarningPresenter(WarningContract.Model model, WarningContract.View mView) {
        super(model, mView);
    }

    public  void fatchWarning(){
        getModel().getWarning().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<AllQuery>() {
            @Override
            public void call(AllQuery itemInfos) {
                if ("0".equals(itemInfos.getCode())) {
                    getView().showContentView();
                    if (itemInfos.getMsg().contains("Success")) {
                        List<ItemInfo> itemInfoList = new ArrayList<ItemInfo>();
                        for (int i = 0; i < itemInfos.getRows().size(); i++) {
                            ItemInfo itemInfo = new ItemInfo();
                            itemInfo.setText("线别:" + itemInfos.getRows().get(i).getProductLine() + "\n" + "工单号:" + itemInfos.getRows().get(i).getSapWorkOrderId() + "\n" + "PCB料号:" + itemInfos.getRows().get(i).getPartNum() + "\n" + "主板:" + itemInfos.getRows().get(i).getMainBoard()+"\n"+"小板:"+ itemInfos.getRows().get(i).getSubBoard()+ "\n" + "需求量：" + itemInfos.getRows().get(i).getAmount() + "\n" + "状态:" + itemInfos.getRows().get(i).getStatus());

//                            JsonProductBorrowList jsonProductBorrowList=new JsonProductBorrowList();
//                            jsonProductBorrowList.setPlanPrdTime(itemInfos.getRows().get(i).getEndTime());
//                            itemInfo.setEndTime(Long.parseLong(TimeSortUtils.getMyStyleTime(jsonProductBorrowList)));
//                            Log.e("info","--------1-------------"+itemInfos.getRows().get(i).getEndTime());
//                            Log.e("info","--------2-------------"+TimeSortUtils.getMyStyleTime(jsonProductBorrowList));
//                            Log.e("info","--------3-------------"+Long.parseLong(TimeSortUtils.getMyStyleTime(jsonProductBorrowList)));
                            itemInfo.setMainBoard(itemInfos.getRows().get(i).getMainBoard());
                            itemInfo.setSubBoard(itemInfos.getRows().get(i).getSubBoard());
                            itemInfo.setMachine(itemInfos.getRows().get(i).getPartNum());
                            itemInfo.setWorkNumber(itemInfos.getRows().get(i).getSapWorkOrderId());
                            itemInfo.setAmount(itemInfos.getRows().get(i).getAmount());
                            itemInfo.setAlarminfoId(itemInfos.getRows().get(i).getId());
                            itemInfo.setAlarminfo(true);//设置标志位，预警选项进入是true
                            itemInfoList.add(itemInfo);
                        }
                        getView().onSucess(itemInfoList);
                    } else {
                        getView().onFailed(itemInfos.getMsg());

                    }
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().showErrorView();
                getView().onFailed("无法连接到服务器，请确认是否处于联网状态，服务器是否开启，如果一直有问题请联系管理員");
            }
        });
    }
}
