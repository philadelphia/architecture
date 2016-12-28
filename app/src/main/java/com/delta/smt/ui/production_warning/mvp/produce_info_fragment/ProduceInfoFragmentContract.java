package com.delta.smt.ui.production_warning.mvp.produce_info_fragment;



import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.ui.production_warning.item.ItemInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */

public interface ProduceInfoFragmentContract {
    interface View extends IView {
        void getItemInfoDatas(List<ItemInfo>itemInfos);
        void getItemInfoDatasFailed();
    }

    interface Model extends IModel {
        Observable<List<ItemInfo>> getItemInfoDatas();
    }
}
