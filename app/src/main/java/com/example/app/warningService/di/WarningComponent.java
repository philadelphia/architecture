package com.example.app.warningService.di;

import com.example.app.di.component.AppComponent;
import com.example.app.warningService.WarningService;

import dagger.Component;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/11 17:06
 */
@ServiceScope
@Component(modules = {WarningSocketPresenterModule.class},dependencies = AppComponent.class)
public interface WarningComponent {

    void inject(WarningService warningService);
}
