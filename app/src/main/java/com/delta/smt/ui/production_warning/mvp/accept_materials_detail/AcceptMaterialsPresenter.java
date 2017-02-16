package com.delta.smt.ui.production_warning.mvp.accept_materials_detail;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.production_warning.item.ItemAcceptMaterialDetail;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Fuxiang.Zhang on 2017/2/13.
 */
@ActivityScope
public class AcceptMaterialsPresenter extends BasePresenter<AcceptMaterialsContract.Model,AcceptMaterialsContract.View>{

    @Inject
    public AcceptMaterialsPresenter(AcceptMaterialsContract.Model model, AcceptMaterialsContract.View mView) {
        super(model, mView);
    }

    //请求item数据
    public void getItemDatas(){
        getModel().getItemDatas().subscribe(new Action1<Result<ItemAcceptMaterialDetail>>() {
            @Override
            public void call(Result<ItemAcceptMaterialDetail> itemAcceptMaterialDetailResult) {
                if (itemAcceptMaterialDetailResult.getCode().equals("0")) {
                    getView().getItemDatas(itemAcceptMaterialDetailResult.getRows());
                }else {
                    getView().getItemDatasFailed(itemAcceptMaterialDetailResult.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getItemDatasFailed(throwable.getMessage());
            }
        });
    }


    //提交扫码成功数据
    public void commitBarode(){
        getModel().commitBarode().subscribe(new Action1<Result>() {
            @Override
            public void call(Result result) {
                if (result.getCode().equals("0")) {
                    getView().commitBarcodeSucess();
                }else{
                    getView().getItemDatasFailed(result.getMessage());
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getItemDatasFailed(throwable.getMessage());
            }
        });
    }

}
