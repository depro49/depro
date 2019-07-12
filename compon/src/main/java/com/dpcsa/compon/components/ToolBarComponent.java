package com.dpcsa.compon.components;

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

    }

    @Override
    public void changeData(Field field) {

    }
}
