package com.delta.smt.ui.checkstock.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.CheckStock;

import java.util.List;

import rx.Observable;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class CheckStockContract {
    public interface View extends IView{
        void onSucess(List<CheckStock> wareHouses);
        void onFailed();
        void onCheckStockNumberSucess(List<CheckStock> wareHouses);
        void onCheckStockNumberFailed();
        void onCheckStockSucess(String wareHouses);
        void onCheckStockFailed();
    }
    public interface Model extends IModel{
        Observable<List<CheckStock>>getCheckStock();
        Observable<List<CheckStock>>getCheckStockNumber();
        Observable<String>getCheckStockSuccess();
    }
}
