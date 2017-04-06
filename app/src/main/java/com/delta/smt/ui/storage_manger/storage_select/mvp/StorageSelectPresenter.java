package com.delta.smt.ui.storage_manger.storage_select.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandler;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandlerSubscriber;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.StoreEntity;

import javax.inject.Inject;


public class StorageSelectPresenter extends BasePresenter<StorageSelectContract.Model, StorageSelectContract.View> {

    private RxErrorHandler rxErrorHandler;
    @Inject
    public StorageSelectPresenter(StorageSelectContract.Model model, StorageSelectContract.View mView, RxErrorHandler rxErrorHandler) {
        super(model, mView);
        this.rxErrorHandler =rxErrorHandler;

    }

    public void getStorageSelect() {

        getModel().getStorageSelect().subscribe(new RxErrorHandlerSubscriber<Result<StoreEntity>>(rxErrorHandler) {
            @Override
            public void onStart() {
                super.onStart();
                getView().showLoadingView();
            }

            @Override
            public void onNext(Result<StoreEntity> storeEntityResult) {
                if ("0".equals(storeEntityResult.getCode())) {
                    if (storeEntityResult.getRows().size() == 0) {
                        getView().showEmptyView();
                    } else {
                        getView().showContentView();
                    }
                    getView().onSucess(storeEntityResult.getRows());
                } else {
                    getView().onFailed(storeEntityResult.getMessage());
                    getView().showContentView();
                }
            }
        });
//        getModel().getStorageSelect().doOnSubscribe(new Action0() {
//            @Override
//            public void call() {
//                getView().showLoadingView();
//            }
//        }).subscribe(new Action1<Result<StoreEntity>>() {
//            @Override
//            public void call(Result<StoreEntity> storageSelect) {
//                if ("0".equals(storageSelect.getCode())) {
//                    if (storageSelect.getRows().size() == 0) {
//                        getView().showEmptyView();
//                    } else {
//                        getView().showContentView();
//                    }
//                    getView().onSucess(storageSelect.getRows());
//                } else {
//                    getView().onFailed(storageSelect.getMessage());
//                    getView().showContentView();
//                }
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//                try {
//                    getView().onFailed(throwable.getMessage());
//                    getView().showErrorView();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

}
