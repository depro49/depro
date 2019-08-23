package com.dpcsa.compon.json_simple;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.dpcsa.compon.interfaces_classes.IBase;
import com.dpcsa.compon.tools.Constants;

public class FieldBroadcaster extends Field{
    public FieldBroadcaster(String name, int type, Object value) {
        super(name, type, value);
    }

    @Override
    public void setValue(Object value, int viewId, IBase iBase) {
        this.value = value;
        Intent intentBroad = new Intent(name);
        intentBroad.putExtra(Constants.SIMPLE_ViewId, viewId);
        LocalBroadcastManager.getInstance(iBase.getBaseActivity()).sendBroadcast(intentBroad);
    }
}
