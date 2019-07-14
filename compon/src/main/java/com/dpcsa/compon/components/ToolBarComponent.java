package com.dpcsa.compon.components;

import android.view.View;
import android.widget.RelativeLayout;

import com.dpcsa.compon.base.BaseComponent;
import com.dpcsa.compon.base.Screen;
import com.dpcsa.compon.interfaces_classes.IBase;
import com.dpcsa.compon.json_simple.Field;
import com.dpcsa.compon.param.ParamComponent;

public class ToolBarComponent extends BaseComponent {
    RelativeLayout tool;
//    stack exists
    public ToolBarComponent(IBase iBase, ParamComponent paramMV, Screen multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        tool = (RelativeLayout) parentLayout.findViewById(paramMV.paramView.viewId);
        if (tool == null) {
            iBase.log("Не найден ToolBar (RelativeLayout) в " + multiComponent.nameComponent);
            return;
        } else {
            showView(true);
        }

    }

    @Override
    public void changeData(Field field) {}

    public void showView(boolean emptyStack) {
        if (emptyStack) {
            for (int i : paramMV.showStackEmpty) {
                tool.findViewById(i).setVisibility(View.VISIBLE);
            }
            for (int i : paramMV.showStackNoEmpty) {
                tool.findViewById(i).setVisibility(View.GONE);
            }
        } else {
            for (int i : paramMV.showStackEmpty) {
                tool.findViewById(i).setVisibility(View.GONE);
            }
            for (int i : paramMV.showStackNoEmpty) {
                tool.findViewById(i).setVisibility(View.VISIBLE);
            }
        }
    }
}
