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

        Observable<JsonProductRequestToolsRoot> getProductToolsInfoItem(String parm);

        Observable<JsonProductRequestToolsRoot> getProductToolsVerfy(String parm);

        Observable<JsonProductRequestToolsRoot> getProductToolsBorrowSubmit(String parm);
    }

    interface View extends IView {

        void getToolsInfo(List<ProductToolsInfo> ProductToolsItem);

        void getToolsInfoAndChangeTool(List<ProductToolsInfo> ProductToolsItem);

        void getToolsInfoInit(List<ProductToolsInfo> ProductToolsItem);

        void getToolsVerfy(List<ProductToolsInfo> ProductToolsItem);

        void getToolsBorrowSubmit(List<ProductToolsInfo> ProductToolsItem);

        void getFail(String message);

        void showLoadingView();

        void showContentView();

        void showErrorView();
    }

}
