package com.example.app.ui.main.mvp;


import com.example.app.api.ApiService;
import com.example.app.base.BaseModel;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/16 12:49
 */


public class MainModel extends BaseModel<ApiService> implements MainContract.Model {

    public MainModel(ApiService apiService) {
        super(apiService);
    }

    //更新
    /*@Override*/
    /*public Observable<Update> getUpdate() {
        return getService().getUpdate().compose(RxsRxSchedulers.<Update>io_main());
    }*/

    //更新
    /*@Override
    public void download(Context context, String urlStr) {
        //Toast.makeText(context,"开始后台下载",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra("urlStr", urlStr);
        context.startService(intent);
    }*/
}
