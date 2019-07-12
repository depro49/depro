package com.dpcsa.compon.param;

import com.dpcsa.compon.base.BaseComponent;
import com.dpcsa.compon.interfaces_classes.Filters;
import com.dpcsa.compon.interfaces_classes.MoreWork;
import com.dpcsa.compon.interfaces_classes.Multiply;
import com.dpcsa.compon.interfaces_classes.Navigator;

public class ParamComponent <T>{
    public static enum TC {PANEL, PANEL_ENTER, PANEL_MULTI, SPINNER, DRAWER, PLUS_MINUS,
        RECYCLER, RECYCLER_HORIZONTAL, RECYCLER_GRID, RECYCLER_EXPANDED, RECYCLER_STICKY, TOOL,
        MENU, MENU_BOTTOM, CONTAINER, MAP, SPLASH, BUTTON, PHONE, TOTAL, SEARCH, PHOTO, RECOGNIZE_VOICE,
        STATIC_LIST, MODEL, PAGER_V, PAGER_F, INTRO, POP_UP, DATE_DIAPASON, BARCODE, LOAD_DB,
        ENABLED, YOU_TUBE};
    public ParamComponent () {
        additionalWork = null;
    }
    public String name;
    public TC type;
    public int fragmentsContainerId;
    public String startScreen;
    public int eventComponent;
    public MoreWork moreWork;
    public Class<T> additionalWork;
    public BaseComponent baseComponent;
    public ParamModel paramModel;
    public ParamView paramView;
    public ParamMap paramMap;
    public Navigator navigator;
    public String intro, auth, main, paramForPathFoto;
    public int[] mustValid;
    public int[] showStackEmpty;
    public int[] showStackNoEmpty;
    public int viewSearchId, titleId;
    public boolean startActual = true;
    public boolean hide = false;
    public String nameReceiver;
    public Multiply[] multiplies;
    public Filters filters;
}
