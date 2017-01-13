package com.delta.smt.ui.feeder.handle.feederCheckIn.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.FeederCheckInItem;
import com.delta.smt.entity.FeederSupplyWarningItem;
import com.delta.smt.entity.Result;

import java.util.List;

import rx.Observable;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/26.
 */

public interface FeederCheckInContract {
    interface View extends IView{
         void onSuccess(List<FeederSupplyWarningItem> datas);
         void onFailed();
    }

    interface  Model extends IModel{
         Observable<Result<FeederCheckInItem>> getAllCheckedInFeeders();
    }
}
