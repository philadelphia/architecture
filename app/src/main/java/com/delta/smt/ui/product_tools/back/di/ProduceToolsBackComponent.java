package com.delta.smt.ui.product_tools.back.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.product_tools.back.ProduceToolsBackActivity;
import dagger.Component;

/**
 * Created by Shaoqiang.Zhang on 2017/1/11.
 */

@ActivityScope
@Component(modules = ProduceToolsBackModule.class, dependencies = AppComponent.class)
public interface ProduceToolsBackComponent {

    void Inject(ProduceToolsBackActivity activity);

}
