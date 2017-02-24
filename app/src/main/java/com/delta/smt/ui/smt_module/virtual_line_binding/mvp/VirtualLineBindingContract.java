package com.delta.smt.ui.smt_module.virtual_line_binding.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.VirtualLineBindingItem;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public interface VirtualLineBindingContract {
    interface View extends IView {

        void onSuccess(VirtualLineBindingItem data);

        void onFalied(VirtualLineBindingItem data);

        void onNetFailed(Throwable throwable);

        /*public void onSuccessBinding(VirtualLineBindingItem data);

        public void onFailBinding();*/

        //public void onSuccessGetModByMate(ModNumByMaterialResult data);

        //public void onFailGetModByMate();
        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();

    }

    interface Model extends IModel {
        Observable<VirtualLineBindingItem> getAllVirtualLineBindingItems(String str);

        Observable<VirtualLineBindingItem> getVirtualBinding(String str);

        //public Observable<ModNumByMaterialResult>getModNumByMaterial(String str,String num);
    }
}
