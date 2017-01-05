package com.delta.smt.ui.smt_module.virtual_line_binding.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.VirtualLineBindingItem;

import java.util.List;

import rx.Observable;

/**
 * Created by Shufeng.Wu on 2017/1/4.
 */

public interface VirtualLineBindingContract {
    interface View extends IView {

        public void onSuccess(List<VirtualLineBindingItem> data);

        public void onFalied();

    }

    interface Model extends IModel {
        public Observable<List<VirtualLineBindingItem>> getAllVirtualLineBindingItems();
    }
}
