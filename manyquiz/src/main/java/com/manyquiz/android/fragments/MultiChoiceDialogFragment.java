package com.manyquiz.android.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;

import com.manyquiz.R;
import com.manyquiz.common.util.IMultiChoiceControl;

public class MultiChoiceDialogFragment extends DialogFragment {

    private final String title;
    private final IMultiChoiceControl multiChoiceControl;

    public MultiChoiceDialogFragment(String title, IMultiChoiceControl multiChoiceControl) {
        this.title = title;
        this.multiChoiceControl = multiChoiceControl;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.Theme_Dialog));
        builder.setTitle(title)
                .setMultiChoiceItems(multiChoiceControl.getNames(), multiChoiceControl.getStates(),
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                multiChoiceControl.setState(which, isChecked);
                            }
                        })
                .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        multiChoiceControl.saveSelection();
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
