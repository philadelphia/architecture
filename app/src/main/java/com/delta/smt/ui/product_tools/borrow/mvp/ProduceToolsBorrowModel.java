package com.delta.smt.ui.product_tools.borrow.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ProductWorkItem;

import java.util.List;

import dagger.Module;
import rx.Observable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

@Module
public class ProduceToolsBorrowModel extends BaseModel<ApiService> implements ProduceToolsBorrowContract.Model{


    public ProduceToolsBorrowModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<List<ProductWorkItem>> getProductWorkItem() {
        return getService().getProductWorkItem();
    }
}
