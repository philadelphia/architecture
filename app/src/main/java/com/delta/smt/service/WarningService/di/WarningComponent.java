package com.delta.smt.service.warningService.di;

import com.delta.smt.di.component.AppComponent;
import com.delta.smt.service.warningService.WarningService;

import dagger.Component;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/11 17:06
 */
@ServiceScope
@Component(modules = {WebSocketClientModule.class},dependencies = AppComponent.class)
public interface WarningComponent {

    void inject(WarningService warningService);
}
