
package com.technologies.pittu.videokenassignment.views.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.technologies.pittu.videokenassignment.R;
import com.technologies.pittu.videokenassignment.databinding.ItemContentBinding;
import com.technologies.pittu.videokenassignment.interfaces.OnItemClickListener;
import com.technologies.pittu.videokenassignment.model.Content;
import com.technologies.pittu.videokenassignment.viewmodel.ItemContentViewModel;

import java.util.Collections;
import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.WordAdapterViewHolder> {

    private List<Content> contentList = Collections.emptyList();
    private OnItemClickListener onItemClickListener;

    public ContentAdapter(List<Content> contentList, OnItemClickListener onItemClickListener) {
        this.contentList = contentList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public WordAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemContentBinding itemContentBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_content,
                        parent, false);
        return new WordAdapterViewHolder(itemContentBinding);
    }

    @Override
    public void onBindViewHolder(WordAdapterViewHolder holder, int position) {
        holder.bindWord(contentList.get(position));
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public void setContentList(List<Content> contentList) {
        this.contentList = contentList;
        notifyDataSetChanged();
    }

    class WordAdapterViewHolder extends RecyclerView.ViewHolder {
        ItemContentBinding itemContentBinding;

        WordAdapterViewHolder(ItemContentBinding itemContentBinding) {
            super(itemContentBinding.itemPeople);
            this.itemContentBinding = itemContentBinding;
        }

        void bindWord(Content content) {
            itemContentBinding.setContentViewModel(new ItemContentViewModel(content, onItemClickListener));
        }
    }
}
