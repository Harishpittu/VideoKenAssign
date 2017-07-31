

package com.technologies.pittu.videokenassignment.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.technologies.pittu.videokenassignment.R;
import com.technologies.pittu.videokenassignment.interfaces.OnItemClickListener;
import com.technologies.pittu.videokenassignment.model.Item;


public class ItemVideoViewModel extends BaseObservable {

    private Item item;
    private OnItemClickListener onItemClickListener;

    public ItemVideoViewModel(Item item, OnItemClickListener onItemClickListener) {
        this.item = item;
        this.onItemClickListener = onItemClickListener;
    }

    public void onItemClick(View view) {
        onItemClickListener.onClick(item);
    }

    public String getTitle() {
        return item.getSnippet().getTitle();
    }
    public String getDescription() {
        return item.getSnippet().getDescription();
    }
    public String getImageUrl() {
        return item.getSnippet().getThumbnails().getMedium().getUrl();
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl).placeholder(R.mipmap.ic_launcher).into(imageView);
    }

}
