package com.delta.smt.ui.feeder.handle.feederSupply.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandler;
import com.delta.commonlibs.rx.rxerrorhandler.RxErrorHandlerSubscriber;
import com.delta.smt.entity.BaseEntity;
import com.delta.smt.entity.DebitData;
import com.delta.smt.entity.FeederSupplyItem;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.UpLoadEntity;

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
    public void getFeederList(String workID) {
        getModel().getFeederList(workID)
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
                                getView().onGetFeederListFailed(feederSupplyItemResult.getMessage());
                            } else {
                                getView().showContentView();
                                getView().onGetFeederListSuccess(feederSupplyItemResult.getRows());
                            }

                        } else {
                            getView().onGetFeederListFailed(feederSupplyItemResult.getMessage());
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
                    getView().onFeederSupplySuccess(feederSupplyItemResult.getRows());
                } else {
                    getView().onGetFeederListFailed(feederSupplyItemResult.getMessage());
                    getView().showContentView();

                }

            }
        });
    }

    //Feeder扣账、入库完成
    public void resetFeederSupplyStatus(String condition) {
        getModel().resetFeederSupplyStatus(condition).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if (0 == result.getCode()) {
                    getView().onAllSupplyComplete();
                } else {
                    getView().onGetFeederListFailed(result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onGetFeederListFailed(throwable.toString());
            }
        });


    }

    public void jumpMES(String value){
        getModel().jumpMES(value).subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if (0 == result.getCode()) {
                    getView().onGetFeederListFailed(result.getMessage());
                } else {
                    getView().onGetFeederListFailed(result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onGetFeederListFailed(throwable.getMessage());
            }
        });
    }



    //扣账
    public void deductionManually(String value) {
        getModel().deductionAutomatically(value).subscribe(new Action1<Result<DebitData>>() {
            @Override
            public void call(Result<DebitData> debitDataResult) {
                if (0 == debitDataResult.getCode()) {
                    getView().showUnDebitedItemList(debitDataResult.getRows());
                } else {
                    getView().onGetFeederListFailed(debitDataResult.getMessage());
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onGetFeederListFailed(throwable.getMessage());
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
                        getView().onGetFeederListFailed("没有扣账列表");
                    } else {
                        getView().showUnDebitedItemList(debitDataResult.getRows());
                    }
                } else {
                    getView().onGetFeederListFailed(debitDataResult.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onGetFeederListFailed(throwable.getMessage());
            }
        });
    }


    //上传Feeder发料到MES
    public void upLoadFeederSupplyToMES(String value) {
        getModel().upLoadFeederSupplyToMES(value).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().upLoading();
            }
        }).subscribe(new Action1<Result>() {
            @Override
            public void call(Result resultFeeder) {

                if (resultFeeder.getCode() == 0){
                    getView().upLoadFailed("Success");
                }
                getView().upLoadFailed("Failed"); //hardCode
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().onGetFeederListFailed(throwable.getMessage());
            }
        });
    }

    //获取没有上传到MES的数据列表
    public void getUnUpLoadToMESList(String condition) {
        getModel().getUnUpLoadToMESList(condition).subscribe(new Action1<BaseEntity<UpLoadEntity>>() {
            @Override
            public void call(BaseEntity<UpLoadEntity> result) {
                if ("0".equalsIgnoreCase(result.getCode())) {
                    getView().showUnUpLoadToMESItemList(result.getT());
                } else {
                    getView().onUpLoadFailed(result.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

   public void lightOff(String argument){
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
