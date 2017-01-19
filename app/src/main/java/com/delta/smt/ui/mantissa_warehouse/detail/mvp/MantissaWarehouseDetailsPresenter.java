package com.delta.smt.ui.mantissa_warehouse.detail.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.MantissaCar;
import com.delta.smt.entity.MantissaWarehouseDetailsResult;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

@ActivityScope
public class MantissaWarehouseDetailsPresenter extends BasePresenter<MantissaWarehouseDetailsContract.Model,MantissaWarehouseDetailsContract.View> {

    @Inject
    public MantissaWarehouseDetailsPresenter(MantissaWarehouseDetailsContract.Model model, MantissaWarehouseDetailsContract.View mView) {
        super(model, mView);
    }

    public void getMantissaWarehouseDetails(String str){

        getModel().getMantissaWarehouseDetails(str).subscribe(new Action1<MantissaWarehouseDetailsResult>() {
            @Override
            public void call(MantissaWarehouseDetailsResult mantissaWarehouseDetailses) {

                if("Success".equals(mantissaWarehouseDetailses.getMsg())){
                    getView().getSucess(mantissaWarehouseDetailses.getRows());
                }else{
                    getView().getFailed(mantissaWarehouseDetailses.getMsg());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getFailed(throwable.getMessage());

            }
        });

    }


    public void getFindCar(String str){


        getModel().getFindCar(str).subscribe(new Action1<MantissaCar>() {
            @Override
            public void call(MantissaCar car) {

                if("Success".equals(car.getMsg())){
                    getView().getFindCarSucess(car);
                }else{
                    getView().getFindCarFailed(car.getMsg());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getFindCarFailed(throwable.getMessage());

            }
        });


    }

    public void getbingingCar(String str){

        getModel().getBingingCar(str).subscribe(new Action1<MantissaCar>() {
            @Override
            public void call(MantissaCar car) {

                if("Success".equals(car.getMsg())){
                    getView().getBingingCarSucess(car);
                }else{
                    getView().getBingingCarFailed(car.getMsg());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getBingingCarFailed(throwable.getMessage());

            }
        });

    }


    public void getMantissaWarehouseput(String str){

        getModel().getMantissaWarehouseput(str).subscribe(new Action1<MantissaWarehouseDetailsResult>() {
            @Override
            public void call(MantissaWarehouseDetailsResult mantissaWarehouseDetailses) {

                if("Success".equals(mantissaWarehouseDetailses.getMsg())){
                    getView().getMantissaWarehouseputSucess(mantissaWarehouseDetailses.getRows());
                }else{
                    getView().getMantissaWarehouseputFailed(mantissaWarehouseDetailses.getMsg());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getMantissaWarehouseputFailed(throwable.getMessage());

            }
        });

    }


}
