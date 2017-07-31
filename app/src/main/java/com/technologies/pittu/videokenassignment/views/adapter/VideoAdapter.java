
package com.technologies.pittu.videokenassignment.views.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.technologies.pittu.videokenassignment.R;
import com.technologies.pittu.videokenassignment.databinding.ItemVideoViewBinding;
import com.technologies.pittu.videokenassignment.interfaces.OnItemClickListener;
import com.technologies.pittu.videokenassignment.model.Item;
import com.technologies.pittu.videokenassignment.viewmodel.ItemVideoViewModel;

import java.util.Collections;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoAdapterViewHolder> {

    private List<Item> itemList = Collections.emptyList();
    private OnItemClickListener onItemClickListener;

    public VideoAdapter( OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public VideoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemVideoViewBinding itemVideoViewBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_video_view,
                        parent, false);
        return new VideoAdapterViewHolder(itemVideoViewBinding);
    }

    @Override
    public void onBindViewHolder(VideoAdapterViewHolder holder, int position) {
        holder.bindWord(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setVideoList(List<Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    class VideoAdapterViewHolder extends RecyclerView.ViewHolder {
        ItemVideoViewBinding itemVideoViewBinding;

        VideoAdapterViewHolder(ItemVideoViewBinding itemVideoViewBinding) {
            super(itemVideoViewBinding.itemPeople);
            this.itemVideoViewBinding = itemVideoViewBinding;
        }

        void bindWord(Item item) {
            itemVideoViewBinding.setVideoViewModel(new ItemVideoViewModel(item, onItemClickListener));
        }
    }
}
