package com.delta.smt.ui.production_scan.work_order.mvp;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.production_scan.ItemWarningInfo;
import com.delta.smt.entity.production_scan.ProduceWarning;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public class WorkOrderPresenter extends BasePresenter<WorkOrderContract.Model, WorkOrderContract.View> {

    @Inject
    public WorkOrderPresenter(WorkOrderContract.Model model, WorkOrderContract.View mView) {
        super(model, mView);
    }

    public void getWorkOrderItems(String condition) {
        getModel().getItemWarningDatas(condition).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<ProduceWarning>() {

            @Override
            public void call(ProduceWarning itemWarningInfos) {
                if (itemWarningInfos.getCode().equals("0")) {

                    //过滤接料预警item
                    List<ItemWarningInfo> list = itemWarningInfos.getRows().getAlarm();
                    List<ItemWarningInfo> listRes = new ArrayList<ItemWarningInfo>();
                    for (int i = 0; i < list.size(); i++) {
                        ItemWarningInfo item = list.get(i);
                        if ("接料预警".equals(item.getTitle())) {
                            listRes.add(item);
                        }
                    }


                    if (listRes.size() == 0) {
                        getView().showEmptyView();
                    } else {
                        getView().showContentView();
                        getView().getItemWarningDatas(itemWarningInfos.getRows().getAlarm());
                        Log.e("aaa", "fagment:预警数量" + String.valueOf(itemWarningInfos.getRows().getAlarm().size()));
                    }

                } else {
                    getView().getItemWarningDatasFailed(itemWarningInfos.getMsg());
                    getView().showErrorView();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                    getView().getItemWarningDatasFailed("Error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
