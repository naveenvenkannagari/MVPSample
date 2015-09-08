package com.example.naveenkumar.mvpsample.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.naveenkumar.mvpsample.R;

import com.example.naveenkumar.mvpsample.network.NetworkRequestQueue;
import com.example.naveenkumar.mvpsample.sync.Entities.Movie;

import java.util.ArrayList;

/**
 * Created by naveenkumar on 02/09/15.
 */
public class MovieListAdapter extends  BaseAdapter{

    private ArrayList<Movie> listOfMovies;
    private Context context;
    private LayoutInflater layoutInflater;

    public MovieListAdapter(Context context){
        this.context = context;
        listOfMovies = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    public void updateDataset(ArrayList<Movie> listOfMovies) {
        this.listOfMovies = listOfMovies;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listOfMovies.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Movie getItem(int position) {
        return listOfMovies.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_movie_box_office, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
        Movie movie = getItem(position);

        mViewHolder.movieName.setText(movie.getTitle());
        int movieReleaseDate = movie.getYear();
        if (movieReleaseDate != 0) {
            String formattedDate = Integer.toString(movieReleaseDate);
            mViewHolder.movieReleaseDate.setText(formattedDate);
        } else {
            mViewHolder.movieReleaseDate.setText("NA");
        }
        int audienceScore = movie.getRating();
        if (audienceScore == -1) {
            mViewHolder.movieRating.setRating(0.0F);
            mViewHolder.movieRating.setAlpha(0.5F);
        } else {
            mViewHolder.movieRating.setRating(audienceScore / 20.0F);
            mViewHolder.movieRating.setAlpha(1.0F);
        }
        NetworkRequestQueue.getImageLoader().get(movie.getUrl(), ImageLoader.getImageListener(mViewHolder.moviePoster,
                        R.drawable.ic_launcher, R.drawable.ic_launcher));
        return convertView;
    }

    private class MyViewHolder {
        TextView movieName;
        TextView movieReleaseDate;
        RatingBar movieRating;
        ImageView moviePoster;

        public MyViewHolder(View view) {
            movieName = (TextView)view.findViewById(R.id.movieTitle);
            movieRating = (RatingBar)view.findViewById(R.id.movieAudienceScore);
            movieReleaseDate = (TextView)view.findViewById(R.id.movieReleaseDate);
            moviePoster = (ImageView)view.findViewById(R.id.movieThumbnail);
        }
    }
}
