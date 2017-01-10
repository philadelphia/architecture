package com.delta.smt.ui.feeder.warning.checkin.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.FeederCheckInItem;
import com.delta.smt.entity.FeederSupplyWarningItem;

import java.util.List;

import rx.Observable;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public interface CheckInContract {
    interface View extends IView{
        public void onSuccess(List<FeederCheckInItem> datas);
        public void onFailed();
    }

    interface  Model extends IModel{
        public Observable<List<FeederCheckInItem>> getAllCheckedInFeeders();
    }
}
