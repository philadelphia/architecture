package com.delta.smt.ui.maintain.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.LedLight;
import com.delta.smt.entity.Success;

import java.util.List;

import rx.Observable;

/**
 * Created by Lin.Hou on 2017-03-13.
 */

public class PCBMaintainContract {
    public interface View extends IView {
        void showLoadingView();
        void showContentView();
        void showErrorView();
        void showEmptyView();
        void getSubshelf(List<LedLight.RowsBean> wareHouses);
        void getUpdate(String wareHouses);
        void onFailed(String wareHouses);
        void Unbound(String wareHouses);
        void UnboundDialog(String code);
    }
    public interface Model extends IModel{
        Observable<LedLight> getSubshelf(String s);
        Observable<Success> getUpdate(String id,String lightSerial);
        Observable<Success> getUnbound(String id);

    }
}
