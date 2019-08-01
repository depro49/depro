package com.dpcsa.compon.custom_components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

//import com.makeramen.roundedimageview.RoundedImageView;

import com.dpcsa.compon.R;

public class ComponImageView extends android.support.v7.widget.AppCompatImageView {
    private Context context;
    private int placeholder, blur;
    private boolean oval;
    private String pathImg;

    public ComponImageView(Context context) {
        super(context);
        this.context = context;
    }

    public ComponImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setAttrs(attrs);
    }

    public ComponImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        setAttrs(attrs);
    }

    private void setAttrs(AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Simple);
        try {
            placeholder = a.getResourceId(R.styleable.Simple_placeholder, 0);
            blur = a.getInt(R.styleable.Simple_blur, -1);
            oval = a.getBoolean(R.styleable.Simple_oval, false);
        } finally {
            a.recycle();
        }

    }

    public void setPathImg(String path) {
        pathImg = path;
    }

    public String getPathImg() {
        return pathImg;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public int getBlur() {
        return blur;
    }

    public boolean isOval() {
        return oval;
    }
}
