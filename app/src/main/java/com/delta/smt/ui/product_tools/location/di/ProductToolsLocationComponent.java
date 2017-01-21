package com.delta.smt.ui.product_tools.location.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.product_tools.location.ProduceToolsLocationActivity;

import dagger.Component;

/**
 * Created by Shaoqiang.Zhang on 2017/1/18.
 */

@ActivityScope
@Component(modules=ProductToolsLocationModule.class, dependencies = AppComponent.class)
public interface ProductToolsLocationComponent {

    void Inject(ProduceToolsLocationActivity activity);

}
