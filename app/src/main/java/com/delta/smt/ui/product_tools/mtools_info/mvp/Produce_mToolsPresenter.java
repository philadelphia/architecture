package com.delta.smt.ui.product_tools.mtools_info.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.JsonProduct_mToolsList;
import com.delta.smt.entity.JsonProduct_mToolsRoot;
import com.delta.smt.entity.Product_mToolsInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Shaoqiang.Zhang on 2017/1/11.
 */
@ActivityScope
public class Produce_mToolsPresenter extends BasePresenter<Produce_mToolsContract.Model,Produce_mToolsContract.View>{

    @Inject
    public Produce_mToolsPresenter(Produce_mToolsContract.Model model, Produce_mToolsContract.View mView) {
        super(model, mView);
    }

    public void getData(String condition_and_jigTypeID){
         getModel().getProduct_mToolsInfo(1000000000,1,condition_and_jigTypeID).subscribe(new Action1<JsonProduct_mToolsRoot>() {
             @Override
             public void call(JsonProduct_mToolsRoot jsonProduct_mToolsRoot) {


                 List<JsonProduct_mToolsList> rows=jsonProduct_mToolsRoot.getRows();
                 List<Product_mToolsInfo> data=new ArrayList<>();
                 int size=0;
                 for (JsonProduct_mToolsList j:rows){

                     size++;
                     Product_mToolsInfo p=new Product_mToolsInfo(String.valueOf(size),j.getBarcode(),j.getJigTypeName(),j.getShelfName(),String.valueOf(j.getJigID()));
                     data.add(p);

                 }

                 getView().get_mToolsData(data);

             }
         }, new Action1<Throwable>() {
             @Override
             public void call(Throwable throwable) {

             }
         });
    }
}
