package com.delta.smt.ui.product_tools.tools_info.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ProductWorkItem;
import com.delta.smt.entity.Product_mToolsInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/10.
 */

public interface ProduceToolsInfoContract {

    interface Model extends IModel {

        Observable<List<Product_mToolsInfo>> getProductToolsInfoItem();

    }

    interface View extends IView {

        void getToolsInfo(List<Product_mToolsInfo> ProductToolsItem);

        void getFail();
    }

}
