package com.delta.smt.ui.smt_module.module_down_details.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandler;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandlerSubscriber;
import com.delta.smt.entity.ModuleDownDebit;
import com.delta.smt.entity.ModuleDownDetailsItem;
import com.delta.smt.entity.Result;

import javax.inject.Inject;

import retrofit2.http.Query;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Shufeng.Wu on 2017/1/5.
 */

public class ModuleDownDetailsPresenter extends BasePresenter<ModuleDownDetailsContract.Model,ModuleDownDetailsContract.View> {

    private RxErrorHandler rxErrorHandler;

    @Inject
    public ModuleDownDetailsPresenter(ModuleDownDetailsContract.Model model, ModuleDownDetailsContract.View mView, RxErrorHandler rxErrorHandler) {
        super(model, mView);
        this.rxErrorHandler = rxErrorHandler;
    }

    public void getAllModuleDownDetailsItems(String str){
        getModel().getAllModuleDownDetailsItems(str).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                try{
                    getView().showLoadingView();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).subscribe(new Action1<Result<ModuleDownDetailsItem>>() {
            @Override
            public void call(Result<ModuleDownDetailsItem> moduleDownDetailsItemResult) {
                try{
                    if ( 0 == moduleDownDetailsItemResult.getCode()) {
                        if (moduleDownDetailsItemResult.getRows().size() == 0) {
                            getView().showEmptyView();
                            getView().onResult(moduleDownDetailsItemResult.getMessage());
                        }else {
                            getView().showContentView();
                            getView().onResult(moduleDownDetailsItemResult.getMessage());
                            getView().onSuccess(moduleDownDetailsItemResult.getRows());
                        }
                    } else {
                        getView().onFailed(moduleDownDetailsItemResult.getMessage());
                        getView().showErrorView();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try{
                    getView().onNetFailed(throwable);
                    getView().showErrorView();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void getAllModuleDownMaintainResult(String str){
        getModel().getModuleDownMaintainResult(str).subscribe(new RxErrorHandlerSubscriber<Result>(rxErrorHandler) {
            @Override
            public void onNext(Result result) {
                if (result.getCode() == 0) {
                        getView().onMaintainResult(result.getMessage());
                }else {
                    getView().onFailed(result.getMessage());
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                getView().showLoadingView();
            }});
    }

//    public  void getDownModuleList(String condition){
//        getModel().getDownModuleList(condition).subscribe(new Action1<ModuleDownDetailsItem>() {
//            @Override
//            public void call(ModuleDownDetailsItem moduleDownDetailsItem) {
//                getView().onSuccess(moduleDownDetailsItem);
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//
//            }
//        });
//    }
//
    public void getFeederCheckInTime(String condition){
        getModel().getFeederCheckInTime(condition).subscribe(new Action1<Result<ModuleDownDetailsItem>>() {
            @Override
            public void call(Result<ModuleDownDetailsItem> moduleDownDetailsItem) {
                    if (moduleDownDetailsItem.getCode() == 0){
                        getView().onSuccess(moduleDownDetailsItem.getRows());
                    }else {
                        getView().onFailed(moduleDownDetailsItem.getMessage());
                    }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onNetFailed(throwable);
            }
        });
    }

    public void getModuleListUnDebitList(@Query("condition") String condition){
            getModel().getModuleListUnDebitList(condition).subscribe(new Action1<Result<ModuleDownDebit>>() {
                @Override
                public void call(Result<ModuleDownDebit> moduleDownDebitResult) {
                    if (0 == moduleDownDebitResult.getCode()){
                        getView().showModuleDownUnDebitedItemList(moduleDownDebitResult.getRows());
                    }else {
                        getView().onFailed(moduleDownDebitResult.getMessage());
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    getView().onFailed(throwable.getMessage());
                }
            });
    }

    public void debitManually(String value){
        getModel().debitManually(value).subscribe(new Action1<Result<ModuleDownDebit>>() {
            @Override
            public void call(Result<ModuleDownDebit> moduleDownDebitResult) {
                if (0 == moduleDownDebitResult.getCode()) {
                    getView().showModuleDownUnDebitedItemList(moduleDownDebitResult.getRows());
                } else {
                    getView().onFailed(moduleDownDebitResult.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.getMessage();
            }
        });
    }

    public void lightOff(String argument) {
        getModel().lightOff(argument).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if (0 == result.getCode()){

                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });

    }
}
