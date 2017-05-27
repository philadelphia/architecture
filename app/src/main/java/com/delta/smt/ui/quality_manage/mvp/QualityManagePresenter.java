package com.delta.smt.ui.quality_manage.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.QualityManage;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Zhenyu.Liu on 2017/4/18.
 */

public class QualityManagePresenter extends BasePresenter<QualityManageContract.Model, QualityManageContract.View> {

    @Inject
    public QualityManagePresenter(QualityManageContract.Model model, QualityManageContract.View mView) {
        super(model, mView);
    }

    public void getQualityList(String str){

        getModel().getQualityList(str).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<QualityManage>() {
            @Override
            public void call(QualityManage qualityManage) {

                    if ("0".equals(qualityManage.getCode())) {
                        if (qualityManage.getRows().size() == 0) {
                            getView().showEmptyView();
                        } else {
                            getView().showContentView();
                        }
                        getView().getQualityListSuccess(qualityManage.getRows());
                    } else {
                        getView().showContentView();
                        getView().getQualityListFailed(qualityManage.getMsg());
                    }

                }
                
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                {
                    try {
                        getView().showErrorView();
                        getView().getQualityListFailed(throwable.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void getYesok(String quality_id) {

        getModel().QualityOK(quality_id).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<QualityManage>() {
            @Override
            public void call(QualityManage qualityManage) {

                if ("0".equals(qualityManage.getCode())) {
                    if (qualityManage.getRows().size() == 0) {
                        getView().showEmptyView();
                    } else {
                        getView().showContentView();
                    }
                    getView().getokSuccess();
                } else {
                    getView().showContentView();
                    getView().getokFailed(qualityManage.getMsg());
                }

            }

        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                {
                    try {
                        getView().showErrorView();
                        getView().getokFailed(throwable.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
