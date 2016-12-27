package com.delta.smt.ui.production_warning.mvp.produce_warning_fragment;


import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.ui.production_warning.item.ItemWarningInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */

public interface ProduceWarningFragmentContract{
    interface View extends IView {
        void getItemWarningDatas(List<ItemWarningInfo> itemWarningInfo);
        void getItemWarningDatasFailed();
    }

    interface Model extends IModel {
        Observable<List<ItemWarningInfo>> getItemWarningDatas();
    }
}
