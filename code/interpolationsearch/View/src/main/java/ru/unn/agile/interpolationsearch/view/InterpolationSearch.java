package ru.unn.agile.interpolationsearch.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.unn.agile.interpolationsearch.viewmodel.ViewModel;

public class InterpolationSearch {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField numberTextField;
    @FXML
    private TextField searchValueTextField;
    @FXML
    private Button insertNumberButton;
    @FXML
    private Button searchButton;
    @FXML
    private ListView<String> listNumbers;
    @FXML
    private Label resultTextArea;

    @FXML
    void initialize() {
        numberTextField.textProperty().bindBidirectional(viewModel.numberProperty());
        searchValueTextField.textProperty().bindBidirectional(viewModel.searchValueProperty());

        insertNumberButton.setOnAction(e -> viewModel.addNumber());
        listNumbers.itemsProperty().bindBidirectional(viewModel.numbersProperty());
        searchButton.setOnAction(e -> viewModel.doSearch());
        resultTextArea.textProperty().bindBidirectional(viewModel.resultProperty());
    }
}
