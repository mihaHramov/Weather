package com.aaa.bbb.ccc.data.model.api.weatherApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain {

    @SerializedName("3h")
    @Expose
    private Double threeHour;

    public Double get3h() {
        return threeHour;
    }

    public void set3h(Double threeHour) {
        this.threeHour = threeHour;
    }
}
