package com.delta.smt.ui.product_tools.tools_info.mvp;

import android.util.Log;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.entity.JsonProductRequestToolsList;
import com.delta.smt.entity.JsonProductRequestToolsRoot;
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
                            toolsStatus = "待取";
                            break;
                        case 2:
                            toolsStatus = "已借出";
                            break;
                        case 3:
                            toolsStatus = "待确定";
                            break;
                        default:
                            toolsStatus = "未知";
                            break;
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


    public void getToolsInfoInit(String parm) {
        getView().showLoadingView();
        getModel().getProductToolsInfoItem(parm).subscribe(new Action1<JsonProductRequestToolsRoot>() {
            @Override
            public void call(JsonProductRequestToolsRoot jsonProductRequestToolsRoot) {
                //getView().getToolsInfo();
                if (jsonProductRequestToolsRoot.getCode() == 0) {
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
                                toolsStatus = "待取";
                                break;
                            case 2:
                                toolsStatus = "已借出";
                                break;
                            case 3:
                                toolsStatus = "待确定";
                                break;
                            default:
                                toolsStatus = "未知";
                                break;
                        }
                        ProductToolsInfo p = new ProductToolsInfo(String.valueOf(size), j.getJigcode(), j.getJigTypeName(), j.getShelfName(), "更多", toolsStatus, String.valueOf(j.getJigTypeId()), String.valueOf(j.getJigId()));
                        data.add(p);
                        Log.e("-------===-------->>>", j.toString());
                    }
                    getView().getToolsInfoInit(data);
                } else {
                    try {
                        getView().getFail(jsonProductRequestToolsRoot.getMessage());
                        getView().showContentView();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getFail(throwable.getMessage());
                    getView().showContentView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getToolsInfoAndChangeTool(String parm) {
        getView().showLoadingView();
        getModel().getProductToolsInfoItem(parm).subscribe(new Action1<JsonProductRequestToolsRoot>() {
            @Override
            public void call(JsonProductRequestToolsRoot jsonProductRequestToolsRoot) {
                //getView().getToolsInfo();
                if (jsonProductRequestToolsRoot.getCode() == 0) {
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
                                toolsStatus = "待取";
                                break;
                            case 2:
                                toolsStatus = "已借出";
                                break;
                            case 3:
                                toolsStatus = "待确定";
                                break;
                            default:
                                toolsStatus = "未知";
                                break;
                        }
                        ProductToolsInfo p = new ProductToolsInfo(String.valueOf(size), j.getJigcode(), j.getJigTypeName(), j.getShelfName(), "更多", toolsStatus, String.valueOf(j.getJigTypeId()), String.valueOf(j.getJigId()));
                        data.add(p);
                        Log.e("-------===-------->>>", j.toString());
                    }
                    getView().getToolsInfoAndChangeTool(data);
                } else {
                    try {
                        getView().getFail(jsonProductRequestToolsRoot.getMessage());
                        getView().showContentView();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    getView().getFail(throwable.getMessage());
                    getView().showContentView();
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
        }).subscribe(new Action1<JsonProductRequestToolsRoot>() {
            @Override
            public void call(JsonProductRequestToolsRoot jsonProductToolsVerfyRoot) {

                if (jsonProductToolsVerfyRoot.getCode() == 0) {
                    List<ProductToolsInfo> data = new ArrayList<>();

                    List<JsonProductRequestToolsList> rows = jsonProductToolsVerfyRoot.getRows();
                    getView().showContentView();
                    int size = 0;
                    for (JsonProductRequestToolsList j : rows) {
                        size++;
                        /*if (j.getLoanStatus() == 1) {
                            status = "待取";
                        } else if(j.getLoanStatus() == 0){
                            status = j.getStatName();

                        }*/

                        switch (j.getLoanStatus()) {
                            case 0:
                                status = "准备中";
                                break;
                            case 1:
                                status = "待取";
                                break;
                            case 2:
                                status = "已借出";
                                break;
                            case 3:
                                status = "待确定";
                                break;
                            default:
                                status = "未知";
                                break;
                        }

                        ProductToolsInfo p = new ProductToolsInfo(String.valueOf(size), j.getJigcode(), j.getJigTypeName(), j.getShelfName(), "更多", status, String.valueOf(j.getJigTypeId()), String.valueOf(j.getJigId()));
                        data.add(p);

                    }

                    getView().getToolsVerfy(data);
                } else {

                    try {
                        getView().getFail(jsonProductToolsVerfyRoot.getMessage());
                        getView().showContentView();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    Log.i(TAG, "call: " + throwable.getMessage());
                    getView().getFail(throwable.getMessage());
                    getView().showContentView();
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
        }).subscribe(new Action1<JsonProductRequestToolsRoot>() {
            @Override
            public void call(JsonProductRequestToolsRoot jsonProductRequestToolsRoot) {

                if (jsonProductRequestToolsRoot.getCode() == 0) {
                    getView().showContentView();
                    //getView().getToolsBorrowSubmit(jsonProductToolsSubmitRoot);
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
                                toolsStatus = "待取";
                                break;
                            case 2:
                                toolsStatus = "已借出";
                                break;
                            case 3:
                                toolsStatus = "待确定";
                                break;
                            default:
                                toolsStatus = "未知";
                                break;
                        }
                        ProductToolsInfo p = new ProductToolsInfo(String.valueOf(size), j.getJigcode(), j.getJigTypeName(), j.getShelfName(), "更多", toolsStatus, String.valueOf(j.getJigTypeId()), String.valueOf(j.getJigId()));
                        data.add(p);
                        Log.e("-------===-------->>>", j.toString());
                    }
                    getView().getToolsBorrowSubmit(data);
                } else {
                    try {
                        getView().getFail(jsonProductRequestToolsRoot.getMessage());
                        getView().showContentView();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                try {
                    Log.i(TAG, "call: " + throwable.getMessage());
                    getView().getFail(throwable.getMessage());
                    getView().showContentView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
