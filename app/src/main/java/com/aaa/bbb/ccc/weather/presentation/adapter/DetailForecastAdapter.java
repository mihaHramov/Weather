package com.aaa.bbb.ccc.weather.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aaa.bbb.ccc.weather.R;
import com.aaa.bbb.ccc.model.ShortForecast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetailForecastAdapter extends RecyclerView.Adapter<DetailForecastAdapter.DetailForecastViewHolder> {
    private List<ShortForecast> items = new ArrayList<>();

    public void setItems(List<ShortForecast> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DetailForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_weather_forecast_item, parent, false);
        return new DetailForecastViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailForecastViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class DetailForecastViewHolder extends RecyclerView.ViewHolder {
        private TextView mDate;
        private TextView minTemperature;
        private TextView maxTemperature;
        private TextView wind;
        private TextView pressure;
        private TextView rain;
        private TextView snow;
        private TextView clouds;
        private ImageView icon;
        private TextView weatherType;
        private View snowCell;
        private View rainCell;


        DetailForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            mDate = itemView.findViewById(R.id.date);
            minTemperature = itemView.findViewById(R.id.min_temperature);
            maxTemperature = itemView.findViewById(R.id.max_temperature);
            wind = itemView.findViewById(R.id.wind);
            pressure = itemView.findViewById(R.id.pressure);
            rain = itemView.findViewById(R.id.rain);
            snow = itemView.findViewById(R.id.snow);
            clouds = itemView.findViewById(R.id.clouds);
            weatherType = itemView.findViewById(R.id.weather_type);
            rainCell = itemView.findViewById(R.id.rain_cell);
            snowCell = itemView.findViewById(R.id.snow_cell);
        }

        void bind(ShortForecast forecast) {
            mDate.setText(forecast.getDate().get(Calendar.DAY_OF_WEEK));
            String min = Double.toString(forecast.getTemperature().getMin());
            String max = Double.toString(forecast.getTemperature().getMax());
            minTemperature.setText(min);
            maxTemperature.setText(max);
            pressure.setText(forecast.getPressure());
            showPrecipitation(rain, rainCell, forecast.getRain().toString());
            showPrecipitation(snow, snowCell, forecast.getSnow().toString());
            clouds.setText(forecast.getClouds());
            Picasso.get().load(forecast.getWeatherType().getIcon()).into(icon);
            weatherType.setText(forecast.getWeatherType().getDescription());
            wind.setText(forecast.getWind().toString());
        }

        private void showPrecipitation(TextView textView, View view, String val) {
            if (val != null && !val.isEmpty()) {
                view.setVisibility(View.VISIBLE);
                textView.setText(val);
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }
}
