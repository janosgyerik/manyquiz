package com.manyquiz.quiz.tools;

import com.manyquiz.quiz.impl.Category;
import com.manyquiz.quiz.impl.CategoryFilterControl;
import com.manyquiz.quiz.model.ICategoryFilterControl;
import com.manyquiz.tools.IPreferenceEditor;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryControlTest {

    @Test
    public void testCategoriesEnabledByDefault() {
        IPreferenceEditor mockPreferenceEditor = mock(IPreferenceEditor.class);
        when(mockPreferenceEditor.getPreferenceValue()).thenReturn("");

        List<Category> mockCategories = new ArrayList<Category>();
        mockCategories.add(new Category("linux"));
        mockCategories.add(new Category("database"));
        ICategoryFilterControl categoryControl = new CategoryFilterControl(mockPreferenceEditor, mockCategories);
        Assert.assertArrayEquals(new String[]{"linux", "database"}, categoryControl.getItems());
        assertArrayEquals(new boolean[]{true, true}, categoryControl.getCheckedItems());
    }

    @Test
    public void testExcludedCategory() {
        IPreferenceEditor mockPreferenceEditor = mock(IPreferenceEditor.class);
        when(mockPreferenceEditor.getPreferenceValue()).thenReturn("linux=");

        List<Category> mockCategories = new ArrayList<Category>();
        mockCategories.add(new Category("linux"));
        mockCategories.add(new Category("database"));
        ICategoryFilterControl categoryControl = new CategoryFilterControl(mockPreferenceEditor, mockCategories);
        Assert.assertArrayEquals(new String[]{"linux", "database"}, categoryControl.getItems());
        assertArrayEquals(new boolean[]{false, true}, categoryControl.getCheckedItems());
    }

    @Test
    public void testChangeFilter() {
        IPreferenceEditor mockPreferenceEditor = mock(IPreferenceEditor.class);
        when(mockPreferenceEditor.getPreferenceValue()).thenReturn("");

        List<Category> mockCategories = new ArrayList<Category>();
        mockCategories.add(new Category("linux"));
        mockCategories.add(new Category("database"));
        ICategoryFilterControl categoryControl = new CategoryFilterControl(mockPreferenceEditor, mockCategories);
        categoryControl.setFilter(0, false);
        assertArrayEquals(new boolean[]{false, true}, categoryControl.getCheckedItems());
    }

    @Test
    public void testSaveCategories() {
        IPreferenceEditor mockPreferenceEditor = mock(IPreferenceEditor.class);
        when(mockPreferenceEditor.getPreferenceValue()).thenReturn("");

        List<Category> mockCategories = new ArrayList<Category>();
        mockCategories.add(new Category("linux"));
        mockCategories.add(new Category("database"));
        ICategoryFilterControl categoryControl = new CategoryFilterControl(mockPreferenceEditor, mockCategories);
        categoryControl.setFilter(0, false);

        categoryControl.saveFilters();
        verify(mockPreferenceEditor).savePreferenceValue("linux=,database=1");
    }

    @Test
    public void testExcessPrefsAreDropped() {
        IPreferenceEditor mockPreferenceEditor = mock(IPreferenceEditor.class);
        when(mockPreferenceEditor.getPreferenceValue()).thenReturn("magic=1");

        List<Category> mockCategories = new ArrayList<Category>();
        mockCategories.add(new Category("linux"));
        mockCategories.add(new Category("database"));
        ICategoryFilterControl categoryControl = new CategoryFilterControl(mockPreferenceEditor, mockCategories);
        Assert.assertArrayEquals(new String[]{"linux", "database"}, categoryControl.getItems());
    }

    private void assertArrayEquals(boolean[] expected, boolean[] actual) {
        if (expected != null && actual != null) {
            if (expected.length == actual.length) {
                for (int i = 0; i < expected.length; ++i) {
                    Assert.assertEquals(expected[i], actual[i]);
                }
            } else {
                Assert.fail(String.format("expected.length != actual.length: %d != %d",
                        expected.length, actual.length));
            }
        } else {
            Assert.fail(String.format("expected and actual must be non-null: %s, %s",
                    expected != null, actual != null));
        }
    }

}
