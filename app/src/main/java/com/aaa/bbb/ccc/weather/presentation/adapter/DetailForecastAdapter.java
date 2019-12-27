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

public class DetailForecastAdapter extends RecyclerView.Adapter<DetailForecastAdapter.ViewHolder> {
    private List<ShortForecast> items = new ArrayList<>();

    public void setItems(List<ShortForecast> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_weather_forecast_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mDate;
        private TextView minTemperature;
        private TextView maxTemperature;
        private TextView wind;
        private TextView pressure;
        private TextView humidity;
        private TextView snow;
        private TextView clouds;
        private ImageView icon;
        private TextView weatherType;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            mDate = itemView.findViewById(R.id.date);
            minTemperature = itemView.findViewById(R.id.min_temperature);
            maxTemperature = itemView.findViewById(R.id.max_temperature);
            wind = itemView.findViewById(R.id.wind);
            pressure = itemView.findViewById(R.id.pressure);
            humidity = itemView.findViewById(R.id.humidity);
            snow = itemView.findViewById(R.id.snow);
            clouds = itemView.findViewById(R.id.clouds);
            weatherType = itemView.findViewById(R.id.weather_type);
        }

        void bind(ShortForecast forecast) {
            mDate.setText(forecast.getDate());
            minTemperature.setText(forecast.getTemperature().getMax());
            maxTemperature.setText(forecast.getTemperature().getMax());
            pressure.setText(forecast.getPressure());
            humidity.setText(forecast.getHumidity());
            snow.setText(forecast.getPrecipitation());
            clouds.setText(forecast.getClouds());
            Picasso.get().load(forecast.getIcon()).into(icon);
            weatherType.setText(forecast.getDescription());
            wind.setText(forecast.getDescription());
        }
    }
}
