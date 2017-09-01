package com.delta.smt.ui.production_scan.work_order.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.production_scan.ItemWarningInfo;
import com.delta.smt.entity.production_scan.ProduceWarning;

import java.util.List;

import rx.Observable;


/**
 * Created by Shufeng.Wu on 2017/1/3.
 */

public interface WorkOrderContract {
    interface View extends IView {

        void getItemWarningDatas(List<ItemWarningInfo> itemWarningInfo);

        void getItemWarningDatasFailed(String message);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();

    }

    interface Model extends IModel {
        Observable<ProduceWarning> getItemWarningDatas(String condition);
    }
}
