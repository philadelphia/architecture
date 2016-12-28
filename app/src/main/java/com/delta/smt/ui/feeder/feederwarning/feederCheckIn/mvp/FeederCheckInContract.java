package com.delta.smt.ui.feeder.feederWarning.feederCheckIn.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.FeederSupplyWorkItem;
import com.delta.smt.entity.WareHouse;

import java.util.List;

import rx.Observable;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

public interface FeederCheckInContract {
    interface View extends IView{
        public void onSuccess(List<FeederSupplyWorkItem> datas);
        public void onFailed();
    }

    interface  Model extends IModel{
        public Observable<List<FeederSupplyWorkItem>> getAllCheckedInFeeders();
    }
}
