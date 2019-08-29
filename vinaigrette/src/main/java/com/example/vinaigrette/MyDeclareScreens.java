package com.example.vinaigrette;

import com.dpcsa.compon.base.DeclareScreens;
import com.dpcsa.compon.interfaces_classes.Menu;
import com.dpcsa.compon.interfaces_classes.Multiply;
import com.dpcsa.compon.param.ParamComponent;

public class MyDeclareScreens extends DeclareScreens {

    public final static String
            SEQUENCE = "sequence", INTRO = "INTRO", AUTH = "auth", MAIN = "main",
            LOGIN = "LOGIN", REGISTRATION = "REGISTRATION", DRAWER = "DRAWER", CATALOG = "CATALOG",
            PRODUCT_LIST = "PRODUCT_LIST", BARCODE = "BARCODE",
            PRODUCT_DESCRIPT = "PRODUCT_DESCRIPT", ADD_PRODUCT = "ADD_PRODUCT",
            DESCRIPT = "DESCRIPT", CHARACTERISTIC = "CHARACTERISTIC", ORDER_LIST = "ORDER_LIST",
            ORDER_PRODUCT = "ORDER_PRODUCT", PROFILE = "PROFILE";

    @Override
    public void declare() {
        activity(SEQUENCE, R.layout.activity_sequence)
                .componentSequence(INTRO, AUTH, MAIN);

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
                                after(setToken(), setProfile("profile"), handler(0, VH.NEXT_SCREEN_SEQUENCE)), true, R.id.login, R.id.password),
                                start(R.id.register, REGISTRATION),
                                handler(R.id.enter_skip, VH.NEXT_SCREEN_SEQUENCE)), 0);

        fragment(REGISTRATION, R.layout.fragment_registration)
                .navigator(back(R.id.back))
                .componentPhoto(R.id.cli, new int[] {R.id.blur, R.id.photo}, R.string.source_photo)
                .component(TC.PANEL_ENTER, null,
                        view(R.id.panel),
                        navigator(handler(R.id.done, VH.CLICK_SEND, model(POST, Api.REGISTER,
                                "login,password,surname,name,second_name,phone,photo,email"),
                                after(setToken(), setProfile("profile"), handler(0, VH.NEXT_SCREEN_SEQUENCE)),
                                true, R.id.login, R.id.password)), 0) ;

        activity(MAIN, R.layout.activity_main)
                .navigator(finishDialog(R.string.attention, R.string.finishOk))
                .drawer(R.id.drawer, R.id.content_frame, R.id.left_drawer, null, DRAWER);

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
                .navigator(back(R.id.back),
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
                .navigator(back(R.id.back),
                        handler(R.id.apply, VH.RESULT_PARAM, "barcode_scanner"))
                .componentBarcode(R.id.barcode_scanner, R.id.result_scan, R.id.repeat);

        activity(PRODUCT_DESCRIPT, R.layout.activity_product_descript, "%1$s", "catalog_name").animate(AS.RL)
                .navigator(back(R.id.back))
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
                .navigator(back(R.id.back), show(R.id.create_new, R.id.new_order), hide(R.id.cancel, R.id.new_order))
                .plusMinus(R.id.count, R.id.plus, R.id.minus, null, new Multiply(R.id.amount, "price"))
                .component(TC.PANEL_ENTER, model(ARGUMENTS), view(R.id.panel),
                        navigator(handler(R.id.add, VH.CLICK_SEND,
                                model(POST_DB, SQL.PRODUCT_ORDER, SQL.PRODUCT_ORDER_PARAM),
                                after(assignValue(R.id.inf_add_product), show(R.id.inf_add_product)), false)))
                .component(TC.PANEL_ENTER, null, view(R.id.new_order),
                        navigator(handler(R.id.create_order, VH.CLICK_SEND,
                                model(POST_DB, SQL.ORDER_TAB, "order_name"),
                                after(hide(0, R.id.new_order), actual(0, R.id.recycler)), false, R.id.order_name)))
                .component(TC.RECYCLER, model(GET_DB, SQL.ORDER_LIST), view(R.id.recycler, "select",
                        new int[] {R.layout.item_order_log, R.layout.item_order_log_select}).selected().noDataView(R.id.no_data),
                        navigator(handler(0, VH.SET_PARAM)))
                .enabled(R.id.add, R.id.recycler);

        fragment(ORDER_LIST, R.layout.fragment_order)
                .navigator(handler(R.id.back, VH.OPEN_DRAWER))
                .component(TC.RECYCLER,
                        model(GET_DB, SQL.ORDER_LIST),
                        view(R.id.recycler, R.layout.item_order_list).noDataView(R.id.no_data),
                        navigator(start(ORDER_PRODUCT, PS.RECORD, after(actual(0, R.id.recycler)))));

        activity(ORDER_PRODUCT, R.layout.activity_order_product, "%1$s", "orderName").animate(AS.RL)
                .navigator(handler(R.id.back, VH.BACK))
                .plusMinus(R.id.count, R.id.plus, R.id.minus, navigator(handler(model(UPDATE_DB, SQL.PRODUCT_ORDER,
                        "count", SQL.PRODUCT_ORDER_WHERE, "product_id"))),
                        new Multiply(R.id.amount, "price", "amount"))
                .component(TC.RECYCLER, model(GET_DB, SQL.PRODUCT_IN_ORDER, "order_name").row("row"),
                        view(R.id.list_product, R.layout.item_order_product),
                        navigator(handler(R.id.del, model(DEL_DB, SQL.PRODUCT_ORDER, SQL.PRODUCT_ORDER_WHERE, "product_id")),
                                handler(R.id.del, VH.ACTUAL)))
                .componentTotal(R.id.total, R.id.list_product, R.id.count, null, "amount", "count")
                .component(ParamComponent.TC.PANEL_ENTER, null, view(R.id.panel),
                        navigator(handler(R.id.send, VH.CLICK_SEND, model(POST, Api.SEND_ORDER,
                                "order_name,list_product(product_id;count)"),
                                after(handler(model(DEL_DB, SQL.ORDER_TAB, SQL.ORDER_WHERE, "order_name")),
                                        handler(model(DEL_DB, SQL.PRODUCT_ORDER, SQL.ORDER_WHERE, "order_name")),
                                        handler(0, VH.RESULT_RECORD)))));

        fragment(PROFILE, R.layout.fragment_profile)
                .navigator(handler(R.id.back, VH.OPEN_DRAWER))
                .componentPhoto(R.id.cli, new int[] {R.id.blur, R.id.photo}, R.string.source_photo)
                .component(TC.PANEL_ENTER, model(Api.PROFILE),
                        view(R.id.panel),
                        navigator(handler(R.id.done, VH.CLICK_SEND, model(POST, Api.EDIT_PROF,
                                "surname,name,second_name,phone,photo,email"),
                                after(setProfile("")))));
    }

    Menu menu = new Menu()
            .item(R.drawable.list, R.string.m_catalog, CATALOG, true)
            .item(R.drawable.shoppingcard, R.string.m_orders, ORDER_LIST)
            .divider()
            .item(R.drawable.icon_profile, R.string.profile, PROFILE).enabled(1);
}
