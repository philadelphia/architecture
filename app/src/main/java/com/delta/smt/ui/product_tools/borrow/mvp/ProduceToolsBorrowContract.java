package com.delta.smt.ui.product_tools.borrow.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.ProductWorkItem;

import java.util.List;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

public interface ProduceToolsBorrowContract {

    interface Model extends IModel{

        List<ProductWorkItem> getProductWorkItem();

    }

    interface View extends IView{

        void getFormData(List<ProductWorkItem> ProductWorkItem);

    }
    
}
