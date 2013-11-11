package com.manyquiz.android.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;
import android.widget.ArrayAdapter;

import com.manyquiz.R;
import com.manyquiz.common.util.ISingleChoiceControl;

public class SingleChoiceDialogFragment extends DialogFragment {

    private static final int NB_PARAMETERS = 2;
    private static final String TITLE_KEY = "title";
    private static final String SINGLE_CHOICE_CONTROL_KEY = "singleChoiceControl";

    private String title;
    private ISingleChoiceControl singleChoiceControl;

    public static SingleChoiceDialogFragment newInstance(String title, ISingleChoiceControl singleChoiceControl) {
        SingleChoiceDialogFragment fragment = new SingleChoiceDialogFragment();
        Bundle bundle = new Bundle(NB_PARAMETERS);
        bundle.putString(TITLE_KEY, title);
        bundle.putSerializable(SINGLE_CHOICE_CONTROL_KEY, singleChoiceControl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        title = getArguments().getString(TITLE_KEY);
        singleChoiceControl = (ISingleChoiceControl) getArguments().getSerializable(SINGLE_CHOICE_CONTROL_KEY);

        AlertDialog.Builder builder =
                new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.Theme_Dialog));
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
