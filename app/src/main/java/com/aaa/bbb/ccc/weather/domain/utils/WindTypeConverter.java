package com.aaa.bbb.ccc.weather.domain.utils;

import com.aaa.bbb.ccc.weather.domain.model.WindType;

public class WindTypeConverter {

    public static WindType convert(com.aaa.bbb.ccc.weather.data.model.weatherApi.Wind wind) {
        WindType w;
        Integer deg = wind.getDeg();
        if (deg == 360 || deg == 0) {
            w = WindType.N;
        } else if (deg > 0 && deg < 90) {
            w = WindType.NE;
        } else if (deg == 90) {
            w = WindType.E;
        } else if (deg > 90 && deg < 160) {
            w = WindType.SE;
        } else if (deg == 160) {
            w = WindType.S;
        } else if (deg > 160 && deg < 270) {
            w = WindType.SW;
        } else if (deg == 270) {
            w = WindType.W;
        } else {
            w = WindType.NW;
        }
        return w;
    }
}
