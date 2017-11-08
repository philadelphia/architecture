package com.example.app.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.commonlibs.mvp.BasePresenter;
import com.example.app.app.App;
import com.example.app.di.component.AppComponent;
import com.example.app.entity.EventNothing;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;



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
        getPresenter().ondestory();
        super.onDestroy();

    }

    Bundle savedState;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Restore State Here
        if (UseEventBus()) {
            EventBus.getDefault().register(this);
        }
        if (!restoreStateFromArguments()) {
            // First Time, Initialize something here
            onFirstTimeLaunched();
        }
    }

    protected void onFirstTimeLaunched() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save State Here
        saveStateToArguments();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Save State Here
        saveStateToArguments();
    }

    ////////////////////
    // Don't Touch !!
    ////////////////////

    private void saveStateToArguments() {
        if (getView() != null)
            savedState = saveState();
        if (savedState != null) {
            Bundle b = getArguments();
            if (b != null) {

                b.putBundle("data", savedState);
            }
        }
    }

    ////////////////////
    // Don't Touch !!
    ////////////////////

    private boolean restoreStateFromArguments() {
        Bundle b = getArguments();
        if (b != null) {

            savedState = b.getBundle("data");
        }
        if (savedState != null) {
            restoreState();
            return true;
        }
        return false;
    }

    /////////////////////////////////
    // Restore Instance State Here
    /////////////////////////////////

    private void restoreState() {
        if (savedState != null) {
            // For Example
            //tv1.setText(savedState.getString(text));
            onRestoreState(savedState);
        }
    }

    protected void onRestoreState(Bundle savedInstanceState) {

    }

    //////////////////////////////
    // Save Instance State Here
    //////////////////////////////

    private Bundle saveState() {
        Bundle state = new Bundle();
        // For Example
        //state.putString(text, tv1.getText().toString());
        onSaveState(state);
        return state;
    }

    protected void onSaveState(Bundle outState) {

    }
}
