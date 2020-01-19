package ru.unn.agile.redblacktree.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewModelTest {
    private ViewModel viewModel;
    @Before
    public void setUp() {
        viewModel = new ViewModel();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.addFieldProperty().get());
        assertEquals("", viewModel.isEmptyFieldProperty().get());
        assertEquals("", viewModel.getSizeFieldProperty().get());
        assertEquals("", viewModel.minimumFieldProperty().get());
        assertEquals("", viewModel.findInsertFieldProperty().get());
        assertEquals("", viewModel.findResultFieldProperty().get());
        assertEquals("", viewModel.removeInsertFieldProperty().get());
        assertEquals("", viewModel.removeResultFieldProperty().get());
    }
}
