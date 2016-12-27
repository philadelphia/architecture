package com.delta.smt.ui.storeroom.mvp;

import com.delta.smt.api.ApiService;
import com.delta.smt.base.BaseModel;

/**
 * Created by Lin.Hou on 2016-12-26.
 */

public class StoreRoomModel extends BaseModel<ApiService> implements StoreRoomContract.Model{

    public StoreRoomModel(ApiService apiService) {
        super(apiService);
    }
}
