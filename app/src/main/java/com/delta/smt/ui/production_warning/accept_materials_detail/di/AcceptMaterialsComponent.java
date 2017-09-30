package com.delta.smt.ui.production_warning.accept_materials_detail.di;

import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.production_warning.accept_materials_detail.AcceptMaterialsActivity;

import dagger.Component;

/**
 * Created by Fuxiang.Zhang on 2017/2/13.
 */
@ActivityScope
@Component(modules = AcceptMaterialsModule.class,dependencies = AppComponent.class)
public interface AcceptMaterialsComponent {

    void inject(AcceptMaterialsActivity acceptMaterialsActivity);
}
