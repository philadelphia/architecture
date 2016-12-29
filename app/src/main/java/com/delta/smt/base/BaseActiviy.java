package com.delta.smt.base;

import android.view.KeyEvent;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.di.component.AppComponent;

import javax.inject.Inject;

/**
 * Created by V.Wenju.Tian on 2016/11/29.
 */

public abstract class BaseActiviy<p extends BasePresenter> extends BaseCommonActivity {

    public final String TAG = getClass().getSimpleName();
    @Inject
    protected p mPresenter;

    private OnDispathchKeyEvent dispathchKeyEvent;

    public void setDispathchKeyEvent(OnDispathchKeyEvent dispathchKeyEvent) {
        this.dispathchKeyEvent = dispathchKeyEvent;
    }

    public void removeDispathchKeyEvent() {
        if (dispathchKeyEvent != null) {
            dispathchKeyEvent = null;
        }
    }

    @Override
    protected void initCView() {
        initView();
    }

    @Override
    protected void initCData() {
        componentInject(getMApplication().getAppComponent());//依赖注入
        initData();
    }

    protected abstract void componentInject(AppComponent appComponent);

    protected abstract void initData();


    protected abstract void initView();


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (dispathchKeyEvent != null) {
            if (dispathchKeyEvent.dispatchKeyEvent(event)) {
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public p getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.ondestory();
        }
        super.onDestroy();
    }

    public interface OnDispathchKeyEvent {
        boolean dispatchKeyEvent(KeyEvent event);
    }
}
