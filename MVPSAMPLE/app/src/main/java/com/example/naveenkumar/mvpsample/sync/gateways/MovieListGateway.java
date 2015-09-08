package com.example.naveenkumar.mvpsample.sync.gateways;

import android.content.Context;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.naveenkumar.mvpsample.eventbus.EventBus;
import com.example.naveenkumar.mvpsample.eventbus.events.NetResponseProcessingCompletedEvent;
import com.example.naveenkumar.mvpsample.network.NetworkRequestQueue;
import com.example.naveenkumar.mvpsample.sync.Entities.Movie;
import com.example.naveenkumar.mvpsample.sync.Entities.MovieList;

import java.util.ArrayList;

public class MovieListGateway extends BMSGateway implements  Response.Listener<String>, Response.ErrorListener {

    private Context mContext;
    private NetworkRequestQueue mNetworkRequestQueue;
    private String MOVIE_LIST_SERVICE_URL = BASE_URL + "limit=";

    public MovieListGateway(Context context) {
        super(context);
        mNetworkRequestQueue = NetworkRequestQueue.GetNetworkRequestQueue(context);
        mContext = context;
    }

    public void doMovieListRequest(int limit) {
        String Url = MOVIE_LIST_SERVICE_URL+String.valueOf(limit);
        System.out.println("Url"+Url);
        StringRequest request = new StringRequest(Request.Method.GET, Url,this,this);
        request.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mNetworkRequestQueue.AddStringToRequestQueue(request);
    }

    @Override
    public void onResponse(String response) {
        String status= null;
        try {
            JSONObject jsonObject = new JSONObject(response);
            status = jsonObject.getString("status");
            if(status.equalsIgnoreCase("ok")) {
                ArrayList<Movie> movieListWdObjectList = new ArrayList<Movie>();
                JSONObject jsonData=  jsonObject.getJSONObject("data");
                JSONArray movieListJsonArray = jsonData.getJSONArray("movies");
                for(int i = 0; i<movieListJsonArray.length(); i++) {
                    Movie movieDomainModel =  new Movie();
                    JSONObject object = (JSONObject) movieListJsonArray.get(i);
                    movieDomainModel.setUrl(object.getString("small_cover_image"));
                    movieDomainModel.setTitle(object.getString("title"));
                    movieDomainModel.setYear(object.getInt("year"));
                    movieDomainModel.setRating(object.getInt("rating"));
                    movieListWdObjectList.add(movieDomainModel);
                }
                MovieList movieList = new MovieList();
                movieList.ListOfMobiles = movieListWdObjectList;
                EventBus eventBus = EventBus.getInstance();
                NetResponseProcessingCompletedEvent movieListGetSuccessEvent  =  new NetResponseProcessingCompletedEvent();
                movieListGetSuccessEvent.mProcessedResultDTO = movieList;
                eventBus.Publish(movieListGetSuccessEvent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(mContext,"Error",Toast.LENGTH_SHORT).show();
        //// TODO: 04/09/15
    }
}
