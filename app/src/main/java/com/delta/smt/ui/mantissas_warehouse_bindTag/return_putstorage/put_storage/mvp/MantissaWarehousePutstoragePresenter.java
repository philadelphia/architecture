package com.delta.smt.ui.mantissas_warehouse_bindTag.return_putstorage.put_storage.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.MantissaWarehousePutstorageBindTagResult;
import com.delta.smt.entity.MantissaWarehousePutstorageResult;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Zhenyu.Liu on 2016/12/30.
 */

@FragmentScope
public class MantissaWarehousePutstoragePresenter extends BasePresenter<MantissaWarehousePutstorageContract.Model,MantissaWarehousePutstorageContract.View> {

    @Inject
    public MantissaWarehousePutstoragePresenter(MantissaWarehousePutstorageContract.Model model, MantissaWarehousePutstorageContract.View mView) {
        super(model, mView);
    }

    public void getMantissaWarehousePutstorage(){

        getModel().getMantissaWarehousePutstorage().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<MantissaWarehousePutstorageResult>() {
            @Override
            public void call(MantissaWarehousePutstorageResult mantissaWarehousePutstorageResult) {

                if("0".equals(mantissaWarehousePutstorageResult.getCode())){
                    if(mantissaWarehousePutstorageResult.getrows().size() == 0){
                        getView().showEmptyView();
                    }else {
                        getView().getSucess(mantissaWarehousePutstorageResult.getrows());
                        getView().showContentView();
                    }

                }else{
                    getView().getFailed(mantissaWarehousePutstorageResult.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getUpdate(){

        getModel().getMantissaWarehousePutstorageUpdate().subscribe(new Action1<MantissaWarehousePutstorageResult>() {
            @Override
            public void call(MantissaWarehousePutstorageResult mantissaWarehousePutstorageResult) {

                if("0".equals(mantissaWarehousePutstorageResult.getCode())){

                    if(mantissaWarehousePutstorageResult.getrows().size() == 0){
                       // getView().showEmptyView();

                    }else {
                        getView().getSucess(mantissaWarehousePutstorageResult.getrows());
                    }

                }else{
                    getView().getFailed(mantissaWarehousePutstorageResult.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getBeginPut(String str){

        getModel().getbeginput(str).subscribe(new Action1<MantissaWarehousePutstorageBindTagResult>() {
            @Override
            public void call(MantissaWarehousePutstorageBindTagResult mantissaWarehousePutstorageBindTagResult) {

                if("0".equals(mantissaWarehousePutstorageBindTagResult.getCode())){
                    getView().getBeginSucess(mantissaWarehousePutstorageBindTagResult.getrows());
                }else{
                    getView().getBeginFailed(mantissaWarehousePutstorageBindTagResult.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void getYesNext(){

        getModel().getYesNext().subscribe(new Action1<MantissaWarehousePutstorageResult>() {
            @Override
            public void call(MantissaWarehousePutstorageResult mantissaWarehousePutstorageResult) {

                if("0".equals(mantissaWarehousePutstorageResult.getCode())){
                    getView().getYesNextSucess(mantissaWarehousePutstorageResult.getrows());
                }else{
                    getView().getYesNextFailed(mantissaWarehousePutstorageResult.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void getYesok(){

        getModel().getYesok().subscribe(new Action1<MantissaWarehousePutstorageResult>() {
            @Override
            public void call(MantissaWarehousePutstorageResult mantissaWarehousePutstorageResult) {

                if("Success".equals(mantissaWarehousePutstorageResult.getMsg())){
                    getView().getYesokSucess();
                }else{
                    getView().getYesokFailed(mantissaWarehousePutstorageResult.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getonclickBeginButton(){

        getModel().onclickBeginButton().subscribe(new Action1<MantissaWarehousePutstorageResult>() {
            @Override
            public void call(MantissaWarehousePutstorageResult mantissaWarehousePutstorageResult) {

                if("0".equals(mantissaWarehousePutstorageResult.getCode())){
                    getView().getonclickBeginButtonSucess(mantissaWarehousePutstorageResult.getrows());
                }else{
                    getView().getonclickBeginButtonFailed(mantissaWarehousePutstorageResult.getMsg());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void bindMaterialCar(String str) {
        getModel().bindMaterialCar(str).subscribe(new Action1<MantissaWarehousePutstorageBindTagResult>() {
            @Override
            public void call(MantissaWarehousePutstorageBindTagResult mantissaWarehousePutstorageBindTagResult) {
                if("0".equals(mantissaWarehousePutstorageBindTagResult.getCode())){
                    getView().bindMaterialCarSucess(mantissaWarehousePutstorageBindTagResult.getrows());
                }else{
                    getView().bindMaterialCarFailed(mantissaWarehousePutstorageBindTagResult.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getUpLocation(String str){

        getModel().getUpLocation(str).subscribe(new Action1<MantissaWarehousePutstorageBindTagResult>() {
            @Override
            public void call(MantissaWarehousePutstorageBindTagResult mantissaWarehousePutstorageBindTagResult) {

                if("0".equals(mantissaWarehousePutstorageBindTagResult.getCode())){
                    getView().getUpLocationSucess(mantissaWarehousePutstorageBindTagResult.getrows());
                }else{
                    getView().getUpLocationFailed(mantissaWarehousePutstorageBindTagResult.getMsg());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void getBindingLabel(String str){

        getModel().getBingingLable(str).subscribe(new Action1<MantissaWarehousePutstorageBindTagResult>() {
            @Override
            public void call(MantissaWarehousePutstorageBindTagResult mantissaWarehousePutstorageBindTagResult) {

                if("0".equals(mantissaWarehousePutstorageBindTagResult.getCode())){
                    getView().getBingingLableSucess(mantissaWarehousePutstorageBindTagResult.getrows());
                }else{
                    getView().getBingingLableFailed(mantissaWarehousePutstorageBindTagResult.getMsg());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void onlickSubmit() {
        getModel().onlickSubmit().subscribe(new Action1<MantissaWarehousePutstorageBindTagResult>() {
            @Override
            public void call(MantissaWarehousePutstorageBindTagResult mantissaWarehousePutstorageBindTagResult) {

                if("0".equals(mantissaWarehousePutstorageBindTagResult.getCode())){
                    getView().onlickSubmitSucess(mantissaWarehousePutstorageBindTagResult);
                }else{
                    getView().onlickSubmitFailed(mantissaWarehousePutstorageBindTagResult.getMsg());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
