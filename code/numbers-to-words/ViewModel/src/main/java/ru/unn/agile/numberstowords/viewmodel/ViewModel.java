package ru.unn.agile.numberstowords.ViewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.unn.agile.numberstowords.model.NumbersToWordsConverter;

import java.util.List;


public class ViewModel {
    private final StringProperty numberInput = new SimpleStringProperty();
    private final StringProperty textOutput = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final ILogger logger;

    public ViewModel(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }

        this.logger = logger;
        numberInput.set("");
        textOutput.set("");
        status.set("");
    }

    public StringProperty numberInputProperty() {
        return numberInput;
    }

    public StringProperty textOutputProperty() {
        return textOutput;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public List<String> getLog() {
        return logger.getLog();
    }

    private static boolean isNumeric(final String strNum) {
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private String convertLogMessage() {
        return LogMessages.CONVERT_WAS_PRESSED
                + "Input: \"" + numberInput.get() + "\".";
    }

    public void convert() {
        logger.log(convertLogMessage());

        if (numberInput.get().equals("")) {
            status.set(Status.EMPTY_INPUT.toString());
            logger.log(LogMessages.EMPTY_INPUT.toString());
        } else if (!isNumeric(numberInput.get())) {
            status.set(Status.WRONG_INPUT.toString());
            logger.log(LogMessages.WRONG_INPUT.toString());
        } else {
            int number = Integer.parseInt(numberInput.get());
            status.set("");
            textOutput.set(NumbersToWordsConverter.toWord(number));
            logger.log(LogMessages.CONVERT_WAS_COMPLETED.toString());
        }
    }
}

enum Status {
    EMPTY_INPUT("Please provide input data"),
    WRONG_INPUT("Please provide correct input data");

    private final String name;
    Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}

enum LogMessages {
    CONVERT_WAS_PRESSED("Convert was pressed."),
    CONVERT_WAS_COMPLETED("The conversion was completed."),
    EMPTY_INPUT("It is impossible to convert. Reason: empty input."),
    WRONG_INPUT("It is impossible to convert. Reason: wrong input.");

    private final String name;
    LogMessages(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
