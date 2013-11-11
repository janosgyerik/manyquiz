package com.manyquiz.common.util;

import java.io.Serializable;

public interface ISingleChoiceControl extends Serializable {

    String[] getNames();

    IChoice getSelectedItem();

    int getSelectedIndex();

    void setSelectedIndex(int selectedIndex);

    void saveSelection();

    void reloadSelection();
}
