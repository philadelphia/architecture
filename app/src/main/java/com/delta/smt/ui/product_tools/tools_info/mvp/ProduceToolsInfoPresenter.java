package com.delta.smt.ui.product_tools.tools_info.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.ProductWorkItem;
import com.delta.smt.entity.Product_mToolsInfo;
import com.delta.smt.ui.product_tools.borrow.mvp.ProduceToolsBorrowContract;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Shaoqiang.Zhang on 2017/1/10.
 */

public class ProduceToolsInfoPresenter extends BasePresenter<ProduceToolsInfoContract.Model,ProduceToolsInfoContract.View>{

    @Inject
    public ProduceToolsInfoPresenter(ProduceToolsInfoContract.Model model, ProduceToolsInfoContract.View mView) {
        super(model, mView);
    }

    public void getToolsInfo(){

        getModel().getProductToolsInfoItem().subscribe(new Action1<List<Product_mToolsInfo>>() {
            @Override
            public void call(List<Product_mToolsInfo> product_mToolsInfos) {
                getView().getToolsInfo(product_mToolsInfos);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getFail();
            }
        });

    }

}
