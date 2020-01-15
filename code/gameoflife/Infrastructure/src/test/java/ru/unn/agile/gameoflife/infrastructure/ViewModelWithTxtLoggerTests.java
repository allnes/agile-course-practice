package ru.unn.agile.gameoflife.infrastructure;

import ru.unn.agile.gameoflife.viewmodel.ViewModel;
import ru.unn.agile.gameoflife.viewmodel.ViewModelTest;

public class ViewModelWithTxtLoggerTests extends ViewModelTest {
    @Override
    public void setUp() {
        TxtLogger logger =
                new TxtLogger("./ViewModelWithTxtLoggerTests.log");
        super.setViewModel(new ViewModel(logger));
    }
}
