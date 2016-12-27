package com.delta.smt.ui.mantissa_warehouse.ready.mvp;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.commonlibs.di.scope.ActivityScope;
import com.delta.smt.entity.MantissaWarehouseReady;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;

/**
 * Created by Zhenyu.Liu on 2016/12/27.
 */
@ActivityScope
public class MantissaWarehouseReadyPresenter extends BasePresenter<MantissaWarehouseReadyContract.Model , MantissaWarehouseReadyContract.View> {


    @Inject
    public MantissaWarehouseReadyPresenter(MantissaWarehouseReadyContract.Model model, MantissaWarehouseReadyContract.View mView) {
        super(model, mView);
    }

  public void getMantissaWarehouseReadies(){

      getModel().getMantissaWarehouseReadies().subscribe(new Action1<List<MantissaWarehouseReady>>() {
          @Override
          public void call(List<MantissaWarehouseReady> mantissaWarehouseReadies) {

              getView().getSucess(mantissaWarehouseReadies);

          }
      }, new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {

              getView().getFailed();
          }
      });

  }
}
