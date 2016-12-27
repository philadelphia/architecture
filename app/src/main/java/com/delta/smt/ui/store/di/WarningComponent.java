package com.delta.smt.ui.store.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.store.WarringFragment;

import dagger.Component;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

@FragmentScope
@Component(modules = WarningModule.class,dependencies = AppComponent.class)
public interface WarningComponent {
    void inject(WarringFragment fagment);
}
