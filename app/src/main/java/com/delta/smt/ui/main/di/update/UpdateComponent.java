package com.delta.smt.ui.main.di.update;


import com.delta.smt.ui.main.update.DownloadService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 更新
 * Created by Shufeng.Wu on 2016/12/28.
 */
@Singleton
@Component(modules = {UpdateClientModule.class,UpdateServiceModule.class})
public interface UpdateComponent {
    void inject(DownloadService downloadService);
}
