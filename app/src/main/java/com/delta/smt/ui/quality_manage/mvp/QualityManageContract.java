package com.delta.smt.ui.quality_manage.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.QualityManage;

import java.util.List;

import rx.Observable;

/**
 * Created by Zhenyu.Liu on 2017/4/18.
 */

public interface QualityManageContract {

    interface Model extends IModel {


        Observable<QualityManage> getQualityList(String str);

    }

    interface View extends IView {

        void showLoadingView();

        void showContentView();

        void showErrorView();

        void showEmptyView();

        void getQualityListSuccess(List<QualityManage.RowsBean> qualityManage);

        void getQualityListFailed(String message);

    }
    
}
