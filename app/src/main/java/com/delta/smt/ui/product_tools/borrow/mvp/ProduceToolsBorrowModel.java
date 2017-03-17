package com.delta.smt.ui.product_tools.borrow.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.JsonProductBorrowRoot;

import dagger.Module;
import rx.Observable;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

@Module
public class ProduceToolsBorrowModel extends BaseModel<ApiService> implements ProduceToolsBorrowContract.Model {


    public ProduceToolsBorrowModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<JsonProductBorrowRoot> getProductWorkItem() {

        return getService().getProductWorkItem().compose(RxsRxSchedulers.<JsonProductBorrowRoot>io_main());
    }

}
