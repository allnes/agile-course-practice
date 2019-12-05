package ru.unn.agile.StatisticsCalculation.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.StatisticsCalculation.model.DiscreteRandomVariable;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private final StringProperty newValue = new SimpleStringProperty();
    private final StringProperty newProbabilitie = new SimpleStringProperty();

    private final StringProperty result = new SimpleStringProperty();
    private final StringProperty operationStatus = new SimpleStringProperty();
    private final StringProperty dataStatus = new SimpleStringProperty();
    private final StringProperty inputDataStatus = new SimpleStringProperty();

    private final BooleanProperty calculationDisabled = new SimpleBooleanProperty();
    private final BooleanProperty deleteDisabled = new SimpleBooleanProperty();
    private final BooleanProperty updateDisabled = new SimpleBooleanProperty();
    private final BooleanProperty enterParameterDisabled = new SimpleBooleanProperty();

    private final ObjectProperty<ObservableList<Operation>> operations =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(Operation.values()));
    private final ObjectProperty<Operation> operation = new SimpleObjectProperty<>();

    private final ObservableList<TableElement> listData = FXCollections.observableArrayList();
    private DiscreteRandomVariable discreteRandomVariable;

    private final List<UpdateDataChangeListener> updateDataChangedListeners = new ArrayList<>();
    // FXML needs default c-tor for binding
    public ViewModel() {
        newValue.set("");
        newProbabilitie.set("");
        result.set("");
        operationStatus.set(OperationStatus.WAITING_DATA.toString());
        dataStatus.set(DataStatus.WAITING.toString());
        inputDataStatus.set(InputDataStatus.WAITING.toString());
        operation.set(null);

        BooleanBinding couldUpdateData = new BooleanBinding() {
            {
                super.bind(newValue, newProbabilitie);
            }
            @Override
            protected boolean computeValue() {
                return updateInputDataStatus() == InputDataStatus.READY;
            }
        };
        updateDisabled.bind(couldUpdateData.not());

        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(operationStatus, dataStatus);
            }
            @Override
            protected boolean computeValue() {
                return updateDataStatus() == DataStatus.READY
                        && updateOperationStatus() == OperationStatus.READY;
            }
        };
        calculationDisabled.bind(couldCalculate.not());

        BooleanBinding couldEnterParameter = new BooleanBinding() {
            {
                super.bind(operationStatus);
            }
            @Override
            protected boolean computeValue() {
                return updateOperationStatus() == OperationStatus.WAITING_PARAMETER;
            }
        };
        enterParameterDisabled.bind(couldEnterParameter);

        final List<StringProperty> fields = new ArrayList<StringProperty>() { {
            add(newValue);
            add(newProbabilitie);
        } };

        for (StringProperty field : fields) {
            final UpdateDataChangeListener listener = new UpdateDataChangeListener();
            field.addListener(listener);
            updateDataChangedListeners.add(listener);
        }
    }

    public StringProperty newValueProperty() {
        return newValue;
    }
    public StringProperty newProbabilitieProperty() {
        return newProbabilitie;
    }

    public StringProperty resultProperty() {
        return result;
    }
    public final String getResult() {
        return result.get();
    }
    public StringProperty operationStatusProperty() {
        return operationStatus;
    }
    public final String getOperationStatus() {
        return operationStatus.get();
    }
    public StringProperty dataStatusProperty() {
        return dataStatus;
    }
    public final String getDataStatus() {
        return dataStatus.get();
    }
    public StringProperty inputDataStatusProperty() {
        return inputDataStatus;
    }
    public final String getInputDataStatus() {
        return inputDataStatus.get();
    }

    public ObservableList<TableElement> getListData() {
        return listData;
    }

    public BooleanProperty calculationDisabledProperty() {
        return calculationDisabled;
    }
    public final boolean isCalculationDisabled() {
        return calculationDisabled.get();
    }
    public BooleanProperty deleteDisabledProperty() {
        return deleteDisabled;
    }
    public final boolean isDeleteDisabled() {
        return deleteDisabled.get();
    }
    public BooleanProperty updateDisabledProperty() {
        return updateDisabled;
    }
    public final boolean isUpdateDisabled() {
        return updateDisabled.get();
    }
    public BooleanProperty enterParameterDisabledProperty() {
        return enterParameterDisabled;
    }
    public final boolean isEnterParameterDisabled() {
        return enterParameterDisabled.get();
    }
    public ObjectProperty<ObservableList<Operation>> operationsProperty() {
        return operations;
    }
    public final ObservableList<Operation> getOperations() {
        return operations.get();
    }
    public ObjectProperty<Operation> operationProperty() {
        return operation;
    }

    public void updateTableElement() {
        inputDataStatus.set(updateInputDataStatus().toString());
        if (updateInputDataStatus() ==  InputDataStatus.READY) {
            listData.add(new TableElement(newValue.getValue(), newProbabilitie.getValue()));
            newValue.set("");
            newProbabilitie.set("");
            inputDataStatus.set(updateInputDataStatus().toString());
        }
        dataStatus.set(updateDataStatus().toString());
        operationStatus.set(updateOperationStatus().toString());
    }

    public void updateTableElement(final int focusedIndex) {
        inputDataStatus.set(updateInputDataStatus().toString());
        if (updateInputDataStatus() ==  InputDataStatus.READY) {
            if (focusedIndex >= 0) {
                listData.set(focusedIndex,
                        new TableElement(newValue.getValue(), newProbabilitie.getValue()));
            } else {
                listData.add(new TableElement(newValue.getValue(), newProbabilitie.getValue()));
            }
            newValue.set("");
            newProbabilitie.set("");
            inputDataStatus.set(updateInputDataStatus().toString());
        }
        dataStatus.set(updateDataStatus().toString());
        operationStatus.set(updateOperationStatus().toString());
    }

    public void deleteTableElement(final int focusedIndex) {
        if (focusedIndex >= 0 && focusedIndex < listData.size()) {
            listData.remove(focusedIndex);
        }
        newValue.set("");
        newProbabilitie.set("");
        inputDataStatus.set(updateInputDataStatus().toString());
        dataStatus.set(updateDataStatus().toString());
        operationStatus.set(updateOperationStatus().toString());
    }

    public void selectElement(final int focusedIndex) {
        newValue.set(listData.get(focusedIndex).getValue());
        newProbabilitie.set(listData.get(focusedIndex).getProbabilitie());
        inputDataStatus.set(updateInputDataStatus().toString());
    }

    public void selectOperation() {
        operationStatus.set(updateOperationStatus().toString());
    }

    private InputDataStatus updateInputDataStatus() {
        InputDataStatus inputDataStatus = InputDataStatus.READY;
        if (newValueProperty().get().isEmpty() || newProbabilitieProperty().get().isEmpty()) {
            inputDataStatus = InputDataStatus.WAITING;
        }
        try {
            if (!newValueProperty().get().isEmpty()) {
                Integer.parseInt(newValueProperty().get());
            }
            if (!newProbabilitieProperty().get().isEmpty()) {
                Double.parseDouble(newProbabilitieProperty().get());
                if (Double.parseDouble(newProbabilitieProperty().get()) > 1.0) {
                    inputDataStatus = InputDataStatus.BAD_FORMAT;
                }
            }
        } catch (NumberFormatException nfe) {
            inputDataStatus = InputDataStatus.BAD_FORMAT;
        }

        return inputDataStatus;
    }

    private DataStatus updateDataStatus() {
        DataStatus dataStatus = DataStatus.READY;
        if (listData.isEmpty()) {
            dataStatus = DataStatus.WAITING;
        } else {
            Number[] values = createArrayValuesFromList();
            Double[] probabilities = createArrayProbabilitiesFromList();
            try {
                discreteRandomVariable = new DiscreteRandomVariable(values, probabilities);
            } catch (IllegalArgumentException exception) {
                dataStatus = DataStatus.BAD_FORMAT;
            }
        }

        return dataStatus;
    }

    private OperationStatus updateOperationStatus() {
        OperationStatus operationStatus = OperationStatus.SUCCESS;
        if (updateDataStatus() != DataStatus.READY) {
            operationStatus = OperationStatus.WAITING_DATA;
        } else {
            if (updateDataStatus() == DataStatus.READY) {
                operationStatus = OperationStatus.WAITING_OPERATION;
            }
            if (operation.get() == Operation.EXPECTED_VALUE
                    || operation.get() == Operation.DISPERSION) {
                operationStatus = OperationStatus.READY;
            }
            if (operation.get() == Operation.CENTRAL_MOMENT
                    || operation.get() == Operation.RAW_MOMENT) {
                operationStatus = OperationStatus.WAITING_PARAMETER;
            }
        }

        return operationStatus;
    }

    private Number[] createArrayValuesFromList() {
        Number[] values = new Number[listData.size()];
        int i = 0;
        for (TableElement element : listData) {
            values[i++] = Integer.parseInt(element.getValue());
        }

        return values;
    }

    private Double[] createArrayProbabilitiesFromList() {
        Double[] probabilities = new Double[listData.size()];
        int i = 0;
        for (TableElement element : listData) {
            probabilities[i++] = Double.parseDouble(element.getProbabilitie());
        }

        return probabilities;
    }

    private class UpdateDataChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            inputDataStatus.set(updateInputDataStatus().toString());
        }
    }
}

enum InputDataStatus {
    WAITING("Enter data"),
    READY("Input data is correct"),
    BAD_FORMAT("Input data error");

    private final String name;
    InputDataStatus(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}

enum DataStatus {
    WAITING("Enter data"),
    READY("Data is correct"),
    BAD_FORMAT("Data normalization error");

    private final String name;
    DataStatus(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}

enum OperationStatus {
    WAITING_OPERATION("Choose an operation"),
    WAITING_DATA("Waiting correct data"),
    READY("Press 'Calculate'"),
    WAITING_PARAMETER("Enter parameter"),
    BAD_FORMAT("Error in parameter"),
    SUCCESS("Success");

    private final String name;
    OperationStatus(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}

