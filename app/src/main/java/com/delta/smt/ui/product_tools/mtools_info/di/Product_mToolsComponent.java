package com.delta.smt.ui.product_tools.mtools_info.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.product_tools.mtools_info.Produce_mToolsActivity;

import dagger.Component;

/**
 * Created by Shaoqiang.Zhang on 2017/1/11.
 */

@ActivityScope
@Component(modules = Product_mToolsModule.class, dependencies = AppComponent.class)
public interface Product_mToolsComponent {

    void Inject(Produce_mToolsActivity activity);

}
