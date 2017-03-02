package com.delta.smt.ui.smt_module.virtual_line_binding.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.VirtualLineBindingItem;

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
        }).subscribe(new Action1<VirtualLineBindingItem>() {
            @Override
            public void call(VirtualLineBindingItem virtualLineBindingItems) {
                try {
                    if ("0".equals(virtualLineBindingItems.getCode())) {
                        if (virtualLineBindingItems.getRows().size() == 0) {
                            getView().showEmptyView();
                            getView().onFalied(virtualLineBindingItems);
                        } else {
                            getView().showContentView();
                            getView().onSuccess(virtualLineBindingItems);
                        }
                    } else {
                        getView().onFalied(virtualLineBindingItems);
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
        }).subscribe(new Action1<VirtualLineBindingItem>() {
            @Override
            public void call(VirtualLineBindingItem virtualLineBindingItem) {
                try {
                    if ("0".equals(virtualLineBindingItem.getCode())) {
                        if (virtualLineBindingItem.getRows().size() == 0) {
                            getView().showEmptyView();
                            getView().onFalied(virtualLineBindingItem);
                        } else {
                            getView().showContentView();
                            getView().onSuccess(virtualLineBindingItem);
                        }
                    } else {
                        getView().onFalied(virtualLineBindingItem);
                        //getView().showErrorView();
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
