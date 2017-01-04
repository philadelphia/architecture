package com.delta.smt.ui.store.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ListWarning;

import java.util.List;

import rx.Observable;

/**
 * Created by Lin.Hou on 2016-12-27.
 */

public class WarningListContract {
    public interface View extends IView{
        void onSucess(List<ListWarning> wareHouses);
        void onFailed();
        void onWarningNumberSucess(List<ListWarning> wareHouses);
        void onWarningNumberFailed();
        void onSucessState(String s);
        void onFailedState(String s);
    }
    public interface Model extends IModel{
        Observable<List<ListWarning>>getListWarning();
        Observable<List<ListWarning>>getWarningNumber();
        Observable<String>getSuccessfulState ();
    }
}
