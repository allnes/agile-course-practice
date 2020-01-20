package ru.unn.agile.binarytree.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.unn.agile.binarytree.viewmodel.ViewModel;
import ru.unn.agile.binarytree.infrastructure.BinaryTreeTxtLogger;

public class BinaryTree {
    @FXML
    private ViewModel viewModel;

    @FXML
    private TextField txtFindKey;
    @FXML
    private TextField txtAddKey;
    @FXML
    private TextField txtAddValue;
    @FXML
    private TextField txtRemoveKey;
    @FXML
    private Button btnFind;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;

    @FXML
    void initialize() {
        viewModel = new ViewModel(new BinaryTreeTxtLogger("./binary_tree.log"));
        txtFindKey.textProperty().bindBidirectional(viewModel.findKeyProperty());
        txtAddKey.textProperty().bindBidirectional(viewModel.addKeyProperty());
        txtAddValue.textProperty().bindBidirectional(viewModel.addValueProperty());
        txtRemoveKey.textProperty().bindBidirectional(viewModel.removeKeyProperty());

        btnFind.setOnAction(event -> viewModel.find());
        btnAdd.setOnAction(event -> viewModel.add());
        btnRemove.setOnAction(event -> viewModel.remove());
    }
}
