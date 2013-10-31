package com.manyquiz.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;

import com.manyquiz.R;
import com.manyquiz.quiz.impl.Category;
import com.manyquiz.quiz.impl.CategoryFilterControl;
import com.manyquiz.quiz.model.ICategoryFilterControl;
import com.manyquiz.tools.IPreferenceEditor;

import java.util.List;

public class SelectCategoriesDialogFragment extends DialogFragment {

    private final ICategoryFilterControl categoryControl;

    public SelectCategoriesDialogFragment(IPreferenceEditor preferenceEditor, List<Category> categories) {
        this.categoryControl = new CategoryFilterControl(preferenceEditor, categories);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light));
        builder.setTitle(R.string.title_select_categories)
                .setMultiChoiceItems(categoryControl.getCategoryNames(), categoryControl.getCategoryStates(),
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                categoryControl.setCategoryState(which, isChecked);
                            }
                        })
                .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        categoryControl.saveFilters();
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
