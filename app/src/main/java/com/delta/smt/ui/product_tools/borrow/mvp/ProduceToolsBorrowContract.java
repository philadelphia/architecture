package com.delta.smt.ui.product_tools.borrow.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.JsonProductBorrowList;
import com.delta.smt.entity.JsonProductBorrowRoot;
import com.delta.smt.entity.ProductWorkItem;

import java.util.List;

import rx.Observable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public interface ProduceToolsBorrowContract {

    interface Model extends IModel{

        Observable<JsonProductBorrowRoot> getProductWorkItem(int pageSize, int pageCurrent);

    }

    interface View extends IView{

        void getFormData(List<ProductWorkItem> ProductWorkItem);

        void getFail();
    }
    
}
