package com.example.vinaigrette;

import android.util.Log;
import android.view.View;

import com.dpcsa.compon.base.BaseComponent;
import com.dpcsa.compon.components.PanelEnterComponent;
import com.dpcsa.compon.interfaces_classes.MoreWork;
import com.dpcsa.compon.json_simple.Field;

public class WorkAddProduct extends MoreWork {

    PanelEnterComponent panel;

    @Override
    public void changeValue(int viewId,  Field field, BaseComponent baseComponent) {
        panel = (PanelEnterComponent) baseComponent;
        Log.d("QWERT","WorkAddProduct panel="+panel);
        if (panel.recordComponent != null) {
            View v = parentLayout.findViewById(R.id.more_residue);
            if (panel.recordComponent.getInt("quantity") < (int) field.value) {
                v.setVisibility(View.VISIBLE);
            } else {
                v.setVisibility(View.GONE);
            }
        }
    }
}