package com.delta.smt.ui.store.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.OutBound;
import com.delta.smt.entity.PcbNumber;
import com.delta.smt.entity.Success;

import java.util.List;

import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Lin.Hou on 2016-12-27.
 */

public class WarningListContract {
    public interface View extends IView{
        void onFailed(String s);
        void onSucessState(String s);
        void onOutSuccess(List<OutBound.DataBean> dataBeanList);
        void getNumberSucces(PcbNumber.DataBean dataBean);

    }
    public interface Model extends IModel{


        Observable<OutBound>getOutbound(int id,String sapWorkOrderId,String partNum,int amount);
        Observable<OutBound> getScheduleDetailed(String sapWorkOrderId, String partNum, int  amount);
        Observable<PcbNumber>getPcbNumber(String s);
        Observable<Success>getPcbSuccess(String s);
        Observable<Success>getAlarmSuccessfulState (String sapWorkOrderId, int alarmId );
        Observable<Success>getScheduleSuccessState (String sapWorkOrderId );


    }
}
