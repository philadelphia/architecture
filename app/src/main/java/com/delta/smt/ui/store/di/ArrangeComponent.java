package com.delta.smt.ui.store.di;

import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.ui.store.ArrangeFragment;

import dagger.Component;

/**
 * Created by Lin.Hou on 2016-12-26.
 */
@FragmentScope
@Component(modules = ArrangeModule.class,dependencies = AppComponent.class)
public interface ArrangeComponent {
    void inject(ArrangeFragment fragment);
}
