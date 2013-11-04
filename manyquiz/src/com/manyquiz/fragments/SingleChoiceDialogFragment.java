package com.manyquiz.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;
import android.widget.ArrayAdapter;

import com.manyquiz.R;
import com.manyquiz.util.ISingleChoiceControl;

public class SingleChoiceDialogFragment extends DialogFragment {

    private final String title;
    private final ISingleChoiceControl singleChoiceControl;

    public SingleChoiceDialogFragment(String title, ISingleChoiceControl singleChoiceControl) {
        this.title = title;
        this.singleChoiceControl = singleChoiceControl;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.choice);
        for (String name : singleChoiceControl.getNames()) {
            adapter.add(name);
        }
        builder.setTitle(title)
                .setSingleChoiceItems(adapter, singleChoiceControl.getSelectedIndex(),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                singleChoiceControl.setSelectedIndex(selectedIndex);
                                singleChoiceControl.saveSelection();
                                SingleChoiceDialogFragment.this.dismiss();
                            }
                        }
                )
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // nothing to do
                    }
                });

        return builder.create();
    }
}
