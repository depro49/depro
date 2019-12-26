package com.example.vinaigrette;

import com.dpcsa.compon.base.DeclareScreens;
import com.dpcsa.compon.interfaces_classes.Menu;
import com.dpcsa.compon.interfaces_classes.Multiply;

import static android.app.NotificationManager.IMPORTANCE_HIGH;

public class MyDeclareScreens extends DeclareScreens {

    public final static String
            SEQUENCE = "sequence", INTRO = "INTRO", AUTH = "auth", MAIN = "main",
            LOGIN = "LOGIN", REGISTRATION = "REGISTRATION", DRAWER = "DRAWER", CATALOG = "CATALOG",
            PRODUCT_LIST = "PRODUCT_LIST", BARCODE = "BARCODE",
            PRODUCT_DESCRIPT = "PRODUCT_DESCRIPT", ADD_PRODUCT = "ADD_PRODUCT",
            DESCRIPT = "DESCRIPT", CHARACTERISTIC = "CHARACTERISTIC", ORDER_LIST = "ORDER_LIST",
            ORDER_PRODUCT = "ORDER_PRODUCT", PROFILE = "PROFILE", FITNESS = "FITNESS",
            PICK_TIME = "pick time", NEWS_EVENTS = "NEWS_EVENTS", NEWS = "NEWS", EVENT = "event",
            NEWS_DETAIL = "NEWS_DETAIL", SETTINGS = "SETTINGS";
    public String PUSH_NEWS = "news", PUSH_EVENTS = "events";

    @Override
    public void declare() {
        activity(SEQUENCE, R.layout.activity_sequence)
                .componentSequence(INTRO, AUTH, MAIN);

        activity(INTRO, R.layout.activity_tutorial)
                .componentIntro(model(JSON, getString(R.string.json_tutorial)),
                        R.id.pager, R.layout.item_tutorial, R.id.indicator, R.id.skip, R.id.contin, R.id.proceed);

        activity(AUTH, R.layout.activity_auth).animate(AS.RL)
                .fragmentsContainer(R.id.content_frame, LOGIN);

        fragment(LOGIN, R.layout.fragment_login)
                .component(TC.PANEL_ENTER, null,
                        view(R.id.panel),
                        navigator(handler(R.id.done, VH.CLICK_SEND, model(POST, Api.LOGIN, "login,password"),
                                after(setToken(), setProfile("profile"), handler(0, VH.NEXT_SCREEN_SEQUENCE)), true, R.id.login, R.id.password),
                                start(R.id.register, REGISTRATION),
                                handler(R.id.enter_skip, VH.NEXT_SCREEN_SEQUENCE)), 0);

        fragment(PROFILE, R.layout.fragment_profile)
                .navigator(handler(R.id.back, VH.OPEN_DRAWER))
                .componentPhoto(R.id.cli, new int[] {R.id.blur, R.id.photo}, R.string.source_photo)
                .component(TC.PANEL_ENTER, model(Api.PROFILE),
                        view(R.id.panel),
                        navigator(handler(R.id.done, VH.CLICK_SEND, model(POST, Api.EDIT_PROF,
                                "surname,name,second_name,phone,photo,email"),
                                after(setProfile()))));

        fragment(REGISTRATION, R.layout.fragment_registration)
                .navigator(back(R.id.back))
                .componentPhoto(R.id.cli, new int[] {R.id.blur, R.id.photo}, R.string.source_photo)
                .component(TC.PANEL_ENTER, null,
                        view(R.id.panel),
                        navigator(handler(R.id.done, VH.CLICK_SEND, model(POST, Api.REGISTER,
                                "login,password,surname,name,second_name,phone,photo,email"),
                                after(setToken(), setProfile("profile"), handler(0, VH.NEXT_SCREEN_SEQUENCE)),
                                true, R.id.login, R.id.password)), 0) ;

        activity(MAIN, MainActivity.class)
                .navigator(finishDialog(R.string.attention, R.string.finishOk))
                .drawer(R.id.drawer, R.id.content_frame, R.id.left_drawer, null, DRAWER)
                .pushNavigator(drawer());

        fragment(DRAWER, R.layout.fragment_drawer)
                .navigator(start(R.id.enter, AUTH), exit(R.id.exit))
                .component(TC.PANEL, model(PROFILE),
                        view(R.id.panel).noDataView(R.id.no_data))
                .menu(model(menu), view(R.id.recycler))
                .pushNavigator(selectMenu(R.id.recycler, PUSH_NEWS, NEWS_EVENTS, true),
                        selectMenu(R.id.recycler, PUSH_EVENTS, NEWS_EVENTS, true));

        fragment(CATALOG, R.layout.fragment_catalog)
                .navigator(handler(R.id.back, VH.OPEN_DRAWER))
                .component(TC.RECYCLER_HORIZONTAL, model(Api.NEWS_PROD).pagination().progress(R.id.progr),
                        view(R.id.recycler_news, R.layout.item_news_prod),
                        navigator(start(PRODUCT_DESCRIPT), start(R.id.add, ADD_PRODUCT, PS.RECORD)))
                .component(TC.RECYCLER, model(Api.CATALOG),
                        view(R.id.recycler, "expandedLevel", new int[]{R.layout.item_catalog_type_1,
                                R.layout.item_catalog_type_2, R.layout.item_catalog_type_3})
                                .expanded(R.id.expand, R.id.expand, model(Api.CATALOG_EX, "catalog_id")),
                        navigator(start(PRODUCT_LIST)));

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
                        navigator(start(PRODUCT_DESCRIPT),
                                handler(R.id.add, ADD_PRODUCT, PS.RECORD)))
                .componentSearch(R.id.search, model(Api.PRODUCT_SEARCH, "product_name"), R.id.recycler);

        activity(BARCODE, R.layout.activity_barcode).animate(AS.RL)
                .navigator(back(R.id.back),
                        handler(R.id.apply, VH.RESULT_PARAM, "barcode_scanner"))
                .componentBarcode(R.id.barcode_scanner, R.id.result_scan, R.id.repeat);

        activity(PRODUCT_DESCRIPT, R.layout.activity_product_descript, "%1$s", "catalog_name").animate(AS.RL)
                .navigator(back(R.id.back))
                .setValue(item(R.id.product_name, TS.PARAM, "product_name"))
                .component(TC.PAGER_F, view(R.id.pager, DESCRIPT, CHARACTERISTIC)
                        .setTab(R.id.tabs, R.array.descript_tab_name));

        fragment(DESCRIPT, R.layout.fragment_descript)
                .component(TC.PANEL, model(Api.PRODUCT_ID, "product_id"),
                        view(R.id.panel).visibilityManager(visibility(R.id.bonus, "bonus")),
                        navigator(handler(R.id.add, ADD_PRODUCT, PS.RECORD)))
                .component(TC.RECYCLER, model(Api.ANALOG_ID_PRODUCT,"product_id"),
                        view(R.id.recycler, R.layout.item_product_list).noDataView(R.id.not_analog)
                                .visibilityManager(visibility(R.id.bonus_i, "bonus"),
                                        visibility(R.id.gift_i, "gift"),
                                        visibility(R.id.newT, "new_product")),
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
                                model(POST_DB, SQL.ORDER_TAB, "order_name,date=SYSTEM_TIME"),
                                after(hide(0, R.id.new_order), actual(0, R.id.recycler)), false, R.id.order_name)))
                .component(TC.RECYCLER, model(GET_DB, SQL.ORDER_LIST_ALL), view(R.id.recycler, "select",
                        new int[] {R.layout.item_order_log, R.layout.item_order_log_select}).selected().noDataView(R.id.no_data),
                        navigator(handler(0, VH.SET_PARAM)))
                .enabled(R.id.add, R.id.recycler);

        fragment(ORDER_LIST, R.layout.fragment_order)
                .navigator(handler(R.id.back, VH.OPEN_DRAWER))
                .componentDateDiapason(R.id.diapason)
                .component(TC.RECYCLER,
                        model(GET_DB, SQL.ORDER_LIST, "from,before"),
                        view(R.id.recycler, R.layout.item_order_list).noDataView(R.id.no_data),
                        navigator(start(ORDER_PRODUCT, PS.RECORD, after(actual(R.id.recycler)))), R.id.diapason);

        activity(ORDER_PRODUCT, R.layout.activity_order_product, "%1$s", "order_name").animate(AS.RL)
                .navigator(back(R.id.back))
                .plusMinus(R.id.count, R.id.plus, R.id.minus, navigator(handler(model(UPDATE_DB, SQL.PRODUCT_ORDER,
                        "count", "product_id"))),
                        new Multiply(R.id.amount, "price", "amount"))
                .component(TC.RECYCLER, model(GET_DB, SQL.PRODUCT_IN_ORDER, "order_name").row("row"),
                        view(R.id.list_product, R.layout.item_order_product),
                        navigator(handler(R.id.del, model(DEL_DB, SQL.PRODUCT_ORDER, "product_id"), after(actual()))))
                .componentTotal(R.id.total, R.id.list_product, R.id.count, null, "amount", "count")
                .component(TC.PANEL_ENTER, null, view(R.id.panel),
                        navigator(handler(R.id.send, VH.CLICK_SEND, model(POST, Api.SEND_ORDER,
                                "order_name,list_product(product_id;count)"),
                                after(handler(model(DEL_DB, SQL.ORDER_TAB, "order_name")),
                                        handler(model(DEL_DB, SQL.PRODUCT_ORDER, SQL.ORDER_WHERE, "order_name")),
                                        handler(VH.RESULT_RECORD)))));
        fragment(FITNESS, R.layout.fragment_fitness)
                .navigator(handler(R.id.back, VH.OPEN_DRAWER))
                .component(TC.SPINNER, model(JSON, getString(R.string.clubs)),
                        view(R.id.spinner, R.layout.item_spin_drop, R.layout.item_spin_hider))
                .component(TC.RECYCLER, model(Api.FITNESS, "clubId"),
                        view(R.id.recycler, R.layout.item_fitness),
                        navigator(start(PICK_TIME)), R.id.spinner);
        activity(PICK_TIME, R.layout.activity_pick_timer, "%1$s %2$s", "club_name,name_fit")
                .navigator(back(R.id.back))
                .component(TC.PANEL_ENTER, model(Api.FREEE_TIME, "clubId,fit_id,date"), view(R.id.panel),
                        navigator(handler(R.id.send, VH.CLICK_SEND, model(POST, Api.SEND_FIT_TIME,
                                "clubId,fit_id,date,worktime"),
                                after(show(R.id.ok)))), R.id.calend)
                .calendar(R.id.calend, "date");
        fragment(NEWS_EVENTS, R.layout.fragment_news_events)
                .navigator(handler(R.id.back, VH.OPEN_DRAWER))
                .component(TC.PAGER_F, view(R.id.pager, NEWS, EVENT)
                        .setTab(R.id.tabs, R.array.news_event))
                .pushNavigator(selectPager(R.id.pager, PUSH_NEWS, NEWS, true),
                        selectPager(R.id.pager, PUSH_EVENTS, EVENT, true));

        fragment(NEWS, R.layout.fragment_news)
                .component(TC.RECYCLER,
                        model(Api.NEWS),
                        view(R.id.recycler, R.layout.item_news),
                        navigator(start(NEWS_DETAIL)))
                .pushNavigator(selectRecycler(R.id.recycler, "news", "news_id", 0,false),
                        nullifyCountPush(PUSH_NEWS));

        fragment(EVENT, R.layout.fragment_news)
                .component(TC.RECYCLER,
                        model(Api.EVENT),
                        view(R.id.recycler, R.layout.item_events))
                .pushNavigator(nullifyCountPush(PUSH_EVENTS));

        activity(NEWS_DETAIL, R.layout.fragment_news_detail).animate(AS.RL)
                .navigator(back(R.id.back))
                .component(TC.PANEL,
                        model(Api.NEWS_DETAIL, "news_id"),
                        view(R.id.panel));

        fragment(SETTINGS, R.layout.fragment_setting)
                .navigator(handler(R.id.back, VH.OPEN_DRAWER))
                .switchComponent(R.id.order, navigator(
                        handler(R.id.order, VH.CLICK_SEND, model(POST, Api.SEND_SUBSCRIBE_PUSH), null,
                                afterError(true, switchOnStatus(R.id.order, false)))), null)
                .switchComponent(R.id.topic,
                        navigator(handler(R.id.topic, VH.CLICK_SEND, model(TOPIC_SUBSCRIBE, Api.LIST_TOPIC), null,
                                afterError(true, switchOnStatus(R.id.topic, false)))),
                        navigator(handler(R.id.topic, VH.CLICK_SEND, model(TOPIC_UNSUBSCRIBE, Api.LIST_TOPIC), null,
                                afterError(true, switchOnStatus(R.id.topic, true)))));

        channel("Channel_1", "Новости и мероприятия", IMPORTANCE_HIGH, MainActivity.class,
                notices(notice(PUSH_NEWS).lotPushs("У Вас непрочитанных новостей", true)
                                .icon(R.drawable.icon_menu_news, getColor(R.color.accent)),
                        notice(PUSH_EVENTS).lotPushs("У Вас новых мероприятий", true)))
                .icon(R.drawable.ic_aura)
                .description("Сообщения о новостях и мероприятиях")
                .iconColor(getColor(R.color.green_teal));
    }

    Menu menu = new Menu()
            .item(R.drawable.list, R.string.m_catalog, CATALOG, true)
            .item(R.drawable.shoppingcard, R.string.m_orders, ORDER_LIST)
            .divider()
            .item(R.drawable.icon_profile, R.string.profile, PROFILE).enabled(1)
            .divider()
            .item(R.drawable.ic_aura, R.string.fitness, FITNESS)
            .item(R.drawable.icon_menu_news, R.string.news_events, NEWS_EVENTS).badge("news,events")
            .item(R.drawable.icon_menu_settings, R.string.setting, SETTINGS);

}
