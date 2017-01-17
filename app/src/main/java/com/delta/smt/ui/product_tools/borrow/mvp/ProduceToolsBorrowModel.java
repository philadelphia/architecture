package com.delta.smt.ui.product_tools.borrow.mvp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.delta.buletoothio.barcode.parse.entity.BaseEntity;
import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.commonlibs.utils.TimeUtils;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.JsonProductBorrowList;
import com.delta.smt.entity.JsonProductBorrowRoot;
import com.delta.smt.entity.ProductWorkItem;
import com.delta.smt.ui.product_tools.TimeSortUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

@Module
public class ProduceToolsBorrowModel extends BaseModel<ApiService> implements ProduceToolsBorrowContract.Model {


    public ProduceToolsBorrowModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<JsonProductBorrowRoot> getProductWorkItem(int pageSize, int pageCurrent) {

        return getService().getProductWorkItem(pageSize, pageCurrent).compose(RxsRxSchedulers.<JsonProductBorrowRoot>io_main());
    }

}
