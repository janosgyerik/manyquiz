package com.manyquiz.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MultiChoiceControl implements IMultiChoiceControl {
    private final List<IChoice> choices;
    private final String[] names;
    private final boolean[] states;
    private final IPreferenceEditor preferenceEditor;

    public MultiChoiceControl(IPreferenceEditor preferenceEditor, List<IChoice> choices) {
        this.preferenceEditor = preferenceEditor;
        this.choices = choices;

        Pair<String[], boolean[]> namesAndStates = getDeserializedNamesAndStates();
        this.names = namesAndStates.first;
        this.states = namesAndStates.second;
    }

    protected Pair<String[], boolean[]> getDeserializedNamesAndStates() {
        String[] names = new String[choices.size()];
        boolean[] states = new boolean[choices.size()];

        String selected = preferenceEditor.getPreferenceValue();
        int i = 0;
        boolean anySelected = false;
        for (IChoice choice : choices) {
            names[i] = choice.getChoiceLabel();
            states[i] = selected.equals(names[i]);
            anySelected |= states[i];
            ++i;
        }
        if (!anySelected && states.length > 0) states[0] = true;
        return new Pair<String[], boolean[]>(names, states);
    }

    private String getSerializedValue() {
        for (int i = 0; i < choices.size(); ++i) {
            if (states[i]) return names[i];
        }
        return names.length > 0 ? names[0] : null;
    }

    @Override
    public String[] getNames() {
        return names;
    }

    @Override
    public boolean[] getStates() {
        return states;
    }

    @Override
    public void setState(int which, boolean enabled) {
        states[which] = enabled;
    }

    @Override
    public Collection<String> getSelectedNames() {
        Set<String> selectedNames = new HashSet<String>();
        for (int i = 0; i < names.length; ++i) {
            if (states[i]) selectedNames.add(names[i]);
        }
        return selectedNames;
    }

    @Override
    public void saveSelection() {
        preferenceEditor.savePreferenceValue(getSerializedValue());
    }
}
