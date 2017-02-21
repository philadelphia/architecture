package com.delta.smt.ui.storage_manger.storage_select.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.StoreEntity;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;


public class StorageSelectPresenter extends BasePresenter<StorageSelectContract.Model, StorageSelectContract.View> {
    @Inject
    public StorageSelectPresenter(StorageSelectContract.Model model, StorageSelectContract.View mView) {
        super(model, mView);

    }

    public void getStorageSelect() {
        getModel().getStorageSelect().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Result<StoreEntity>>() {
            @Override
            public void call(Result<StoreEntity> storageSelect) {
                if ("0".equals(storageSelect.getCode())) {
                    if (storageSelect.getRows().size() == 0) {
                        getView().showEmptyView();
                    } else {
                        getView().showContentView();
                    }
                    getView().onSucess(storageSelect.getRows());
                } else {
                    getView().onFailed(storageSelect.getMessage());
                    getView().showContentView();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().onFailed(throwable.getMessage());
                    getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
