package com.delta.smt.ui.fault_processing.fault_add.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
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
 * @date : 2017/1/4 20:37
 */


public class FaultProcessingAddModel extends BaseModel<ApiService> implements FaultProcessingAddContract.Model {

    public FaultProcessingAddModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<BaseEntity> addSolution(String ids) {


        return getService().addSolution(ids).compose(RxsRxSchedulers.<BaseEntity>io_main());
    }

    @Override
    public Observable<BaseEntity<String>> getTemplateContent(String fileName) {
        return getService().getTemplateContent(fileName).compose(RxsRxSchedulers.<BaseEntity<String>>io_main());
    }

    @Override
    public Observable<ResultFault> upLoadFile(RequestBody requestBody, MultipartBody.Part part, String argument) {
        return getService().upLoadFile(requestBody, part, argument).compose(RxsRxSchedulers.<ResultFault>io_main());
    }



}
