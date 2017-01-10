package com.delta.smt.ui.storage_manger.storage_select.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.StorageSelect;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;



public class StorageSelectPresenter extends BasePresenter<StorageSelectContract.Model, StorageSelectContract.View> {
    @Inject
    public StorageSelectPresenter(StorageSelectContract.Model model, StorageSelectContract.View mView) {
        super(model, mView);

    }

    public void getStorageSelect(){
        getModel().getStorageSelect().subscribe(new Action1<List<StorageSelect>>() {
            @Override
            public void call(List<StorageSelect> storageSelect) {
                getView().onSucess(storageSelect);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }
}
