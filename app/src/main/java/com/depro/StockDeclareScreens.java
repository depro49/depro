package com.depro;

import com.dpcsa.compon.base.DeclareScreens;
import com.dpcsa.compon.interfaces_classes.Multiply;
import com.dpcsa.compon.json_simple.Field;
import com.dpcsa.compon.param.ParamMap;

public class StockDeclareScreens extends DeclareScreens {
    public final static String MAIN = "main", HOME = "home", SERVICE = "service", NEWS = "news",
            CATEGORY = "category", PRODUCT = "product", ITEM_FORM_REPAIR = "item_form_repair",
            ITEM_FORM = "item_form", REPAIRS_MAIN = "repairs_main", REPAIRS_CALC = "repairs_calc",
            ITEM_FORM_SERVICE = "ITEM_FORM_SERVICE", COUNTRY_CODE_PH = "COUNTRY_CODE_PH", COMENT = "COMENT",
            THANKS = "THANKS", NEWS_DETAIL = "NEWS_DETAIL", ABOUT = "ABOUT", WRITE_US = "WRITE_US",
            BACK_THANKS = "BACK_THANKS", YOUTUBE = "YOUTUBE",
            PHOTO = "photo";

    @Override
    public void declare() {

        activity(MAIN, R.layout.activity_main)
//                .toolBar(R.id.tool, R.id.title, listId(R.id.sel_lang), listId(R.id.back))
                .fragmentsContainer(R.id.content_frame)
                .setValue(item(R.id.lang_txt, TS.LOCALE))
//                .navigator(handler(R.id.apply, VH.SET_LOCALE), show(R.id.sel_lang, R.id.lang), back(R.id.back))
                .navigator(hide(R.id.apply, R.id.lang), handler(R.id.apply, VH.SET_LOCALE))
                .menuBottom(R.id.nav, HOME, REPAIRS_MAIN, ABOUT, NEWS)
                .component(TC.RECYCLER,             // меню вибору мови
                        model(JSON, getString(R.string.jsonListLang)),
                        view(R.id.recycler, new int[] {R.layout.item_lang, R.layout.item_lang_sel}).selected("id_language"));
        fragment(HOME, R.layout.fragment_home)
                .setValue(item(R.id.lang_txt, TS.LOCALE))
                .navigator(show(R.id.sel_lang, R.id.lang, true))
                .component(TC.RECYCLER,
                        model(API.CATEGORIES, 1).sort("order"),
                        view(R.id.recycler, R.layout.item_home),
                        navigator(start(CATEGORY, PS.RECORD)));
        fragment(REPAIRS_MAIN, R.layout.fragment_repairs_main)
                .setValue(item(R.id.lang_txt, TS.LOCALE))
                .navigator(show(R.id.sel_lang, R.id.lang, true),
                        start(R.id.apply, SERVICE, true));
        fragment(SERVICE, R.layout.fragment_service).animate(AS.RL)
                .componentMap(R.id.map, model(API.MARKER_MAP), new ParamMap(true)
                                .levelZoom(5f)
                                .coordinateValue(48.3794327, 31.1655807)
                                .markerImg(0, R.drawable.pin)
                                .markerClick(R.id.infoWindow, true),
                        navigator(start(R.id.requisition, ITEM_FORM_REPAIR), back(R.id.requisition),
                                handler(R.id.phones, VH.DIAL_UP),
                                handler(R.id.cost, VH.GET_DATA, model(API.REPAIRS)
                                                .addField("total,amount", Field.TYPE_INTEGER, 0),
                                        after(handler(0, VH.SET_GLOBAL, "services"),
                                                start(R.id.cost, REPAIRS_CALC))),
                                back(R.id.cost)), 0);
        fragment(REPAIRS_CALC, R.layout.fragment_repairs_calc).animate(AS.RL)
                .navigator(back(R.id.back),
                        start(R.id.request, ITEM_FORM_SERVICE))
                .plusMinus(R.id.total, R.id.plus, R.id.minus, null,
                        new Multiply(0, "price", "amount"))
                .component(TC.RECYCLER,
                        model(GLOBAL, "services"),
                        view(R.id.recycler, R.layout.item_repairs_calc))
                .componentTotal(R.id.sum, R.id.recycler, R.id.total, null, "amount");
        fragment(CATEGORY, R.layout.fragment_category).animate(AS.RL)
                .navigator(back(R.id.back))
                .component(TC.PANEL,
                        model(ARGUMENTS),
                        view(R.id.panel))
                .component(TC.RECYCLER,
                        model(API.PRODUCTS, "categoryId").errorShowView(R.id.error_view),
                        view(R.id.recycler, R.layout.item_category).noDataView(R.id.no_product),
                        navigator(start(0, PRODUCT)));
        fragment(PRODUCT, R.layout.fragment_product).animate(AS.RL)
                .navigator(back(R.id.back),
                        handler(R.id.apply, ITEM_FORM, PS.RECORD_COMPONENT, R.id.panel))
                .component(TC.PANEL,
                        model(API.DETAIL, "productId"),
                        view(R.id.panel).visibilityManager(visibility(R.id.video, "videoLink"),
                                visibility(R.id.full_desc, "text2"),
                                visibility(R.id.charact, "description.characteristics")),
                        navigator(handler(R.id.video, VH.SET_PARAM), start(R.id.video, YOUTUBE),
                                showHide(R.id.full_desc, R.id.text2, R.string.hide, R.string.full_desc)));
        fragment(ITEM_FORM, R.layout.fragment_item_form).animate(AS.RL)
                .navigator(back(R.id.back))
                .component(TC.PANEL_ENTER,
                        model(ARGUMENTS),
                        view(R.id.panel),
                        navigator(handler(R.id.country, COUNTRY_CODE_PH, after(assignValue(R.id.codePlus))),
                                handler(R.id.add_comment, COMENT, PS.RECORD, "comment",
                                        after(assignValue(R.id.comment), showComponent(R.id.panel_comment))),
                                handler(R.id.edit, COMENT, PS.RECORD, "comment", after(assignValue(R.id.comment))),
                                handler(R.id.apply, VH.CLICK_SEND,
                                        model(POST, API.SEND_PRODUCT, "name,phone,comment,productId"),
                                        after(start(THANKS)))))
                .enabled(R.id.apply, R.id.name,  R.id.phone);
        activity(COUNTRY_CODE_PH, R.layout.activity_country_code).animate(AS.BT)
                .navigator(back(R.id.back))
                .component(TC.RECYCLER,
                        model(COUNTRY_CODE, "380,48,995,374,994"),
                        view(R.id.recycler, "isPopular", new int[] {R.layout.item_country_code, R.layout.item_country_code_pop}),
                        navigator(handler(0, VH.RESULT_RECORD)));
        activity(COMENT, R.layout.activity_coment).animate(AS.RL)
                .navigator(back(R.id.back),
                        handler(R.id.apply, VH.RESULT_RECORD, "comment"))
                .component(TC.PANEL_ENTER,
                        model(ARGUMENTS),
                        view(R.id.panel_comment))
                .enabled(R.id.apply, R.id.comment);
        fragment(ITEM_FORM_REPAIR, R.layout.fragment_item_form_repair).animate(AS.RL)
                .navigator(back(R.id.back))
                .component(TC.PANEL_ENTER, null,
                        view(R.id.panel),
                        navigator(handler(R.id.country, COUNTRY_CODE_PH, after(assignValue(R.id.codePlus))),
                                handler(R.id.add_comment, COMENT, PS.RECORD, "comment",
                                        after(assignValue(R.id.comment), showComponent(R.id.panel_comment))),
                                handler(R.id.edit, COMENT, PS.RECORD, "comment", after(assignValue(R.id.comment))),
                                handler(R.id.apply, VH.CLICK_SEND,
                                        model(POST, API.SEND_SERVICES, "name,phone,comment,stationId"),
                                        after(start(THANKS)))))
                .enabled(R.id.apply, R.id.name,  R.id.phone);
        fragment(ITEM_FORM_SERVICE, R.layout.fragment_item_form_service, ItemServiceMore.class).animate(AS.RL)
                .navigator(handler(R.id.clear, VH.CLICK_VIEW),
                        handler(R.id.all_list, VH.CLICK_VIEW), back(R.id.back))
                .component(TC.RECYCLER,
                        model(GLOBAL, "services").filters(2, filter("total", FO.more, 0)),
                        view(R.id.recycler, R.layout.item_form_service),
                        navigator(handler(R.id.delete, VH.CLICK_VIEW)))
                .component(TC.PANEL_ENTER, null,
                        view(R.id.panel),
                        navigator(handler(R.id.country, COUNTRY_CODE_PH, after(assignValue(R.id.codePlus))),
                                handler(R.id.add_comment, COMENT, PS.RECORD, "comment",
                                        after(assignValue(R.id.comment), showComponent(R.id.panel_comment))),
                                handler(R.id.edit, COMENT, PS.RECORD, "comment", after(assignValue(R.id.comment))),
                                handler(R.id.apply, VH.CLICK_SEND,
                                        model(POST, API.SEND_SERVICES, "name,phone,comment,stationId"),
                                        after(start(THANKS)), false)))
                .enabled(R.id.apply, R.id.name,  R.id.phone);
        activity(YOUTUBE, YouTubeActivity.class).animate(AS.RL)
                .navigator(back(R.id.cancel))
                .componentYoutube(R.id.player)
                .setValue(item(R.id.player, TS.PARAM, "videoLink"));
        fragment(THANKS, R.layout.fragment_thanks).animate(AS.RL)
                .navigator(setMenu(R.id.apply), keyBack(R.id.apply));
        fragment(NEWS, R.layout.fragment_news)
                .setValue(item(R.id.lang_txt, TS.LOCALE))
                .navigator(show(R.id.sel_lang, R.id.lang, true))
                .component(TC.RECYCLER,
                        model(API.NEWS).errorShowView(R.id.error_view),
                        view(R.id.recycler, R.layout.item_news).noDataView(R.id.no_news),
                        navigator(start(0, NEWS_DETAIL)));
        fragment(NEWS_DETAIL, R.layout.fragment_news_detail).animate(AS.RL)
                .navigator(back(R.id.back))
                .component(TC.PANEL,
                        model(API.NEWS_DETAIL, "newsId"),
                        view(R.id.panel));
        fragment(ABOUT, R.layout.fragment_about)
                .setValue(item(R.id.lang_txt, TS.LOCALE))
//                .componentPhoto(R.id.ttt, images(R.id.ova, R.id.fon), R.string.about)
                .navigator(show(R.id.sel_lang, R.id.lang, true),
                        start(R.id.apply, WRITE_US),
                        handler(R.id.video, VH.SET_PARAM, "videoLink", "C6d_iP_RZrc"),
                        start(R.id.video, YOUTUBE),
                        handler(R.id.phone, VH.DIAL_UP),
                        showHide(R.id.full_desc, R.id.text2, R.string.hide, R.string.full_desc));
        fragment(WRITE_US, R.layout.fragment_write_us).animate(AS.RL)
                .navigator(back(R.id.back))
                .component(TC.PANEL_ENTER,
                        model(ARGUMENTS),
                        view(R.id.panel),
                        navigator(handler(R.id.country, COUNTRY_CODE_PH, after(assignValue(R.id.codePlus))),
                                handler(R.id.add_comment, COMENT, PS.RECORD, "comment",
                                        after(assignValue(R.id.comment), showComponent(R.id.panel_comment))),
                                handler(R.id.edit, COMENT, PS.RECORD, "comment", after(assignValue(R.id.comment))),
                                handler(R.id.apply, VH.CLICK_SEND,
                                        model(POST, API.SEND_FEEDBACK, "name,phone,comment"),
                                        after(start(BACK_THANKS)))))
                .enabled(R.id.apply, R.id.name,  R.id.phone);
        fragment(BACK_THANKS, R.layout.fragment_back_thanks).animate(AS.RL)
                .navigator(setMenu(R.id.apply), keyBack(R.id.apply));



//        fragment(PHOTO, R.layout.fragment_photo)
//                .componentPhoto(R.id.cli, new int[] {R.id.blur, R.id.oval}, R.string.about);
    }
}