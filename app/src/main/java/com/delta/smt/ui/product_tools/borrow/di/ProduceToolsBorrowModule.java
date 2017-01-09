package com.delta.smt.ui.product_tools.borrow.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.api.ApiService;
import com.delta.smt.ui.product_tools.borrow.mvp.ProduceToolsBorrowContract;
import com.delta.smt.ui.product_tools.borrow.mvp.ProduceToolsBorrowModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Shaoqiang.Zhang on 2017/1/9.
 */

@Module
public class ProduceToolsBorrowModule {

    ProduceToolsBorrowContract.View view;

    public ProduceToolsBorrowModule(ProduceToolsBorrowContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ProduceToolsBorrowContract.View getView(){
        return this.view;
    }

    @ActivityScope
    @Provides
    ProduceToolsBorrowModel getProduceToolsBorrowModel(ApiService service){
        return new ProduceToolsBorrowModel(service);
    }

}
