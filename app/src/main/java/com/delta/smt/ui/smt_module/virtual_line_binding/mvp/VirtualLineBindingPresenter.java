package com.delta.smt.ui.smt_module.virtual_line_binding.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.VirtualLineItem;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public class VirtualLineBindingPresenter extends BasePresenter<VirtualLineBindingContract.Model, VirtualLineBindingContract.View> {
    @Inject
    public VirtualLineBindingPresenter(VirtualLineBindingContract.Model model, VirtualLineBindingContract.View mView) {
        super(model, mView);
    }

    public void getAllVirtualLineBindingItems(String str) {
        getModel().getAllVirtualLineBindingItems(str).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                /*try {
                    getView().showLoadingView();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        }).subscribe(new Action1<Result<VirtualLineItem>>() {
            @Override
            public void call(Result<VirtualLineItem>  virtualLineItemResult) {
                try {
                    if (virtualLineItemResult.getCode() == 0) {
                        if (virtualLineItemResult.getRows().size() == 0) {
                            getView().showEmptyView();
                        } else {
                            getView().showContentView();
                            getView().onSuccess(virtualLineItemResult.getRows());
                        }
                    } else {
                        getView().onFailed(virtualLineItemResult.getMessage());
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

    public void getAllVirtualBindingResult(String str) {
        getModel().getVirtualBinding(str).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                /*try {
                    getView().showLoadingView();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        }).subscribe(new Action1<Result<VirtualLineItem>>() {
            @Override
            public void call(Result<VirtualLineItem> virtualLineItemResult) {
                try {
                    if (virtualLineItemResult.getCode() == 0) {
                        if (virtualLineItemResult.getRows().size() == 0) {
                            getView().showEmptyView();
                        } else {
                            getView().showContentView();
                            getView().onSuccess(virtualLineItemResult.getRows());
                        }
                    } else if (virtualLineItemResult.getCode() == -1){
                        getView().onFailed(virtualLineItemResult.getMessage());
                        getView().showContentView();
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
                    //getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
