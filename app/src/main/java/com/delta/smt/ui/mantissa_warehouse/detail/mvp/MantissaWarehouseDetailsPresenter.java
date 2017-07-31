package com.delta.smt.ui.mantissa_warehouse.detail.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandler;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandlerSubscriber;
import com.delta.smt.entity.DebitData;
import com.delta.smt.entity.IssureToWarehFinishResult;
import com.delta.smt.entity.MantissaWarehouseDetailsResult;
import com.delta.smt.entity.MaterialCar;
import com.delta.smt.entity.Result;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Zhenyu.Liu on 2016/12/29.
 */

@ActivityScope
public class MantissaWarehouseDetailsPresenter extends BasePresenter<MantissaWarehouseDetailsContract.Model, MantissaWarehouseDetailsContract.View> {

    private RxErrorHandler mRxErrorHandler;

    @Inject
    public MantissaWarehouseDetailsPresenter(MantissaWarehouseDetailsContract.Model model, MantissaWarehouseDetailsContract.View mView, RxErrorHandler mRxRerrorHander) {

        super(model, mView);
        this.mRxErrorHandler = mRxRerrorHander;

    }

    public void getMantissaWarehouseDetails(String str) {

        getModel().getMantissaWarehouseDetails(str).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<MantissaWarehouseDetailsResult>() {
            @Override
            public void call(MantissaWarehouseDetailsResult mantissaWarehouseDetails) {

                if ("0".equals(mantissaWarehouseDetails.getCode())) {
                    if (mantissaWarehouseDetails.getRows().size() == 0) {
                        getView().showEmptyView();
                    } else {
                        getView().showContentView();
                    }
                    getView().getMantissaWarehouseDetailsSucess(mantissaWarehouseDetails);
                } else {
                    getView().showContentView();
                    getView().getMantissaWarehouseDetailsFailed(mantissaWarehouseDetails.getMsg());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().showErrorView();
                    getView().getFailed(throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void getFindCar(String str) {


        getModel().getFindCar(str).subscribe(new Action1<Result<MaterialCar>>() {
            @Override
            public void call(Result<MaterialCar> car) {

                if (0 == car.getCode()) {
                    getView().getFindCarSucess(car);
                } else {
                    getView().getFindCarFailed(car.getMessage());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                try {
                    getView().getFailed(throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public void getbingingCar(String str) {

        getModel().getBingingCar(str).subscribe(new Action1<Result<MaterialCar>>() {
            @Override
            public void call(Result<MaterialCar> car) {

                if (0 == car.getCode()) {
                    getView().getBingingCarSuccess(car);
                } else {
                    getView().getBingingCarFailed(car.getMessage());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                try {
                    getView().getFailed(throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }


    public void getMantissaWarehouseput(String str) {

        getModel().getMantissaWarehouseput(str).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<MantissaWarehouseDetailsResult>() {
            @Override
            public void call(MantissaWarehouseDetailsResult mantissaWarehouseDetails) {

                if ("0".equals(mantissaWarehouseDetails.getCode())) {
                    getView().getMantissaWarehouseputSuccess(mantissaWarehouseDetails);
                    if (mantissaWarehouseDetails.getRows().size() == 0) {
                        getView().showEmptyView();
                    } else {
                        getView().showContentView();
                    }
                } else {
                    getView().showContentView();
                    getView().getMantissaWarehouseputFailed(mantissaWarehouseDetails.getMsg());

                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                try {
                    getView().showErrorView();
                    getView().getFailed(throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void getMantissaWareOver(String s) {

        getModel().getMantissaWareOver(s).subscribe(new Action1<IssureToWarehFinishResult>() {
            @Override
            public void call(IssureToWarehFinishResult issureToWarehFinishResult) {
                if ("0".equals(issureToWarehFinishResult.getCode())) {
                    getView().getMantissaWareOverSuccess(issureToWarehFinishResult);
                } else {
                    getView().getMantissaWareOverFailed(issureToWarehFinishResult.getMsg());
                }
            }


        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getFailed(throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }


    public void deduction(String mS) {
        getModel().deduction(mS).subscribe(new RxErrorHandlerSubscriber<Result<DebitData>>(mRxErrorHandler) {
            @Override
            public void onNext(Result<DebitData> mDebitDataResult) {
                if (0 == mDebitDataResult.getCode()) {
                    getView().deductionSuccess(mDebitDataResult.getRows());
                } else {
                    getView().deductionFailed(mDebitDataResult.getMessage());
                }
            }
        });
    }
    public void getDebitDataList(String mS) {

        getModel().getDebitDataList(mS).subscribe(new RxErrorHandlerSubscriber<Result<DebitData>>(mRxErrorHandler) {
            @Override
            public void onNext(Result<DebitData> mDebitDataResult) {

                if (mDebitDataResult.getCode() == 0) {
                    getView().getDebitDataSuccess(mDebitDataResult.getRows());
                } else {
                    getView().getDebitDataFailed(mDebitDataResult.getMessage());
                }
            }
        });
    }

    public void changecarshelf(String mGsonListString) {

        getModel().changecarshelf(mGsonListString).subscribe(new RxErrorHandlerSubscriber<Result>(mRxErrorHandler) {
            @Override
            public void onNext(Result mResult) {

                if (0==mResult.getCode()){
                    getView().changecarshelfSuccess(mResult.getMessage());
                }else {
                    getView().changecarshelfFailed(mResult.getMessage());
                }
            }
        });
    }

    public void offcarshelflight(String mS) {

        getModel().offcarshelflight(mS).subscribe(new RxErrorHandlerSubscriber<Result>(mRxErrorHandler) {
            @Override
            public void onNext(Result mResult) {
                if (0==mResult.getCode()){
                    getView().changecarshelfSuccess(mResult.getMessage());
                }else {
                    getView().changecarshelfFailed(mResult.getMessage());
                }

            }
        });
    }
}
