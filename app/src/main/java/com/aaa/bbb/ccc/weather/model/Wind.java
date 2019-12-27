package com.aaa.bbb.ccc.weather.model;

import java.io.Serializable;

public class Wind implements Serializable {

    private String mSpeed;
    private String mName;

    public String getName() {
        return mName;
    }

    public String getSpeed() {
        return mSpeed;
    }

    public void setSpeed(String speed) {
        mSpeed = speed;
    }

    public void setName(String name) {
        mName = name;
    }
}
