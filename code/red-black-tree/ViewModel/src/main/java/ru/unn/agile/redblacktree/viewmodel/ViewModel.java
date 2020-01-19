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
    private final StringProperty findInsertField = new SimpleStringProperty();
    private final StringProperty removeInsertField = new SimpleStringProperty();
    private RedBlackTree rbTree = new RedBlackTree();

    private final BooleanProperty addElementToTreeDisabled = new SimpleBooleanProperty();
    private final BooleanProperty findElementToTreeDisabled = new SimpleBooleanProperty();
    private final BooleanProperty removeElementToTreeDisabled = new SimpleBooleanProperty();

    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();
    private final StringProperty resultFind = new SimpleStringProperty();
    private final StringProperty resultRemove = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    public BooleanProperty addElementToTreeDisabledProperty() {
        return addElementToTreeDisabled;
    }
    public final boolean isAddElementToTreeDisabled() {
        return addElementToTreeDisabled.get();
    }
    public BooleanProperty findElementToTreeDisabledProperty() {
        return findElementToTreeDisabled;
    }
    public final boolean isFindElementToTreeDisabled() {
        return findElementToTreeDisabled.get();
    }
    public BooleanProperty removeElementToTreeDisabledProperty() {
        return removeElementToTreeDisabled;
    }
    public final boolean isRemoveElementToTreeDisabled() {
        return removeElementToTreeDisabled.get();
    }

    public StringProperty resultFindProperty() {
        return resultFind;
    }
    public final String getResultFind() {
        return resultFind.get();
    }
    public StringProperty resultRemoveProperty() {
        return resultRemove;
    }
    public final String getResultRemove() {
        return resultRemove.get();
    }

    public ViewModel() {
        addField.set("");
        findInsertField.set("");
        removeInsertField.set("");
        resultFind.set("");
        resultRemove.set("");

        status.set(Status.WAITING.toString());

        BooleanBinding couldAddElementToTree = new BooleanBinding() {
            {
                super.bind(addField);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        addElementToTreeDisabled.bind(couldAddElementToTree.not());

        BooleanBinding couldFindElementToTree = new BooleanBinding() {
            {
                super.bind(findInsertField);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        findElementToTreeDisabled.bind(couldFindElementToTree.not());

        BooleanBinding couldRemoveElementToTree = new BooleanBinding() {
            {
                super.bind(removeInsertField);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        removeElementToTreeDisabled.bind(couldRemoveElementToTree.not());

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
    public StringProperty findInsertFieldProperty() {
        return findInsertField;
    }
    public StringProperty removeInsertFieldProperty() {
        return removeInsertField;
    }

    public StringProperty statusProperty() {
        return status;
    }
    public final String getStatus() {
        return status.get();
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        if (addField.get().isEmpty() &&
                findInsertField.get().isEmpty() &&
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

    public void findElementToTree() {
        if (findElementToTreeDisabled.get()) {
            return;
        }
        boolean answer = rbTree.find(Integer.parseInt(findInsertField.get()));
        resultFind.set(Boolean.toString(answer));
        status.set(Status.SUCCESS.toString());
    }

    public void removeElementToTree() {
        if (removeElementToTreeDisabled.get()) {
            return;
        }
        boolean answer = rbTree.remove(Integer.parseInt(removeInsertField.get()));
        resultRemove.set(Boolean.toString(answer));
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