package com.delta.smt.ui.mantissa_warehouse.return_putstorage.put_storage.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.FragmentScope;
import com.delta.smt.entity.MantissaWarehousePutstorageResult;

import javax.inject.Inject;

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

        getModel().getMantissaWarehousePutstorage().subscribe(new Action1<MantissaWarehousePutstorageResult>() {
            @Override
            public void call(MantissaWarehousePutstorageResult mantissaWarehousePutstorageResult) {

                if("Success".equals(mantissaWarehousePutstorageResult.getMsg())){
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

                if("Success".equals(mantissaWarehousePutstorageResult.getMsg())){

                    if(mantissaWarehousePutstorageResult.getrows().size() == 0){
                        getView().showEmptyView();
                    }else {
                        getView().getSucessUpdate(mantissaWarehousePutstorageResult.getrows());
                        getView().showContentView();
                    }

                }else{
                    getView().getFailedUpdate(mantissaWarehousePutstorageResult.getrows().get(0));
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

    public void getBeginPut(){

        getModel().getbeginput().subscribe(new Action1<MantissaWarehousePutstorageResult>() {
            @Override
            public void call(MantissaWarehousePutstorageResult mantissaWarehousePutstorageResult) {

                if("Success".equals(mantissaWarehousePutstorageResult.getMsg())){
                    getView().getBeginSucess(mantissaWarehousePutstorageResult.getrows());
                }else{
                    getView().getBeginFailed(mantissaWarehousePutstorageResult.getMsg());
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

                if("Success".equals(mantissaWarehousePutstorageResult.getMsg())){
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

    public void getBindingLabel(String str){

        getModel().getBingingLable(str).subscribe(new Action1<MantissaWarehousePutstorageResult>() {
            @Override
            public void call(MantissaWarehousePutstorageResult mantissaWarehousePutstorageResult) {

                if("Success".equals(mantissaWarehousePutstorageResult.getMsg())){
                    getView().getBingingLableSucess(mantissaWarehousePutstorageResult.getrows());
                }else{
                    getView().getBingingLableFailed(mantissaWarehousePutstorageResult.getMsg());
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

        getModel().getUpLocation(str).subscribe(new Action1<MantissaWarehousePutstorageResult>() {
            @Override
            public void call(MantissaWarehousePutstorageResult mantissaWarehousePutstorageResult) {

                if("Success".equals(mantissaWarehousePutstorageResult.getMsg())){
                    getView().getUpLocationSucess(mantissaWarehousePutstorageResult.getrows());
                }else{
                    getView().getUpLocationFailed(mantissaWarehousePutstorageResult.getMsg());
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

                if("Success".equals(mantissaWarehousePutstorageResult.getMsg())){
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


}
