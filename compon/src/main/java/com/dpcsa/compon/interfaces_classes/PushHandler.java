package com.dpcsa.compon.interfaces_classes;

public class PushHandler {
    public int viewId;
    public enum TYPE {DRAWER, SELECT_MENU, SELECT_PAGER};
    public TYPE type;
    public String[] pushName;
    public String screen, pushType;

    public PushHandler(int viewId, TYPE type, String[] pushName) {
        this.viewId = viewId;
        this.type = type;
        this.pushName = pushName;
    }

    public PushHandler(int viewId, TYPE type, String pushType, String screen) {
        this.viewId = viewId;
        this.type = type;
        this.pushType = pushType;
        this.screen = screen;
    }
}
