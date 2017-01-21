package com.delta.smt.ui.product_tools.borrow.mvp;

import android.util.Log;

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

                        String lastDate = TimeSortUtils.getMyStyleTime(j);
                        Log.e("time",lastDate);
                        ProductWorkItem productWorkItem = new ProductWorkItem(String.valueOf(j.getOrderID()), j.getOrderType()==0?"内部":"外部", j.getModel(), j.getPcbCode(), j.getCompositeMaterial(), j.getLineName(), j.getPcbMaterial(), j.getSide(), lastDate, j.getOrderStatus() == 0 ? "等待" : "准备就绪");
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
}
