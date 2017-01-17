package com.delta.smt.ui.product_tools.borrow.mvp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.delta.buletoothio.barcode.parse.entity.Feeder;
import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.JsonProductBorrowList;
import com.delta.smt.entity.JsonProductBorrowRoot;
import com.delta.smt.entity.ProductWorkItem;
import com.delta.smt.ui.product_tools.TimeSortUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Shaoqiang.Zhang on 2017/1/6.
 */

@ActivityScope
public class ProduceToolsBorrowPresenter extends BasePresenter<ProduceToolsBorrowContract.Model,ProduceToolsBorrowContract.View> {

    @Inject
    public ProduceToolsBorrowPresenter(ProduceToolsBorrowContract.Model model, ProduceToolsBorrowContract.View mView) {
        super(model, mView);
    }

    public void getData(){
        //TODO 修改输入的参数
        getModel().getProductWorkItem(1000000000,1).subscribe(new Action1<JsonProductBorrowRoot>() {

            List<ProductWorkItem> data=new ArrayList<>();
            @Override
            public void call(JsonProductBorrowRoot jsonProductBorrowRoot) {

                if (jsonProductBorrowRoot.getCode() == 0) {
                    List<JsonProductBorrowList> rows = jsonProductBorrowRoot.getRows();
                    for (JsonProductBorrowList j : rows) {

                        String lastDate = getMyStyleTime(j);
                        Log.e("time",lastDate);
                        ProductWorkItem productWorkItem = new ProductWorkItem(j.getOrderName(), String.valueOf(j.getOrderType()), j.getModel(), j.getPcbCode(), j.getCompositeMaterial(), j.getLineName(), j.getPcbMaterial(), j.getSide(), lastDate, j.getOrderStatus() == 0 ? "等待" : "准备就绪");
                        data.add(productWorkItem);

                    }
                }
                getView().getFormData(data);
            }


        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("getfail","fail");
                getView().getFail();
            }
        });
    }

    @NonNull
    private String getMyStyleTime(JsonProductBorrowList j) {
        String[] date = j.getPlanPrdTime().split(" ");
        String lastDate = date[2] + "-";
        String month;
        switch (TimeSortUtils.getContentFromSource(date[0], 0, 3)) {
            case "Jan":
                month = "01-";
                break;
            case "Feb":
                month = "02-";
                break;
            case "Mar":
                month = "03-";
                break;
            case "Apr":
                month = "04-";
                break;
            case "May":
                month = "05-";
                break;
            case "Jun":
                month = "06-";
                break;
            case "Jul":
                month = "07-";
                break;
            case "Aug":
                month = "08-";
                break;
            case "Sep":
                month = "09-";
                break;
            case "Oct":
                month = "10-";
                break;
            case "Nov":
                month = "11-";
                break;
            case "Dec":
                month = "12-";
                break;
            default:
                month = "";
        }

        lastDate += month + date[1].replace(",", "") + " ";

        String[] time = date[3].split(":");
        if (date[4].equals("PM")) {
            time[0] = String.valueOf(Integer.parseInt(time[0]) + 12);
        }

        lastDate += time[0] + ":" + time[1] + ":" + time[2];


        TimeSortUtils.getContentFromSource(date[0], 0, 2);
        Log.e("ProduceToolsBorrowModel", lastDate);
        return lastDate;
    }
}
