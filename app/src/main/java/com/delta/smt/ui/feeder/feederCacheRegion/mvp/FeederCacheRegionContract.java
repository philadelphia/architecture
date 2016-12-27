package com.delta.smt.ui.feeder.feederCacheRegion.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.LoginResult;
import com.delta.smt.entity.WareHouse;

import java.util.List;

import rx.Observable;

/**
 * Created by Tao.ZT.Zhang on 2016/12/21.
 */

public interface FeederCacheRegionContract {
    interface View extends IView{
        void onSucess(List<WareHouse> wareHouses);
        void onFailed();
    }
    interface Model extends IModel{
        Observable<List<WareHouse>> getAllWareHouse();

    };

}
