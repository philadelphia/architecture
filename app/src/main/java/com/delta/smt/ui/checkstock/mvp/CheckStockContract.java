package com.delta.smt.ui.checkstock.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.CheckStock;
import com.delta.smt.entity.ExceptionsBean;
import com.delta.smt.entity.Success;

import java.util.List;

import rx.Observable;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class CheckStockContract {
    public interface View extends IView{
        void onSucess(List<CheckStock.RowsBean> wareHouses);
        void onFailed(String s);
        void onCheckStockNumberSucess(String wareHouses);
        void onErrorSucess(String wareHouses);
        void onErrorsSucess(String wareHouses);
        void onExceptionSucess(String wareHouses);
        void onSubmitSucess(String wareHouses);
        void onCheckStockSucess(String wareHouses);


        void showLoadingView();
        void showContentView();
        void showErrorView();
        void showEmptyView();
        void JudgeSuceess(String s);

    }
    public interface Model extends IModel{
        Observable<CheckStock>getCheckStock(String s);
        Observable<Success>getCheckStockNumber(int id, int realCount);
        Observable<Success>getError(String boxSerial,String subShelfSerial);
        Observable<ExceptionsBean>getException(String subShelfCode);
        Observable<Success>getSubmit(String subShelfCode);
        Observable<String>getCheckStockSuccess();
        Observable<Success>getJudgeSuceess(String s);

    }
}
