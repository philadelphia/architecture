package com.delta.smt.ui.feeder.warning.supply.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.FeederSupplyWarningItem;

import java.util.List;

import rx.Observable;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/21.
 */

public interface SupplyContract {
    interface View extends IView{
         void onSuccess(List<FeederSupplyWarningItem> data);
         void onFailed();
    }

    interface Model extends IModel{
         Observable<List<FeederSupplyWarningItem>> getAllSupplyWorkItems();
    }
}
