package com.example.naveenkumar.mvpsample.sync.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.naveenkumar.mvpsample.domain.BMSDataObject;


public class Movie extends BMSDataObject {

    private String url;
    private String title;
    private  int year;
    private  int rating;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
