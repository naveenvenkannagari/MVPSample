package com.example.naveenkumar.mvpsample.view;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.naveenkumar.mvpsample.CustomComponents.CustomProgressDialog;
import com.example.naveenkumar.mvpsample.Iview.IMovieListView;
import com.example.naveenkumar.mvpsample.Presenters.MovieListPresenter;
import com.example.naveenkumar.mvpsample.R;
import com.example.naveenkumar.mvpsample.sync.Entities.Movie;
import com.example.naveenkumar.mvpsample.util.ViewUtils;
import com.example.naveenkumar.mvpsample.widgets.BMSBaseActivity;


import java.util.ArrayList;

/**
 * Created by naveenkumar on 01/09/15.
 */
public class MovieListActivity extends BMSBaseActivity implements IMovieListView {

    private ListView mMovieListView;
    private MovieListPresenter mMovieListPresenter;
    private MovieListAdapter mMovieListAdapter;
    private CustomProgressDialog mProgressDialog;
    private RelativeLayout mMainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list_view);
        loadPresenter();
        loadView();
    }

    private void loadPresenter() {
        mMovieListPresenter = new MovieListPresenter(this);
        mMovieListPresenter.OnCreate();
    }

    private void loadView() {
        mMainLayout = (RelativeLayout)findViewById(R.id.mainView);
        mMovieListView = (ListView)findViewById(R.id.listView);
        mMovieListAdapter = new MovieListAdapter(this);
        mMovieListView.setAdapter(mMovieListAdapter);
        mMovieListPresenter.OnViewLoaded();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMovieListPresenter.OnStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewUtils.unbindReferences(mMainLayout);
    }

    @Override
    public void ShowMovieList(ArrayList<Movie> movieList) {
      mMovieListAdapter.updateDataset(movieList);
    }

    @Override
    public void ShowProgressOverLay() {
        HideProgressOverLay();
        if (mProgressDialog == null) {
            mProgressDialog = new CustomProgressDialog(this);
            mProgressDialog.show();
        }
    }

    @Override
    public void HideProgressOverLay() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
