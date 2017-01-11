package com.delta.smt.ui.product_tools.mtools_info.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.Product_mToolsInfo;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Shaoqiang.Zhang on 2017/1/11.
 */

public class Produce_mToolsPresenter extends BasePresenter<Produce_mToolsContract.Model,Produce_mToolsContract.View>{

    @Inject
    public Produce_mToolsPresenter(Produce_mToolsContract.Model model, Produce_mToolsContract.View mView) {
        super(model, mView);
    }

    public void getData(){
         getModel().getProduct_mToolsInfo().subscribe(new Action1<List<Product_mToolsInfo>>() {
             @Override
             public void call(List<Product_mToolsInfo> product_mToolsInfos) {
                 getView().get_mToolsData(product_mToolsInfos);
             }
         }, new Action1<Throwable>() {
             @Override
             public void call(Throwable throwable) {
                 getView().getFail();
             }
         });
    }
}
