package com.manyquiz.common.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MultiChoiceControl implements IMultiChoiceControl {
    private static final String PAIR_SEP = ",";
    private static final String KEYVAL_SEP = "=";
    private static final String TRUE_VAL = "1";
    private static final String FALSE_VAL = "";

    private final IPreferenceEditor preferenceEditor;
    private final List<? extends IChoice> choices;
    private final boolean enabledByDefault;

    private final String[] names;
    private final boolean[] states;

    public MultiChoiceControl(IPreferenceEditor preferenceEditor, List<? extends IChoice> choices, boolean enabledByDefault) {
        this.preferenceEditor = preferenceEditor;
        this.choices = choices;
        this.enabledByDefault = enabledByDefault;

        Pair<String[], boolean[]> namesAndStates = getDeserializedNamesAndStates();
        this.names = namesAndStates.first;
        this.states = namesAndStates.second;
    }

    protected Pair<String[], boolean[]> getDeserializedNamesAndStates() {
        String serializedValue = preferenceEditor.getPreferenceValue();
        Map<String, Boolean> choiceMap = new HashMap<String, Boolean>();
        if (serializedValue.length() > 0) {
            for (String keyval : serializedValue.split(PAIR_SEP)) {
                String[] pair = keyval.split(KEYVAL_SEP);
                switch (pair.length) {
                    case 1:
                        choiceMap.put(pair[0], false);
                        break;
                    case 2:
                        choiceMap.put(pair[0], pair[1].equals(TRUE_VAL));
                        break;
                }
            }
        }
        for (IChoice choice : choices) {
            String key = choice.getChoiceValue();
            if (!choiceMap.containsKey(key)) {
                choiceMap.put(key, enabledByDefault);
            }
        }

        String[] names = new String[choices.size()];
        boolean[] states = new boolean[choices.size()];

        int i = 0;
        for (IChoice choice : choices) {
            names[i] = choice.getChoiceLabel();
            states[i] = choiceMap.get(choice.getChoiceValue());
            ++i;
        }
        return new Pair<String[], boolean[]>(names, states);
    }

    private String getSerializedValue() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < names.length; ++i) {
            builder.append(names[i]).append(KEYVAL_SEP).append(states[i] ? TRUE_VAL : FALSE_VAL);
            if (i < names.length - 1) builder.append(PAIR_SEP);
        }
        return builder.toString();
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

    @Override
    public void reloadSelection() {
        for (int i = 0; i < states.length; ++i) {
            states[i] = enabledByDefault;
        }
    }
}
