package ru.unn.agile.redblacktree.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void statusIsReadyWhenFieldsAreFill() {
        viewModel.addFieldProperty().set("1");
        viewModel.findInsertFieldProperty().set("2");
        viewModel.removeInsertFieldProperty().set("3");

        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportBadFormatAddField() {
        viewModel.addFieldProperty().set("a");

        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportBadFormatFindInsertField() {
        viewModel.findInsertFieldProperty().set("b");

        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportBadFormatRemoveInsertField() {
        viewModel.removeInsertFieldProperty().set("c");

        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledInitiallyForAddElement() {
        assertTrue(viewModel.addElementToTreeDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledInitiallyForFindElement() {
        assertTrue(viewModel.findElementToTreeDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledInitiallyForRemoveElement() {
        assertTrue(viewModel.removeElementToTreeDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWhenFormatIsBadForAddElement() {
        setInputData();
        viewModel.addFieldProperty().set("trash");

        assertTrue(viewModel.addElementToTreeDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWhenFormatIsBadForFindElement() {
        setInputData();
        viewModel.findInsertFieldProperty().set("trash");

        assertTrue(viewModel.findElementToTreeDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWhenFormatIsBadForRemoveElement() {
        setInputData();
        viewModel.removeInsertFieldProperty().set("trash");

        assertTrue(viewModel.removeElementToTreeDisabledProperty().get());
    }

    private void setInputData() {
        viewModel.addFieldProperty().set("1");
        viewModel.findInsertFieldProperty().set("1");
        viewModel.removeInsertFieldProperty().set("1");
    }

    @Test
    public void operationAddHasCorrectResult() {
        viewModel.addFieldProperty().set("1");
        viewModel.addElementToTree();

        viewModel.findInsertFieldProperty().set("1");
        viewModel.findElementToTree();

        assertTrue(Boolean.parseBoolean(viewModel.resultFindProperty().get()));
    }

    @Test
    public void operationFindHasCorrectResult() {
        viewModel.findInsertFieldProperty().set("1");
        viewModel.findElementToTree();

        assertFalse(Boolean.parseBoolean(viewModel.resultFindProperty().get()));
    }

    @Test
    public void operationRemoveHasCorrectResult() {
        viewModel.addFieldProperty().set("1");
        viewModel.addElementToTree();

        viewModel.removeInsertFieldProperty().set("1");
        viewModel.removeElementToTree();

        assertTrue(Boolean.parseBoolean(viewModel.resultRemoveProperty().get()));
    }
}
