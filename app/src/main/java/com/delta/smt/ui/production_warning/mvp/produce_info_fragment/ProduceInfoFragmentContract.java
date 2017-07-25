package com.delta.smt.ui.production_warning.mvp.produce_info_fragment;





import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ProduceWarning;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.production_warining_item.ItemInfo;

import java.util.List;

import rx.Observable;


/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */

public interface ProduceInfoFragmentContract {
    interface View extends IView {
        void getItemInfoDatas(List<ItemInfo>itemInfos);
        void getItemInfoDatasFailed(String message);
        void getItemInfoConfirmSucess();

        void showLoadingView();

        void showContentView();

        //void showErrorView();

        void showEmptyView();
    }

    interface Model extends IModel {
        Observable<ProduceWarning> getItemInfoDatas(String condition);
        Observable<Result> getItemInfoConfirm(String codition);

    }
}
