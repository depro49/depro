package com.example.vinaigrette;

import com.dpcsa.compon.base.BaseActivity;

public class MainActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    public String getNameScreen() {
        return MyDeclareScreens.MAIN;
    }
}
