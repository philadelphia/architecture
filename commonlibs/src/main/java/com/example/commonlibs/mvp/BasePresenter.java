package com.example.commonlibs.mvp;

import java.lang.ref.WeakReference;


public class BasePresenter<M extends IModel, V extends IView> implements Ipresenter {
    protected final String TAG = this.getClass().getSimpleName();
    protected M mModel;
    protected V mView;
    WeakReference<IView> IviewWeakReference = new WeakReference<IView>(mView);

    WeakReference<IModel> iModelWeakReference = new WeakReference<IModel>(mModel);

    public BasePresenter(M model, V mView) {
        this.mModel = model;
        this.mView = mView;
        onStart();
    }

    public M getModel() {
        return mModel;
    }

    public V getView() {
        return mView;
    }

    public BasePresenter(V rootView) {
        this.mView = rootView;
        onStart();
    }


    public void onStart() {


    }

    public void ondestory() {
        IviewWeakReference.clear();
        iModelWeakReference.clear();
    }

}
