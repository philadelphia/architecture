package com.delta.smt.ui.product_tools.borrow.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.product_tools.borrow.ProduceToolsBorrowActivity;

import dagger.Component;

/**
 * Created by Shaoqiang.Zhang on 2017/1/9.
 */

@ActivityScope
@Component(modules = ProduceToolsBorrowModule.class, dependencies = AppComponent.class)
public interface ProduceToolsBorrowComponent {

    void Inject(ProduceToolsBorrowActivity activity);

}
