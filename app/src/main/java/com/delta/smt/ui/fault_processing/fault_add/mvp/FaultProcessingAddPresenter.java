package com.delta.smt.ui.fault_processing.fault_add.mvp;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.BaseEntity;
import com.delta.smt.entity.ResultFault;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:38
 */

@ActivityScope
public class FaultProcessingAddPresenter extends BasePresenter<FaultProcessingAddContract.Model, FaultProcessingAddContract.View> {


    @Inject
    public FaultProcessingAddPresenter(FaultProcessingAddContract.Model model, FaultProcessingAddContract.View mView) {
        super(model, mView);
    }

    public void addSolution(String content) {

        getModel().addSolution(content).subscribe(new Action1<BaseEntity>() {
            @Override
            public void call(BaseEntity falutMesages) {

                if ("0".equals(falutMesages.getCode())) {
                    getView().onSuccess(falutMesages.getMsg());
                } else {

                    getView().onFailed(falutMesages.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed(throwable.getMessage());
            }
        });
    }


    public void getTemplateContent(String fileName) {
        Log.i(TAG, "getTemplateContent: ");
        getModel().getTemplateContent(fileName).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<BaseEntity<String>>() {
            @Override
            public void call(BaseEntity<String> stringResult) {
                if (stringResult.getCode().equalsIgnoreCase("0")) {
                    getView().showContentView();
                    getView().onSuccess(stringResult.getT());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                Log.e(TAG, "call: " + throwable.getMessage());
                getView().showErrorView();
                getView().onFailed(throwable.getMessage());
            }
        });

    }

    public void upLoadFile(RequestBody requestBody, MultipartBody.Part part, String argument) {
        getModel().upLoadFile(requestBody, part, argument).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingDialog();
            }
        }).subscribe(new Action1<ResultFault>() {
            @Override
            public void call(ResultFault result) {
                if (result.getCode().equalsIgnoreCase("0")) {
                    getView().upLoadFileSuccess();
                    getView().showLoadingDialogSuccess();
                } else {
                    getView().onFailed("上传失败");
                    getView().showLoadingDialogFailed();
                }


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().showMessage("上传失败" + throwable.getMessage());
                getView().showLoadingDialogFailed();
            }
        });
    }
}
