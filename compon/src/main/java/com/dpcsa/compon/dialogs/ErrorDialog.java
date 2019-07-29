package com.dpcsa.compon.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dpcsa.compon.json_simple.Record;
import com.dpcsa.compon.json_simple.WorkWithRecordsAndViews;
import com.dpcsa.compon.single.ComponGlob;
import com.dpcsa.compon.single.Injector;

public class ErrorDialog extends DialogFragment {

    private View cancel;
    private View.OnClickListener listener;
    private Record rec;
    public WorkWithRecordsAndViews workWithRecordsAndViews;
    private View parentLayout;

    public ErrorDialog() {
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ComponGlob componGlob = Injector.getComponGlob();
        if (componGlob.appParams.errorDialogLayoutId != 0) {
            workWithRecordsAndViews = new WorkWithRecordsAndViews();
            parentLayout = inflater.inflate(componGlob.appParams.errorDialogLayoutId, container, false);
            cancel = parentLayout.findViewById(componGlob.appParams.errorDialogCancelId);
            if (cancel != null) {
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
            }
        }
        return parentLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        workWithRecordsAndViews.RecordToView(rec, parentLayout);
    }

    public void setParam(Record rec, View.OnClickListener listener) {
        this.listener = listener;
        this.rec = rec;
    }
}
