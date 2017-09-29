package com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.schedule.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ScheduleResult;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2017/9/26.
 */

public interface ScheduleContract {

    interface Model extends IModel {

        Observable<ScheduleResult> getSchedule( );

    }


    interface View extends IView {

        void getScheduleSuccess(List<ScheduleResult.Schedule> scheduleResults);

        void getScheduleFailed(String message);

        void showLoadingView();

        void showEmptyView();

        void showContentView();

        void showErrorView();
    }

}
