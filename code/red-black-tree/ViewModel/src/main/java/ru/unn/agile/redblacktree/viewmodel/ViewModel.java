package ru.unn.agile.redblacktree.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.redblacktree.model.RedBlackTree;

import java.util.ArrayList;
import java.util.List;

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

    private final BooleanProperty addElementToTreeDisabled = new SimpleBooleanProperty();

    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();
    private final StringProperty status = new SimpleStringProperty();

    public ViewModel() {
        addField.set("");
        isEmptyField.set("");
        getSizeField.set("");
        minimumField.set("");
        findInsertField.set("");
        findResultField.set("");
        removeInsertField.set("");
        removeResultField.set("");

        status.set(Status.WAITING.toString());

        BooleanBinding couldAddElementToTree = new BooleanBinding() {
            {
                super.bind(addField, findInsertField, removeInsertField);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        addElementToTreeDisabled.bind(couldAddElementToTree.not());

        // Add listeners to the input text fields
        final List<StringProperty> fields = new ArrayList<StringProperty>() { {
            add(addField);
            add(findInsertField);
            add(removeInsertField);
        } };

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
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

    public StringProperty statusProperty() {
        return status;
    }
    public final String getStatus() {
        return status.get();
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        if (addField.get().isEmpty() ||
                findInsertField.get().isEmpty() ||
                removeInsertField.get().isEmpty()) {
            inputStatus = Status.WAITING;
        }
        try {
            if (!addField.get().isEmpty()) {
                Integer.parseInt(addField.get());
            }
            if (!findInsertField.get().isEmpty()) {
                Integer.parseInt(findInsertField.get());
            }
            if (!removeInsertField.get().isEmpty()) {
                Integer.parseInt(removeInsertField.get());
            }
        } catch (NumberFormatException nfe) {
            inputStatus = Status.BAD_FORMAT;
        }
        return inputStatus;
    }

    public void addElementToTree() {
        if (addElementToTreeDisabled.get()) {
            return;
        }
        rbTree.insert(Integer.parseInt(addField.get()));
        status.set(Status.SUCCESS.toString());
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            status.set(getInputStatus().toString());
        }
    }
}

enum Status {
    WAITING("Please provide input data"),
    READY("Press button"),
    BAD_FORMAT("Bad format"),
    SUCCESS("Success");

    private final String name;
    Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}