package com.aaa.bbb.ccc.weather.presentation.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.aaa.bbb.ccc.model.ShortForecast;
import com.aaa.bbb.ccc.weather.R;
import com.aaa.bbb.ccc.weather.presentation.vh.DetailForecastViewHolder;
import com.aaa.bbb.ccc.weather.presentation.vh.ShortForecastViewHolder;

public class ForecastAdapter extends BaseRecyclerViewAdapter<ShortForecast> {
    private Integer layoutId;

    public void setLayoutId(Integer layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
        if (viewType == R.layout.details_weather_forecast_item) {
            return new DetailForecastViewHolder(view);
        }
        return new ShortForecastViewHolder(view);
    }
}
