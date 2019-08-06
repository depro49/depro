package com.dpcsa.compon.components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import com.dpcsa.compon.R;
import com.dpcsa.compon.base.BaseActivity;
import com.dpcsa.compon.base.BaseComponent;
import com.dpcsa.compon.base.BaseProvider;
import com.dpcsa.compon.base.BaseProviderAdapter;
import com.dpcsa.compon.base.Screen;
import com.dpcsa.compon.interfaces_classes.IBase;
import com.dpcsa.compon.interfaces_classes.Menu;
import com.dpcsa.compon.interfaces_classes.OnResumePause;
import com.dpcsa.compon.interfaces_classes.ViewHandler;
import com.dpcsa.compon.json_simple.Field;
import com.dpcsa.compon.json_simple.ListRecords;
import com.dpcsa.compon.json_simple.Record;
import com.dpcsa.compon.param.ParamComponent;
import com.dpcsa.compon.presenter.ListPresenter;

public class MenuComponent extends BaseComponent {
    RecyclerView recycler;
    ListRecords listData;
    BaseProviderAdapter adapter;
    private String componentTag = "MENU_";
    private String fieldType = "select";
    int colorNorm, colorSelect, colorEnabl;
    boolean isBaseItem;
    boolean isEnabled = false;
    BroadcastReceiver tokenMessageReceiver = null;
    private Field fieldMenu;
    private Menu menu;

    public MenuComponent(IBase iBase, ParamComponent paramMV, Screen multiComponent) {
        super(iBase, paramMV, multiComponent);
    }

    @Override
    public void initView() {
        if (paramMV.paramView == null || paramMV.paramView.viewId == 0) {
            recycler = (RecyclerView) componGlob.findViewByName(parentLayout, "recycler");
        } else {
            recycler = (RecyclerView) parentLayout.findViewById(paramMV.paramView.viewId);
        }
        if (recycler == null) {
            iBase.log("Не найден RecyclerView для Menu в " + multiComponent.nameComponent);
        }
        if (navigator != null) {
            for (ViewHandler vh : navigator.viewHandlers) {
                if (vh.viewId == 0 && vh.type == ViewHandler.TYPE.FIELD_WITH_NAME_FRAGMENT) {
                    selectViewHandler = vh;
                    break;
                }
            }
        } else {
            iBase.log("Нет навигатора для Menu в " + multiComponent.nameComponent);
        }
        isBaseItem = false;
        paramMV.paramView.fieldType = fieldType;
        if (paramMV.paramView.layoutTypeId == null) {
            isBaseItem = true;
            paramMV.paramView.layoutTypeId = new int[]{R.layout.item_menu_base, R.layout.item_menu_base,
                    R.layout.item_menu_divider_base, R.layout.item_menu_base};
        }
        listData = new ListRecords();
        listPresenter = new ListPresenter(this);
        provider = new BaseProvider(listData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recycler.setLayoutManager(layoutManager);
        adapter = new BaseProviderAdapter(this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void changeData(Field field) {
        if (field == null) return;
        fieldMenu = field;
        if (field instanceof Menu) {
            menu = (Menu) field;
        }
        listData.clear();
        listData.addAll((ListRecords) field.value);
        colorNorm = getThemeColor("colorPrimary");
        colorSelect = getThemeColor("colorPrimaryDark");
        colorEnabl = 0xffbbbbbb;
        if (menu != null) {
            if (menu.colorNorm != 0 && menu.colorSelect != 0 && menu.colorEnabl != 0) {
                colorNorm = activity.getResources().getColor(menu.colorNorm);
                colorSelect = activity.getResources().getColor(menu.colorSelect);
                colorEnabl = activity.getResources().getColor(menu.colorEnabl);
            }
        }
        provider.setData(listData);
        int selectStart = preferences.getNameInt(componentTag + multiComponent.nameComponent, -1);
        int ik = listData.size();
        isEnabled = false;
        for (int i = 0; i < ik; i++) {      // визначається текст по його ід
            Record r = listData.get(i);
            int stId = r.getInt("nameId");
            if (stId != 0) {
                Field ff = r.getField("name");
                if (ff == null) {
                    r.add(new Field("name", Field.TYPE_STRING, activity.getString(stId)));
                } else {
                    ff.value = activity.getString(stId);
                }
            }
            if (r.getInt("enabled") > 0) {
                isEnabled = true;
            }
        }

        if (isEnabled) {
            tokenMessageReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    changeView(true);
                    adapter.notifyDataSetChanged();
                }
            };
            iBase.setResumePause(resumePause);
            LocalBroadcastManager.getInstance(activity).registerReceiver(tokenMessageReceiver,
                    new IntentFilter(componGlob.profile.name));
            changeView(false);
        }
        if (selectStart == -1) {
            for (int i = 0; i < ik; i++) {
                Record r = listData.get(i);
                int j = (Integer) r.getValue(fieldType);
                if (r.getBoolean("start")) {
                    selectStart = i;
                    break;
                }
            }
        } else {
            Record r = listData.get(selectStart);
            Field ft = r.getField(fieldType);
            int type = (int) ft.value;
            if (type != 1) {
                if (type == 3) {
                    Record rr;
                    for (int i = 0; i < ik; i++) {
                        rr = listData.get(i);
                        Field frr = rr.getField(fieldType);
                        if ((int) frr.value == 1) {
                            frr.value = 0;
                        }
                    }
                    if (menu != null && menu.menuStart > -1) {
                        listData.get(menu.menuStart).getField(fieldType).value = 1;
                        selectStart = menu.menuStart;
                    } else {
                        for (int i = 0; i < ik; i++) {
                            rr = listData.get(i);
                            Field frr = rr.getField(fieldType);
                            if ((int) frr.value == 0) {
                                frr.value = 1;
                                selectStart = i;
                                break;
                            }
                        }
                    }
                } else {
                    ft.value = 1;
                }
            }

        }
        listPresenter.changeData(listData, selectStart);
        adapter.notifyDataSetChanged();
    }

    private void changeView(boolean notif) {
        int ik = listData.size();
        int iSel = -1;
        boolean isToken = componGlob.token != null && ((String)componGlob.token.value).length() > 0;
        for (int i = 0; i < ik; i++) {
            Record r = listData.get(i);
            if (r.getInt("enabled") > 0) {
                Field ff = r.getField(fieldType);
                if ( ! isToken) {
                    if (((int) ff.value) == 1) {
                        iSel = i;
                    }
                    ff.value = 3;
                } else {
                    ff.value = 0;
                }
            }
        }
        if (iSel > -1) {
            int selectStart = -1;
            Record rr;
            if (menu != null && menu.menuStart > -1) {
                listData.get(menu.menuStart).getField(fieldType).value = 1;
                selectStart = menu.menuStart;
            } else {
                for (int i = 0; i < ik; i++) {
                    rr = listData.get(i);
                    Field frr = rr.getField(fieldType);
                    if ((int) frr.value == 0) {
                        frr.value = 1;
                        selectStart = i;
                        break;
                    }
                }
            }
            listPresenter.changeData(listData, selectStart);
        }
        if (notif) {
            adapter.notifyDataSetChanged();
        }
    }

    OnResumePause resumePause = new OnResumePause() {
        @Override
        public void onResume() {

        }

        @Override
        public void onPause() {

        }

        @Override
        public void onDestroy() {
            if (tokenMessageReceiver != null) {
                LocalBroadcastManager.getInstance(activity).unregisterReceiver(tokenMessageReceiver);
                tokenMessageReceiver = null;
            }
        }
    };

    @Override
    public void changeDataPosition(int position, boolean select) {
        adapter.notifyItemChanged(position);
        ((BaseActivity) activity).closeDrawer();
        preferences.setNameInt(componentTag + multiComponent.nameComponent, position);
        if (select && selectViewHandler != null) {
            Record record = listData.get(position);
            componGlob.setParam(record);
            String screen = (String) record.getField(selectViewHandler.nameFieldScreen).value;
            if (screen != null && screen.length() > 0) {
                iBase.startScreen(screen, true);
            }
        }
    }

    public void setColor(int position, Record record, RecyclerView.ViewHolder holder) {
        int type = record.getInt(fieldType);
        ImageView img = (ImageView) componGlob.findViewByName(holder.itemView, "icon");
        TextView txt = (TextView) componGlob.findViewByName(holder.itemView, "name");
        switch (type) {
            case 0:     // norm
                setColors(img, colorNorm);
                txt.setTextColor(colorNorm);
                break;
            case 1:     // select
                setColors(img, colorSelect);
                txt.setTextColor(colorSelect);
                break;
            case 3:     // enabled
                setColors(img, colorEnabl);
                txt.setTextColor(colorEnabl);
                break;
        }
    }

    public void setColors(ImageView img, int color) {
        ColorStateList stateList = new ColorStateList(
                new int[][]{
                        new int[]{}
                },
                new int[] {color}
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            img.setImageTintList(stateList);
        } else {
            img.setImageDrawable(tintIcon(img.getDrawable(), stateList));
        }
    }

    public Drawable tintIcon(Drawable icon, ColorStateList colorStateList) {
        if(icon!=null) {
            icon = DrawableCompat.wrap(icon).mutate();
            DrawableCompat.setTintList(icon, colorStateList);
            DrawableCompat.setTintMode(icon, PorterDuff.Mode.SRC_IN);
        }
        return icon;
    }

    public int getThemeColor (String nameColor) {
        int colorAttr = activity.getResources().getIdentifier(nameColor, "attr", activity.getPackageName());
        TypedValue value = new TypedValue ();
        activity.getTheme ().resolveAttribute (colorAttr, value, true);
        return value.data;
    }
}
