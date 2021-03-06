package ru.unn.agile.gameoflife.viewmodel;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.gameoflife.model.GameOfLife;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private final SimpleStringProperty heightField = new SimpleStringProperty();
    private final SimpleStringProperty widthField = new SimpleStringProperty();
    private final SimpleStringProperty statusText = new SimpleStringProperty();
    private final SimpleBooleanProperty couldNotCreate = new SimpleBooleanProperty();
    private final SimpleBooleanProperty couldNotGetNextStep = new SimpleBooleanProperty();
    private final StringProperty logsField = new SimpleStringProperty();

    private ILogger logger;

    private GameOfLife gameOfLife;
    private char[][] gridArray;

    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger value can not be null");
        }
        this.logger = logger;
    }

    public ViewModel() {
        init();
    }

    public ViewModel(final ILogger logger) {
        setLogger(logger);
        init();
    }

    private void init() {
        statusText.set(Status.WAITING.toString());
        couldNotGetNextStep.set(true);
        couldNotCreate.set(true);
        heightField.set("");
        widthField.set("");

        final List<StringProperty> fields = new ArrayList<StringProperty>() { {
            add(heightField);
            add(widthField);
        } };

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public StringProperty logsFieldProperty() {
        return logsField;
    }

    public final String getLogsField() {
        return logsField.get();
    }

    public final List<String> getLog() {
        return logger.getLog();
    }

    public SimpleStringProperty heightFieldProperty() {
        return heightField;
    }

    public SimpleStringProperty widthFieldProperty() {
        return widthField;
    }

    public SimpleStringProperty statusTextProperty() {
        return statusText;
    }

    public SimpleBooleanProperty couldNotCreateProperty() {
        return couldNotCreate;
    }

    public SimpleBooleanProperty couldNotGetNextStepProperty() {
        return couldNotGetNextStep;
    }

    public char[][] gridArrayProperty() {
        return gridArray;
    }

    public void createGrid() {
        if (!couldNotCreate.get()) {
            gameOfLife = new GameOfLife(Integer.parseInt(widthField.get()),
                                        Integer.parseInt(heightField.get()));

            gridArray = gameOfLife.getGrid().clone();
            statusText.set(Status.INITIALISE.toString());
            StringBuilder message = new StringBuilder(LogMessages.CREATE_WAS_PRESSED);
            message.append("Size: height = ").append(heightField.get())
                    .append("; width = ").append(widthField.get());
            logger.log(message.toString());
            updateLogs();
        }
    }

    private void gameOverCheck() {
        int width = Integer.parseInt(widthField.get());
        int height = Integer.parseInt(heightField.get());

        boolean isGridEmpty = true;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (gridArray[y][x] == '*') {
                    isGridEmpty = false;
                    break;
                }
            }
        }

        if (isGridEmpty) {
            statusText.set(Status.INITIALISE.toString());
            couldNotGetNextStep.set(true);
        }
    }

    public void changeCellStatus(final int y, final int x) {
        if (gridArray[y][x] == '.') {
            gameOfLife.setCell(y, x);
            gridArray = gameOfLife.getGrid().clone();
            statusText.set(Status.GAMING.toString());

            StringBuilder message = new StringBuilder(LogMessages.CELL_WAS_CHANGED);
            message.append("Cell at ").append(y)
                    .append(", ").append(x)
                    .append(" now is alive");
            logger.log(message.toString());

            couldNotGetNextStep.set(false);
        } else {
            gameOfLife.deleteCell(y, x);
            gameOverCheck();

            StringBuilder message = new StringBuilder(LogMessages.CELL_WAS_CHANGED);
            message.append("Cell at ").append(y)
                    .append(", ").append(x)
                    .append(" now is dead");
            logger.log(message.toString());
        }
        updateLogs();
        gridArray = gameOfLife.getGrid().clone();
    }

    public void getNextStep() {
        gameOfLife.makeTurn();
        gridArray = gameOfLife.getGrid().clone();
        gameOverCheck();

        StringBuilder message = new StringBuilder(LogMessages.NEXT_WAS_PRESSED);
        logger.log(message.toString());
        updateLogs();
    }

    private void updateLogs() {
        List<String> fullLog = logger.getLog();
        String newRecord = new String("");
        for (String log : fullLog) {
            newRecord += log + "\n";
        }
        logsField.set(newRecord);
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        couldNotCreate.set(false);
        if (widthField.get().isEmpty() || heightField.get().isEmpty()) {
            inputStatus = Status.WAITING;
            couldNotCreate.set(true);
        }
        try {
            if (!widthField.get().isEmpty()) {
                if (Integer.parseInt(widthField.get()) <= 0) {
                    inputStatus = Status.IVALID_INPUT;
                    couldNotCreate.set(true);
                }
            }
            if (!heightField.get().isEmpty()) {
                if (Integer.parseInt(heightField.get()) <= 0) {
                    inputStatus = Status.IVALID_INPUT;
                    couldNotCreate.set(true);
                }
            }
        } catch (NumberFormatException nfe) {
            inputStatus = Status.BAD_FORMAT;
            couldNotCreate.set(true);
        }

        return inputStatus;
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            statusText.set(getInputStatus().toString());
        }
    }
}

enum Status {
    WAITING("Please enter height and width of the field."),
    READY("Press 'Create' to create the field."),
    INITIALISE("Please select 'live' cells."),
    GAMING("Press 'Next' to make the game step or add live cells."),
    BAD_FORMAT("Bad format."),
    IVALID_INPUT("Height and width should be positive.");

    private final String name;
    Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}

final class LogMessages {
    public static final String CREATE_WAS_PRESSED = "Create grid. ";
    public static final String NEXT_WAS_PRESSED = "Next generation. ";
    public static final String CELL_WAS_CHANGED = "Updated cell. ";

    private LogMessages() { }
}
