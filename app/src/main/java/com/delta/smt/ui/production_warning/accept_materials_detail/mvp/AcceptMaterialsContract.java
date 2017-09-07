package com.delta.smt.ui.production_warning.accept_materials_detail.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.production_warining_item.ItemAcceptMaterialDetail;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2017/2/13.
 */

public interface AcceptMaterialsContract {
    interface View extends IView {
        void getAcceptMaterialsItemDatas(ItemAcceptMaterialDetail itemAcceptMaterialDetail);
        void getItemDatasFailed(String message);
        void showMessage(String message);
        void commitSerialNumberSucess();
    }

    interface Model extends IModel {
        Observable<ItemAcceptMaterialDetail> getAcceptMaterialsItemDatas(String codition);
        Observable<Result> commitSerialNumber(String condition);
        Observable<Result> requestCloseLight(String condition);
    }
}
