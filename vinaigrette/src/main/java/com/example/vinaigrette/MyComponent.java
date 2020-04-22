package com.example.vinaigrette;

import com.dpcsa.compon.base.BaseComponent;
import com.dpcsa.compon.json_simple.Field;
import com.dpcsa.compon.json_simple.Record;

public class MyComponent extends BaseComponent {
    @Override
    public void initView() {
        componentTag = "MY_COMPONENT_";
        viewComponent = null;
        if (paramMV.paramView != null || paramMV.paramView.viewId != 0) {
            viewComponent = parentLayout.findViewById(paramMV.paramView.viewId);
        }
        if (viewComponent == null) {
            iBase.log("Не найдена View для MyComponent в " + multiComponent.nameComponent);
            return;
        }
    }

    @Override
    public void changeData(Field field) {
        if (field != null) {
            workWithRecordsAndViews.RecordToView((Record) field.value, viewComponent, this, clickView);
        }
    }
}
