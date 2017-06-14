package com.delta.smt.ui.store.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.OutBound;
import com.delta.smt.entity.PcbNumber;
import com.delta.smt.entity.Success;

import java.util.List;

import rx.Observable;

/**
 * Created by Lin.Hou on 2016-12-27.
 */

public class WarningListContract {
    public interface View extends IView{
        void onFailed(String s);
        void onSucessState(String s);
        void onSucessStates(String s);
        void onFailedSate(String s);
        void onOutSubmit(String s);
        void onOutSuccess(List<OutBound.DataBean> dataBeanList);
        void getNumberSucces(PcbNumber.DataBean dataBean);
        void onCloseLightSucces(String s);
        void showLoadingView();
        void showContentView();
        void showErrorView();
        void showEmptyView();

    }
    public interface Model extends IModel{


        Observable<OutBound>getOutbound(String sapWorkOrderId);
        Observable<OutBound> getScheduleDetailed(String sapWorkOrderId);
        Observable<PcbNumber>getPcbNumber(String s);
        Observable<Success>getPcbSuccess(String s);
        Observable<Success>getAlarmSuccessfulState (String sapWorkOrderId);
        Observable<Success>getScheduleSuccessState (String scheduleId);
        Observable<Success>Closelighting(String subShelfCode);
        Observable<Success>getOutSubmit(String scheduleId);
        Observable<Success>getAlarmOutSubmit(String scheduleId);
        Observable<OutBound>getRefresh(String partNum);






    }
}
