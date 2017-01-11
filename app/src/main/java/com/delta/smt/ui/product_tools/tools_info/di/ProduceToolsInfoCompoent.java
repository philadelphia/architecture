package com.delta.smt.ui.product_tools.tools_info.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.product_tools.tools_info.ProduceToolsInfoActivity;

import dagger.Component;

/**
 * Created by Shaoqiang.Zhang on 2017/1/10.
 */

@ActivityScope
@Component(modules = ProduceToolsInfoModule.class, dependencies = AppComponent.class)
public interface ProduceToolsInfoCompoent {

    void Inject(ProduceToolsInfoActivity activity);

}
