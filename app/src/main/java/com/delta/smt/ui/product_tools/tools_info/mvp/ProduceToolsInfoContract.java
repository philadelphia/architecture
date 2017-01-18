package com.delta.smt.ui.product_tools.tools_info.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.JsonProductRequestToolsRoot;
import com.delta.smt.entity.ProductToolsInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/10.
 */

public interface ProduceToolsInfoContract {

    interface Model extends IModel {

        Observable<JsonProductRequestToolsRoot> getProductToolsInfoItem(int pageSize, int pageCurrent,String condition);

    }

    interface View extends IView {

        void getToolsInfo(List<ProductToolsInfo> ProductToolsItem);

        void getFail();
    }

}
