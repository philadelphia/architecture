package com.delta.smt.ui.fault_processing.fault_add.mvp;

import com.delta.commonlibs.base.mvp.IModel;
import com.delta.commonlibs.base.mvp.IView;
import com.delta.smt.entity.BaseEntity;
import com.delta.smt.entity.Result;
import com.delta.smt.entity.ResultFault;
import com.delta.smt.entity.ResultString;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import rx.Observable;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2017/1/4 20:24
 */


public interface FaultProcessingAddContract {

    interface Model extends IModel {
        Observable<BaseEntity> addSolution(String contents);
        Observable<ResultString<String>> getTemplateContent(String fileName);
        Observable<ResultFault> upLoadFile(RequestBody requestBody, MultipartBody.Part part, String argument);




    }

    interface View extends IView {

        void onSuccess(String message);

        void onFailed(String message);

    }
}
