package com.example.vinaigrette;

import com.dpcsa.compon.base.DeclareScreens;

public class MyDeclareScreens extends DeclareScreens {

    public final static String SPLASH = "splash", INTRO = "INTRO", AUTH = "auth", MAIN = "main",
            LOGIN = "LOGIN", REGISTRATION = "REGISTRATION";

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
                                after(setToken(), handler(0, VH.NEXT_SCREEN_SPLASH)), true, R.id.login, R.id.password),
                                start(R.id.register, REGISTRATION),
                                handler(R.id.enter_skip, VH.NEXT_SCREEN_SPLASH)), 0);

        fragment(REGISTRATION, R.layout.fragment_registration)
                .navigator(back(R.id.back))
                .componentPhoto(R.id.cli, new int[] {R.id.blur, R.id.photo}, R.string.source_photo)
                .component(TC.PANEL_ENTER, null,
                        view(R.id.panel),
                        navigator(handler(R.id.done, VH.CLICK_SEND, model(POST, Api.REGISTER, "login,password,surname,name,second_name,phone,photo,email"),
                                after(handler(0, VH.NEXT_SCREEN_SPLASH)), true, R.id.login, R.id.password)), 0) ;

        activity(MAIN, R.layout.activity_main).animate(AS.RL);
    }
}
