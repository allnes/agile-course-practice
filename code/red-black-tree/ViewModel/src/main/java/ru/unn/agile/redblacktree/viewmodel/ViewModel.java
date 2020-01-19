package ru.unn.agile.redblacktree.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import ru.unn.agile.redblacktree.model.RedBlackTree;

public class ViewModel {
    private final StringProperty add_field = new SimpleStringProperty();
    private final StringProperty is_empty_field = new SimpleStringProperty();
    private final StringProperty get_size_field = new SimpleStringProperty();
    private final StringProperty minimum_field = new SimpleStringProperty();
    private final StringProperty find_insert_field = new SimpleStringProperty();
    private final StringProperty find_result_field = new SimpleStringProperty();
    private final StringProperty remove_insert_field = new SimpleStringProperty();
    private final StringProperty remove_result_field = new SimpleStringProperty();
    private RedBlackTree rb_tree = new RedBlackTree();

    public ViewModel() {
        add_field.set("");
        is_empty_field.set("");
        get_size_field.set("");
        minimum_field.set("");
        find_insert_field.set("");
        find_result_field.set("");
        remove_insert_field.set("");
        remove_result_field.set("");
    }

    public StringProperty addFieldProperty() {
        return add_field;
    }
    public StringProperty isEmptyFieldProperty() {
        return is_empty_field;
    }
    public StringProperty getSizeFieldProperty() {
        return get_size_field;
    }
    public StringProperty minimumFieldProperty() {
        return minimum_field;
    }
    public StringProperty findInsertFieldProperty() {
        return find_insert_field;
    }
    public StringProperty findResultFieldProperty() {
        return find_result_field;
    }
    public StringProperty removeInsertFieldProperty() {
        return remove_insert_field;
    }
    public StringProperty removeResultFieldProperty() {
        return remove_result_field;
    }
}
