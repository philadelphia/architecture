package com.delta.smt.ui.smt_module.module_up_binding.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandler;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandlerSubscriber;
import com.delta.smt.entity.BaseEntity;
import com.delta.smt.entity.ModuleUpBindingItem;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.UpLoadEntity;
import com.delta.smt.ui.smt_module.JumpOver.JumpOverModel;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class ModuleUpBindingPresenter extends BasePresenter<ModuleUpBindingContract.Model, ModuleUpBindingContract.View> {
    private RxErrorHandler mRxErrorHandler;
    private JumpOverModel mJumpOverModule;


    @Inject
    public ModuleUpBindingPresenter(ModuleUpBindingContract.Model model, ModuleUpBindingContract.View mView, RxErrorHandler mRxErrorHandler, JumpOverModel mJumpOverModule) {
        super(model, mView);
        this.mRxErrorHandler = mRxErrorHandler;
        this.mJumpOverModule =mJumpOverModule;
    }

    public void getAllModuleUpBindingItems(String str) {
        getModel().getAllModuleUpBindingItems(str).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                try {
                    getView().showLoadingView();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).subscribe(new Action1<Result<ModuleUpBindingItem>>() {
            @Override
            public void call(Result<ModuleUpBindingItem> moduleUpBindingItemResult) {
                try {
                    if (0 == moduleUpBindingItemResult.getCode()) {

                        if (moduleUpBindingItemResult.getRows().size() == 0) {
                            getView().showEmptyView();
//                            getView().onFailed(moduleUpBindingItems);
                        } else {
                            getView().showContentView();
                            getView().onSuccess(moduleUpBindingItemResult.getRows());
                        }

                    } else {
                        getView().onFailed(moduleUpBindingItemResult.getMessage());
                        getView().showErrorView();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().onNetFailed(throwable);
                    getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void upLoadToMESManually(String value){
        getModel().upLoadToMesManually(value).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if (0 == result.getCode()){
                    getView().uploadSuccess(result.getMessage());
                }else{
                    getView().upLoadFailed(result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed(throwable.getMessage());
            }
        });

    }

    public void getMaterialAndFeederBindingResult(String str) {
        getModel().getMaterialAndFeederBindingResult(str).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                try {
                    getView().showLoadingView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).subscribe(new Action1<Result<ModuleUpBindingItem>>() {
            @Override
            public void call(Result<ModuleUpBindingItem> moduleUpBindingItemResult) {
                try {
                    if (0 == moduleUpBindingItemResult.getCode()) {

                        if (moduleUpBindingItemResult.getRows().size() == 0) {
                            getView().showEmptyView();
//                            getView().onFailedBinding(moduleUpBindingItem.getMessage());
                        } else {
                            getView().showContentView();
                            getView().onSuccess(moduleUpBindingItemResult.getRows());
                        }

                    } else {
                        getView().showContentView();
                        getView().onFailed(moduleUpBindingItemResult.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().onNetFailed(throwable);
                    getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void getneeduploadtomesmaterials(String mArgument) {

        getModel().getneeduploadtomesmaterials(mArgument).subscribe(new RxErrorHandlerSubscriber<BaseEntity<UpLoadEntity>>(mRxErrorHandler) {
            @Override
            public void onNext(BaseEntity<UpLoadEntity> mUpLoadEntityResult) {

                if ("0".equals(mUpLoadEntityResult.getCode())) {

                    getView().getNeedUpLoadToMESMaterialsSuccess(mUpLoadEntityResult.getT());
                } else {
                    getView().getNeedUpLoadTOMESMaterislsFailed(mUpLoadEntityResult.getMsg());
                }
            }
        });

    }

    public void jumpOver(String mGsonListString) {
        mJumpOverModule.jumpOver(mGsonListString).subscribe(new RxErrorHandlerSubscriber<Result>(mRxErrorHandler) {
            @Override
            public void onNext(Result mResult) {

                getView().showMessage(mResult.getMessage());
            }
        });
    }
}
