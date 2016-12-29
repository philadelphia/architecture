package com.delta.smt.ui.store.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;
import com.delta.smt.entity.ListWarning;

import java.util.List;

import rx.Observable;

/**
 * Created by Lin.Hou on 2016-12-27.
 */

public class WarningListModel extends BaseModel<ApiService> implements WarningListContract.Model {

    public WarningListModel(ApiService service) {
        super(service);
    }

    @Override
    public Observable<List<ListWarning>> getListWarning() {
        return getService().getListWarning();
    }
}
