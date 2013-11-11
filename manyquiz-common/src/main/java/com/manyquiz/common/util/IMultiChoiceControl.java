package com.manyquiz.common.util;

import java.util.Collection;

public interface IMultiChoiceControl {

    String[] getNames();

    boolean[] getStates();

    void setState(int which, boolean enabled);

    Collection<String> getSelectedNames();

    void saveSelection();

    void reloadSelection();
}
