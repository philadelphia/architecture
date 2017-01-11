package com.delta.smt.ui.feeder.wareSelect.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.WareHouse;

import java.util.List;

import rx.Observable;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public interface WareSelectContract {
    interface View extends IView{
        void onSuccess(List<WareHouse> wareHouses);
        void onFailed();
    }

    interface Model extends IModel{
        Observable<List<WareHouse>> getAllWareHouse();
    }

}
