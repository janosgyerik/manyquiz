package com.manyquiz.util;

import java.util.ArrayList;
import java.util.List;

public class SingleChoiceControl implements ISingleChoiceControl {
    private final IPreferenceEditor preferenceEditor;
    private final List<? extends IChoice> choices;
    private int selectedIndex;

    public SingleChoiceControl(IPreferenceEditor preferenceEditor, List<? extends IChoice> choices, int selectedIndex) {
        this.preferenceEditor = preferenceEditor;
        this.choices = choices;
        this.selectedIndex = selectedIndex;
    }

    public SingleChoiceControl(IPreferenceEditor preferenceEditor, List<? extends IChoice> choices) {
        this(preferenceEditor, choices, findSelectedIndex(preferenceEditor, choices));
    }

    private static int findSelectedIndex(IPreferenceEditor preferenceEditor, List<? extends IChoice> choices) {
        String value = preferenceEditor.getPreferenceValue();
        int i = 0;
        for (IChoice choice : choices) {
            if (value.equals(choice.getChoiceValue())) return i;
            ++i;
        }
        return 0;
    }

    @Override
    public String[] getNames() {
        List<String> names = new ArrayList<String>();
        for (IChoice choice : choices) {
            names.add(choice.getChoiceLabel());
        }
        return names.toArray(new String[names.size()]);
    }

    @Override
    public IChoice getSelectedItem() {
        return choices.get(selectedIndex);
    }

    @Override
    public int getSelectedIndex() {
        return selectedIndex;
    }

    @Override
    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    @Override
    public void saveSelection() {
        preferenceEditor.savePreferenceValue(choices.get(selectedIndex).getChoiceValue());
    }
}
