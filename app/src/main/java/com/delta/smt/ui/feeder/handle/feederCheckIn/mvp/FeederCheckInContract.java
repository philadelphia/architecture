package com.delta.smt.ui.feeder.handle.feederCheckIn.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.FeederCheckInItem;
import com.delta.smt.entity.FeederSupplyWarningItem;

import java.util.List;

import rx.Observable;

/**
 * Created by Tao.ZT.Zhang on 2016/12/26.
 */

public interface FeederCheckInContract {
    interface View extends IView{
        public void onSuccess(List<FeederSupplyWarningItem> datas);
        public void onFailed();
    }

    interface  Model extends IModel{
        public Observable<List<FeederCheckInItem>> getAllCheckedInFeeders();
    }
}
