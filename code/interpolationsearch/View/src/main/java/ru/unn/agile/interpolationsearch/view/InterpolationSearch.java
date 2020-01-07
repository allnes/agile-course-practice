package ru.unn.agile.interpolationsearch.view;

import javafx.beans.property.Property;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.unn.agile.interpolationsearch.viewmodel.ViewModel;

public class InterpolationSearch {
    private final String pointInputTooltip = "Only numbers allowed";

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
        initTextField(numberTextField, viewModel.numberProperty());
        initTextField(searchValueTextField, viewModel.searchValueProperty());

        insertNumberButton.setOnAction(e -> viewModel.addNumber());
        listNumbers.itemsProperty().bindBidirectional(viewModel.numbersProperty());
        searchButton.setOnAction(e -> viewModel.doSearch());
        resultTextArea.textProperty().bindBidirectional(viewModel.resultProperty());
    }

    private void initTextField(final TextField textField,
                               final Property<String> property) {
        textField.textProperty().bindBidirectional(property);
        textField.tooltipProperty().setValue(new Tooltip(pointInputTooltip));
    }
}
