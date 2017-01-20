package com.delta.smt.ui.product_tools.tools_info.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.JsonProductRequestToolsList;
import com.delta.smt.entity.JsonProductRequestToolsRoot;
import com.delta.smt.entity.JsonProductToolsLocation;
import com.delta.smt.entity.JsonProductToolsVerfyList;
import com.delta.smt.entity.JsonProductToolsVerfyRoot;
import com.delta.smt.entity.ProductToolsInfo;

import java.util.ArrayList;
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

    public void getToolsInfo(String condition){

        getModel().getProductToolsInfoItem(1000000000,1,condition).subscribe(new Action1<JsonProductRequestToolsRoot>() {
            @Override
            public void call(JsonProductRequestToolsRoot jsonProductRequestToolsRoot) {
                //getView().getToolsInfo();
                List<JsonProductRequestToolsList> rows=jsonProductRequestToolsRoot.getRows();
                List<ProductToolsInfo> data=new ArrayList<>();
                int size=0;
                for(JsonProductRequestToolsList j:rows){
                    size++;
                    ProductToolsInfo p=new ProductToolsInfo(String.valueOf(size),j.getBarcode(),j.getJigTypeName(),j.getShelfName(),"更多",j.getLoanStatus()==1?"待确认":"待取",String.valueOf(j.getJigTypeID()),String.valueOf(j.getJigID()));
                    data.add(p);
                }
                getView().getToolsInfo(data);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getFail();
            }
        });

    }

    public void getToolsVerfy(String condition){

        getModel().getProductToolsVerfy(condition).subscribe(new Action1<JsonProductToolsVerfyRoot>() {
            @Override
            public void call(JsonProductToolsVerfyRoot jsonProductToolsVerfyRoot) {

                List<JsonProductToolsVerfyList> rows=jsonProductToolsVerfyRoot.getRows();

                List<ProductToolsInfo> data=new ArrayList<>();

                int size=0;
                for(JsonProductToolsVerfyList j:rows){
                    size++;
                    ProductToolsInfo p=new ProductToolsInfo(String.valueOf(size),j.getBarcode(),j.getJigTypeName(),"","更多",j.getStatID()==1?"待确认":"待取",String.valueOf(j.getJigTypeID()),String.valueOf(j.getJigID()));
                    data.add(p);

                }

                getView().getToolsVerfy(data);

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getFail();

            }
        });

    }

    public void getToolsBorrowSubmit(String param){

        getModel().getProductToolsBorrowSubmit(param).subscribe(new Action1<JsonProductToolsLocation>() {
            @Override
            public void call(JsonProductToolsLocation jsonProductToolsLocation) {

                getView().getToolsBorrowSubmit(jsonProductToolsLocation);

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                getView().getFail();

            }
        });

    }

}
