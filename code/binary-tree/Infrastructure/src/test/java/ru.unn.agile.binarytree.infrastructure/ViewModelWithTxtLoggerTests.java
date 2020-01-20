package ru.unn.agile.binarytree.infrastructure;

import ru.unn.agile.binarytree.viewmodel.ViewModel;
import ru.unn.agile.binarytree.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        BinaryTreeTxtLogger realLogger =
            new BinaryTreeTxtLogger("./ViewModel_with_TxtLogger_Tests-lab3.log");
        super.setExternalViewModel(new ViewModel(realLogger));
    }
}
