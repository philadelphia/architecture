package com.delta.smt.ui.production_warning.mvp.produce_warning;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ProduceWarning;
import com.delta.smt.ui.production_warning.item.ItemProduceLine;
import com.delta.smt.ui.production_warning.item.TitleNumber;

import java.util.List;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2016/12/22.
 */

public interface ProduceWarningContract {
    interface View extends IView {
        void getTitleDatas(TitleNumber titleNumber);
        void getTitleDatasFailed(String message);
    }

    interface Model extends IModel {
        Observable<ProduceWarning> getTitleDatas(String condition);
    }
}
