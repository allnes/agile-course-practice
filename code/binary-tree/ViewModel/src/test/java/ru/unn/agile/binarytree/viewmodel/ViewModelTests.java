package ru.unn.agile.binarytree.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    public void setExternalViewModelForStructure(final ViewModel viewModelForm) {
        this.viewModel = viewModelForm;
    }

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new ViewModel(new FakeLogger());
        }
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.addKeyProperty().get());
        assertEquals("", viewModel.addValueProperty().get());
        assertEquals(Status.WAITING.toString(), viewModel.addStatusProperty().get());

        assertEquals("", viewModel.findKeyProperty().get());
        assertEquals("", viewModel.findResultProperty().get());
        assertEquals(Status.WAITING.toString(), viewModel.findStatusProperty().get());

        assertEquals("", viewModel.removeKeyProperty().get());
        assertEquals("", viewModel.removeResultProperty().get());
        assertEquals(Status.WAITING.toString(), viewModel.removeStatusProperty().get());
    }

    @Test
    public void addStatusIsWaitingWhenOnlyKeyInserted() {
        viewModel.addKeyProperty().set("18");
        assertEquals(Status.WAITING.toString(), viewModel.getAddStatus());
    }

    @Test
    public void addStatusIsWaitingWhenOnlyValuesInserted() {
        viewModel.addValueProperty().set("FOO");
        assertEquals(Status.WAITING.toString(), viewModel.getAddStatus());
    }

    @Test
    public void addStatusIsReadyWhenInsertedBoth() {
        viewModel.addKeyProperty().set("18");
        viewModel.addValueProperty().set("FOO");
        assertEquals(Status.READY.toString(), viewModel.getAddStatus());
    }

    @Test
    public void addStatusIsBadWhenInsertKeyNotInteger() {
        viewModel.addKeyProperty().set("PASS");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getAddStatus());
    }

    @Test
    public void findStatusIsReadyWhenIntegerInserted() {
        viewModel.findKeyProperty().set("18");
        assertEquals(Status.READY.toString(), viewModel.getFindStatus());
    }

    @Test
    public void findStatusIsBadWhenNotIntegerInserted() {
        viewModel.findKeyProperty().set("PASS");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getFindStatus());
    }

    @Test
    public void removeStatusIsReadyWhenIntegerInserted() {
        viewModel.removeKeyProperty().set("18");
        assertEquals(Status.READY.toString(), viewModel.getRemoveStatus());
    }

    @Test
    public void removeStatusIsBadWhenNotIntegerInserted() {
        viewModel.removeKeyProperty().set("PASS");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getRemoveStatus());
    }

    @Test
    public void addStatusIsSuccessWhenInputCorrect() {
        viewModel.addKeyProperty().set("2");
        viewModel.addValueProperty().set("PASS");

        viewModel.add();

        assertEquals(Status.SUCCESS.toString(), viewModel.addStatusProperty().get());
    }

    @Test
    public void findStatusIsSuccessWhenInputCorrect() {
        viewModel.findKeyProperty().set("3");

        viewModel.find();

        assertEquals(Status.SUCCESS.toString(), viewModel.findStatusProperty().get());
    }

    @Test
    public void removeStatusIsSuccessWhenInputCorrect() {
        viewModel.removeKeyProperty().set("3");

        viewModel.remove();

        assertEquals(Status.SUCCESS.toString(), viewModel.removeStatusProperty().get());
    }

    @Test
    public void findResultIsNullForNonAddedKey() {
        viewModel.findKeyProperty().set("3");

        viewModel.find();

        assertEquals(">NOT FOUND<", viewModel.findResultProperty().get());
    }

    @Test
    public void findResultIsExpectedForAddedKey() {
        final String key = "3";
        final String value = "PASS";

        viewModel.addKeyProperty().set(key);
        viewModel.addValueProperty().set(value);

        viewModel.add();

        viewModel.findKeyProperty().set(key);

        viewModel.find();

        assertEquals(value, viewModel.findResultProperty().get());
    }

    @Test
    public void removeResultIsNullForNonAddedKey() {
        viewModel.removeKeyProperty().set("3");

        viewModel.remove();

        assertFalse(Boolean.parseBoolean(viewModel.removeResultProperty().get()));
    }

    @Test
    public void removeResultIsExpectedForAddedKey() {
        final String key = "3";

        viewModel.addKeyProperty().set(key);
        viewModel.addValueProperty().set("PASS");

        viewModel.add();

        viewModel.removeKeyProperty().set(key);

        viewModel.remove();

        assertTrue(Boolean.parseBoolean(viewModel.removeResultProperty().get()));
    }

    @Test
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
        try {
            new ViewModel(null);
            fail("Exception wasn't thrown");
        } catch (IllegalArgumentException ex) {
            assertEquals("Logger parameter can't be null", ex.getMessage());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }
}
