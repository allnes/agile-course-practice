package ru.unn.agile.numberstowords.ViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.numberstowords.viewmodel.FakeLogger;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        FakeLogger logger = new FakeLogger();
        viewModel = new ViewModel(logger);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.numberInputProperty().get());
        assertEquals("", viewModel.textOutputProperty().get());
        assertEquals("", viewModel.statusProperty().get());
    }

    @Test
    public void setErrorMessageWithEmptyInput() {
        viewModel.convert();

        assertEquals(Status.EMPTY_INPUT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void setErrorMessageWithIncorrectInput() {
        viewModel.numberInputProperty().set("qwe");

        viewModel.convert();

        assertEquals(Status.WRONG_INPUT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void convertingHasCorrectResult() {
        viewModel.numberInputProperty().set("15");

        viewModel.convert();

        assertEquals("fifteen", viewModel.textOutputProperty().get());
    }

    @Test
    public void setErrorMessageWithNotInteger() {
        viewModel.numberInputProperty().set("15.5");

        viewModel.convert();

        assertEquals(Status.WRONG_INPUT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void clearErrorMessageWithCorrectInput() {
        viewModel.numberInputProperty().set("wrong");
        viewModel.convert();

        viewModel.numberInputProperty().set("1");
        viewModel.convert();

        assertEquals("", viewModel.statusProperty().get());
    }

    @Test
    public void canCreateViewModelWithLogger() {
        FakeLogger logger = new FakeLogger();
        ViewModel viewModelLogged = new ViewModel(logger);

        assertNotNull(viewModelLogged);
    }
}
