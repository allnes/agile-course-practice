package ru.unn.agile.redblacktree.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import ru.unn.agile.redblacktree.model.RedBlackTree;

public class ViewModel {
    private final StringProperty addField = new SimpleStringProperty();
    private final StringProperty isEmptyField = new SimpleStringProperty();
    private final StringProperty getSizeField = new SimpleStringProperty();
    private final StringProperty minimumField = new SimpleStringProperty();
    private final StringProperty findInsertField = new SimpleStringProperty();
    private final StringProperty findResultField = new SimpleStringProperty();
    private final StringProperty removeInsertField = new SimpleStringProperty();
    private final StringProperty removeResultField = new SimpleStringProperty();
    private RedBlackTree rbTree = new RedBlackTree();

    public ViewModel() {
        addField.set("");
        isEmptyField.set("");
        getSizeField.set("");
        minimumField.set("");
        findInsertField.set("");
        findResultField.set("");
        removeInsertField.set("");
        removeResultField.set("");
    }

    public StringProperty addFieldProperty() {
        return addField;
    }
    public StringProperty isEmptyFieldProperty() {
        return isEmptyField;
    }
    public StringProperty getSizeFieldProperty() {
        return getSizeField;
    }
    public StringProperty minimumFieldProperty() {
        return minimumField;
    }
    public StringProperty findInsertFieldProperty() {
        return findInsertField;
    }
    public StringProperty findResultFieldProperty() {
        return findResultField;
    }
    public StringProperty removeInsertFieldProperty() {
        return removeInsertField;
    }
    public StringProperty removeResultFieldProperty() {
        return removeResultField;
    }
}
