package com.delta.smt.ui.hand_add.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.ui.hand_add.item.ItemHandAdd;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Fuxiang.Zhang on 2016/12/27.
 */
@ActivityScope
public class HandAddPresenter extends BasePresenter<HandAddContract.Model,HandAddContract.View> {

    @Inject
    public HandAddPresenter(HandAddContract.Model model, HandAddContract.View mView) {
        super(model, mView);
    }

    public void getItemHandAddDatas(){
        getModel().getItemHandAddDatas().subscribe(new Action1<List<ItemHandAdd>>() {
            @Override
            public void call(List<ItemHandAdd> itemHandAdds) {
                getView().getItemHandAddDatas(itemHandAdds);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                getView().getItemHandAddDatasFailed();
            }
        });
    }
}
