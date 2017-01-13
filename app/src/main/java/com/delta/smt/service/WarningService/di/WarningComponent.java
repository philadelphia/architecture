package com.delta.smt.service.warningService.di;

import com.delta.smt.service.warningService.WarningService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/11 17:06
 */
@Singleton
@Component(modules = {WebSocketClientModule.class})
public interface WarningComponent {

    void inject(WarningService warningService);
}
