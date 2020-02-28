package com.aaa.bbb.ccc.model;

public class WindTypeConverter {
    private WindTypeConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static WindType convert(int deg) {
        WindType w;
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
