package com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.schedule.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.ScheduleResult;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Zhenyu.Liu on 2017/9/27.
 */

@ActivityScope
public class SchedulePresenter extends BasePresenter<ScheduleContract.Model , ScheduleContract.View> {

    @Inject
    public SchedulePresenter(ScheduleContract.Model model, ScheduleContract.View mView) {
        super(model, mView);
    }


    public void getScheduleList() {

        getModel().getSchedule().subscribe(new Action1<ScheduleResult>() {
            @Override
            public void call(ScheduleResult scheduleResultResult) {

                if ("0".equals(scheduleResultResult.getCode())) {
                    getView().getScheduleSuccess(scheduleResultResult.getRows());
                } else {
                    getView().getScheduleFailed(scheduleResultResult.getmessage());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getScheduleFailed(throwable.getMessage());
                    getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
