package com.dpcsa.compon.interfaces_classes;

//import Screen;
import com.dpcsa.compon.json_simple.Field;
import com.dpcsa.compon.json_simple.ListRecords;
import com.dpcsa.compon.json_simple.Record;

public class Menu extends Field {

    public ListRecords menuList;
    public int menuStart;
    public enum TYPE {NORMAL, SELECT, DIVIDER, ENABLED};
    public int colorNorm, colorSelect, colorEnabl;

    public Menu() {
        this(0, 0, 0);
    }

    public Menu(int colorNorm, int colorSelect, int colorEnabl) {
        this.colorNorm = colorNorm;
        this.colorSelect = colorSelect;
        this.colorEnabl = colorEnabl;
        name = "menu";
        type = TYPE_LIST_FIELD;
        menuList = new ListRecords();
        value = menuList;
        menuStart = -1;
    }


    public Menu item(int icon, int title, String nameFragment) {
        return item(icon, title, nameFragment, false);
    }

    public Menu divider(){
        Record item = new Record();
        item.add(new Field("select", Field.TYPE_INTEGER, 2));
        menuList.add(item);
        return this;
    }

    public Menu item(int icon, int title, String nameFragment, TYPE type) {
        Record item = new Record();
        item.add(new Field("icon", Field.TYPE_INTEGER, icon));
        item.add(new Field("nameId", Field.TYPE_INTEGER, title));
        item.add(new Field("nameFunc", Field.TYPE_STRING, nameFragment));
//        item.add(new Field("badge", Field.TYPE_INTEGER, badge));
        item.add(new Field("select", Field.TYPE_INTEGER, type.ordinal()));
        menuList.add(item);
        return this;
    }

    public Menu item(int icon, int title, String nameFragment, boolean start) {
        Record item = new Record();
        item.add(new Field("icon", Field.TYPE_INTEGER, icon));
        item.add(new Field("nameId", Field.TYPE_INTEGER, title));
        item.add(new Field("nameFunc", Field.TYPE_STRING, nameFragment));
//        item.add(new Field("badge", Field.TYPE_INTEGER, badge));
        if (start && menuStart < 0) {
            item.add(new Field("select", Field.TYPE_INTEGER, 1));
            menuStart = menuList.size();
        } else {
            item.add(new Field("select", Field.TYPE_INTEGER, 0));
        }
        menuList.add(item);
        return this;
    }

    public Menu enabled(int enable) {
        int i = menuList.size() - 1;
        if (i > -1 && menuList.get(i).getValue("select") == null) {
            menuList.get(i).add(new Field("enabled", Field.TYPE_INTEGER, enable));
        }
        return this;
    }

    public Menu badge(String value) {
        int i = menuList.size() - 1;
        if (i > -1) {
            menuList.get(i).add(new Field("badge", Field.TYPE_STRING, value));
        }
        return this;
    }
}
