package com.example.vinaigrette;

import com.dpcsa.compon.base.DeclareScreens;
import com.dpcsa.compon.interfaces_classes.Menu;
import com.dpcsa.compon.interfaces_classes.Multiply;

public class MyDeclareScreens extends DeclareScreens {

    public final static String TEST = "TEST",
            SPLASH = "splash", INTRO = "INTRO", AUTH = "auth", MAIN = "main",
            LOGIN = "LOGIN", REGISTRATION = "REGISTRATION", DRAWER = "DRAWER", CATALOG = "CATALOG",
            PRODUCT_LIST = "PRODUCT_LIST", BARCODE = "BARCODE", FILTER = "FILTER",
            PRODUCT_DESCRIPT = "PRODUCT_DESCRIPT", ADD_PRODUCT = "ADD_PRODUCT",
            DESCRIPT = "DESCRIPT", CHARACTERISTIC = "CHARACTERISTIC",
            SETTINGS = "SETTINGS";

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
                        navigator(start(0, PRODUCT_LIST, PS.RECORD)));

        activity(PRODUCT_LIST, R.layout.activity_product_list).animate(AS.RL)
                .navigator(handler(R.id.back, VH.BACK),
                        handler(R.id.barcode, BARCODE, after(handler(R.id.recycler, VH.UPDATE_DATA,
                                model(Api.PRODUCT_BARCODE, "barcode_scanner")))))
                .componentRecognizeVoice(R.id.microphone, R.id.search)
                .component(TC.RECYCLER, model(Api.PRODUCT_LIST, "expandedLevel,catalog_id"),
                        view(R.id.recycler, R.layout.item_product_list)
                                .visibilityManager(visibility(R.id.bonus_i, "bonus"),
                                        visibility(R.id.gift_i, "gift"),
                                        visibility(R.id.newT, "new_product")),
                        navigator(start(0, PRODUCT_DESCRIPT, PS.RECORD), handler(R.id.add, ADD_PRODUCT, PS.RECORD)))
                .componentSearch(R.id.search, model(Api.PRODUCT_SEARCH, "product_name"),
                        view(R.id.recycler), null, false);

        activity(BARCODE, R.layout.activity_barcode).animate(AS.RL)
                .navigator(handler(R.id.back, VH.BACK),
                        handler(R.id.apply, VH.RESULT_PARAM, "barcode_scanner"))
                .componentBarcode(R.id.barcode_scanner, R.id.result_scan, R.id.repeat);

        activity(PRODUCT_DESCRIPT, R.layout.activity_product_descript, "%1$s", "catalog_name").animate(AS.RL)
                .navigator(handler(R.id.back, VH.BACK))
                .component(TC.PANEL, model(ARGUMENTS),
                        view(R.id.name_panel))
                .component(TC.PAGER_F, view(R.id.pager,
                        new String[] {DESCRIPT, CHARACTERISTIC})
                        .setTab(R.id.tabs, R.array.descript_tab_name));

        fragment(DESCRIPT, R.layout.fragment_descript)
                .component(TC.PANEL, model(Api.PRODUCT_ID, "product_id"),
                        view(R.id.panel).visibilityManager(visibility(R.id.bonus, "bonus")),
                        navigator(handler(R.id.add, ADD_PRODUCT, PS.RECORD)))
                .component(TC.RECYCLER, model(Api.ANALOG_ID_PRODUCT,"product_id"),
                        view(R.id.recycler, R.layout.item_product_list).noDataView(R.id.not_analog),
                        navigator(start(0, PRODUCT_DESCRIPT, PS.RECORD),
                                handler(R.id.add, ADD_PRODUCT, PS.RECORD), handler(0, VH.BACK))) ;

        fragment(CHARACTERISTIC, R.layout.fragment_characteristic)
                .component(TC.RECYCLER, model(Api.CHARACT_ID_PRODUCT, "product_id"),
                        view(R.id.recycler, "2", new int[] {R.layout.item_property, R.layout.item_property_1}));

        activity(ADD_PRODUCT, R.layout.activity_add_product, WorkAddProduct.class).animate(AS.RL)
                .plusMinus(R.id.count, R.id.plus, R.id.minus, null, new Multiply(R.id.amount, "price"))
                .component(TC.PANEL_ENTER, model(ARGUMENTS), view(R.id.panel),
                        navigator(handler(R.id.add, VH.CLICK_SEND,
                                model(POST_DB, SQL.PRODUCT_ORDER, SQL.PRODUCT_ORDER_PARAM),
                                after(show(R.id.inf_add_product, R.id.orderName)), false)))
                .component(TC.RECYCLER, model(GET_DB, SQL.ORDER_LIST), view(R.id.recycler, "status",
                        new int[] {R.layout.item_order_log, R.layout.item_order_log_select}).selected().noDataView(R.id.no_data),
                        navigator(handler(0, VH.SET_PARAM)));


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
