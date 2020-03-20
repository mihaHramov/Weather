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
import java.util.Locale;

public class ShortForecastViewHolder extends RecyclerView.ViewHolder implements BaseRecyclerViewAdapter.Binder<ShortForecast> {
    private ImageView mIcon;
    private TextView mDay;
    private TextView mTime;
    private TextView mMaxTemperature;
    private TextView mMinTemperature;

    public ShortForecastViewHolder(@NonNull View view) {
        super(view);
        mIcon = view.findViewById(R.id.icon);
        mDay = view.findViewById(R.id.day);
        mTime = view.findViewById(R.id.time);
        mMaxTemperature = view.findViewById(R.id.max_temperature);
        mMinTemperature = view.findViewById(R.id.min_temperature);
    }

    @Override
    public void bind(ShortForecast dailyForecast) {
        Calendar calendar = DateServices.getDateByInteger(dailyForecast.getDate());
        Picasso.get().load(dailyForecast.getWeatherType().getIcon()).into(mIcon);
        String time = calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE);
        mTime.setText(time);
        String max = Double.toString(dailyForecast.getTemperature().getMax());
        String min = Double.toString(dailyForecast.getTemperature().getMin());
        mMaxTemperature.setText(max);
        mMinTemperature.setText(min);
        String day = DateServices.getDateByInteger(dailyForecast.getDate()).getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
        mDay.setText(day);
    }
}
