package com.example.naveenkumar.mvpsample.Presenters;

import com.example.naveenkumar.mvpsample.Iview.IMovieListView;
import com.example.naveenkumar.mvpsample.domain.BMSDataObject;
import com.example.naveenkumar.mvpsample.eventbus.EventBus;
import com.example.naveenkumar.mvpsample.eventbus.IEventSubscriber;
import com.example.naveenkumar.mvpsample.eventbus.events.NetResponseProcessingCompletedEvent;
import com.example.naveenkumar.mvpsample.network.GatewayHelper;
import com.example.naveenkumar.mvpsample.network.IGatewayHelper;
import com.example.naveenkumar.mvpsample.sync.Entities.MovieList;
import com.example.naveenkumar.mvpsample.widgets.BMSPresenter;

/**
 * Created by naveenkumar on 02/09/15.
 */
public class MovieListPresenter extends  BMSPresenter implements IEventSubscriber{
    IMovieListView mMovieListView;
    private EventBus mEventBus;

    public MovieListPresenter(IMovieListView view) {
        super(view);
        mMovieListView = view;
        RegisterForEvents();
    }

    private void RegisterForEvents() {
        mEventBus = EventBus.getInstance();
        mEventBus.Subscribe(NetResponseProcessingCompletedEvent.class, this);
    }

    @Override
    public void OnViewLoaded() {
        super.OnViewLoaded();
        mMovieListView.ShowProgressOverLay();
        FireMovieListRequest(10);
    }

    
    @Override
    public void OnCreate() {
        super.OnCreate();
    }

    @Override
    public void OnStart() {
    }

    @Override
    public void OnStop() {
        mEventBus.UnSubscribe(NetResponseProcessingCompletedEvent.class,this);
    }

    @Override
    public void OnDestroy() {
        super.OnDestroy();

    }

    @Override
    public void OnEvent(BMSDataObject event) {
        mMovieListView.HideProgressOverLay();
        NetResponseProcessingCompletedEvent Event = (NetResponseProcessingCompletedEvent)event;
        MovieList list = (MovieList)Event.mProcessedResultDTO;
        mMovieListView.ShowMovieList(list.ListOfMobiles);
    }

    private void FireMovieListRequest(int i) {
        IGatewayHelper helper = GatewayHelper.mGateWayHelper;
        helper.FireMovieListRequest(i);
    }
}