package com.manyquiz.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;

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
        builder.setTitle(title)
                .setMultiChoiceItems(singleChoiceControl.getNames(), new boolean[singleChoiceControl.getNames().length],
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                singleChoiceControl.setSelectedIndex(i);
                            }
                        })
                        // TODO
                        // E/AndroidRuntime( 5131): Caused by: android.content.res.Resources$NotFoundException: File res/color/primary_text_light.xml from xml type colorstatelist resource ID #0x0
//                .setSingleChoiceItems(singleChoiceControl.getNames(), singleChoiceControl.getSelectedIndex(),
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
//                                singleChoiceControl.setSelectedIndex(selectedIndex);
//                            }
//                        }
//                )
                .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        singleChoiceControl.saveSelection();
                    }
                })
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // nothing to do
                    }
                });

        return builder.create();
    }
}
