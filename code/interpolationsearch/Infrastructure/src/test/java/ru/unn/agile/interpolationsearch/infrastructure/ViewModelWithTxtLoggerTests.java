package ru.unn.agile.interpolationsearch.infrastructure;

import ru.unn.agile.interpolationsearch.viewmodel.ViewModel;
import ru.unn.agile.interpolationsearch.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger = new TxtLogger("./ViewModelWithTxtLoggerTests.log");
        super.setOuterViewModel(new ViewModel(realLogger));
    }
}
