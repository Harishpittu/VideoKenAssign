package com.technologies.pittu.videokenassignment.api;

import com.google.gson.Gson;
import com.technologies.pittu.videokenassignment.model.Video;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by harish on 09/11/16.
 */

public class API {
    public static final String TAG = API.class.getName();
    private Retrofit mRetrofit;
    private APIInterface mvpApiInterface;
    private Gson gson;
    private String baseURL = "https://www.googleapis.com/";

    public API() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.connectTimeout(10, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(interceptor);

        gson = new Gson();

        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseURL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient.build())
                .build();
        mvpApiInterface = mRetrofit.create(APIInterface.class);
    }

    public Call<Video> getVideo(String query)
    {
         return mvpApiInterface.getVideos(query);
    }

}
