package com.delta.smt.ui.store.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ListWarning;
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
        void onFailed();
        void onSucessState(String s);
        void onOutSuccess(List<OutBound.DataBean> dataBeanList);
        void getNumberSucces(PcbNumber.DataBean dataBean);

    }
    public interface Model extends IModel{

        Observable<String>getSuccessfulState ();
        Observable<OutBound>getOutbound(String s);
        Observable<PcbNumber>getPcbNumber(String s);
        Observable<Success>getPcbSuccess(String s);

    }
}
