package com.delta.smt.ui.product_tools.mtools_info.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ProductWorkItem;
import com.delta.smt.entity.Product_mToolsInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/11.
 */

public interface Produce_mToolsContract {

    interface Model extends IModel {
        Observable<List<Product_mToolsInfo>> getProduct_mToolsInfo();
    }

    interface View extends IView {

        void get_mToolsData(List<Product_mToolsInfo> data);

        void getFail();

    }

}
