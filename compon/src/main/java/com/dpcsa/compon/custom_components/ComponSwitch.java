package com.dpcsa.compon.custom_components;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.CompoundButton;

import com.dpcsa.compon.interfaces_classes.ISwitch;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

public class ComponSwitch extends SwitchCompat implements ISwitch {

    private OnCheckedChangeListener checListener;
    private boolean changeStatus;

    public ComponSwitch(Context context) {
        super(context);
    }

    public ComponSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ComponSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        changeStatus = true;
        setOnCheckedChangeListener(listener);
    }

    OnCheckedChangeListener listener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (checListener != null && changeStatus) {
                checListener.onCheckedChanged(buttonView, isChecked);
            } else {
                changeStatus = true;
            }
        }
    };

    @Override
    public void setOn(boolean checked) {
        setChecked(checked);
    }

    @Override
    public void setOnStatus(boolean checked) {
        changeStatus = false;
        setChecked(checked);
    }

    @Override
    public boolean isOn() {
        return false;
    }

    @Override
    public void change() {
        toggle();
    }

    @Override
    public void changeStatus() {
        changeStatus = false;
        toggle();
    }

    @Override
    public void setOnChangeListener(@Nullable OnCheckedChangeListener listener) {
        checListener = listener;
    }
}
