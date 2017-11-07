package com.example.app.ui.main.di;



import com.example.app.MainActivity;
import com.example.app.di.component.AppComponent;
import com.example.commonlibs.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by Shufeng.Wu on 2016/12/26.
 */

@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
