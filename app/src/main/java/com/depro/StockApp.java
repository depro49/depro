package com.depro;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.dpcsa.compon.single.DeclareParam;

//public class StockApp extends Application {
public class StockApp extends Application {
    private static StockApp instance;
    private Context context;

    public static StockApp getInstance() {
        if (instance == null) {
            instance = new StockApp();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();

        DeclareParam.build(context)
                .setNetworkParams(new StockAppParams())
                .setDeclareScreens(new StockDeclareScreens());
    }

}
