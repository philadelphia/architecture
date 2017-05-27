package com.delta.smt.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delta.commonlibs.base.mvp.BasePresenter;
import com.delta.smt.app.App;
import com.delta.smt.di.component.AppComponent;
import com.delta.smt.entity.EventNothing;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @description :
 * @autHor :  V.Wenju.Tian
 * @date : 2016/12/12 15:30
 */

public abstract class BaseFragment<p extends BasePresenter> extends SupportFragment {
    @Inject
    protected p mPresenter;
    private App application;
    private View rootView;
    private Activity mainActivity;
    private Unbinder bind;
    public String TAG = getClass().getSimpleName();

    @Override
    public void onAttach(Context context) {
        if (context instanceof Activity) {
            mainActivity = getActivity();
        } else {
            throw new ClassCastException("context can't cast to activity");
        }
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        application = (App) (getActivity().getApplication());
        componentInject(App.getAppComponent());//依赖注入
        rootView = inflater.inflate(getContentViewId(), container, false);
        bind = ButterKnife.bind(this, rootView);
        initData();
        initView();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (UseEventBus()) {
            EventBus.getDefault().register(this);
        }
        super.onActivityCreated(savedInstanceState);
    }

    //默认不使用
    protected boolean UseEventBus() {
        return false;
    }

    @Subscribe
    public void recieve(EventNothing event) {

    }

    protected abstract void initView();

    public Activity getmActivity() {
        return mainActivity;
    }

    protected abstract void componentInject(AppComponent appComponent);


    public p getPresenter() {
        return mPresenter;
    }

    protected abstract void initData();

    protected abstract int getContentViewId();

    @Override
    public void onDestroy() {
        if (bind != Unbinder.EMPTY) {
            bind.unbind();

        }
        if (UseEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }
}
