package com.example.vinaigrette;

import com.dpcsa.compon.base.BaseActivity;
import com.dpcsa.compon.base.BaseComponent;
import com.dpcsa.compon.interfaces_classes.DataFieldGet;
import com.dpcsa.compon.interfaces_classes.Menu;
import com.dpcsa.compon.json_simple.Field;

public class GetData extends DataFieldGet {
    BaseActivity activity;
    @Override
    public Field getField(BaseComponent mComponent) {
        activity = mComponent.activity;
        if (mComponent.multiComponent.nameComponent != null) {
            switch (mComponent.multiComponent.nameComponent) {
                case MyDeclareScreens.DRAWER: return setMenu();
            }
        }
        return null;
    }

    private Field setMenu() {
        Menu menu = new Menu()
            .item(R.drawable.list, R.string.m_catalog, MyDeclareScreens.CATALOG, true)
            .divider()
            .item(R.drawable.settings, R.string.m_settings, MyDeclareScreens.SETTINGS);
        return menu;
    }

}
