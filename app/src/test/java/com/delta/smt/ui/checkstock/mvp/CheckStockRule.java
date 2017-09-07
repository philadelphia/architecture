package com.delta.smt.ui.checkstock.mvp;

import com.delta.smt.di.component.AppComponent;

import it.cosenonjaviste.daggermock.DaggerMockRule;

/**
 * Created by Lin.Hou on 2017/9/4.
 */

public class CheckStockRule extends DaggerMockRule<AppComponent> {

    public CheckStockRule(Class<AppComponent> componentClass, Object... modules) {
        super(componentClass, modules);
    }
}
