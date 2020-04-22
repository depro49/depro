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
    private boolean callListener;

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
        callListener = true;
        setOnCheckedChangeListener(listener);
    }

    OnCheckedChangeListener listener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (checListener != null) {
                if (callListener) {
                    checListener.onCheckedChanged(buttonView, isChecked);
                }
            }
        }
    };

    @Override
    public void setOn(boolean checked) {
        setChecked(checked);
    }

//  меняет статус без вызова листенера
    @Override
    public void setOnStatus(boolean checked) {
        callListener = false;
        setChecked(checked);
        callListener = true;
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
        callListener = false;
        toggle();
        callListener = true;
    }

    @Override
    public void setOnChangeListener(@Nullable OnCheckedChangeListener listener) {
        checListener = listener;
    }
}
