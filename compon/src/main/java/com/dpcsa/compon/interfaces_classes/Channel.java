package com.dpcsa.compon.interfaces_classes;

public class Channel {
    public int id;
    public String name;
    public String description;
    public Notice[] notices;
    public boolean enableLights = false, enableVibration = false;
    public int lightColor, drawableId;
    public long[] vibrationPattern;

    public Channel(int id, String name, String description, Notice[] notices) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.notices = notices;
    }


    public Channel enableLights(boolean lights) {
        enableLights = lights;
        return this;
    }

    public Channel enableVibration(boolean vibration) {
        enableVibration = vibration;
        return this;
    }

    public Channel vibrationPattern(long[] vibration) {
        enableVibration = vibration != null && vibration.length > 0;
        vibrationPattern = vibration;
        return this;
    }

    public Channel lightColor(int color) {
        lightColor = color;
        return this;
    }

    public Channel icon(int drawableId) {
        this.drawableId = drawableId;
        return this;
    }

}
