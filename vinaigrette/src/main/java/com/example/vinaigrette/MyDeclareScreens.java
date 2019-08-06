package com.example.vinaigrette;

import com.dpcsa.compon.base.DeclareScreens;
import com.dpcsa.compon.interfaces_classes.Menu;

public class MyDeclareScreens extends DeclareScreens {

    public final static String TEST = "TEST",
            SPLASH = "splash", INTRO = "INTRO", AUTH = "auth", MAIN = "main",
            LOGIN = "LOGIN", REGISTRATION = "REGISTRATION", DRAWER = "DRAWER", CATALOG = "CATALOG",
            PRODUCT_LIST = "PRODUCT_LIST", SETTINGS = "SETTINGS";

    @Override
    public void declare() {
        activity(SPLASH, R.layout.activity_splash)
                .componentSplash(INTRO, AUTH, MAIN);

        activity(INTRO, R.layout.activity_tutorial)
                .componentIntro(model(JSON, getString(R.string.json_tutorial)),
                        R.id.pager, R.layout.item_tutorial, R.id.indicator, R.id.skip, R.id.contin, R.id.proceed);
//                .component(TC.PAGER_V,
//                        model(JSON, getString(R.string.json_tutorial)),
//                        view(R.id.pager, R.layout.item_tutorial)
//                                .visibilityManager(visibility(R.id.contin, "contin"),
//                                        visibility(R.id.proceed, "proceed"))
//                                .setIndicator(R.id.indicator)
//                                .setFurtherView(R.id.further),
//                        navigator(
//                                handler(R.id.skip, VH.NEXT_SCREEN_SPLASH),
//                                handler(R.id.proceed, VH.NEXT_SCREEN_SPLASH),
//                                handler(R.id.contin, VH.PAGER_PLUS)));

        activity(AUTH, R.layout.activity_auth).animate(AS.RL)
                .fragmentsContainer(R.id.content_frame, LOGIN);

        fragment(LOGIN, R.layout.fragment_login)
                .component(TC.PANEL_ENTER, null,
                        view(R.id.panel),
                        navigator(handler(R.id.done, VH.CLICK_SEND, model(POST, Api.LOGIN, "login,password"),
                                after(setToken(), setProfile(), handler(0, VH.NEXT_SCREEN_SPLASH)), true, R.id.login, R.id.password),
                                start(R.id.register, REGISTRATION),
                                handler(R.id.enter_skip, VH.NEXT_SCREEN_SPLASH)), 0);

        fragment(REGISTRATION, R.layout.fragment_registration)
                .navigator(back(R.id.back))
                .componentPhoto(R.id.cli, new int[] {R.id.blur, R.id.photo}, R.string.source_photo)
                .component(TC.PANEL_ENTER, null,
                        view(R.id.panel),
                        navigator(handler(R.id.done, VH.CLICK_SEND, model(POST, Api.REGISTER,
                                "login,password,surname,name,second_name,phone,photo,email"),
                                after(setToken(), setProfile(), handler(0, VH.NEXT_SCREEN_SPLASH)),
                                true, R.id.login, R.id.password)), 0) ;

        activity(MAIN, R.layout.activity_main)
                .drawer(R.id.drawer, R.id.content_frame, R.id.left_drawer, null, DRAWER);

//        fragment(DRAWER, R.layout.fragment_drawer)
//                .menu(model(new GetData()), view(R.id.recycler,
//                        new int[]{R.layout.item_menu, R.layout.item_menu_select,
//                                R.layout.item_menu_divider));

        fragment(DRAWER, R.layout.fragment_drawer)
                .navigator(start(R.id.enter, AUTH), exit(R.id.exit))
                .component(TC.PANEL, model(PROFILE),
                        view(R.id.panel).noDataView(R.id.no_data))
                .menu(model(menu), view(R.id.recycler));

        fragment(CATALOG, R.layout.fragment_catalog)
                .navigator(handler(R.id.back, VH.OPEN_DRAWER))
                .component(TC.RECYCLER, model(Api.CATALOG),
                        view(R.id.recycler, "expandedLevel", new int[]{R.layout.item_catalog_type_1,
                                R.layout.item_catalog_type_2, R.layout.item_catalog_type_3})
                                .expanded(R.id.expand, R.id.expand, model(Api.CATALOG_EX, "catalog_id")),
                        navigator(handler(0, PRODUCT_LIST, PS.RECORD)));

        fragment(SETTINGS, R.layout.fragment_settings)
                .navigator(handler(R.id.back, VH.OPEN_DRAWER));

        fragment(TEST, R.layout.fragment_test)
                .navigator(handler(R.id.back, VH.OPEN_DRAWER));
    }

    Menu menu = new Menu()
            .item(R.drawable.list, R.string.m_catalog, CATALOG, true)
            .divider()
            .item(R.drawable.settings, R.string.m_settings, SETTINGS)
            .item(R.drawable.settings, R.string.m_test, TEST).enabled(1);
}
