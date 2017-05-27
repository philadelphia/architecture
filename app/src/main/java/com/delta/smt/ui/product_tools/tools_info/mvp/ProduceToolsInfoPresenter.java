package com.delta.smt.ui.product_tools.tools_info.mvp;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.JsonProductRequestToolsList;
import com.delta.smt.entity.JsonProductRequestToolsRoot;
import com.delta.smt.entity.JsonProductToolsSubmitRoot;
import com.delta.smt.entity.JsonProductToolsVerfyList;
import com.delta.smt.entity.JsonProductToolsVerfyRoot;
import com.delta.smt.entity.ProductToolsInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Shaoqiang.Zhang on 2017/1/10.
 */

public class ProduceToolsInfoPresenter extends BasePresenter<ProduceToolsInfoContract.Model, ProduceToolsInfoContract.View> {


    String status;

    @Inject
    public ProduceToolsInfoPresenter(ProduceToolsInfoContract.Model model, ProduceToolsInfoContract.View mView) {
        super(model, mView);
    }

    //刚进入页面时获取治具信息
    public void getToolsInfo(String parm) {

        getView().showLoadingView();

        getModel().getProductToolsInfoItem(parm).subscribe(new Action1<JsonProductRequestToolsRoot>() {
            @Override
            public void call(JsonProductRequestToolsRoot jsonProductRequestToolsRoot) {
                //getView().getToolsInfo();
                getView().showContentView();
                List<JsonProductRequestToolsList> rows = jsonProductRequestToolsRoot.getRows();
                List<ProductToolsInfo> data = new ArrayList<>();
                int size = 0;
                for (JsonProductRequestToolsList j : rows) {
                    size++;
                    String toolsStatus = "";
                    switch (j.getLoanStatus()) {
                        case 0:
                            toolsStatus = "准备中";
                            break;
                        case 1:
                            toolsStatus = "待發";
                            break;
                        case 2:
                            toolsStatus = "已借出";
                            break;
                        case 3:
                            toolsStatus = "待确定";
                            break;
                        default:
                            toolsStatus = "状态未知";
                    }

                    ProductToolsInfo p = new ProductToolsInfo(String.valueOf(size), j.getJigcode(), j.getJigTypeName(), j.getShelfName(), "更多", toolsStatus, String.valueOf(j.getJigTypeId()), String.valueOf(j.getJigId()));
                    data.add(p);


                    Log.e("-------===-------->>>", j.toString());

                }
                getView().getToolsInfo(data);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getFail(throwable.getMessage());
                } catch (Exception e) {

                }

            }
        });

    }

    //点击确定，开始扫描
    public void getToolsVerfy(String parm) {

        getModel().getProductToolsVerfy(parm).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<JsonProductToolsVerfyRoot>() {
            @Override
            public void call(JsonProductToolsVerfyRoot jsonProductToolsVerfyRoot) {

                if (jsonProductToolsVerfyRoot.getCode() == 0) {
                    List<ProductToolsInfo> data = new ArrayList<>();

                    List<JsonProductToolsVerfyList> rows = jsonProductToolsVerfyRoot.getRows();
                    getView().showContentView();
                    int size = 0;
                    for (JsonProductToolsVerfyList j : rows) {
                        size++;
                        if (j.getLoanStatus() == 1) {
                            status = "待取";
                        } else {
                            status = j.getStatName();

                        }

                        ProductToolsInfo p = new ProductToolsInfo(String.valueOf(size), j.getJigcode(), j.getJigTypeName(), "", "更多", status, String.valueOf(j.getJigTypeId()), String.valueOf(j.getJigId()));
                        data.add(p);

                    }

                    getView().getToolsVerfy(data);
                } else {

                    getView().getFail(jsonProductToolsVerfyRoot.getMessage());
                    getView().showContentView();
                }


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    Log.i(TAG, "call: " + throwable.getMessage());
                    getView().getFail(throwable.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    //扫描完成上传数据
    public void getToolsBorrowSubmit(String parm) {

        getModel().getProductToolsBorrowSubmit(parm).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                getView().showLoadingView();
            }
        }).subscribe(new Action1<JsonProductToolsSubmitRoot>() {
            @Override
            public void call(JsonProductToolsSubmitRoot jsonProductToolsSubmitRoot) {

                getView().showContentView();
                getView().getToolsBorrowSubmit(jsonProductToolsSubmitRoot);

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {


            }
        });

    }

}
