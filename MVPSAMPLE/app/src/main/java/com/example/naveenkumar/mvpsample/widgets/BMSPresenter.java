package com.example.naveenkumar.mvpsample.widgets;

import com.example.naveenkumar.mvpsample.Iview.IBMSView;

/**
 * Created by naveenkumar on 02/09/15.
 */



public abstract class BMSPresenter  {

    protected boolean mIsViewLoaded;
    protected IBMSView mView;


    public BMSPresenter(IBMSView view) {
        SetView(view);
    }

    public void OnCreate() {

    }

    public  abstract  void OnStart();

    public abstract void OnStop();

    public void OnViewLoaded() {
        SetViewLoaded(true);
    }

    public void SetViewLoaded(boolean viewLoaded) {
        this.mIsViewLoaded = viewLoaded;
    }

    public boolean IsViewLoaded() {
        return mIsViewLoaded;
    }


    public void SetView(IBMSView view) {
        this.mView = view;
    }

    public IBMSView GetView() {
        return mView;
    }

    public void OnDestroy() {
        mView = null;
    }
}
