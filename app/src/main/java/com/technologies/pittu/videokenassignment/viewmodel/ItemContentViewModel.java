

package com.technologies.pittu.videokenassignment.viewmodel;

import android.databinding.BaseObservable;
import android.view.View;

import com.technologies.pittu.videokenassignment.interfaces.OnItemClickListener;
import com.technologies.pittu.videokenassignment.model.Content;

import java.util.concurrent.TimeUnit;


public class ItemContentViewModel extends BaseObservable {

    private Content content;
    private OnItemClickListener onItemClickListener;

    public ItemContentViewModel(Content content, OnItemClickListener onItemClickListener) {
        this.content = content;
        this.onItemClickListener = onItemClickListener;
    }

    public void onItemClick(View view) {
        onItemClickListener.onClick(content);
    }

    public String getId() {
        return String.valueOf(content.getId());
    }

    public String getTitle() {
        return String.valueOf(content.getId()) + ". " + content.getTitle();
    }

    public String getDuration() {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(content.getDuration());
        long seconds = TimeUnit.MILLISECONDS.toSeconds(content.getDuration());
        return String.valueOf(minutes+":"+seconds);
    }

}
