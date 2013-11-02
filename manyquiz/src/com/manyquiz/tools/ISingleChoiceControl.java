package com.manyquiz.tools;

public interface ISingleChoiceControl {

    String[] getNames();

    IChoice getSelectedItem();

    int getSelectedIndex();

    void setSelectedIndex(int selectedIndex);

    void saveSelection();
}
