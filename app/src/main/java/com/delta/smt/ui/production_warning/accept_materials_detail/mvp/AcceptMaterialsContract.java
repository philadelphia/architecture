package com.delta.smt.ui.production_warning.accept_materials_detail.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.AcceptMaterialResult;
import com.delta.smt.entity.LightOnResultItem;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.production_warining_item.ItemAcceptMaterialDetail;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2017/2/13.
 */

public interface AcceptMaterialsContract {
    interface View extends IView {
        /*获取接料列表成功回调*/
        void getAcceptMaterialListSuccess(ItemAcceptMaterialDetail itemAcceptMaterialDetail);
        /*获取接料列表失败回调*/
        void getAcceptMaterialListFailed(String message);
        void showMessage(String message);

        /*接料成功回调*/
        void commitSerialNumberSuccess(int rows);
        /*接料失败回调*/
        void commitSerialNumberFailed(String message);

        void onNewMaterialNotExists(String message);
        void onOldMaterialNotExists(String message);
        void onLightOnSuccess(LightOnResultItem lightOnResultItem);
        void onLightOnFailed();
        void onLightOffSuccess();
        void onLightOffFailed();
    }

    interface Model extends IModel {
        Observable<ItemAcceptMaterialDetail> getAcceptMaterialList(String condition);
        Observable<AcceptMaterialResult> commitSerialNumber(String condition);
        Observable<Result> requestCloseLight(String condition);
        Observable<Result<LightOnResultItem>> turnLightOn(String value);
        Observable<Result> turnLightOff(String value);
    }
}
