package ru.unn.agile.numberstowords.Infrastructure;

import ru.unn.agile.numberstowords.ViewModel.ILogger;

import java.util.List;

public class TxtLogger implements ILogger {
    private final String filename;


    public TxtLogger(final String filename) {
        this.filename = filename;
    }

    @Override
    public void log(final String s) {
    }

    @Override
    public List<String> getLog() {
        return null;
    }
}
