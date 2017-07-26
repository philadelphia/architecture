package com.delta.smt.ui.production_warning.mvp.produce_breakdown_fragment;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ProduceWarning;
import com.delta.smt.entity.production_warining_item.ItemBreakDown;

import java.util.List;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2016/12/23.
 */

public interface ProduceBreakdownFragmentContract {
    interface View extends IView {
        void getItemBreakdownDatas(List<ItemBreakDown> itemBreakDown);
        void getItemBreakdownDatasFailed(String message);

        void showLoadingView();

        void showContentView();

        //void showErrorView();

        void showEmptyView();
    }

    interface Model extends IModel {
        Observable<ProduceWarning> getItemBreakdownDatas(String condition);
    }
}
