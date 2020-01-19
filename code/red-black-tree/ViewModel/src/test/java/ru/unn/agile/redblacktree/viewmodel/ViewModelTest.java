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
        assertEquals("", viewModel.findInsertFieldProperty().get());
        assertEquals("", viewModel.removeInsertFieldProperty().get());
    }


    @Test
    public void statusIsWaitingWhenAddElementToTreeWithEmptyFields() {
        viewModel.addElementToTree();
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenFindElementToTreeWithEmptyFields() {
        viewModel.findElementToTree();
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenRemoveElementToTreeWithEmptyFields() {
        viewModel.removeElementToTree();
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }
}
