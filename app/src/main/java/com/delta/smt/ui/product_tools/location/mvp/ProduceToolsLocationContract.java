package com.delta.smt.ui.product_tools.location.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.JsonLocationVerfyRoot;
import com.delta.smt.entity.JsonProductToolsLocationRoot;

import java.util.HashMap;

import rx.Observable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public interface ProduceToolsLocationContract {

    interface Model extends IModel {

        Observable<JsonProductToolsLocationRoot> getLocationVerify(String param);
        Observable<JsonLocationVerfyRoot> getLocationSubmit(String param);

    }

    interface View extends IView {

        int getLocation(JsonProductToolsLocationRoot param);
        int getSubmitResult(JsonLocationVerfyRoot JsonLocationVerfyRoot);
        void Fail();

    }

}
