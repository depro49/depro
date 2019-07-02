package com.depro;

import android.view.View;
import android.widget.TextView;

import com.dpcsa.compon.base.BaseComponent;
import com.dpcsa.compon.components.RecyclerComponent;
import com.dpcsa.compon.custom_components.SimpleTextView;
import com.dpcsa.compon.interfaces_classes.Filters;
import com.dpcsa.compon.interfaces_classes.MoreWork;
import com.dpcsa.compon.json_simple.Field;
import com.dpcsa.compon.json_simple.ListRecords;
import com.dpcsa.compon.json_simple.Record;

public class ItemServiceMore extends MoreWork {

    // розрахунок суми
    @Override
    public void afterChangeData(BaseComponent baseComponent) {
        if (baseComponent.paramMV.paramView.viewId == R.id.recycler) {
            int count = 0;
            if (baseComponent.listRecords != null) {
                int sum = 0;
                for (Record rec : baseComponent.listRecords) {
                    int amount = rec.getInt("amount");
                    if (amount > 0) {
                        count++;
                        sum += amount;
                    }
                }
                SimpleTextView tv = (SimpleTextView) baseComponent.parentLayout.findViewById(R.id.sum);
                if (tv != null) {
                    tv.setData(sum);
                }
                View v = baseComponent.parentLayout.findViewById(R.id.all_list);
                if (count > 2) {
                    v.setVisibility(View.VISIBLE);
                } else {
                    v.setVisibility(View.GONE);
                }
                View v1 = baseComponent.parentLayout.findViewById(R.id.clear);
                if (count > 0) {
                    v1.setVisibility(View.VISIBLE);
                } else {
                    v1.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void clickView(View viewClick, View parentView, BaseComponent baseComponent, Record rec, int position) {
        BaseComponent bc = screen.getComponent(R.id.recycler);
        if (bc == null) return;
        switch (viewClick.getId()) {
            case R.id.clear:
                if (bc.listRecords != null) {
                    for (Record rec1 : bc.listRecords) {
                        Field ff = rec1.getField("amount");
                        ff.value = 0;
                        ff = rec1.getField("total");
                        ff.value = 0;
                    }
                    bc.setFilterData();
                }
                break;
            case R.id.delete:
                if (rec != null) {
                    Field ff = rec.getField("amount");
                    ff.value = 0;
                    ff = rec.getField("total");
                    ff.value = 0;
                    bc.setFilterData();
                }
                break;
            case R.id.all_list:
                TextView all_list = parentView.findViewById(R.id.all_list);
                Filters filters = bc.paramMV.paramModel.filters;
                if (filters.maxSize < Integer.MAX_VALUE) {
                    filters.maxSize = Integer.MAX_VALUE;
                    all_list.setText(activity.getString(R.string.hide));
                } else {
                    filters.maxSize = 2;
                    all_list.setText(activity.getString(R.string.all_list));
                }
                bc.setFilterData();
                break;
        }
    }

    @Override
    public void setPostParam(int viewId, Record rec) {
        if (viewId == R.id.apply) {
            ListRecords serv = new ListRecords();
            RecyclerComponent rc = (RecyclerComponent)screen.getComponent(R.id.recycler);
            for (Record rr : rc.listRecords) {
                int total = rr.getInt("total");
                if (total > 0) {
                    Record recServ = new Record();
                    recServ.add(new Field("id", Field.TYPE_INTEGER, rr.getInt("serviceId")));
                    recServ.add(new Field("total", Field.TYPE_INTEGER, total));
                    serv.add(recServ);
                }
            }
            rec.add(new Field("serviceIds", Field.TYPE_LIST_RECORD, serv));
        }
    }
}
