package com.technologies.pittu.videokenassignment.views;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.technologies.pittu.videokenassignment.R;
import com.technologies.pittu.videokenassignment.views.adapter.VideoAdapter;
import com.technologies.pittu.videokenassignment.api.API;
import com.technologies.pittu.videokenassignment.databinding.ActivityMainBinding;
import com.technologies.pittu.videokenassignment.interfaces.OnItemClickListener;
import com.technologies.pittu.videokenassignment.model.Item;
import com.technologies.pittu.videokenassignment.model.Video;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private VideoAdapter videoAdapter;
    private ActivityMainBinding activityMainBinding;
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        api = new API();
        videoAdapter = new VideoAdapter(new OnItemClickListener() {
            @Override
            public void onClick(Object object) {
                Item item = (Item) object;
                Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                intent.putExtra("videoId",item.getId().getVideoId());
                startActivity(intent);
            }
        });
        setupRecyclerView(activityMainBinding.listPeople);
    }

    /**
     * on click of search button
     * @param view view
     */
    public void onClickSearch(View view) {
        // Check if no view has focus:
        View views = this.getCurrentFocus();
        if (views != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(views.getWindowToken(), 0);
        }
        if (!TextUtils.isEmpty(activityMainBinding.searchText.getText().toString())) {
            activityMainBinding.progressBar.setVisibility(View.VISIBLE);
            api.getVideo(activityMainBinding.searchText.getText().toString()).enqueue(new Callback<Video>() {
                @Override
                public void onResponse(Call<Video> call, Response<Video> response) {
                    if (response != null && response.body() != null && response.body().getItems() != null) {
                        videoAdapter.setVideoList(response.body().getItems());
                    }
                    activityMainBinding.progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<Video> call, Throwable t) {
                    activityMainBinding.progressBar.setVisibility(View.GONE);

                }
            });
        }
    }

    /**
     * @param recyclerView recycler view
     */
    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setAdapter(videoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
