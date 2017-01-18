package com.delta.smt.ui.product_tools.location.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.JsonProductToolsLocation;

import rx.Observable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public interface ProduceToolsLocationContract {

    interface Model extends IModel {

        Observable<JsonProductToolsLocation> getLocationVerify(String param);
        Observable<JsonProductToolsLocation> getLocationSubmit(String param);

    }

    interface View extends IView {

        int getLocation(int param);
        int getSubmitResoult(int param);

    }

}
