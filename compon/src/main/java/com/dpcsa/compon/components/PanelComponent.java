package com.dpcsa.compon.components;

import android.util.Log;
import android.view.View;

import com.dpcsa.compon.base.BaseComponent;
import com.dpcsa.compon.base.Screen;
import com.dpcsa.compon.interfaces_classes.IBase;
import com.dpcsa.compon.json_simple.Field;
import com.dpcsa.compon.json_simple.ListRecords;
import com.dpcsa.compon.json_simple.Record;
import com.dpcsa.compon.param.ParamComponent;

public class PanelComponent extends BaseComponent {

    private View v_splash;
    @Override
    public void initView() {
        viewComponent = parentLayout.findViewById(paramMV.paramView.viewId);
        if (viewComponent == null) {
            iBase.log("Не найдена панель в " + multiComponent.nameComponent);
        } else {
            viewComponent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            int splash = paramMV.paramView.splashScreenViewId;
            if (splash != 0) {
                v_splash = parentLayout.findViewById(splash);
                v_splash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            } else {
                iBase.log("Не найден SplashView в " + multiComponent.nameComponent);
            }
        }

    }

    @Override
    public void changeData(Field field) {
        if (field == null) {
            recordComponent = null;
            showView();
            return;
        }
        if (field.value instanceof ListRecords) {
            ListRecords lr = (ListRecords) field.value;
            if (lr. size() > 0) {
                recordComponent = lr.get(0);
                showView();
//                workWithRecordsAndViews.RecordToView(recordComponent, viewComponent, this, clickView);
            } else {
                iBase.log("Тип данных не Record в " + multiComponent.nameComponent);
            }
        } else {
            if (field.value instanceof Record) {
                recordComponent = (Record) field.value;
                showView();
            } else {
                iBase.log("Тип данных не Record в " + multiComponent.nameComponent);
            }
        }
    }

    private void showView() {
        if (v_splash != null) {
            if (recordComponent != null && recordComponent.size() > 0) {
                workWithRecordsAndViews.RecordToView(recordComponent, viewComponent, this, clickView);
                v_splash.setVisibility(View.GONE);
                viewComponent.setVisibility(View.VISIBLE);
            } else {
                viewComponent.setVisibility(View.GONE);
                v_splash.setVisibility(View.VISIBLE);
            }
        } else {
            if (recordComponent != null && recordComponent.size() > 0) {
                workWithRecordsAndViews.RecordToView(recordComponent, viewComponent, this, clickView);
            }
        }
    }

    public PanelComponent(IBase iBase, ParamComponent paramMV, Screen multiComponent) {
        super(iBase, paramMV, multiComponent);
    }
}
