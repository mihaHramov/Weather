package com.aaa.bbb.ccc.weather.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<T> items = new ArrayList<>();
    private OnItemClickLister lister;

    public interface Binder<T> {
        void bind(T data);
    }

    public void setLister(OnItemClickLister lister) {
        this.lister = lister;
    }

    public interface OnItemClickLister {
        void click(Integer id);
    }

    public abstract int getLayoutId();

    @Override
    public int getItemCount() {
        return items.size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Binder) {
            ((Binder) holder).bind(items.get(position));
        }
        if (lister != null) {
            holder.itemView.setOnClickListener(v -> lister.click(holder.getAdapterPosition()));
        }
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return getViewHolder(v, viewType);
    }

    public abstract RecyclerView.ViewHolder getViewHolder(View parent, int viewType);
}
