package com.aaa.bbb.ccc.weather.presentation.vh;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aaa.bbb.ccc.model.ShortForecast;
import com.aaa.bbb.ccc.utils.DateServices;
import com.aaa.bbb.ccc.weather.R;
import com.aaa.bbb.ccc.weather.presentation.adapter.BaseRecyclerViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class ShortForecastViewHolder extends RecyclerView.ViewHolder implements BaseRecyclerViewAdapter.Binder<ShortForecast> {
    private ImageView mIcon;
    private TextView mDate;
    private TextView mWeatherType;
    private TextView mMaxTemperature;
    private TextView mMinTemperature;

    public ShortForecastViewHolder(@NonNull View view) {
        super(view);
        mIcon = view.findViewById(R.id.icon);
        mDate = view.findViewById(R.id.date);
        mWeatherType = view.findViewById(R.id.weatherType);
        mMaxTemperature = view.findViewById(R.id.max_temperature);
        mMinTemperature = view.findViewById(R.id.min_temperature);
    }

    @Override
    public void bind(ShortForecast dailyForecast) {
        Picasso.get().load(dailyForecast.getWeatherType().getIcon()).into(mIcon);
        mWeatherType.setText(dailyForecast.getWeatherType().getDescription());
        String max = Double.toString(dailyForecast.getTemperature().getMax());
        String min = Double.toString(dailyForecast.getTemperature().getMin());
        mMaxTemperature.setText(max);
        mMinTemperature.setText(min);
        mDate.setText(DateServices.getDateByInteger(dailyForecast.getDate()).get(Calendar.DAY_OF_WEEK));
    }
}
