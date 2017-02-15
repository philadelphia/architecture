package com.delta.smt.ui.production_warning.mvp.accept_materials_detail;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.production_warning.item.ItemAcceptMaterialDetail;

import java.util.List;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2017/2/13.
 */

public interface AcceptMaterialsContract {
     interface View extends IView {
         void getItemDatas(List<ItemAcceptMaterialDetail> itemAcceptMaterialDetails);
         void getItemDatasFailed(String message);
         void commitBarcodeSucess();
    }

    interface Model extends IModel {
        Observable<Result<ItemAcceptMaterialDetail>> getItemDatas();
        Observable<Result> commitBarode();
    }
}
