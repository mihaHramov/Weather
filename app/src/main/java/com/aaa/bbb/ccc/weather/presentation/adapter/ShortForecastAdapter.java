package com.aaa.bbb.ccc.weather.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aaa.bbb.ccc.weather.R;
import com.aaa.bbb.ccc.weather.model.ShortForecast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShortForecastAdapter extends RecyclerView.Adapter<ShortForecastAdapter.ViewHolder> {
    public interface OnItemClickLister {
        void click(Integer id);
    }

    private OnItemClickLister onItemClickLister;
    private List<ShortForecast> items = new ArrayList<>();

    public void setOnItemClickLister(OnItemClickLister onItemClickLister) {
        this.onItemClickLister = onItemClickLister;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ViewHolder(v);
    }

    public void setItems(List<ShortForecast> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
        holder.itemView.setOnClickListener(v -> onItemClickLister.click(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIcon;
        private TextView mDate;
        private TextView mWeatherType;
        private TextView mMaxTemperature;
        private TextView mMinTemperature;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            mIcon = view.findViewById(R.id.icon);
            mDate = view.findViewById(R.id.date);
            mWeatherType = view.findViewById(R.id.weatherType);
            mMaxTemperature = view.findViewById(R.id.max_temperature);
            mMinTemperature = view.findViewById(R.id.min_temperature);

        }

        void bind(ShortForecast dailyForecast) {
            Picasso.get().load(dailyForecast.getIcon()).into(mIcon);
            mWeatherType.setText(dailyForecast.getDescription());
            mMaxTemperature.setText(dailyForecast.getTemperature().getMax());
            mMinTemperature.setText(dailyForecast.getTemperature().getMin());
            mDate.setText(dailyForecast.getDate());
        }
    }
}
