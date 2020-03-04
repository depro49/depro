package com.example.vinaigrette;

import android.view.View;
import android.widget.TextView;
import com.dpcsa.compon.base.BaseComponent;
import com.dpcsa.compon.json_simple.Field;

public class MyComponent extends BaseComponent {
    @Override
    public void initView() {
        componentTag = "MY_COMPONENT_";
        View v = null;
        if (paramMV.paramView != null || paramMV.paramView.viewId != 0) {
            v = parentLayout.findViewById(paramMV.paramView.viewId);
        }
        if (v != null && v instanceof TextView) {
            ((TextView) v).setText("WOOO DePro");
        } else {
            iBase.log("Не найдена View для MyComponent в " + multiComponent.nameComponent);
            return;
        }
    }

    @Override
    public void changeData(Field field) {

    }
}
