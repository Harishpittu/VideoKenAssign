package com.technologies.pittu.videokenassignment.api;

import com.technologies.pittu.videokenassignment.model.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by harish on 09/11/16.
 */

public interface APIInterface {
    @GET("youtube/v3/search?part=snippet&type=video&maxResults=50&videoCategoryId=27&key=AIzaSyDgfJkWUhv4QdWP9y3CwW7ZhQu_hYV_4MM")
    Call<Video> getVideos(@Query("q") String query);
}
