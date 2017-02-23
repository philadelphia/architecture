package com.delta.smt.ui.storage_manger.details.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.Constant;
import com.delta.smt.entity.BindPrepCarIDByWorkOrderResult;
import com.delta.smt.entity.IssureToWarehFinishResult;
import com.delta.smt.entity.MaterialCar;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.StorageDetails;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Zhenyu.Liu on 2016/12/28.
 */
@ActivityScope
public class StorageDetailsPresenter extends BasePresenter<StorageDetailsContract.Model, StorageDetailsContract.View> {

    @Inject
    public StorageDetailsPresenter(StorageDetailsContract.Model model, StorageDetailsContract.View mView) {
        super(model, mView);
    }

    public void getStorageDetails(String content) {

        getModel().getStorageDetails(content).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Result<StorageDetails>>() {
            @Override
            public void call(Result<StorageDetails> storageDetailses) {

                if ("0".equals(storageDetailses.getCode())) {

                    if (storageDetailses.getRows().size() == 0) {
                        getView().showEmptyView();
                    } else {
                        getView().showContentView();
                        getView().getSucess(storageDetailses);
                    }

                } else {
                    getView().getFailed(storageDetailses.getMessage());
                    getView().showContentView();

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

    public void queryMaterailCar(String content) {
        getModel().queryMaterialCar(content).subscribe(new Action1<MaterialCar>() {
            @Override
            public void call(MaterialCar materialCar) {

                if ("0".equals(materialCar.getCode())) {
                    getView().queryMaterailCar(materialCar.getRows());
                } else {
                    getView().queryMaterailCarFailed(materialCar.getMsg());
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

    public void bindBoundPrepCar(String content) {

        getModel().bindMaterialCar(content).subscribe(new Action1<BindPrepCarIDByWorkOrderResult>() {
            @Override
            public void call(BindPrepCarIDByWorkOrderResult result) {

                if ("0".equalsIgnoreCase(result.getCode())) {
                    getView().bindMaterialCarSucess(result.getRows());
                } else {
                    getView().bindMaterialCarFailed(result.getMsg());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getFailed(throwable.getMessage());
            }
        });
    }

    public void issureToWareh(String content) {
        getModel().issureToWareh(content).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<Result<StorageDetails>>() {
            @Override
            public void call(Result<StorageDetails> issureToWarehResult) {

                if ("0".equalsIgnoreCase(issureToWarehResult.getCode())) {

                    if (issureToWarehResult.getRows().size() == 0) {
                        getView().showEmptyView();
                    } else {
                        getView().showContentView();
                    }
                    getView().issureToWarehSuccess(issureToWarehResult);
                } else {

                    getView().showContentView();
                    if (Constant.JUMP_MATERIALS_STRING.equals(issureToWarehResult.getMessage())) {
                        getView().issureToWarehFailedWithjumpMaterials(issureToWarehResult.getMessage());
                    } else {
                        getView().issureToWarehFailedWithoutJumpMaterials(issureToWarehResult.getMessage());
                    }
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

    public void issureToWarehFinish() {

        getModel().issureToWarehFinish().subscribe(new Action1<IssureToWarehFinishResult>() {
            @Override
            public void call(IssureToWarehFinishResult issureToWarehFinishResult) {

                if ("0".equalsIgnoreCase(issureToWarehFinishResult.getCode())) {
                    getView().issureToWarehFinishSuccess(issureToWarehFinishResult.getMsg());
                } else {
                    if (Constant.SURE_END_ISSUE_STRING.equalsIgnoreCase(issureToWarehFinishResult.getMsg())) {
                        getView().issureToWarehFinishFaildSure(issureToWarehFinishResult.getMsg());
                    } else {
                        getView().issureToWarehFinishFailedWithoutSure(issureToWarehFinishResult.getMsg());
                    }
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

    public void jumpMaterials() {
        getModel().jumpMaterials().subscribe(new Action1<Result<StorageDetails>>() {
            @Override
            public void call(Result<StorageDetails> storageDetailsResult) {
                if ("0".equalsIgnoreCase(storageDetailsResult.getCode())) {
                    getView().jumpMaterialsSuccess(storageDetailsResult);
                } else {
                    getView().jumpMaterialsFailed(storageDetailsResult.getMessage());
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


    public void deduction() {
        getModel().deduction().subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {

                if ("0".equals(result.getCode())) {
                    getView().deductionSuccess();
                } else {
                    getView().deductionFailed(result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                try {
                    getView().deductionFailed(throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sureCompleteIssue() {
        getModel().sureCompleteIssue().subscribe(new Action1<IssureToWarehFinishResult>() {
            @Override
            public void call(IssureToWarehFinishResult issureToWarehFinishResult) {

                if ("0".equalsIgnoreCase(issureToWarehFinishResult.getCode())) {
                    getView().sureCompleteIssueSucess(issureToWarehFinishResult.getMsg());
                } else {
                    getView().sureCompleteIssueFailed(issureToWarehFinishResult.getMsg());
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
}
