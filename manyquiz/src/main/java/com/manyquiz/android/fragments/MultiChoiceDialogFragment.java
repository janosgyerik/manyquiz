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

    private static final int NB_PARAMETERS = 2;
    private static final String TITLE_KEY = "title";
    private static final String MULTIPLE_CHOICE_CONTROL_KEY = "multipleChoiceControl";

    private String title;
    private IMultiChoiceControl multiChoiceControl;

    public static MultiChoiceDialogFragment newInstance(String title, IMultiChoiceControl multiChoiceControl) {
        MultiChoiceDialogFragment fragment = new MultiChoiceDialogFragment();
        Bundle bundle = new Bundle(NB_PARAMETERS);
        bundle.putString(TITLE_KEY, title);
        bundle.putSerializable(MULTIPLE_CHOICE_CONTROL_KEY, multiChoiceControl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        title = getArguments().getString(TITLE_KEY);
        multiChoiceControl = (IMultiChoiceControl) getArguments().getSerializable(MULTIPLE_CHOICE_CONTROL_KEY);

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
