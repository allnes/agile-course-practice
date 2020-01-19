package ru.unn.agile.numberstowords.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModelForm;

    @Before
    public void setUp() {
        viewModelForm = new ViewModel();
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
}
