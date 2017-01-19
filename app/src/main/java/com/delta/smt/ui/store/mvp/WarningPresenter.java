package com.delta.smt.ui.store.mvp;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.AllQuery;
import com.delta.smt.entity.ItemInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
        getModel().getWarning().subscribe(new Action1<AllQuery>() {
            @Override
            public void call(AllQuery itemInfos) {
                if ("0".equals(itemInfos.getCode())) {
                    if (itemInfos.getMsg().contains("Sucess")) {
                        List<ItemInfo> itemInfoList = new ArrayList<ItemInfo>();
                        for (int i = 0; i < itemInfos.getRows().size(); i++) {
                            ItemInfo itemInfo = new ItemInfo();
                            itemInfo.setText("产线:" + itemInfos.getRows().get(i).getProductLine() + "\n" + "工单号:" + itemInfos.getRows().get(i).getSapWorkOrderId() + "\n" + "PCB料号:" + itemInfos.getRows().get(i).getPartNum() + "\n" + "机种:" + itemInfos.getRows().get(i).getMachineType() + "\n" + "需求量：" + itemInfos.getRows().get(i).getAmount() + "\n" + "状态:" + itemInfos.getRows().get(i).getStatus());
                            //itemInfo.setEndTime(Long.valueOf(itemInfos.getRows().get(i).getEndTime()));
                            itemInfo.setEndTime(9000);
                            itemInfo.setCountdown("7:53:48");
                            itemInfo.setMaterialNumber(itemInfos.getRows().get(i).getMachineType());
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
                getView().onFailed("请确认后台服务正常启动");
            }
        });
    }
}
