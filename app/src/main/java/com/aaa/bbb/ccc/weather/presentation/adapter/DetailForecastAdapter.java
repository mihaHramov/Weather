package com.aaa.bbb.ccc.weather.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aaa.bbb.ccc.weather.R;
import com.aaa.bbb.ccc.weather.domain.model.ShortForecast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
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

        private String fromDoubleToString(Double val){
            return val.toString();
        }
        void bind(ShortForecast forecast) {
            int hour = forecast.getDate().get(Calendar.HOUR);
            int minute = forecast.getDate().get(Calendar.MINUTE);
            String date  = hour +":"+ minute;
            mDate.setText(date);
            minTemperature.setText(fromDoubleToString(forecast.getTemperature().getMin()));
            maxTemperature.setText(fromDoubleToString(forecast.getTemperature().getMax()));
            pressure.setText(forecast.getPressure().toString());
            humidity.setText(forecast.getHumidity().toString());
            snow.setText(fromDoubleToString(forecast.getSnow()));
            clouds.setText(forecast.getClouds());
            Picasso.get().load(forecast.getWeatherType().getIcon()).into(icon);
            weatherType.setText(forecast.getWeatherType().getDescription());
            String w =  forecast.getWind().getWindSpeed().toString();
            w= w.concat("(м/c)");
            switch (forecast.getWind().getWindType()){
                case E:
                   w =  w.concat("Восток");
                    break;
                case N:
                    w =  w.concat("Север");
                    break;
                case S:
                    w =  w.concat("Юг");
                    break;
                case W:
                    w =  w.concat("Запад");
                    break;
                case NE:
                    w =  w.concat("Северо-Восток");
                    break;
                case NW:
                    w =  w.concat("Северо-Запад");
                    break;
                case SE:
                    w =  w.concat("Юго-Восток");
                    break;
                case SW:
                    w =  w.concat("Юго-Запад");
                    break;
            }
            wind.setText(w);
        }
    }
}
