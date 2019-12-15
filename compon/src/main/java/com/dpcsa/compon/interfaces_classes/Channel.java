package com.dpcsa.compon.interfaces_classes;

public class Channel<T> {
    public String id;
    public String name;
    public String description;
    public Class<T> screen;
    public Notice[] notices;
    public boolean enableLights = false, enableVibration = false;
    public int lightColor, drawableId;
    public long[] vibrationPattern;

    public Channel(String id, String name, String description, Class<T> screen, Notice[] notices) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.screen = screen;
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
