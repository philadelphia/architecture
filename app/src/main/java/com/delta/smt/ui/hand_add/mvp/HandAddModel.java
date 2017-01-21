package com.delta.smt.ui.hand_add.mvp;

import com.delta.commonlibs.utils.RxsRxSchedulers;
import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.Result;
import com.delta.smt.ui.hand_add.item.ItemHandAdd;

import rx.Observable;

/**
 * Created by Fuxiang.Zhang on 2016/12/27.
 */

public class HandAddModel extends BaseModel<ApiService> implements HandAddContract.Model {

    public HandAddModel(ApiService apiService) {
        super(apiService);
    }

    @Override
    public Observable<Result<ItemHandAdd>> getItemHandAddDatas() {
/*        List<ItemHandAdd> datas = new ArrayList<>();

        for (int mI = 0; mI < 20; mI++) {
            ItemHandAdd mItemHandAdd=new ItemHandAdd("料站Pass预警", "产线：H13",
                    "模组料站：06T021", "预计Pass数量：2", "预警信息：IC201位置需要手补件",
                    "400001",System.currentTimeMillis()+40000l);
            mItemHandAdd.setTimeId(mI);
            datas.add(mItemHandAdd);
            mItemHandAdd=new ItemHandAdd("料站Pass预警", "产线：H14",
                    "模组料站：06T022", "预计Pass数量：4", "预警信息：IC201位置需要手补件",
                    "400001",System.currentTimeMillis()+30000l);
            mItemHandAdd.setTimeId(mI+20);
            datas.add(mItemHandAdd);
        }

        return Observable.just(datas);*/
        return getService().getItemHandAddDatas().compose(RxsRxSchedulers.<Result<ItemHandAdd>>io_main());
    }

    @Override
    public Observable<Result> getItemHandAddConfirm(String condition) {
        return getService().getItemHandAddConfirm(condition).compose(RxsRxSchedulers.<Result>io_main());
    }
}
