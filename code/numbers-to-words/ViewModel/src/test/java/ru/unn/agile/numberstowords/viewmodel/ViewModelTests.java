package ru.unn.agile.numberstowords.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModelForm;

    protected void setViewModelForm(final ViewModel viewModelForm) {
        this.viewModelForm = viewModelForm;
    }

    @Before
    public void setUp() {
        FakeLogger logger = new FakeLogger();
        viewModelForm = new ViewModel(logger);
    }

    @After
    public void tearDown() {
        viewModelForm = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModelForm.numberInputProperty().get());
        assertEquals("", viewModelForm.textOutputProperty().get());
        assertEquals("", viewModelForm.statusProperty().get());
    }

    @Test
    public void setErrorMessageWithEmptyInput() {
        viewModelForm.convert();

        assertEquals(Status.EMPTY_INPUT.toString(), viewModelForm.statusProperty().get());
    }

    @Test
    public void setErrorMessageWithIncorrectInput() {
        viewModelForm.numberInputProperty().set("qwe");

        viewModelForm.convert();

        assertEquals(Status.WRONG_INPUT.toString(), viewModelForm.statusProperty().get());
    }

    @Test
    public void convertingHasCorrectResult() {
        viewModelForm.numberInputProperty().set("15");

        viewModelForm.convert();

        assertEquals("fifteen", viewModelForm.textOutputProperty().get());
    }

    @Test
    public void setErrorMessageWithNotInteger() {
        viewModelForm.numberInputProperty().set("15.5");

        viewModelForm.convert();

        assertEquals(Status.WRONG_INPUT.toString(), viewModelForm.statusProperty().get());
    }

    @Test
    public void clearErrorMessageWithCorrectInput() {
        viewModelForm.numberInputProperty().set("wrong");
        viewModelForm.convert();

        viewModelForm.numberInputProperty().set("1");
        viewModelForm.convert();

        assertEquals("", viewModelForm.statusProperty().get());
    }

    @Test
    public void canCreateViewModelWithLogger() {
        FakeLogger logger = new FakeLogger();
        ViewModel viewModelLogged = new ViewModel(logger);

        assertNotNull(viewModelLogged);
    }

    @Test
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
        try {
            new ViewModel(null);
            fail("Exception wasn't thrown");
        } catch (IllegalArgumentException ex) {
            assertEquals("Logger can't be null", ex.getMessage());
        } catch (Exception ex) {
            fail("Invalid exception type when logger is null");
        }
    }

    @Test
    public void logIsEmptyInTheBeginning() {
        List<String> log = viewModelForm.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void convertingIsLogged() {
        viewModelForm.convert();
        List<String> log = viewModelForm.getLog();

        assertNotEquals(0, log.size());
    }

    @Test
    public void logContainsPressInformation() {
        viewModelForm.convert();
        String message = viewModelForm.getLog().get(0);

        assertTrue(message.matches(".*"
                + LogMessages.CONVERT_WAS_PRESSED.toString() + ".*"));
    }

    @Test
    public void logContainsInputInformation() {
        viewModelForm.numberInputProperty().set("42");
        viewModelForm.convert();
        String message = viewModelForm.getLog().get(0);

        assertTrue(message.matches(".*" + "Input: \"42\"." + ".*"));
    }

    @Test
    public void canPutSeveralMessages() {
        viewModelForm.convert();
        viewModelForm.convert();
        viewModelForm.convert();
        List<String> log = viewModelForm.getLog();

        assertEquals(6, log.size());
    }

    @Test
    public void logContainsConvertingEmptyInputInformation() {
        viewModelForm.convert();
        String message = viewModelForm.getLog().get(viewModelForm.getLog().size() - 1);

        assertTrue(message.matches(".*"
                + LogMessages.EMPTY_INPUT.toString() + ".*"));
    }

    @Test
    public void logContainsConvertingWrongInputInformation() {
        viewModelForm.numberInputProperty().set("wrong");
        viewModelForm.convert();
        String message = viewModelForm.getLog().get(viewModelForm.getLog().size() - 1);

        assertTrue(message.matches(".*"
                + LogMessages.WRONG_INPUT.toString() + ".*"));
    }

    @Test
    public void logContainsConvertingCorrectInputInformation() {
        viewModelForm.numberInputProperty().set("42");
        viewModelForm.convert();
        String message = viewModelForm.getLog().get(viewModelForm.getLog().size() - 1);

        assertTrue(message.matches(".*"
                + LogMessages.CONVERT_WAS_COMPLETED.toString() + ".*"));
    }
}
