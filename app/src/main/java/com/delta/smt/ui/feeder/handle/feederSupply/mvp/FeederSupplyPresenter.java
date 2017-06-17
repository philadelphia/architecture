package com.delta.smt.ui.feeder.handle.feederSupply.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandler;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandlerSubscriber;
import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.entity.DebitData;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.ResultFeeder;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Author:   Tao.ZT.Zhang
 * Date:     2016/12/26.
 */

@ActivityScope
public class FeederSupplyPresenter extends BasePresenter<FeederSupplyContract.Model, FeederSupplyContract.View> {
    private RxErrorHandler rxErrorHandler;

    @Inject
    FeederSupplyPresenter(FeederSupplyContract.Model model, FeederSupplyContract.View mView, RxErrorHandler rxErrorHandler) {
        super(model, mView);
        this.rxErrorHandler = rxErrorHandler;
    }


    //    //获取对应工单的Feeder备料列表
    public void getAllToBeSuppliedFeeders(String workID) {
        getModel().getAllToBeSuppliedFeeders(workID)
                .subscribe(new RxErrorHandlerSubscriber<Result<FeederSupplyItem>>(rxErrorHandler) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        getView().showLoadingView();
                    }

                    @Override
                    public void onNext(Result<FeederSupplyItem> feederSupplyItemResult) {

                        if (feederSupplyItemResult.getCode() == 0) {
                            if (feederSupplyItemResult.getRows().size() == 0) {
                                getView().showEmptyView();
                                getView().onFailed(feederSupplyItemResult.getMessage());
                            } else {
                                getView().showContentView();
                                getView().onSuccess(feederSupplyItemResult.getRows());
                            }

                        } else {
                            getView().onFailed(feederSupplyItemResult.getMessage());
                            getView().showErrorView();
                        }

                    }
                });


    }


    //获取feeder上模组时间
    public void getFeederInsertionToSlotTimeStamp(String condition) {
        getModel().getFeederInsertionToSlotTimeStamp(condition).
                doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        try {
                            getView().showLoadingView();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).subscribe(new RxErrorHandlerSubscriber<Result<FeederSupplyItem>>(rxErrorHandler) {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onNext(Result<FeederSupplyItem> feederSupplyItemResult) {
                if (feederSupplyItemResult.getCode() == 0) {
                    getView().showContentView();
                    getView().onSuccess(feederSupplyItemResult.getRows());
                } else {
                    getView().onFailed(feederSupplyItemResult.getMessage());
//                    getView().showErrorView();

                }

            }
        });
    }

    public void resetFeederSupplyStatus(String condition) {
        getModel().resetFeederSupplyStatus(condition).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if (0 == result.getCode()) {
                    getView().onAllSupplyComplete();
                } else {
                    getView().onFailed(result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed(throwable.toString());
            }
        });


    }

    public void upLoadToMES() {
        getModel().upLoadFeederSupplyResult().subscribe(new Action1<ResultFeeder>() {
            @Override
            public void call(ResultFeeder resultFeeder) {
                if (!resultFeeder.isRows()) {
                    getView().onUpLoadFailed("上传失败，请手动上传到MES");
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    //自动扣账
    public void deductionAutomatically(String value) {
        getModel().deductionAutomatically(value).subscribe(new Action1<Result<DebitData>>() {
            @Override
            public void call(Result<DebitData> debitDataResult) {
                if (0 == debitDataResult.getCode()) {
                    getView().showUnDebitedItemList(debitDataResult.getRows());
                } else {
                    getView().onFailed(debitDataResult.getMessage());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed(throwable.getMessage());
            }
        });
    }


    //获取没有扣账的工单
    public void getUnDebitedItemList(String condition) {
        getModel().getUnDebitedItemList(condition).subscribe(new Action1<Result<DebitData>>() {
            @Override
            public void call(Result<DebitData> debitDataResult) {
                if (0 == debitDataResult.getCode()) {
                    if (debitDataResult.getRows().size() == 0) {
                        getView().onFailed("没有扣账列表");
                    } else {
                        getView().showUnDebitedItemList(debitDataResult.getRows());
                    }
                } else {
                    getView().onFailed(debitDataResult.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onFailed(throwable.getMessage());
            }
        });
    }
}
