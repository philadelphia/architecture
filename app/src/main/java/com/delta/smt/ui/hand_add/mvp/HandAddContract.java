package com.delta.smt.ui.hand_add.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.hand_add.item.ItemHandAdd;

import java.util.List;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2016/12/27.
 */

public interface HandAddContract {
    interface View extends IView {
        void getItemHandAddDatas(List<ItemHandAdd> itemHandAdds);
        void getItemHandAddDatasFailed( String message);

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();

    }

    interface Model extends IModel {
        Observable<Result<ItemHandAdd>> getItemHandAddDatas();

        Observable<Result> getItemHandAddConfirm(String condition);
    }
}
