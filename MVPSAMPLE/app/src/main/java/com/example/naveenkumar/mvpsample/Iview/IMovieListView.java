package com.example.naveenkumar.mvpsample.Iview;

import com.example.naveenkumar.mvpsample.sync.Entities.Movie;

import java.util.ArrayList;

/**
 * Created by naveenkumar on 01/09/15.
 */
public interface IMovieListView extends  IBMSView{

    void ShowMovieList(ArrayList<Movie> movieList);

    void ShowProgressOverLay();

    void HideProgressOverLay();



}
