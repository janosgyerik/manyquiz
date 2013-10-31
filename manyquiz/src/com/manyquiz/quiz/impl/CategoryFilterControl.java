package com.manyquiz.quiz.impl;

import com.manyquiz.quiz.model.ICategoryFilterControl;
import com.manyquiz.tools.IPreferenceEditor;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CategoryFilterControl implements ICategoryFilterControl {
    private static final String PAIR_SEP = ",";
    private static final String KEYVAL_SEP = "=";
    private static final String TRUE_VAL = "1";
    private static final String FALSE_VAL = "";

    private final List<Category> categories;
    private final String[] categoryNames;
    private final boolean[] categoryStates;
    private final IPreferenceEditor preferenceEditor;

    public CategoryFilterControl(IPreferenceEditor preferenceEditor, List<Category> categories) {
        this.preferenceEditor = preferenceEditor;
        this.categories = categories;
        this.categoryNames = new String[categories.size()];
        this.categoryStates = new boolean[categories.size()];

        deserializeFromPrefs();
    }

    private void deserializeFromPrefs() {
        String serializedValue = preferenceEditor.getPreferenceValue();
        Map<String, Boolean> categoryFilterMap = new HashMap<String, Boolean>();
        if (serializedValue.length() > 0) {
            for (String keyval : serializedValue.split(PAIR_SEP)) {
                String[] pair = keyval.split(KEYVAL_SEP);
                switch (pair.length) {
                    case 1:
                        categoryFilterMap.put(pair[0], false);
                        break;
                    case 2:
                        categoryFilterMap.put(pair[0], pair[1].equals(TRUE_VAL));
                        break;
                }
            }
        }
        for (Category category : categories) {
            if (!categoryFilterMap.containsKey(category.name)) {
                categoryFilterMap.put(category.name, true);
            }
        }
        int i = 0;
        for (Category category : categories) {
            categoryNames[i] = category.name;
            categoryStates[i] = categoryFilterMap.get(category.name);
            ++i;
        }
    }

    private String serialized() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < categories.size(); ++i) {
            builder.append(categoryNames[i]).append("=").append(categoryStates[i] ? TRUE_VAL : FALSE_VAL);
            if (i < categories.size() - 1) builder.append(PAIR_SEP);
        }
        return builder.toString();
    }

    @Override
    public void saveFilters() {
        preferenceEditor.savePreferenceValue(serialized());
    }

    @Override
    public String[] getCategoryNames() {
        return categoryNames;
    }

    @Override
    public boolean[] getCategoryStates() {
        return categoryStates;
    }

    @Override
    public void setCategoryState(int which, boolean enabled) {
        categoryStates[which] = enabled;
    }

    @Override
    public Collection<String> getSelectedItems() {
        Set<String> selectedItems = new HashSet<String>();
        for (int i = 0; i < categoryNames.length; ++i) {
            if (categoryStates[i]) selectedItems.add(categoryNames[i]);
        }
        return selectedItems;
    }
}
