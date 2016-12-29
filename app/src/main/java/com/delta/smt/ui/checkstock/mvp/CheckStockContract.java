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
    }
    public interface Model extends IModel{
        Observable<List<CheckStock>>getCheckStock();
    }
}
