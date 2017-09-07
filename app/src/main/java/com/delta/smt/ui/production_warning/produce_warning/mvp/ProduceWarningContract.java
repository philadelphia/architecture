package com.delta.smt.ui.production_warning.produce_warning.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ProduceWarning;
import com.delta.smt.entity.production_warining_item.TitleNumber;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public interface ProduceWarningContract {
    interface View extends IView {
        void getTitleDatas(TitleNumber titleNumber);
        void getTitleDatasFailed(String message);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();
    }

    interface Model extends IModel {
        Observable<ProduceWarning> getTitleDatas(String condition);
    }
}
