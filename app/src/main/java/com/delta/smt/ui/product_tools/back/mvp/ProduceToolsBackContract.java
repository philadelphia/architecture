package com.delta.smt.ui.product_tools.back.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.JsonProductBackRoot;

import rx.Observable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public interface ProduceToolsBackContract {

    interface Model extends IModel {
        Observable<JsonProductBackRoot> getProductToolsBack(String param);
    }

    interface View extends IView {

        void getData(JsonProductBackRoot data);

        void getFail();

    }

}
