package com.dpcsa.compon.json_simple;

import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.dpcsa.compon.interfaces_classes.IBase;

public class FieldBroadcaster extends Field{
    public String SIMPLE_ViewId = "SIMPLE_ViewId";

    public FieldBroadcaster(String name, int type, Object value) {
        super(name, type, value);
    }

    @Override
    public void setValue(Object value, int viewId, IBase iBase) {
        this.value = value;
        Intent intentBroad = new Intent(name);
        intentBroad.putExtra(SIMPLE_ViewId, viewId);
        LocalBroadcastManager.getInstance(iBase.getBaseActivity()).sendBroadcast(intentBroad);
    }
}
